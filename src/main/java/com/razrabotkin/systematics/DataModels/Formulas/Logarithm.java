package com.razrabotkin.systematics.DataModels.Formulas;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Логарифм
 */
public class Logarithm extends ExpressionModel{

    /** Основание */
    private SignedAtomModel Base;

    /** Число (аргумент) */
    private SignedAtomModel Argument;

    public Logarithm(FormulaModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "log(" + Base.toString() + ", " + Argument.toString() + ")";
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
    public boolean isResult() {
        // Если аргумет дальше вычислить уже нельзя, но при этом он является числом, значит,
        // значение функции ещё можно вычислить.
        // Во всех остальных случаях значение вычислить уже нельзя, значит, это результат.
        if (Base.isResult() && Argument.isResult()){
            if (Base.isNumber() && Argument.isNumber()){
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
        Logarithm logarithm = new Logarithm(parent);
        logarithm.setBase((SignedAtomModel) Base.copy(logarithm));
        logarithm.setArgument((SignedAtomModel) Argument.copy(logarithm));
        return logarithm;
    }

    @Override
    public boolean canSolve() {
        return Base.isNumber() && Argument.isNumber();
    }

    @Override
    public void solve() {
        throw new NotImplementedException();
    }

    // Методы-мутаторы
    public SignedAtomModel getBase() {
        return Base;
    }

    public void setBase(SignedAtomModel base) {
        Base = base;
    }

    public SignedAtomModel getArgument() {
        return Argument;
    }

    public void setArgument(SignedAtomModel argument) {
        Argument = argument;
    }
}
