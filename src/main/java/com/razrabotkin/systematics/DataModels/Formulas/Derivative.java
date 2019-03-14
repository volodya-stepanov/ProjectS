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

    @Override
    public boolean isResult() {
        // Производная никогда н являеся результатом,
        // так как её всегда можно вычислить
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
                || isNaturalLogarithm()
                || isEulerFunction()
                || isSinus()
                || isCosine()
                || isTangent()
                || isCotangent()

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
     * Показательная функция (a^x). Из-за трудностей перевода показательная функция здесь названа экспоненциальной, а экспоненциальная - Эйлеровой
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

    /**
     * Экспоненциальная функция (e^x). Из-за трудностей перевода показательная функция здесь названа экспоненциальной, а экспоненциальная - Эйлеровой
     *
     * @return Истина, если функция является экспоненциальной, иначе ложь
     */
    private boolean isEulerFunction() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), ExponentialFunction.class)) {
                    return true;
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

    private boolean isNaturalLogarithm() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), NaturalLogarithm.class)) {
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
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), SquareRoot.class)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isSinus() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), Sinus.class)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isCosine() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), Cosine.class)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isTangent() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), Tangent.class)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isCotangent() {
        if (Function.Terms.size() == 1) {
            TermModel term = Function.Terms.get(0);

            if (term.Factors.size() == 1) {
                FactorModel factor = term.Factors.get(0);
                SignedAtomModel signedAtom = factor.getBase();
                AtomModel atom = signedAtom.getAtom();

                if (ClassHelper.isTypeOf(atom.getExpression(), Cotangent.class)) {
                    return true;
                }
            }
        }

        return false;
    }
































    public void solve() {
        if (canSolve()) {
            // Если перед нами элементарная функция
            if (Function.isNumber()) {
                AtomModel parent = (AtomModel) Parent;
                NumberModel number = new NumberModel(parent, 0);
                // number.setResult(true);
                parent.setExpression(number);
            } else if (isPowerFunction()) {
                findPowerFunctionDerivative();
            } else if (isExponentialFunction()) {
                findExponentialFunctionDerivative();
            }
            else if (isLogarithm()) {
                findLogarithmDerivative();
            }
            else if (isNaturalLogarithm()) {
                findNaturalLogarithmDerivative();
            }
            else if (isEulerFunction()) {
                findEulerFunctionDerivative();
            }
            else if (isSquareRoot()) {
                findSquareRootDerivative();
            }
            else if (isSinus()) {
                findSinusDerivative();
            }
            else if (isCosine()) {
                findCosineDerivative();
            }
            else if (isTangent()) {
                findTangentDerivative();
            }
            else if (isCotangent()) {
                findCotangentDerivative();
            }
            else {
                System.out.println("Программа не смогла вычислить значение производной");
            }
        }
        // Если перед нами производная суммы
        else if (Function.Terms.size() > 1){
            // Берём родительский атом этой производной
            AtomModel atom = (AtomModel) Parent;

            // Создаём выражение, которое будет содержать сумму производных
            ExpressionModel newExpression = new ExpressionModel(atom);

            for (TermModel term : Function.Terms){
                TermModel newTerm = ParseHelper.parseTerm("(" + term.toString() + ")'");
                newTerm.setParent(newExpression);
                newTerm.setMathOperation(term.getMathOperation());
                newExpression.Terms.add(newTerm);
            }

            atom.setExpression(newExpression);
        }
        // Усли перед нами производная произведения или частного
        else {
            // Берём родительский атом этой производной
            AtomModel atom = (AtomModel) Parent;

            // Создаём выражение, которое будет содержать формулу для вычисления производной произведения
            ExpressionModel newExpression = new ExpressionModel(atom);

            // Проверяем количество множителей в произведении
            if (Function.Terms.get(0).Factors.size() == 2){

                FactorModel factor1 = Function.Terms.get(0).Factors.get(0);
                FactorModel factor2 = Function.Terms.get(0).Factors.get(1);

                // Произвная произведения
                if (factor2.getMathOperation() == MathOpModel.Multiply){
                    TermModel term1 = ParseHelper.parseTerm("(" + factor1.toString() + ")'*" + factor2.toString());
                    term1.setParent(newExpression);
                    term1.setMathOperation(MathOpModel.None);
                    newExpression.Terms.add(term1);

                    TermModel term2 = ParseHelper.parseTerm(factor1.toString() + "*(" + factor2.toString() + ")'");
                    term2.setParent(newExpression);
                    term2.setMathOperation(MathOpModel.Plus);
                    newExpression.Terms.add(term2);
                }
                // Производная частного
                else if (factor2.getMathOperation() == MathOpModel.Divide){
                    TermModel term = ParseHelper.parseTerm("((" + factor1.toString() + ")'*" + factor2.toString() + "-" + factor1.toString() + "*(" + factor2.toString() + ")')/(" + factor2.toString() + ")^2");
                    term.setParent(newExpression);
                    term.setMathOperation(MathOpModel.None);
                    newExpression.Terms.add(term);
                } else {
                    System.out.println("Перед вторым множителем в произведении не стоит знак умножения или деления");
                }

            } else {
                System.out.println("Количество множителей в произведении, производную которого нужно вычислить, больше двух");
            }

            atom.setExpression(newExpression);
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
        // parentExpression.setResult(true);
}

    private void findExponentialFunctionDerivative() {

        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);

        // Определяем значение основания степени
        SignedAtomModel baseSignedAtom = factor.getBase();
        double baseValue = baseSignedAtom.getValue();

        // Определяем имя переменной в показателе степени
        SignedAtomModel exponentSignedAtom = factor.getExponent();
        AtomModel exponentAtom = exponentSignedAtom.getAtom();
        VariableModel exponentVariable = (VariableModel) exponentAtom.getExpression();
        String exponentName = exponentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression(baseValue + "^" + exponentName + "*ln(" + baseValue + ")");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
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
        // parentExpression.setResult(true);
    }

    private void findNaturalLogarithmDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с логарифмом
        NaturalLogarithm naturalLogarithm = (NaturalLogarithm) atom.getExpression();

        // Определяем имя переменной в аргументе
        SignedAtomModel exponentSignedAtom = naturalLogarithm.getArgument();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel argumentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = argumentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("1/" + argumentName);

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
    }

    private void findEulerFunctionDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с логарифмом
        ExponentialFunction exponentialFunction = (ExponentialFunction) atom.getExpression();

        // Определяем имя переменной в показатее степени
        SignedAtomModel exponentSignedAtom = exponentialFunction.getExponent();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel exponentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = exponentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("exp(" + argumentName + ")");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
    }

    private void findSquareRootDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с квадратным корнем
        SquareRoot squareRoot = (SquareRoot) atom.getExpression();

        // Определяем имя переменной в аргументе
        SignedAtomModel exponentSignedAtom = squareRoot.getRadicalExpression();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel argumentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = argumentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("1/(2*sqrt(" + argumentName + "))");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
    }

    private void findSinusDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с синусом
        Sinus sinus = (Sinus) atom.getExpression();

        // Определяем имя переменной в аргументе
        SignedAtomModel exponentSignedAtom = sinus.getArgument();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel argumentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = argumentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("cos(" + argumentName + ")");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
    }

    private void findCosineDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с косинусом
        Cosine cosine = (Cosine) atom.getExpression();

        // Определяем имя переменной в аргументе
        SignedAtomModel exponentSignedAtom = cosine.getArgument();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel argumentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = argumentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("-sin(" + argumentName + ")");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
    }

    private void findTangentDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с тангенсом
        Tangent tangent = (Tangent) atom.getExpression();

        // Определяем имя переменной в аргументе
        SignedAtomModel exponentSignedAtom = tangent.getArgument();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel argumentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = argumentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("1/cos(" + argumentName + ")^2");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
    }

    private void findCotangentDerivative() {

        // Извлекаем атом
        TermModel term = Function.Terms.get(0);
        FactorModel factor = term.Factors.get(0);
        SignedAtomModel signedAtom = factor.getBase();
        AtomModel atom = signedAtom.getAtom();

        // Работаем с котангенсом
        Cotangent cotangent = (Cotangent) atom.getExpression();

        // Определяем имя переменной в аргументе
        SignedAtomModel exponentSignedAtom = cotangent.getArgument();
        AtomModel argumentAtom = exponentSignedAtom.getAtom();
        VariableModel argumentVariable = (VariableModel) argumentAtom.getExpression();
        String argumentName = argumentVariable.getName();

        // Создаём выражение-результат и подставляем его вместо текущей функции
        ExpressionModel resultExpression = ParseHelper.parseExpression("-1/sin(" + argumentName + ")^2");

        AtomModel parent = (AtomModel) Parent;
        resultExpression.setParent(parent);
        parent.setExpression(resultExpression);

        ExpressionModel parentExpression = resultExpression.getParentExpression();
        // parentExpression.setResult(true);
    }










    // Методы-мутаторы
    public void setFunction(ExpressionModel function) {
        Function = function;
    }

    public ExpressionModel getFunction(){
        return Function;
    }
}
