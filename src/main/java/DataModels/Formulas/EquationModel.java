package DataModels.Formulas;

import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Уравнение
 */
public class EquationModel extends FormulaModel {

    /** Выражения, являющиеся частями данного уравнения */
    public ArrayList<ExpressionModel> Expressions;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     */
    public EquationModel(FormulaModel parent){
        super(parent);
        Expressions = new ArrayList<ExpressionModel>();
    }

    @Override
    public String toString() {
        String str = "";

        for (ExpressionModel expression : Expressions){
            switch (expression.getRelation()) {
                case Equals:
                    str+="=";
                    break;
                case GreaterThan:
                    str+=">";
                    break;
                case LessThan:
                    str+="<";
                    break;
            }

            str+=expression.toString();
        }

        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {

        // Создаём ArrayList для возврата из этого метода
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        for (ExpressionModel expression : Expressions){

            // Добавляем знак перед частью
            if (expression.getRelation() != null){
                // Устанавливаем знак между двумя частями
                // Создаём JAXBElement со знаком для подставления в произведение
                org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
                org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
                CTR r = mathObjectFactory.createCTR();
                JAXBElement<org.docx4j.math.CTR> rWrappedSign = mathObjectFactory.createCTOMathR(r);
                // Create object for rPr (wrapped in JAXBElement)
                RPr rpr = wmlObjectFactory.createRPr();
                JAXBElement<RPr> rprWrapped = wmlObjectFactory.createSdtPrRPr(rpr);
                r.getContent().add(rprWrapped);
                // Create object for rFonts
                RFonts rfonts2 = wmlObjectFactory.createRFonts();
                rpr.setRFonts(rfonts2);
                rfonts2.setAscii("Cambria Math");
                rfonts2.setCs("Times New Roman");
                rfonts2.setHAnsi("Cambria Math");
                // Create object for lang
                CTLanguage language2 = wmlObjectFactory.createCTLanguage();
                rpr.setLang(language2);
                language2.setVal("en-US");
                // Create object for sz
                HpsMeasure hpsmeasure3 = wmlObjectFactory.createHpsMeasure();
                rpr.setSz(hpsmeasure3);
                hpsmeasure3.setVal(BigInteger.valueOf(28));
                // Create object for szCs
                HpsMeasure hpsmeasure4 = wmlObjectFactory.createHpsMeasure();
                rpr.setSzCs(hpsmeasure4);
                hpsmeasure4.setVal(BigInteger.valueOf(28));

                Text text = wmlObjectFactory.createText();

                switch (expression.getRelation()) {
                    case Equals:
                        text.setValue("=");
                        break;
                    case GreaterThan:
                        text.setValue(">");
                        break;
                    case LessThan:
                        text.setValue("<");
                        break;
                }

                r.getContent().add(text);
                arrayList.add(rWrappedSign);
            }

            // Добавляем часть
            for (JAXBElement rWrapped : expression.toOpenXML()) {
                arrayList.add(rWrapped);
            }
        }

        return arrayList;
    }

    public FormulaModel copy(FormulaModel parent) {

        EquationModel equation = new EquationModel(null);

        for (ExpressionModel expression : Expressions){
            equation.Expressions.add((ExpressionModel) expression.copy(this));
        }

        return equation;
    }

    public boolean canSolve() {
        return false;
    }

    public void solve() {

    }

    protected double getValue() {
        return 0;
    }

    protected void setValue(double value) {

    }
}
