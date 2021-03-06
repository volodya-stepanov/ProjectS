package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.DocumentHelper;
import com.razrabotkin.systematics.Helpers.ClassHelper;
import com.razrabotkin.systematics.Helpers.ParseHelper;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Множитель (атом со знаком, возведённый в степень)
 */
public class FactorModel extends FormulaModel {

    /** Основание степени */
    private SignedAtomModel Base;

    /** Показатель степени */
    private SignedAtomModel Exponent;

    /** Математическая операция, которая стоит перед множителем (умножить или разделить) */
    private MathOpModel MathOperation;

    /**
     * Инициализиурет экземпляр класса
     * @param parent Родитель
     */
    public FactorModel(TermModel parent){
        super(parent);
        MathOperation = MathOpModel.None;

        // TODO: Убрать везде проверку показателя степени на null
        // Инициализируем показатель степени единицей
        Exponent = new SignedAtomModel(this);

        AtomModel exponentAtom = new AtomModel(Exponent);
        Exponent.setAtom(exponentAtom);

        NumberModel exponentNumber = new NumberModel(exponentAtom, 1);
        exponentAtom.setExpression(exponentNumber);
    }

    public FactorModel(TermModel parent, double value) {
        this(parent);
        setValue(value);
    }

    public FactorModel(TermModel parent, String name) {
        this(parent);
        setName(name);
    }

    public FactorModel(TermModel parent, String name, String index) {
        this(parent);
        setName(name, index);
    }

    @Override
    public String toString() {
        if (showExponent()){
            if (Base.isNumber()){
                NumberModel number = (NumberModel) Base.getAtom().getExpression();
                if (number.getValue() < 0){
                    return "(" + Base.toString() + ")^" + Exponent.toString();
                }
            }
            return Base.toString() + "^" + Exponent.toString();
        }

        if (needParenthesis()){
            return "(" + Base.toString() + ")";
        }

        return Base.toString();
    }

    /**
     * Определяет, нужно ли брать данный множитель в скобки в методе toString()
     * @return Истина, если данный множитель нужно брать в скобки в методе toString(), иначе ложь
     */
    private boolean needParenthesis(){
        // Множитель нужно брать в скобки в следующих случаях:
        // 1. Когда он не единственный и не первый в массиве множителей члена
        ClassHelper helper = new ClassHelper();

        if (helper.isTypeOf(Parent, AtomModel.class)){
            TermModel term = (TermModel) Parent;

            if (term.Factors.size() > 1 && term.Factors.indexOf(this) != 0){
                return true;
            }
        }

        return false;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        DocumentHelper helper = new DocumentHelper();

        if (showExponent()){
            return helper.createPower(Base, Exponent);
        } else{
            return Base.toOpenXML();
        }
    }

    /**
     * Определяет, нужно ли выводить показатель степени
     * @return Истина, если показатель степени нужно выводить, иначе ложь
     */
    private boolean showExponent(){
        boolean isPositive = false;

        if (Exponent != null) {

            // Определяем, нужно ли ставить минус перед показателем степени
            isPositive = !Exponent.isNegative();

            if (isPositive) {
                // Определяем, является ли показатель степени числом
                ClassHelper classHelper = new ClassHelper();
                Object obj = Exponent.getAtom().getExpression();
                if (classHelper.isTypeOf(obj, NumberModel.class)) {
                    NumberModel numberModel = (NumberModel) Exponent.getAtom().getExpression();

                    // Если показатель степени - единица, выводим только основание
                    if (numberModel.getValue() == 1) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean isNumber() {
        // Множитель является числом тогда, когда основание степени является числом,
        // а показатель степени либо отсутствует, либо тоже является числом и равен единице
        if (Base.isNumber()){
            if (Exponent == null) {
                return true;
            } else if (Exponent.isNumber()){
                NumberModel number = (NumberModel) Exponent.getAtom().getExpression();
                if (number.getValue() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isVariable() {
        // Множитель является числом тогда, когда основание степени является числом,
        // а показатель степени либо отсутствует, либо тоже является числом и равен единице
        if (Base.isVariable()){
            if (Exponent == null) {
                return true;
            } else if (Exponent.isNumber()){
                NumberModel number = (NumberModel) Exponent.getAtom().getExpression();
                if (number.getValue() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isResult() {
        return Base.isResult() && Exponent.isResult();
    }

    public FormulaModel copy(FormulaModel parent) {
        FactorModel factor = new FactorModel((TermModel) parent);

        factor.setMathOperation(MathOperation);

        factor.setBase((SignedAtomModel) Base.copy(factor));

        if (Exponent != null){
            factor.setExponent((SignedAtomModel) Exponent.copy(factor));
        }

        return factor;
    }

    public boolean canSolve() {
        // Множитель можно вычислить, если основание степени является числом,
        // а показатель степени либо отсутствует, либо тоже является числом
        if (Base.isNumber()){
            if (Exponent == null || Exponent.isNumber()){
                return true;
            }
            return false;
        }
        return false;
    }

    public void solve() {
        if(canSolve()) {
            // Получаем значения основания и показателя степени
            double baseValue = Base.getValue();
            double exponentValue = Exponent.getValue();

            // Вычисляем степень и устанавливаем основанию новое значение, а показателю - единицу
            Base.setValue(Math.pow(baseValue, exponentValue));
            Exponent.setValue(1);
        }

        else if (canSimplify()){
            simplify();
        }

        else {
            Base.solve();

            if (Exponent != null){
                Exponent.solve();
            }
        }
    }

    public boolean canSimplify(){
        if (isSquareOfSum()){
            return true;
        }

        return false;
    }

    private boolean isSquareOfSum() {
        AtomModel atom = Base.getAtom();
        ExpressionModel baseExpression = atom.getExpression();

        if (baseExpression.Terms.size() == 2 && Exponent.isNumber() && Exponent.getValue() == 2){
            return true;
        }

        return false;
    }


    public void simplify() {
       if (isSquareOfSum()) {
            simplifySquareOfSum();
        }
    }

    private void simplifySquareOfSum() {
        // Получаем коэффициенты перед членами и название переменной
        double coef1 = 0, coef2 = 0;
        String variableName = "";
        MathOpModel mathOperation = MathOpModel.None;

        AtomModel atom = Base.getAtom();
        ExpressionModel baseExpression = atom.getExpression();

        // Получаем коэффициент перед первым членом
        TermModel term1 = baseExpression.Terms.get(0);
        if (term1.Factors.size() == 2) {
            if (term1.Factors.get(0).isNumber()) {
                coef1 = term1.Factors.get(0).getValue();
            } else {
                System.out.println("Первый член выражения, возводимого в квадрат, состоит из двух множителей, но его первый множитель - не число");
            }

            if (term1.Factors.get(1).isVariable()) {
                variableName = term1.Factors.get(1).getName();
            } else {
                System.out.println("Первый член выражения, возводимого в квадрат, состоит из двух множителей, но его второй множитель - не переменная");
            }
        } else if (term1.Factors.size() == 1) {
            if (term1.Factors.get(0).isVariable()) {
                coef1 = 1;
                variableName = term1.Factors.get(0).getName();
            } else {
                System.out.println("Первый член выражения, возводимого в квадрат, состоит из одного множителя, но это не переменная");
            }
        }

        // Получаем коэффициент перед вторым членом
        TermModel term2 = baseExpression.Terms.get(1);
        if (term2.Factors.get(0).isNumber()) {
            coef2 = term2.Factors.get(0).getValue();

            mathOperation = term2.getMathOperation();
        } else {
            System.out.println("Первый член выражения, возводимого в квадрат, состоит одного множителя, но это не число");
        }

        // Составляем формулу
        ParseHelper parseHelper = new ParseHelper();

        String expressionStr = Math.pow(coef1, 2) + "*" + variableName + "^2";
        if (mathOperation == MathOpModel.Plus){
            expressionStr += "+";
        } else if (mathOperation == MathOpModel.Minus) {
            expressionStr += "-";
        }
        expressionStr += (2 * coef1 * coef2) + "*" + variableName + "+" + Math.pow(coef2, 2);
        ExpressionModel expression = parseHelper.parseExpression(expressionStr);
        expression.setParent(atom);
        atom.setExpression(expression);

        Exponent.setValue(1);
    }

    // Методы-мутаторы
    /**
     * Задаёт основание степени
     * @param base Основание степени
     */
    public void setBase(SignedAtomModel base) {
        Base = base;
    }

    /**
     * Возвращает основание степени
     * @return Основание степени
     */
    public SignedAtomModel getBase() {
        return Base;
    }

    /**
     * Задаёт показатель степени
     * @param exponent Показатель степени
     */
    public void setExponent(SignedAtomModel exponent) {
        Exponent = exponent;
    }

    /**
     * Возвращает показатель степени
     * @return Показатель степени
     */
    public SignedAtomModel getExponent(){
        return Exponent;
    }

    public MathOpModel getMathOperation() {
        return MathOperation;
    }

    public void setMathOperation(MathOpModel mathOperation) {
        MathOperation = mathOperation;
    }

    public double getValue() {
        if (isNumber()){
            return Base.getValue();
        } else {
            System.out.println("Не удаётся получить значение, так как выражение не является числом");
            return 0;
        }
    }

    public void setValue(double value) {
        if (Base == null){
            Base = new SignedAtomModel(this);
        }

        Base.setValue(value);
        Exponent.setValue(1);
    }

    private void setName(String name) {
        if (Base == null){
            Base = new SignedAtomModel(this);
        }

        Base.setName(name);
        Exponent.setValue(1);
    }

    private void setName(String name, String index) {
        if (Base == null){
            Base = new SignedAtomModel(this);
        }

        Base.setName(name, index);
        Exponent.setValue(1);
    }

    public String getName(){
        return Base.getName();
    }
}
