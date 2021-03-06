package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.ClassHelper;
import com.razrabotkin.systematics.Helpers.ParseHelper;
import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Выражение
 */
public class ExpressionModel extends FormulaModel{

    /** Члены */
    public ArrayList<TermModel> Terms;

    /** Операция отношения, стоящая перед выражением, если оно является частью уравнения */
    private RelOpModel Relation;

    /** Признак того, что данное выражение является результатом решения задачи, идальше его решать не надо */
    private boolean IsResult;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     */
    public ExpressionModel(FormulaModel parent){
        super(parent);
        Terms = new ArrayList<TermModel>();
        Relation = RelOpModel.None;
        IsResult = false;
    }

    public ExpressionModel(EquationModel parent, String name) {
        this(parent);
        setName(name);
    }

    public ExpressionModel(EquationModel parent, String name, String index) {
        this(parent);
        setName(name, index);
    }

    /**
     * Определяет, нужно ли брать данное выражение в скобки в методе toString()
     * @return Истина, если данное выражение нужно брать в скобки в методе toString(), иначе ложь
     */
    private boolean needParenthesis(){
        // Выражение нужно брать в скобки в следующих случаях:
        // 1. Когда оно не единственное в массиве множителей члена
        // 2. Когда оно не единственное в массиве членов родительского выражения
        ClassHelper helper = new ClassHelper();

        if (Parent != null) {
            if (helper.isTypeOf(Parent, AtomModel.class)) {
                AtomModel atom = (AtomModel) Parent;
                SignedAtomModel signedAtom = (SignedAtomModel) atom.getParent();
                FactorModel factor = (FactorModel) signedAtom.getParent();
                TermModel term = (TermModel) factor.getParent();

                if (term.Factors.size() > 1) {
                    return true;
                }

                ExpressionModel expression = (ExpressionModel) term.getParent();

                if (expression.Terms.size() > 1) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String str = "";

        if (needParenthesis()){
            str += "(";
        }

        for (int i = 0; i < Terms.size(); i++){
            TermModel termModel = Terms.get(i);

            if (termModel.getMathOperation().equals(MathOpModel.Plus)){
                str+="+";
            } else if (termModel.getMathOperation().equals(MathOpModel.Minus)){
                str+="-";
            }

            str += termModel.toString();
        }

        if (needParenthesis()){
            str += ")";
        }

        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {

        // Создаём ArrayList для возврата из этого метода
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        // Перебираем члены
        for (TermModel termModel : Terms){

            // Создаём JAXBElement со знаком для подставления в произведение
            org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
            org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
            CTR r = mathObjectFactory.createCTR();
            JAXBElement<org.docx4j.math.CTR> rWrappedSign = mathObjectFactory.createCTOMathR(r);
            // Create object for rPr (wrapped in JAXBElement)
            RPr rpr = wmlObjectFactory.createRPr();
            JAXBElement<RPr> rprWrapped = wmlObjectFactory.createSdtPrRPr(rpr);
            r.getContent().add( rprWrapped);
            // Create object for rFonts
            RFonts rfonts2 = wmlObjectFactory.createRFonts();
            rpr.setRFonts(rfonts2);
            rfonts2.setAscii( "Cambria Math");
            rfonts2.setCs( "Times New Roman");
            rfonts2.setHAnsi( "Cambria Math");
            // Create object for lang
            CTLanguage language2 = wmlObjectFactory.createCTLanguage();
            rpr.setLang(language2);
            language2.setVal( "en-US");
            // Create object for sz
            HpsMeasure hpsmeasure3 = wmlObjectFactory.createHpsMeasure();
            rpr.setSz(hpsmeasure3);
            hpsmeasure3.setVal( BigInteger.valueOf( 28) );
            // Create object for szCs
            HpsMeasure hpsmeasure4 = wmlObjectFactory.createHpsMeasure();
            rpr.setSzCs(hpsmeasure4);
            hpsmeasure4.setVal( BigInteger.valueOf( 28) );

            // В зависимости от знака, стоящего перед членом, добавляем соответствующий в ArrayList, который будет записан в документ
            if (termModel.getMathOperation().equals(MathOpModel.Plus)){
                Text text = wmlObjectFactory.createText();
                text.setValue("+");
                r.getContent().add(text);
            } else if (termModel.getMathOperation().equals(MathOpModel.Minus)){
                Text text = wmlObjectFactory.createText();
                text.setValue("-");
                r.getContent().add(text);
            }

            arrayList.add(rWrappedSign);

            // Теперь добавляем в массива все элементы массива, возвращенного методом toOpenXML() множителя
            for (JAXBElement rWrapped : termModel.toOpenXML()){
                arrayList.add(rWrapped);
            }
        }

        return arrayList;
    }

    @Override
    public boolean isNumber() {
        // Выражение является числом тогда, когда оно состоит только из одного члена, и этот член является числом
        return Terms.size() == 1 && Terms.get(0).isNumber();
    }

    @Override
    public boolean isVariable() {
        return Terms.size() == 1 && Terms.get(0).isVariable();
    }

    @Override
    public boolean isResult() {
        for (TermModel term : Terms){
            if (!term.isResult()){
                return false;
            }
        }

        return true;
    }

    /**
     * Копирует данное выражение путём создания нового экземпляра данного класса с тем же родителем.
     * Все данные, хранящиеся в данном выражении, рекурсивно копируются аналогичным способом
     * @param parent Родитель
     * @return Скопированное выражение
     */
    public FormulaModel copy(FormulaModel parent) {
        ExpressionModel expression = new ExpressionModel(parent);
        expression.setRelation(Relation);

        for (TermModel term : Terms){
            expression.Terms.add((TermModel) term.copy(expression));
        }

        return expression;
    }

    /**
     * Копирует данное выражение путём парсинга его строкового представления
     * @param parent Родитель
     * @return Скопированное выражение
     */
    public ExpressionModel copyParse(FormulaModel parent){
        com.razrabotkin.systematics.Helpers.ParseHelper parseHelper = new ParseHelper();
        ExpressionModel resultExpression = parseHelper.parseExpression(this.toString());
        resultExpression.setParent(parent);
        return resultExpression;
    }

    public boolean canSolve() {
        boolean canSolve = true;

        // Если все члены выражения являются числами, его можно решить
        for (TermModel term : Terms){
            if (!term.isNumber()){
                canSolve = false;
                break;
            }
        }

        return canSolve;
    }

    /**
     * Определяет, является ли хотя бы один член выражения нулём
     * @return Истина, если хотя бы один член выражения является нулём, иначе ложь
     */
    private boolean hasZeros() {
        for (TermModel term : Terms){
            if (term.isNumber() && term.getValue() == 0){
                return true;
            }
        }

        return false;
    }

    public void solve() {
        if (hasZeros()){
            removeZeros();
        }

        if(canSolve()){
            double result = Terms.get(0).getValue();

            for(int i = 1; i < Terms.size(); i++){
                TermModel term = Terms.get(i);
                if (term.getMathOperation().equals(MathOpModel.Plus)) {
                    result += term.getValue();
                } else if (term.getMathOperation().equals(MathOpModel.Minus)) {
                    result -= term.getValue();
                }
            }

            setValue(result);
        } else {
            for(TermModel term : Terms){
                term.solve();
            }
        }
    }

    /**
     * Удаляет нулевые члены из выражения
     */
    private void removeZeros() {
        for (int i = Terms.size() - 1; i >= 0; i--){
            TermModel term = Terms.get(i);

            if (term.isNumber() && term.getValue() == 0){
                Terms.remove(term);
            }
        }
    }

    public double getValue() {
        if (isNumber()){
            return Terms.get(0).getValue();
        } else {
            System.out.println("Не удаётся получить значение, так как выражение не является числом");
            return 0;
        }
    }

    protected void setValue(double value) {
        Terms.clear();
        Terms.add(new TermModel(this, value));
    }

    public void setName(String name) {
        Terms.clear();
        Terms.add(new TermModel(this, name));
    }

    protected void setName(String name, String index) {
        Terms.clear();
        Terms.add(new TermModel(this, name, index));
    }

    // Методы-мутаторы
    public RelOpModel getRelation() {
        return Relation;
    }

    public void setRelation(RelOpModel relation) {
        Relation = relation;
    }

//    public boolean isResult() {
//        return IsResult;
//    }
//
//    public void setResult(boolean result) {
//        IsResult = result;
//    }
}
