package com.razrabotkin.systematics.DataModels.Tasks;

import com.razrabotkin.systematics.DataModels.Formulas.*;
import com.razrabotkin.systematics.DataModels.Objects.DocumentModel;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.FormulaBlock;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.SolutionBlock;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.TextBlock;
import com.razrabotkin.systematics.Helpers.ClassHelper;
import com.razrabotkin.systematics.Helpers.ParseHelper;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class QuadraticEquation extends TaskModel{

    /** Массив ответов на это уравнение */
    public ArrayList<Double> Answers;

    /** Для генерации формул */
    private ParseHelper Helper;

    /**
     * Инициализирует экземпляр класса
     *
     * @param document      Документ, в котором содержится данное задание
     * @param description   Описание (текстовая часть) задания
     * @param formulaString Формула, введённая пользователем в виде строки
     */
    public QuadraticEquation(DocumentModel document, String description, String formulaString) {
        super(document, description, formulaString);
        DisplayName = "Квадратное уравнение";
        Answers = new ArrayList<Double>();
        Helper = new ParseHelper();

        Formula = Helper.parseEquation(formulaString);
    }

    @Override
    public void saveToDocument() {
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

            Document.addAnswer(Answer);

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
    public void solve() {


        Formula.getVariablesHashMap().put("c", 0.0);

        findCoefficients();

        SolutionBlocks.add(new TextBlock("Вычисляем дискриминант"));
        double discriminant = findDiscriminant();

        if (discriminant > 0){
            SolutionBlocks.add(new TextBlock("Дискриминант больше нуля, следовательно, уравнение имеет два корня"));
            double x1 = findX1();
            double x2 = findX2();

            Answers.add(x1);
            Answers.add(x2);
        } else if (discriminant == 0){
            SolutionBlocks.add(new TextBlock("Дискриминант равен нулю, следовательно, уравнение имеет один корень"));
            double x = findX();
            Answers.add(x);
        } else {
            SolutionBlocks.add(new TextBlock("Дискриминант меньше нуля, следовательно, уравнение не имеет действительных корней"));
        }

        // Добавляем в документ ответ
        NumberFormat nf = new DecimalFormat("#.######");

        if (Answers.size() == 2){
            String x1 = nf.format(Answers.get(0));
            String x2 = nf.format(Answers.get(1));
            Answer = "x1 = " + x1 + "; x2 = " + x2 + ".";
        } else if (Answers.size() == 1) {
            String x = nf.format(Answers.get(0));
            Answer = "x1,2 = " + x + ".";
        } else {
            Answer = "Нет корней.";
        }
    }

    /**
     * Находит коэффициенты квадратного уравнения и записывает их в хэш-таблицу VariablesHashMap объекта Formula
     */
    private void findCoefficients() {
        // Приводим формулу к типу квадратного уравнения
        EquationModel quadraticEquation = (EquationModel) Formula;

        // Перебираем члены выражения, стоящего в левой части
        for (TermModel term : quadraticEquation.Expressions.get(0).Terms){

            // Определяем знак, стоящий перед членом
            boolean isNegative = false;

            if (term.getMathOperation().equals(MathOpModel.Minus)){
                isNegative = true;
            }

            FactorModel firstFactor = term.Factors.get(0);



            // Если член имеет два множителя
            if (term.Factors.size() == 2){

                FactorModel secondFactor = term.Factors.get(1);

                // Если второй множитель имеет степень
                if (secondFactor.getExponent().getValue() == 2){

                    // Извлекаем показатель степени
                    //NumberModel exponentNumberModel = (NumberModel)secondFactor.getExponent().getAtom().getExpression();

                    // Если показатель степени равен двум
                    //if(exponentNumberModel.getName() == 2){

                        // Извлекаем значение коэффициента
                        NumberModel coefANumberModel = (NumberModel)firstFactor.getBase().getAtom().getExpression();

                        // Полученное число - значение коэффициента A
                        double coefA = coefANumberModel.getValue();

                        // Если перед членом стоит минус, меняем знак коэффициента A
                        if (isNegative) {
                            coefA = -coefA;
                        }

                        // Заносим значение коэффициента в хэш-таблицу
                        Formula.getVariablesHashMap().put("a", coefA);
//                    } else {
//                        System.out.println("Множитель имеет степень, но показатель степени не равен двум");
//                    }
                }
                // Иначе (если второй множитель не имеет степени)
                else if (secondFactor.getExponent().getValue() == 1){
                    // Извлекаем значение коэффициента
                    NumberModel coefBNumberModel = (NumberModel)firstFactor.getBase().getAtom().getExpression();

                    // Полученное число - значение коэффициента B
                    double coefB = coefBNumberModel.getValue();

                    // Если перед членом стоит минус, меняем знак коэффициента B
                    if (isNegative) {
                        coefB = -coefB;
                    }

                    // Заносим значение коэффициента в хэш-таблицу
                    Formula.getVariablesHashMap().put("b", coefB);
                }
            }
            // Иначе (если член имеет только один множитель)
            else {
                // Определяем тип этого множителя - число или переменная
                Object obj = firstFactor.getBase().getAtom().getExpression();
                ClassHelper helper = new ClassHelper();

                // Если это переменная
                if (helper.isTypeOf(obj, VariableModel.class)){

                    // Если эта переменная имеет степень
                    if (firstFactor.getExponent().getValue() != 1){

                        // Извлекаем показатель степени
                        NumberModel exponentNumberModel = (NumberModel)firstFactor.getExponent().getAtom().getExpression();

                        // Если показатель степени равен двум
                        if(exponentNumberModel.getValue() == 2){

                            // Коэффициент A равен 1
                            double coefA = 1;

                            // Если само основание степени является отрицательным
                            if (firstFactor.getBase().isNegative()){
                                coefA = -coefA;
                            }

                            // Если перед членом стоит минус, меняем знак коэффициента A
                            if (isNegative) {
                                coefA = -coefA;
                            }

                            // Заносим значение коэффициента в хэш-таблицу
                            Formula.getVariablesHashMap().put("a", coefA);
                        } else {
                            System.out.println("Множитель имеет степень, но показатель степени не равен двум");
                        }
                    }
                    // Иначе (если второй множитель не имеет степени)
                    else{
                        // Коэффициент B равен 1
                        double coefB = 1;

                        // Если перед членом стоит минус, меняем знак коэффициента B
                        if (isNegative) {
                            coefB = -coefB;
                        }

                        // Заносим значение коэффициента в хэш-таблицу
                        Formula.getVariablesHashMap().put("b", coefB);
                    }
                }
                // Иначе (если множитель - число)
                else if (helper.isTypeOf(obj, NumberModel.class)){
                    // Извлекаем значение коэффициента
                    NumberModel coefCNumberModel = (NumberModel)firstFactor.getBase().getAtom().getExpression();

                    // Полученное число - значение коэффициента C
                    double coefC = coefCNumberModel.getValue();

                    // Если перед членом стоит минус, меняем знак коэффициента C
                    if (isNegative) {
                        coefC = -coefC;
                    }

                    // Заносим значение коэффициента в хэш-таблицу
                    Formula.getVariablesHashMap().put("c", coefC);
                }
                // Иначе (если множитель - не число и не переменная)
                else {
                    System.out.println("Множитель - не число и не переменная");
                }
            }
        }
    }

    /**
     * Вычисляет дискриминант, ход решения записывает в массив SolutionBlocks, а результат - в хэш-таблицу VariablesHashMap
     */
    private double findDiscriminant(){
        EquationModel dFormula = Helper.parseEquation("D = b^2 - 4*a*c");

        // TODO: Некрасиво, что приходится перекидывать таблицу значений из одного уравнения в другое.
        //  Возможно, можно держать в уравнении ссылку на задание, к которому оно принадлежит, а таблцу - уже в самом задании
        dFormula.setVariablesHashMap(Formula.getVariablesHashMap());

        // TODO: Всё это нужно перенести в EquationModel

        while (!dFormula.Expressions.get(dFormula.Expressions.size()-1).isNumber()) {
            ExpressionModel lastExpression = dFormula.Expressions.get(dFormula.Expressions.size()-1);

            // Копируем правую часть дискриминанта и добавляем её к уравнению
            ExpressionModel newExpression = (ExpressionModel) lastExpression.copy(dFormula);

            newExpression.solve();

            dFormula.Expressions.add(newExpression);
        }

        SolutionBlocks.add(new FormulaBlock(dFormula));

        double value = dFormula.Expressions.get(dFormula.Expressions.size()-1).getValue();

        Formula.getVariablesHashMap().put("D", value);

        return value;
    }

    /**
     * Находит единственный корень квадратного уравнения
     * @return Значение корня
     */
    private double findX() {
        EquationModel xFormula = Helper.parseEquation("x = -b/(2*a)");

        // TODO: Некрасиво, что приходится перекидывать таблицу значений из одного уравнения в другое.
        //  Возможно, можно держать в уравнении ссылку на задание, к которому оно принадлежит, а таблцу - уже в самом задании
        xFormula.setVariablesHashMap(Formula.getVariablesHashMap());

        while (!xFormula.Expressions.get(xFormula.Expressions.size()-1).isNumber()) {
            ExpressionModel lastExpression = xFormula.Expressions.get(xFormula.Expressions.size()-1);

            // Копируем правую часть и добавляем её к уравнению
            ExpressionModel newExpression = (ExpressionModel) lastExpression.copy(xFormula);

            newExpression.solve();

            xFormula.Expressions.add(newExpression);
        }

        SolutionBlocks.add(new FormulaBlock(xFormula));

        return xFormula.Expressions.get(xFormula.Expressions.size()-1).getValue();
    }

    /**
     * Находит первый корень квадратного уравнения
     * @return Значение корня X1
     */
    private double findX1() {
        EquationModel x1Formula = Helper.parseEquation("x = (-b+sqrt(D))/(2*a)");

        // TODO: Некрасиво, что приходится перекидывать таблицу значений из одного уравнения в другое.
        //  Возможно, можно держать в уравнении ссылку на задание, к которому оно принадлежит, а таблцу - уже в самом задании
        x1Formula.setVariablesHashMap(Formula.getVariablesHashMap());

        while (!x1Formula.Expressions.get(x1Formula.Expressions.size()-1).isNumber()) {
            ExpressionModel lastExpression = x1Formula.Expressions.get(x1Formula.Expressions.size()-1);

            // Копируем правую часть и добавляем её к уравнению
            ExpressionModel newExpression = (ExpressionModel) lastExpression.copy(x1Formula);

            newExpression.solve();

            x1Formula.Expressions.add(newExpression);
        }

        SolutionBlocks.add(new FormulaBlock(x1Formula));

        return x1Formula.Expressions.get(x1Formula.Expressions.size()-1).getValue();
    }

    /**
     * Находит второй корень квадратного уравнения
     * @return Значение корня X1
     */
    private double findX2() {
        EquationModel x2Formula = Helper.parseEquation("x = (-b-sqrt(D))/(2*a)");

        // TODO: Некрасиво, что приходится перекидывать таблицу значений из одного уравнения в другое.
        //  Возможно, можно держать в уравнении ссылку на задание, к которому оно принадлежит, а таблцу - уже в самом задании
        x2Formula.setVariablesHashMap(Formula.getVariablesHashMap());

        while (!x2Formula.Expressions.get(x2Formula.Expressions.size()-1).isNumber()) {
            ExpressionModel lastExpression = x2Formula.Expressions.get(x2Formula.Expressions.size()-1);

            // Копируем правую часть и добавляем её к уравнению
            ExpressionModel newExpression = (ExpressionModel) lastExpression.copy(x2Formula);

            newExpression.solve();

            x2Formula.Expressions.add(newExpression);
        }

        SolutionBlocks.add(new FormulaBlock(x2Formula));

        return x2Formula.Expressions.get(x2Formula.Expressions.size()-1).getValue();
    }
}
