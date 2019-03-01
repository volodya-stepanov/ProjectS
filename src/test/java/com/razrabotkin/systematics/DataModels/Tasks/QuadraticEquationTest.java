package com.razrabotkin.systematics.DataModels.Tasks;

import com.razrabotkin.systematics.DataModels.Objects.DocumentModel;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuadraticEquationTest {

    private DocumentModel Document;
    private String Description;

    public QuadraticEquationTest(){
        Document = new DocumentModel();
        Description = "Решите квадратное уравнение:";
    }

    /**
     * Тестирует решение простого уравнения
     */
    @Test
    public void solveEquationSimple(){
        QuadraticEquation quadraticEquation = new QuadraticEquation(Document, Description, "5*x^2-7*x+2=0");
        quadraticEquation.solve();

        assertEquals("Неверно определено количество корней уравнения", 2, quadraticEquation.Answers.size());
        assertEquals("Неверно определён корень уравнения", 1, quadraticEquation.Answers.get(0).getValue(), 0.1);
        assertEquals("Неверно определён корень уравнения", 0.4, quadraticEquation.Answers.get(1).getValue(), 0.1);
    }

    /**
     * Тестирует решение квадратного уравнения, в котором главный коэффициент равен минус единице
     */
    @Test
    public void solveEquationMainCoefMinus1(){
        QuadraticEquation quadraticEquation = new QuadraticEquation(Document, Description, "-x^2+7*x-10=0");
        quadraticEquation.solve();

        assertEquals("Неверно определено количество корней уравнения", 2, quadraticEquation.Answers.size());
        assertEquals("Неверно определён корень уравнения", 2.0, quadraticEquation.Answers.get(0).getValue(), 0.1);
        assertEquals("Неверно определён корень уравнения", 5.0, quadraticEquation.Answers.get(1).getValue(), 0.1);
    }

    /**
     * Тестирует решение квадратного уравнения, в котором второй коэффициент равен единице
     */
    @Test
    public void solveEquationSecondCoef1(){
        QuadraticEquation quadraticEquation = new QuadraticEquation(Document, Description, "6*x^2+x-1=0");
        quadraticEquation.solve();

        assertEquals("Неверно определено количество корней уравнения", 2, quadraticEquation.Answers.size());
        assertEquals("Неверно определён корень уравнения", 1.0/3.0, quadraticEquation.Answers.get(0).getValue(), 0.1);
        assertEquals("Неверно определён корень уравнения", -0.5, quadraticEquation.Answers.get(1).getValue(), 0.1);
    }

    /**
     * Тестирует решение уравнения, имеющего один корень
     */
    @Test
    public void solveEquationOneRoot(){
        QuadraticEquation quadraticEquation = new QuadraticEquation(Document, Description, "x^2+10*x+25=0");
        quadraticEquation.solve();

        assertEquals("Неверно определено количество корней уравнения", 1, quadraticEquation.Answers.size());
    }

    /**
     * Тестирует решение уравнения, не имеющего корней
     */
    @Test
    public void solveEquationNoRoots(){
        QuadraticEquation quadraticEquation = new QuadraticEquation(Document, Description, "x^2+10*x+30=0");
        quadraticEquation.solve();

        assertEquals("Неверно определено количество корней уравнения", 0, quadraticEquation.Answers.size());
    }
}