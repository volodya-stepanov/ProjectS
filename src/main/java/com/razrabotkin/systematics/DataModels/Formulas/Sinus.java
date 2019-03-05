package com.razrabotkin.systematics.DataModels.Formulas;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Синус
 */
public class Sinus extends ExpressionModel{

    /** Аргумент */
    private SignedAtomModel Argument;

    public Sinus(FormulaModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "sin(" + Argument.toString() + ")";
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
        Sinus sinus = new Sinus(parent);
        sinus.setArgument((SignedAtomModel) Argument.copy(sinus));
        return sinus;
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
