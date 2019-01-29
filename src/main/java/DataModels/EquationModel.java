package DataModels;

/**
 * Уравнение
 */
public class EquationModel extends FormulaModel {

    /**
     * Левая часть
     */
    private ExpressionModel LeftSide;

    /**
     * Правая часть
     */
    private ExpressionModel RightSide;

    /**
     * Отношение между частями
     */
    private RelOpModel Relation;

    /**
     * Возвращает левую часть
     * @return Левая часть
     */
    public ExpressionModel getLeftSide() {
        return LeftSide;
    }

    /**
     * Задаёт левую часть
     * @param leftSide Левая часть
     */
    public void setLeftSide(ExpressionModel leftSide) {
        LeftSide = leftSide;
    }

    /**
     * Возвращает правую часть
     * @return Правая часть
     */
    public ExpressionModel getRightSide() {
        return RightSide;
    }

    /**
     * Задаёт правую часть
     * @param rightSide Правая часть
     */
    public void setRightSide(ExpressionModel rightSide) {
        RightSide = rightSide;
    }

    /**
     * Возвращает отношение между частями
     * @return Отношение между частями
     */
    public RelOpModel getRelation() {
        return Relation;
    }

    /**
     * Задаёт отношение между частями
     * @param relation Отношение между частями
     */
    public void setRelation(RelOpModel relation) {
        Relation = relation;
    }

    @Override
    public String toString() {
        String str = LeftSide.toString();

        switch (Relation) {
            case Equals:
                str+="=";
                break;
            case GreaterThan:
                str+=">";
                break;
            case LessThan:
                str+="<";
                break;
        }

        str+=RightSide.toString();

        return str;
    }
}
