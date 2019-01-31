package DataModels.SolutionBlocks;

/**
 * Текстовый блок решения задания
 */
public class TextBlock extends SolutionBlock {
    /**
     * Текст, содержащийся в блоке
     */
    private String Value;

    /**
     * Инициализирует экземпляр класса
     * @param value Текст
     */
    public TextBlock(String value) {
        Value = value;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }
}
