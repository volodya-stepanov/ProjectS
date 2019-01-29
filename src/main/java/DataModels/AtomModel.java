package DataModels;

import org.docx4j.math.CTR;

/**
 * Атом
 */
public class AtomModel extends FormulaModel {
    /**
     * Выражение (число, переменная или выражение в скобках)
     */
    private ExpressionModel mExpression;

    /**
     * Возвращает выражение
     * @return Выражение
     */
    public ExpressionModel getExpression() {
        return mExpression;
    }

    /**
     * Задаёт выражение
     * @param mExpression Выражение
     */
    public void setExpression(ExpressionModel mExpression) {
        this.mExpression = mExpression;
    }

    @Override
    public String toString() {
        return mExpression.toString();
    }

    @Override
    public CTR toOpenXML() {
        return mExpression.toOpenXML();
    }
}
