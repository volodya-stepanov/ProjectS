package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.DocumentHelper;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;
// TODO: Разобраться с показательной и экспоненциальной функциями!
/**
 * Экспоненциальная функция
 */
public class ExponentialFunction extends ExpressionModel{
    /** Показатель степени */
    private SignedAtomModel Exponent;

    public ExponentialFunction(FormulaModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return "e^" + Exponent.toString();
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
        ExponentialFunction exponentialFunction =   new ExponentialFunction(parent);
        exponentialFunction.setExponent((SignedAtomModel) Exponent.copy(exponentialFunction));
        return exponentialFunction;
    }

    @Override
    public boolean canSolve() {
        throw new NotImplementedException();
    }

    @Override
    public void solve() {
        throw new NotImplementedException();
    }

    // Методы-мутаторы
    public SignedAtomModel getExponent() {
        return Exponent;
    }

    public void setExponent(SignedAtomModel exponent) {
        Exponent = exponent;
    }
}
