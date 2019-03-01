package com.razrabotkin.systematics.DataModels.Tasks;

import com.razrabotkin.systematics.DataModels.Objects.DocumentModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class FindDerivativeTest {

    private DocumentModel Document;
    private String Description;

    public FindDerivativeTest(){
        Document = new DocumentModel();
        Description = "Вычислите производную функции:";
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


}