package com.razrabotkin.systematics.DataModels.Formulas;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Тангенс
 */
public class Tangent extends ExpressionModel{

    /** Аргумент */
    private SignedAtomModel Argument;

    public Tangent(FormulaModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "tg(" + Argument.toString() + ")";
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
        Tangent tangent = new Tangent(parent);
        tangent.setArgument((SignedAtomModel) Argument.copy(tangent));
        return tangent;
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
