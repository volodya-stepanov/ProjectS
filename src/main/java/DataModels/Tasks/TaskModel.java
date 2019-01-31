package DataModels.Tasks;

import DataModels.Formulas.FormulaModel;
import DataModels.Objects.DocumentModel;
import DataModels.SolutionBlocks.SolutionBlock;
import java.util.ArrayList;

/**
 * Модель задания
 */
public abstract class TaskModel {

    /** Описание (текстовая часть) задания */
    protected String Description;

    /** Формула, введённая пользователем в виде строки */
    protected String FormulaString;

    /** Формула в объектном виде, разобранная парсером */
    protected FormulaModel Formula;

    /** Документ, в котором содержится данное задание */
    protected DocumentModel Document;

    /** Массив блоков решения для записи в документ */
    protected ArrayList<SolutionBlock> SolutionBlocksList;

    /**
     * Инициализирует экземпляр класса
     * @param document Документ, в котором содержится данное задание
     * @param description Описание (текстовая часть) задания
     * @param formulaString Формула, введённая пользователем в виде строки
     */
    public TaskModel(DocumentModel document, String description, String formulaString) {
        Document = document;
        Description = description;
        FormulaString = formulaString;

        SolutionBlocksList = new ArrayList<SolutionBlock>();
    }

    /**
     * Решает данное задание и заполняет массив SolutionBlocksList блоками решения
     */
    public void solve(){
        // TODO:
    }

    /**
     * Выполняет запись задания и решения в документ
     */
    public void saveToDocument(){

    }






    /**
     * Возвращает описание (текстовую часть) задания
     * @return Описание (текстовая часть) задания
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Задаёт описание (текстовую часть) задания
     * @param description Описание (текстовая часть) задания
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Возвращает формулу, введённую пользователем в виде строки
     * @return Формула, введённая пользователем в виде строки
     */
    public String getFormulaString() {
        return FormulaString;
    }

    /**
     * Задаёт формулу, введённую пользователем в виде строки
     * @param formulaString Формула, введённая пользователем в виде строки
     */
    public void setFormulaString(String formulaString) {
        FormulaString = formulaString;
    }

    /**
     * Задаёт формулу
     * @param formula Формула
     */
    public void setFormula(FormulaModel formula) {
        Formula = formula;
    }

    /**
     * Возвращает формулу
     * @return Формула
     */
    public FormulaModel getFormula(){
        return Formula;
    }
}
