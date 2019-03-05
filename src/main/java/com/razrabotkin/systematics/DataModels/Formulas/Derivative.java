package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.DocumentHelper;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Производная функции
 */
public class Derivative extends ExpressionModel {

    /**
     * Функция, от которой берётся производная
     */
    private ExpressionModel Function;

    /**
     * Инициализирует экземпляр класса
     *
     * @param parent Родитель - формула, в которую входит данная формула
     */
    public Derivative(FormulaModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "(" + Function.toString() + ")'";
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        DocumentHelper helper = new DocumentHelper();

        // Создаём массив элементов со скобками
        ArrayList<JAXBElement> arrayList = helper.createParenthesis(Function.toOpenXML());

        // Добавляем в этот массив символ штриха
        arrayList.add(helper.createRun("'"));

        return arrayList;
    }

    @Override
    public boolean isNumber() {
        // Производная функции никогда не является числом
        return false;
    }

    public FormulaModel copy(FormulaModel parent) {
        Derivative derivative = new Derivative(parent);
        derivative.setFunction((ExpressionModel) Function.copy(derivative));
        return derivative;
    }

    public boolean canSolve() {
        // Константа
        if (Function.isNumber()) {
            return true;
        }

        if (isPowerFunction()
                || isExponentialFunction()
                || isPowerFunction()
                || isSquareRoot()
                || isLogarithm()

        ) {
            return true;
        }

        return false;
    }

    // Методы, определяющие, к какому типу элементарной функции относится функция, от которой мы берём производную

    /**
     * Степенная функция
     *
     * @return Истина, если функция является степенной, иначе ложь
     */
    private boolean isPowerFunction() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);

                if (factor.getExponent().isNumber() && factor.getExponent().getValue() != 1) {
                    SignedAtomModel signedAtom = factor.getBase();
                    AtomModel atom = signedAtom.getAtom();

                    if (ClassHelper.isTypeOf(atom.getExpression(), VariableModel.class)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Показательная функция
     *
     * @return Истина, если функция является показательной, иначе ложь
     */
    private boolean isExponentialFunction() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);

                if (factor.getBase().isNumber()) {
                    SignedAtomModel signedAtom = factor.getExponent();
                    AtomModel atom = signedAtom.getAtom();

                    if (ClassHelper.isTypeOf(atom.getExpression(), VariableModel.class)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean isLogarithm() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), Logarithm.class)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Квадратный корень
     *
     * @return Истина, если функция является квадратным корнем, иначе ложь
     */
    private boolean isSquareRoot() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getExponent();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), SquareRoot.class)) {
                    return true;
                }
            }
        }

        return false;
    }















    public void solve() {
        if (canSolve()) {
            if (Function.isNumber()) {
                AtomModel parent = (AtomModel) Parent;
                NumberModel number = new NumberModel(parent, 0);
                number.setResult(true);
                parent.setExpression(number);
            } else if (isPowerFunction()) {
                findPowerFunctionDerivative();
            } else if (isExponentialFunction()) {
                findExponentialFunctionDerivative();
            } else if (isLogarithm()) {
                findLogarithmDerivative();
            } else {
                System.out.println("Программа не смогла вычислить значение производной");
            }
        }
    }

    // Методы, находящие производные элементарных функций
    private void findPowerFunctionDerivative() {

        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);

        // Определяем имя переменной в основании
        SignedAtomModel baseSignedAtom = factor.getBase();
        AtomModel baseAtom = baseSignedAtom.getAtom();
        VariableModel baseVariable = (VariableModel) baseAtom.getExpression();
        String baseName = baseVariable.getName();

        // Определяем значение показателя степени
        SignedAtomModel exponentSignedAtom = factor.getExponent();
        double exponentValue = exponentSignedAtom.getValue();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression(exponentValue + "*" + baseName + "^" + (exponentValue - 1));

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        parentExpression.setResult(true);
}

    private void findExponentialFunctionDerivative() {

        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);

        // Определяем значение основания степени
        SignedAtomModel baseSignedAtom = factor.getBase();
        double exponentValue = baseSignedAtom.getValue();

        // Определяем имя переменной в показателе степени
        SignedAtomModel exponentSignedAtom = factor.getExponent();
        AtomModel exponentAtom = exponentSignedAtom.getAtom();
        VariableModel exponentVariable = (VariableModel) exponentAtom.getExpression();
        String baseName = exponentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        // TODO: Добавить логарифм натуральный
        ExpressionModel resultExpression = ParseHelper.parseExpression(exponentValue + "*" + baseName + "^" + (exponentValue - 1));

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        parentExpression.setResult(true);
}

    private void findLogarithmDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с логарифмом
        Logarithm logarithm = (Logarithm) atom.getExpression();

        // Получаем основаниие
        SignedAtomModel baseSignedAtom = logarithm.getBase();
        double baseValue = baseSignedAtom.getValue();

        // Определяем имя переменной в аргументе
        SignedAtomModel exponentSignedAtom = logarithm.getArgument();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel argumentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = argumentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("1/(" + argumentName + "*ln(" + baseValue + "))");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        parentExpression.setResult(true);
    }








    // Методы-мутаторы
    public void setFunction(ExpressionModel function) {
        Function = function;
    }

    public ExpressionModel getFunction(){
        return Function;
    }
}
