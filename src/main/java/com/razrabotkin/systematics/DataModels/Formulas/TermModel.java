package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.DocumentHelper;
import com.razrabotkin.systematics.Helpers.ClassHelper;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Член
 */
public class TermModel extends FormulaModel {

    /** Множители */
    public ArrayList<FactorModel> Factors;

    /** Математическая операция, которая стоит перед членом (плюс или минус) */
    private MathOpModel MathOperation;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     */
    public TermModel(ExpressionModel parent){
        super(parent);
        Factors = new ArrayList<FactorModel>();
        MathOperation = MathOpModel.None;
    }

    public TermModel(ExpressionModel parent, double value) {
        this(parent);
        setValue(value);
    }

    public TermModel(ExpressionModel parent, String name) {
        this(parent);
        setName(name);
    }

    public TermModel(ExpressionModel parent, String name, String index) {
        this(parent);
        setName(name, index);
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < Factors.size(); i++){
            FactorModel factorModel = Factors.get(i);

            if (factorModel.getMathOperation().equals(MathOpModel.Multiply)){
                str+="*";
            } else if (factorModel.getMathOperation().equals(MathOpModel.Divide)){
                str+="/";
            }

            str += factorModel.toString();
        }

        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {

        DocumentHelper documentHelper = new DocumentHelper();

        // Создаём ArrayList для возврата из этого метода
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        // Перебираем множители
        for (FactorModel factorModel : Factors){

            // В зависимости от знака, стоящего перед множителем, добавляем соответствующий в ArrayList, который будет записан в документ
            if (factorModel.getMathOperation().equals(MathOpModel.Multiply)){
                // Знак умножения ставим только в том случае, если следующий множитель - число. Перед переменными знак умножения не ставим
                // Определяем тип множителя
                Object obj = factorModel.getBase().getAtom().getExpression();
                ClassHelper classHelper = new ClassHelper();
                if (classHelper.isTypeOf(obj, NumberModel.class)) {
                    JAXBElement signElement = documentHelper.createRun("∙");
                    arrayList.add(signElement);
                }
            } else if (factorModel.getMathOperation().equals(MathOpModel.Divide)){
                // С помощью метода createFraction() получаем JAXB-элемент, представляющий собой дробь
                arrayList = createFraction(arrayList, factorModel);

                // TODO: Костыль. Цикл прерывается принудительно, так как в противном случае после выполнения метода createFraction элемент, перед со знаком деления остаётся в массиве и добавляется к дроби в качестве множителя
                break;
            }

            // Теперь добавляем в массива все элементы массива, возвращенного методом toOpenXML() множителя
            for (JAXBElement rWrapped : factorModel.toOpenXML()){
                arrayList.add(rWrapped);
            }
        }

        return arrayList;
    }

    /**
     * Разделяет массив множителей на два массива: числитель и знаменатель.
     * Генерирует массив JAXB-элементов с дробью и заменяет им исходный массив JAXB-элементов-множителей
     *
     * @param arrayList Массив JAXB-элементов (множителей), сформированнй ранее
     * @param factor Множитель, перед которым стоит знак деления и который будет помещён в знаменатель
     * @return Массив JAXB-элементов, представляющий дробь
     */
    private ArrayList<JAXBElement> createFraction(ArrayList<JAXBElement> arrayList, FactorModel factor) {
        // Создаём массив, в котором будут храниться все элементы, относящиеся к числителю
        ArrayList<JAXBElement> numeratorArrayList = new ArrayList<JAXBElement>();

        // Добавляем в массив числителя все элементы массива, сгенерированного методом toOpenXML() ранее для всех множителей
        numeratorArrayList.addAll(arrayList);

        // Создаём массив, в котором будут храниться все элементы, относящиеся к числителю
        ArrayList<JAXBElement> denominatorArrayList = new ArrayList<JAXBElement>();

        // Добавляем в массив знаменателя все элементы массива, сгенерированного методом toOpenXML() для множителя, перед которым стоит знак деления
        denominatorArrayList.addAll(factor.toOpenXML());

        // Создаём JAXB-элемент с дробью
        DocumentHelper helper = new DocumentHelper();
        JAXBElement fractionElement = helper.createFraction(numeratorArrayList, denominatorArrayList);

        // Удаляем из исходного массива все элементы (множители) и добавляем туда элемент с дробью. Возвращаем массив.
        arrayList.clear();
        arrayList.add(fractionElement);
        return arrayList;
    }

    @Override
    public boolean isNumber() {
        // Произведение считается числом тогда, когда оно имеет только один множитель, и он является числом
        return Factors.size() == 1 && Factors.get(0).isNumber();
    }

    @Override
    public boolean isVariable() {
        // Произведение считается числом тогда, когда оно имеет только один множитель, и он является числом
        return Factors.size() == 1 && Factors.get(0).isVariable();
    }

    @Override
    public boolean isResult() {
        for (FactorModel factor : Factors){
            if (!factor.isResult()){
                return false;
            }
        }

        return true;
    }

    public FormulaModel copy(FormulaModel parent) {
        TermModel term = new TermModel((ExpressionModel) parent);
        term.setMathOperation(MathOperation);

        for (FactorModel factor : Factors){
            term.Factors.add((FactorModel) factor.copy(term));
        }

        return term;
    }

    public boolean canSolve() {
        boolean canSolve = true;

        for (FactorModel factor : Factors){
            if (!factor.isNumber()){
                canSolve = false;
                break;
            }
        }

        return canSolve;
    }

    public void solve() {
        if(canSolve()){
            double result = Factors.get(0).getValue();

            for(int i = 1; i < Factors.size(); i++){
                FactorModel factor = Factors.get(i);
                if (factor.getMathOperation().equals(MathOpModel.Multiply)) {
                    result *= factor.getValue();
                } else if (factor.getMathOperation().equals(MathOpModel.Divide)){
                    result /= factor.getValue();
                }
            }

            setValue(result);
        } else {
            for(FactorModel factor : Factors){
                factor.solve();
            }
        }
    }

    // Методы-мутаторы
    public MathOpModel getMathOperation() {
        return MathOperation;
    }

    public void setMathOperation(MathOpModel mathOperation) {
        MathOperation = mathOperation;
    }

    public double getValue() {
        if (isNumber()){
            return Factors.get(0).getValue();
        } else {
            System.out.println("Не удаётся получить значение, так как выражение не является числом");
            return 0;
        }
    }

    public void setValue(double value) {
        Factors.clear();
        Factors.add(new FactorModel(this, value));
    }

    public void setName(String name) {
        Factors.clear();
        Factors.add(new FactorModel(this, name));
    }

    public void setName(String name, String index) {
        Factors.clear();
        Factors.add(new FactorModel(this, name, index));
    }
}
