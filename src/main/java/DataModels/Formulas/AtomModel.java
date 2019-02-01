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
