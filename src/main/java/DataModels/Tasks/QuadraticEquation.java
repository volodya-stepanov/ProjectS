package DataModels.Tasks;

import DataModels.Formulas.*;
import DataModels.Objects.DocumentModel;
import DataModels.SolutionBlocks.FormulaBlock;
import DataModels.SolutionBlocks.SolutionBlock;
import DataModels.SolutionBlocks.TextBlock;
import Helpers.ClassHelper;

public class QuadraticEquation extends TaskModel{

    /**
     * Инициализирует экземпляр класса
     *
     * @param document      Документ, в котором содержится данное задание
     * @param description   Описание (текстовая часть) задания
     * @param formulaString Формула, введённая пользователем в виде строки
     */
    public QuadraticEquation(DocumentModel document, String description, String formulaString) {
        super(document, description, formulaString);
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
        findDiscriminant();
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
                    if (firstFactor.getExponent()!=null){

                        // Извлекаем показатель степени
                        NumberModel exponentNumberModel = (NumberModel)firstFactor.getExponent().getAtom().getExpression();

                        // Если показатель степени равен двум
                        if(exponentNumberModel.getValue() == 2){

                            // Коэффициент A равен 1
                            double coefA = 1;

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
    private void findDiscriminant(){
        EquationModel dFormula = new EquationModel(null);

        // TODO: Некрасиво, что приходится перекидывать таблицу значений из одного уравнения в другое.
        //  Возможно, можно держать в уравнении ссылку на задание, к которому оно принадлежит, а таблцу - уже в самом задании
        dFormula.setVariablesHashMap(Formula.getVariablesHashMap());

        // Левая часть
        ExpressionModel expression1 = new ExpressionModel(dFormula);
        dFormula.Expressions.add(expression1);

        TermModel term1 = new TermModel(expression1);
        expression1.Terms.add(term1);

        FactorModel factor1 = new FactorModel(term1);
        term1.Factors.add(factor1);

        SignedAtomModel signedAtom1 = new SignedAtomModel(factor1);
        factor1.setBase(signedAtom1);

        AtomModel atom1 = new AtomModel(signedAtom1);
        signedAtom1.setAtom(atom1);

        VariableModel variableModel1 = new VariableModel(atom1, "D");
        atom1.setExpression(variableModel1);

        // Правая часть
        ExpressionModel expression2 = new ExpressionModel(dFormula);
        expression2.setRelation(RelOpModel.Equals);
        dFormula.Expressions.add(expression2);

        // Первый член: b^2
        TermModel term2 = new TermModel(expression2);
        expression2.Terms.add(term2);

        FactorModel factor3 = new FactorModel(term2);
        term2.Factors.add(factor3);

        // Добавляем основание степени: b
        SignedAtomModel signedAtom2 = new SignedAtomModel(factor3);
        factor3.setBase(signedAtom2);

        AtomModel atom2 = new AtomModel(signedAtom2);
        signedAtom2.setAtom(atom2);

        VariableModel variableModel2 = new VariableModel(atom2, "b");
        atom2.setExpression(variableModel2);

        // Добавляем показатель степени: 2
        SignedAtomModel signedAtom3 = new SignedAtomModel(factor3);
        factor3.setExponent(signedAtom3);

        AtomModel atom3 = new AtomModel(signedAtom3);
        signedAtom3.setAtom(atom3);

        NumberModel numberModel1 = new NumberModel(atom3, 2.0);
        atom3.setExpression(numberModel1);

        // Второй член: -4*a*c
        TermModel term3 = new TermModel(expression2);
        term3.setMathOperation(MathOpModel.Minus);
        expression2.Terms.add(term3);

        // Первый член: 4
        FactorModel factor4 = new FactorModel(term3);
        term3.Factors.add(factor4);

        SignedAtomModel signedAtom4 = new SignedAtomModel(factor4);
        factor4.setBase(signedAtom4);

        AtomModel atom4 = new AtomModel(signedAtom4);
        signedAtom4.setAtom(atom4);

        NumberModel numberModel2 = new NumberModel(atom4, 4);
        atom4.setExpression(numberModel2);

        // Второй член: a
        FactorModel factor5 = new FactorModel(term3);
        factor5.setMathOperation(MathOpModel.Multiply);
        term3.Factors.add(factor5);

        SignedAtomModel signedAtom5 = new SignedAtomModel(factor5);
        factor5.setBase(signedAtom5);

        AtomModel atom5 = new AtomModel(signedAtom5);
        signedAtom5.setAtom(atom5);

        VariableModel variableModel3 = new VariableModel(atom5, "a");
        atom5.setExpression(variableModel3);

        // Третий член: c
        FactorModel factor6 = new FactorModel(term3);
        factor6.setMathOperation(MathOpModel.Multiply);
        term3.Factors.add(factor6);

        SignedAtomModel signedAtom6 = new SignedAtomModel(factor6);
        factor6.setBase(signedAtom6);

        AtomModel atom7 = new AtomModel(signedAtom6);
        signedAtom6.setAtom(atom7);

        VariableModel variableModel4 = new VariableModel(atom7, "c");
        atom7.setExpression(variableModel4);

        // TODO: Всё это нужно перенести в EquationModel

        while (!dFormula.Expressions.get(dFormula.Expressions.size()-1).isNumber()) {
            ExpressionModel lastExpression = dFormula.Expressions.get(dFormula.Expressions.size()-1);

            // Копируем правую часть дискриминанта и добавляем её к уравнению
            ExpressionModel newExpression = (ExpressionModel) lastExpression.copy(dFormula);

            newExpression.solve();

            dFormula.Expressions.add(newExpression);
        }

        SolutionBlocks.add(new FormulaBlock(dFormula));
    }
}
