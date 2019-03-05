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
