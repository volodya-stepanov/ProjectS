package DataModels;

/**
 * Атом
 */
public class AtomModel {
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
}
