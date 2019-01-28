package DataModels;

/**
 * Число
 */
public class NumberModel extends ExpressionModel{

    /**
     * Значение
     */
    private double mValue;

    /**
     * Инициализирует экземпляр класса
     * @param value Значение
     */
    public NumberModel(double value) {
        mValue = value;
    }

    /**
     * Возвращает значение
     * @return Значение
     */
    public double getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return String.valueOf(mValue);
    }
}
