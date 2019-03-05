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

        assertEquals("Неверно вычислена производная", 0, findDerivative.Answers.get(0).getValue(), 0.1);
    }

    @Test
    public void logarithm(){
        FindDerivative findDerivative = new FindDerivative(Document, Description, "log(14, x)");
        findDerivative.solve();

        String expectedAnswer = ParseHelper.parseExpression("1/(x*ln(14))").toString();
        String actualAnswer = findDerivative.Answers.get(0).toString();

        assertEquals("Неверно вычислена производная", expectedAnswer, actualAnswer);
    }


}