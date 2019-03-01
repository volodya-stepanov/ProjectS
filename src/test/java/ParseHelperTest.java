import com.razrabotkin.systematics.DataModels.Formulas.*;
import com.razrabotkin.systematics.Helpers.ClassHelper;
import com.razrabotkin.systematics.Helpers.ParseHelper;

import static org.junit.Assert.*;

/**
 * Класс для тестирования методов класса ParseHelper
 */
public class ParseHelperTest {

    ParseHelper ParseHelper;

    ClassHelper ClassHelper;

    public ParseHelperTest(){
        ParseHelper = new ParseHelper();
        ClassHelper = new ClassHelper();
    }

    @org.junit.Test
    public void parseEquation() {
        EquationModel equation = ParseHelper.parseEquation("-x^2+7*x-10=0");

        int actualExpressionsNumber = equation.Expressions.size();
        int expectedExpressionsNumber = 2;

        assertEquals(
                "Неверно определено количество выражений в уравнении",
                expectedExpressionsNumber,
                actualExpressionsNumber);

        assertEquals("Неверно определён знак между частями уравнения", RelOpModel.Equals, equation.Expressions.get(1).getRelation());
    }

    @org.junit.Test
    public void parseVariable(){
        ExpressionModel expression = ParseHelper.parseExpression("x");

        assertEquals("Неверно определено количество членов в выражении", 1, expression.Terms.size());

        TermModel term = expression.Terms.get(0);

        assertEquals("Неверно определено количество множителей в члене", 1, term.Factors.size());

        FactorModel factor = term.Factors.get(0);

        assertEquals("Неверный показатель степени множителя", 1, factor.getExponent().getValue(), 0.1);

        SignedAtomModel signedAtom = factor.getBase();

        assertFalse("Неверный знак атома со знаком", signedAtom.isNegative());

        AtomModel atom = signedAtom.getAtom();
        ExpressionModel atomExpression = atom.getExpression();

        assertTrue("Выражение атома не является переменной", ClassHelper.isTypeOf(atomExpression, VariableModel.class));

        VariableModel variable = (VariableModel) atomExpression;

        assertEquals("Неверно распознано имя переменной", "x", variable.getName());
    }

    @org.junit.Test
    public void parseNumber(){
        ExpressionModel expression = ParseHelper.parseExpression("5");

        assertTrue("Выражение не является числом", expression.isNumber());

        assertEquals("Число неверно распознано", 5, expression.getValue(), 0.1);
    }

    @org.junit.Test
    public void parseSignedAtom(){
        ExpressionModel expression = ParseHelper.parseExpression("-a");

        assertEquals("Неверно определено количество членов в выражении", 1, expression.Terms.size());

        TermModel term = expression.Terms.get(0);

        assertEquals("Неверно определено количество множителей в члене", 1, term.Factors.size());

        FactorModel factor = term.Factors.get(0);

        assertEquals("Неверный показатель степени множителя", 1, factor.getExponent().getValue(), 0.1);

        SignedAtomModel signedAtom = factor.getBase();

        assertTrue("Неверный знак атома со знаком", signedAtom.isNegative());
    }

    @org.junit.Test
    public void parseFactorPower(){
        ExpressionModel expression = ParseHelper.parseExpression("x^2");

        assertEquals("Неверно определено количество членов в выражении", 1, expression.Terms.size());

        TermModel term = expression.Terms.get(0);

        assertEquals("Неверно определено количество множителей в члене", 1, term.Factors.size());

        FactorModel factor = term.Factors.get(0);

        assertEquals("Неверно определён показатель степени множителя", 2, factor.getExponent().getValue(), 0.1);

        SignedAtomModel base = factor.getBase();

        assertFalse("Неверно определён знак атома со знаком", base.isNegative());

        // Тестируем основание степени
        AtomModel baseAtom = base.getAtom();
        ExpressionModel atomExpression = baseAtom.getExpression();

        assertTrue("Выражение атома не является переменной", ClassHelper.isTypeOf(atomExpression, VariableModel.class));

        VariableModel variable = (VariableModel) atomExpression;

        assertEquals("Неверно распознано имя переменной", "x", variable.getName());

        // Тестируем показатель степени
        SignedAtomModel exponent = factor.getExponent();

        assertFalse("Неверно определён знак атома со знаком", exponent.isNegative());
        assertTrue("Показатель степени не распознан как число", exponent.isNumber());
        assertEquals("Значение показателя степени неверно распознано", 2, exponent.getValue(), 0.1);
    }

    @org.junit.Test
    public void parseFactorMultiple(){
        ExpressionModel expression = ParseHelper.parseExpression("3 * a / x^2");

        assertEquals("Неверно определено количество членов в выражении", 1, expression.Terms.size());

        TermModel term = expression.Terms.get(0);

        assertEquals("Неверно определено количество множителей в члене", 3, term.Factors.size());

        // Тестируем первый множитель
        FactorModel factor1 = term.Factors.get(0);

        assertTrue("Множитель не распознан как число", factor1.isNumber());
        assertEquals("Значение множителя неверно распознано", 3, factor1.getValue(), 0.1);

        // Тестируем второй множитель
        FactorModel factor2 = term.Factors.get(1);

        assertEquals("Неверно определён знак перед множителем", MathOpModel.Multiply, factor2.getMathOperation());

        SignedAtomModel signedAtom = factor2.getBase();
        AtomModel atom = signedAtom.getAtom();
        ExpressionModel atomExpression = atom.getExpression();

        assertTrue("Выражение атома не распознано как переменная", ClassHelper.isTypeOf(atomExpression, VariableModel.class));

        VariableModel variable1 = (VariableModel) atomExpression;

        assertEquals("Неверно распознано имя переменной", "a", variable1.getName());

        // Тестируем третий множитель
        FactorModel factor3 = term.Factors.get(2);

        assertEquals("Неверно определён знак перед множителем", MathOpModel.Divide, factor3.getMathOperation());

        // Тестируем основание степени
        SignedAtomModel base = factor3.getBase();
        AtomModel baseAtom = base.getAtom();
        ExpressionModel baseAtomExpression = baseAtom.getExpression();

        assertTrue("Выражение атома не распознано как переменная", ClassHelper.isTypeOf(baseAtomExpression, VariableModel.class));

        VariableModel variable2 = (VariableModel) baseAtomExpression;

        assertEquals("Неверно распознано имя переменной", "x", variable2.getName());

        // Тестируем показатель степени
        SignedAtomModel exponent = factor3.getExponent();

        assertFalse("Неверно определён знак атома со знаком", exponent.isNegative());
        assertTrue("Показатель степени не распознан как число", exponent.isNumber());
        assertEquals("Значение показателя степени неверно распознано", 2, exponent.getValue(), 0.1);
    }

    @org.junit.Test
    public void parseExpression() {
        ExpressionModel expression = ParseHelper.parseExpression("-1 + a - 10");

        assertEquals("Неверно определено количество членов в выражении", 3, expression.Terms.size());

        // Тестируем первый член
        TermModel term1 = expression.Terms.get(0);

        assertEquals("Неверно определено количество множителей в члене", 1, term1.Factors.size());

        FactorModel factor1 = term1.Factors.get(0);

        assertTrue("Множитель не распознан как число", factor1.isNumber());
        assertEquals("Значение множителя неверно распознано", -1, factor1.getValue(), 0.1);

        // Тестируем второй член
        TermModel term2 = expression.Terms.get(1);

        assertEquals("Неверно определён знак перед членом", MathOpModel.Plus, term2.getMathOperation());
        assertEquals("Неверно определено количество множителей в члене", 1, term2.Factors.size());

        FactorModel factor2 = term2.Factors.get(0);

        // Тестируем показатель степени
        SignedAtomModel signedAtom2 = factor2.getBase();

        assertFalse("Неверно определён знак атома со знаком", signedAtom2.isNegative());

        AtomModel atom = signedAtom2.getAtom();
        ExpressionModel atomExpression = atom.getExpression();

        assertTrue("Выражение атома не распознано как переменная", ClassHelper.isTypeOf(atomExpression, VariableModel.class));

        VariableModel variable = (VariableModel) atomExpression;

        assertEquals("Неверно распознано имя переменной", "a", variable.getName());

        // Тестируем третий член
        TermModel term3 = expression.Terms.get(2);

        assertEquals("Неверно определён знак перед членом", MathOpModel.Minus, term3.getMathOperation());
        assertTrue("Член не распознан как число", term3.isNumber());
        assertEquals("Значение множителя неверно распознано", 10, term3.getValue(), 0.1);
    }

    @org.junit.Test
    public void parseDerivative() {
        ExpressionModel expression = ParseHelper.parseExpression("(128)'");

        assertEquals("Неверно определено количество членов в выражении", 1, expression.Terms.size());

        TermModel term = expression.Terms.get(0);

        assertEquals("Неверно определено количество множителей в члене", 1, term.Factors.size());

        FactorModel factor = term.Factors.get(0);

        SignedAtomModel signedAtom = factor.getBase();

        assertFalse("Неверно определён знак атома со знаком", signedAtom.isNegative());

        AtomModel atom = signedAtom.getAtom();
        ExpressionModel atomExpression = atom.getExpression();

        assertTrue("Выражение атома не распознано как производная", ClassHelper.isTypeOf(atomExpression, Derivative.class));

        Derivative derivative = (Derivative) atomExpression;

        ExpressionModel function = derivative.getFunction();

        assertTrue("Функция производной не распознана как число", function.isNumber());
        assertEquals("Значение функции произвводной неверно распознано", 128, function.getValue(), 0.1);
    }
}