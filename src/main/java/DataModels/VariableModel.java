package DataModels;

/**
 * Переменная
 */
public class VariableModel extends ExpressionModel{
    /**
     * Имя переменной
     */
    private String mValue;

    /**
     * Инициализирует экземпляр класса
     * @param value Значение
     */
    public VariableModel(String value) {
        mValue = value;
    }

    /**
     * Возвращает значение
     * @return Значение
     */
    public String getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return mValue;
    }
}
