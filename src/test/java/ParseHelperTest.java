import com.razrabotkin.systematics.DataModels.Formulas.*;
import com.razrabotkin.systematics.Helpers.ClassHelper;
import com.razrabotkin.systematics.Helpers.ParseHelper;

import static org.junit.Assert.*;

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
    }

    @org.junit.Test
    public void parseExpression() {
        EquationModel equation = ParseHelper.parseEquation("-x^2+7*x-10=0");

        int actualExpressionsNumber = equation.Expressions.size();
        int expectedExpressionsNumber = 2;

        assertEquals(
                "Неверное количество выражений в уравнении",
                expectedExpressionsNumber,
                actualExpressionsNumber);
    }

    @org.junit.Test
    public void parseVariable(){
        ExpressionModel expression = ParseHelper.parseExpression("x");

        assertEquals("Неверное количество членов в выражении", expression.Terms.size(), 1);

        TermModel term = expression.Terms.get(0);

        assertEquals("Неверное количество множителей в члене", term.Factors.size(), 1);

        FactorModel factor = term.Factors.get(0);

        assertEquals("Неверный показатель степени множителя", factor.getExponent().getValue(), 1, 0.1);

        SignedAtomModel signedAtom = factor.getBase();

        assertFalse("Неверный знак атома со знаком", signedAtom.isNegative());

        AtomModel atom = signedAtom.getAtom();
        ExpressionModel atomExpression = atom.getExpression();

        assertTrue("Выражение атома не является переменной", ClassHelper.isTypeOf(atomExpression, VariableModel.class));

        VariableModel variable = (VariableModel) atomExpression;

        assertEquals("Неверно распознано имя переменной", variable.getName(), "x");
    }

    @org.junit.Test
    public void parseNumber(){
        ExpressionModel expression = ParseHelper.parseExpression("5");

        assertTrue("Выражение не является числом", expression.isNumber());

        assertEquals("Число неверно распознано", expression.getValue(), 5, 0.1);
    }
}