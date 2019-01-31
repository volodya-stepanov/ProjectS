package DataModels.SolutionBlocks;

import DataModels.Formulas.FormulaModel;

/**
 * Блок решения задачи с формулой
 */
public class FormulaBlock extends SolutionBlock{
    /**
     * Формула, содержащаяся в блоке
     */
    private FormulaModel Formula;

    /**
     * Инициалзирует экземпляр класса
     * @param formula Формула
     */
    public FormulaBlock(FormulaModel formula) {
        Formula = formula;
    }

    public FormulaModel getFormula() {
        return Formula;
    }

    public void setFormula(FormulaModel formula) {
        Formula = formula;
    }
}
