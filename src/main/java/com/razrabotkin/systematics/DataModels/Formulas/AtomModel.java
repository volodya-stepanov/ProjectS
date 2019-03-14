package com.razrabotkin.systematics.DataModels.Formulas;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Атом
 */
public class AtomModel extends FormulaModel {
    /** Выражение (число, переменная или выражение в скобках) */
    private ExpressionModel Expression;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     */
    public AtomModel(SignedAtomModel parent) {
        super(parent);
    }

    @Override
    public String toString() {
        return Expression.toString();
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        return Expression.toOpenXML();
    }

    @Override
    public boolean isNumber() {
        return Expression.isNumber();
    }

    @Override
    public boolean isResult() {
        return Expression.isResult();
    }

    public FormulaModel copy(FormulaModel parent) {
        AtomModel atom = new AtomModel((SignedAtomModel) parent);

        atom.setExpression((ExpressionModel) Expression.copy(atom));

        return atom;
    }

    public boolean canSolve() {
        return Expression.canSolve();
    }

    public void solve() {
        if(canSolve()){
            //TODO: Вот сюда заходит программа, когда нужно вычислить квадратный корень, а здесь ничего нет.
            System.out.println("Программа решила, что значение этого атома можно вычислить");
            Expression.solve();
        } else {
            Expression.solve();
        }
    }

    protected double getValue() {
        if (isNumber()){
            return Expression.getValue();
        } else {
            System.out.println("Не удаётся получить значение, так как выражение не является числом");
            return 0;
        }
    }

    protected void setValue(double value) {
        if (Expression == null){
            Expression = new NumberModel(this, value);
        } else {
            //NumberModel number = (NumberModel) Expression;
            //number.setValue(value);
            Expression.setValue(value);
        }
    }

    // Методы-мутаторы
    /**
     * Возвращает выражение
     * @return Выражение
     */
    public ExpressionModel getExpression() {
        return Expression;
    }

    /**
     * Задаёт выражение
     * @param mExpression Выражение
     */
    public void setExpression(ExpressionModel mExpression) {
        this.Expression = mExpression;
    }

    public void setName(String name) {
        if (Expression == null){
            Expression = new VariableModel(this, name);
        } else {
            VariableModel variable = (VariableModel) Expression;
            variable.setName(name);
        }
    }

    public void setName(String name, String index) {
        if (Expression == null){
            Expression = new VariableModel(this, name, index);
        } else {
            VariableModel variable = (VariableModel) Expression;
            variable.setName(name, index);
        }
    }
}
