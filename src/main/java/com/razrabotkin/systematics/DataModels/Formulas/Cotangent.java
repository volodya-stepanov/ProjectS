package com.razrabotkin.systematics.DataModels.Formulas;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Котангенс
 */
public class Cotangent extends ExpressionModel{

    /** Аргумент */
    private SignedAtomModel Argument;

    public Cotangent(FormulaModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "ctg(" + Argument.toString() + ")";
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
        Cotangent cotangent = new Cotangent(parent);
        cotangent.setArgument((SignedAtomModel) Argument.copy(cotangent));
        return cotangent;
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
