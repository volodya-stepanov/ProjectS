package com.razrabotkin.systematics.DataModels.Tasks;

import com.razrabotkin.systematics.DataModels.Formulas.ExpressionModel;
import com.razrabotkin.systematics.DataModels.Objects.DocumentModel;
import com.razrabotkin.systematics.Helpers.ParseHelper;
import org.junit.Test;

import static org.junit.Assert.*;

public class FindDerivativeTest {

    private DocumentModel Document;
    private String Description;
    private ParseHelper ParseHelper;

    public FindDerivativeTest(){
        Document = new DocumentModel();
        Description = "Вычислите производную функции:";
        ParseHelper= new ParseHelper();
    }

    // Производные элементарных функций
    /**
     * Константа (число)
     */
    @Test
    public void constant(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "10");
        findDerivative.solve();

        assertEquals("Неверно вычислена производная", 0, findDerivative.Answers.get(0).getValue(), 0.1);
    }

    /**
     * Степенная функция
     */
    @Test
    public void powerFunction(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "x^3");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("3*x^2").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    /**
     * Показательная функция
     */
    @Test
    public void exponentFunction(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "3^x");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("3^x*ln(3)").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void eulerFunction(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "exp(x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("e^x").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void logarithm(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "log(14, x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("1/(x*ln(14))").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void naturalLogarithm(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "ln(x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("1/x").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void sinus(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "sin(x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("cos(x)").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void cosine(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "cos(x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("-sin(x)").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void sqrt(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "sqrt(x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("1/(2*sqrt(x))").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void tangent(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "tan(x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("1/cos(x)^2").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void cotangent(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "ctan(x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("-1/sin(x)^2").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void binomial(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "3*x-5");
        findDerivative.solve();

        String expectedAnswer = "3";
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    // Производные, вычисляемые по правилам дифференцирования
    @Test
    public void summ(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "x^2-5");
        findDerivative.solve();

        String expectedAnswer = "2*x";
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void production(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "x^2*3^x");
        findDerivative.solve();

        String expectedAnswer = "2*x";
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }

    @Test
    public void quotient(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "x^2/(2*x+1)");
        findDerivative.solve();

        String expectedAnswer = "2*x";
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }
}