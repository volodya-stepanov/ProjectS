package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.DocumentHelper;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Квадратный корень
 */
public class SquareRoot extends ExpressionModel {
    /** Подкоренное выражение */
    private SignedAtomModel RadicalExpression;

    /**
     * Инициализирует экземпляр класса
     *
     * @param parent Родитель
     */
    public SquareRoot(FormulaModel parent) {
        super(parent);
    }

    /**
     * Инициализирует экземпляр класса числовым значением, создавая всю лежащую ниже иерархию и добавляя в самый низ экземпляр класса NumberModel с этим числовым значением
     * @param parent
     * @param value
     */
    public SquareRoot(FormulaModel parent, double value){
        this(parent);
        RadicalExpression = new SignedAtomModel(this);
        RadicalExpression.setValue(value);
    }

    public SquareRoot(FormulaModel parent, String name) {
        this(parent);
        RadicalExpression = new SignedAtomModel(this);
        RadicalExpression.setName(name);
    }

    @Override
    public String toString() {
        return "sqrt(" + RadicalExpression.toString() + ")";
    }

    // TODO: Возможно, сделать абстрактным, чтобы не приходилось каждый раз переопределять.
    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        DocumentHelper helper = new DocumentHelper();
        return helper.createRadical(RadicalExpression.toOpenXML());
    }

    // TODO: Возможно, сделать абстрактным, чтобы не приходилось каждый раз переопределять.
    @Override
    public boolean isNumber() {
        // Квадратный корень никогда не является числом
        return false;
    }

    public FormulaModel copy(FormulaModel parent) {
        SquareRoot squareRoot = new SquareRoot(parent);

        squareRoot.setRadicalExpression((SignedAtomModel) RadicalExpression.copy(squareRoot));

        return squareRoot;
    }

    public boolean canSolve() {
        return RadicalExpression.isNumber();
    }

    public void solve() {
        if (canSolve()){
            double value = RadicalExpression.getValue();
            double result = Math.sqrt(value);

            AtomModel parent = (AtomModel) Parent;
            parent.setExpression(new NumberModel(parent, result));
        } else {
            RadicalExpression.solve();
        }
    }

    // Методы-мутаторы
    public void setRadicalExpression(SignedAtomModel radicalExpression) {
        RadicalExpression = radicalExpression;
    }

    public SignedAtomModel getRadicalExpression() {
        return RadicalExpression;
    }
}
