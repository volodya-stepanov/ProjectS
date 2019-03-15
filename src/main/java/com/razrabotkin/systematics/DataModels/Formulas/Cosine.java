package com.razrabotkin.systematics.DataModels.Formulas;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Косинус
 */
public class Cosine extends ExpressionModel{

    /** Аргумент */
    private SignedAtomModel Argument;

    public Cosine(FormulaModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "cos(" + Argument.toString() + ")";
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    @Override
    public boolean isVariable() {
        return false;
    }

    @Override
    public boolean isResult() {
        // Если аргумет дальше вычислить уже нельзя, но при этом он является числом, значит,
        // значение функции ещё можно вычислить.
        // Во всех остальных случаях значение вычислить уже нельзя, значит, это результат.
        if (Argument.isResult()){
            if (Argument.isNumber()){
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public FormulaModel copy(FormulaModel parent) {
        Cosine cosine = new Cosine(parent);
        cosine.setArgument((SignedAtomModel) Argument.copy(cosine));
        return cosine;
    }

    @Override
    public boolean canSolve() {
        return Argument.isNumber();
    }

    @Override
    public void solve() {
        throw new NotImplementedException();
    }

    // Методы-мутаторы
    public SignedAtomModel getArgument() {
        return Argument;
    }

    public void setArgument(SignedAtomModel argument) {
        Argument = argument;
    }
}
