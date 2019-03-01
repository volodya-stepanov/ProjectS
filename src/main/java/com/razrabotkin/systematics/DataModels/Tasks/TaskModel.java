package com.razrabotkin.systematics.DataModels.Tasks;

import com.razrabotkin.systematics.DataModels.Formulas.ExpressionModel;
import com.razrabotkin.systematics.DataModels.Formulas.FormulaModel;
import com.razrabotkin.systematics.DataModels.Objects.DocumentModel;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.FormulaBlock;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.SolutionBlock;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.TextBlock;
import com.razrabotkin.systematics.Helpers.ClassHelper;
import com.razrabotkin.systematics.Helpers.ParseHelper;

import java.util.ArrayList;

/**
 * Модель задания
 */
public abstract class TaskModel {

    /** Постоянное название задания для отображения в дереве заданий */
    protected String DisplayName;

    /** Описание (текстовая часть) задания */
    protected String Description;

    /** Формула, введённая пользователем в виде строки */
    protected String FormulaString;

    /** Формула в объектном виде, разобранная парсером */
    protected FormulaModel Formula;

    /** Документ, в котором содержится данное задание */
    protected DocumentModel Document;

    /** Ответ в виде строки для отображения и вставки в документ */
    protected String AnswerString;

    /** Массив блоков решения для записи в документ */
    public ArrayList<SolutionBlock> SolutionBlocks;

    /** Массив ответов на это уравнение */
    public ArrayList<ExpressionModel> Answers;

    /** Для генерации формул */
    protected ParseHelper Helper;

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
        Helper = new ParseHelper();


        SolutionBlocks = new ArrayList<SolutionBlock>();
        Answers = new ArrayList<ExpressionModel>();
    }

    /**
     * Решает данное задание и заполняет массив SolutionBlocks блоками решения
     */
    public void solve(){
        // TODO:
    }

    /**
     * Выполняет запись задания и решения в документ
     */
    public void saveToDocument(){
        if (Document.create()){
            Document.addText(Description, true);
            Document.addFormula(Formula);
            Document.addText("Решение", true);

            // Добавляем в документ каждый из блоков, находящихся в массиве SolutionBlocks
            for (SolutionBlock solutionBlock : SolutionBlocks){

                // Определяем, формулой или текстом является блок,
                // и на основании этого добавляем его в документ тем или иным методом.
                ClassHelper helper = new ClassHelper();
                if (helper.isTypeOf(solutionBlock, TextBlock.class)){
                    TextBlock textBlock = (TextBlock) solutionBlock;
                    Document.addText(textBlock.getValue(), false);
                } else if (helper.isTypeOf(solutionBlock, FormulaBlock.class)){
                    FormulaBlock formulaBlock = (FormulaBlock) solutionBlock;
                    Document.addFormula(formulaBlock.getFormula());
                }
            }

            Document.addAnswer(AnswerString);

            Document.addBreak();

            if (Document.save()){
                System.out.println("Документ сохранён");
            } else {
                System.out.println("Не удалось сохранить документ");
            }
        } else {
            System.out.println("Не удалось создать документ");
        }
    }

    @Override
    public String toString() {
        return DisplayName;
    }

    // Методы-мутаторы
    /**
     * Возвращает постоянное название задания для отображения в дереве заданий
     * @return Постоянное название задания для отображения в дереве заданий
     */
    public String getDisplayName() {
        return DisplayName;
    }

    /**
     * Задаёт постоянное название задания для отображения в дереве заданий
     * @param displayName Постоянное название задания для отображения в дереве заданий
     */
    public void setDisplayName(String displayName) {
        DisplayName = displayName;
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
     * Возвращает ответ в виде строки для отображения и вставки в документ
     * @return Ответ в виде строки для отображения и вставки в документ
     */
    public String getAnswerString() {
        return AnswerString;
    }

    /**
     * Задаёт ответ в виде строки для отображения и вставки в документ
     * @param answerString Ответ в виде строки для отображения и вставки в документ
     */
    public void setAnswerString(String answerString) {
        AnswerString = answerString;
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
