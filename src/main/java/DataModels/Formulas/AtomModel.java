package DataModels.Formulas;

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
        NumberModel number = (NumberModel) Expression;
        number.setValue(value);
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
}
