package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.DocumentHelper;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Переменная
 */
public class VariableModel extends ExpressionModel{
    /** Имя переменной */
    private String Name;

    /** Подстрочный индекс в имени переменной */
    private String Index;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     * @param name Имя переменной
     */
    public VariableModel(AtomModel parent, String name) {
        super(parent);
        Name = name;
        Index = "";
    }

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     * @param name Имя переменной
     * @param index Подстрочный индекс переменной
     */
    public VariableModel(AtomModel parent, String name, String index) {
        this(parent, name);
        Index = index;
    }

    @Override
    public String toString() {
        return Name + Index;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        DocumentHelper helper =  new DocumentHelper();

        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        JAXBElement base = helper.createRun(Name);

        // Если переменная с нижним индексом, создаём соответствующее выражение и добавляем его в arrayList
        if (!Index.equals("")){
            JAXBElement index = helper.createRun(Index);
            JAXBElement element = helper.createSubscript(base, index);
            arrayList.add(element);
        }

        // Иначе добавляем в arrayList только элемент основания
        else {
            arrayList.add(base);
        }

        return arrayList;

        // TODO: Разобраться с тем, что переменные в формулах не ставятся курсивом.
        //  Дело в том, что буква переменной должна иметь тип CTText.
        //  Но при её использовании возникает исключение: unable to marshal type as an element because it is missing an @XmlRootElement annotation,
        //  Это означает, что в исходном коде класса CTText перед его сигнатурой не поставлен атрибут @XmlRootElement.
        //  Поставить его туда самостоятельно я не могу: код недоступен для редактирования.
        //  Пробовал создать собственный тип MyCTText - возникает исключение, что программа не знает такого типа.
        //  Пробовал создавать переменную типа Text вместо CTText - переменная отображается в формуле обычным шрифтом Times New Roman, даже не курсивом.
        //  Если просто напечатать в ворде текст шрифтом Cambria Math, то он отображается совершенно по-другому.
        //  Мой вариант решения задачи - попытаться открыть исходники для редактирования и добавить туда наконец этот несчастный атрибут.
        //  Если не получится - просто сделать шрифтом Times New Roman.
    }

    @Override
    public boolean isNumber() {
        return false;
    }

    public FormulaModel copy(FormulaModel parent) {
        VariableModel variable = new VariableModel((AtomModel) parent, Name);

        return variable;
    }

    public boolean canSolve() {
        return false;
    }

    public void solve(){
        // Получаем уравнение-родитель
        EquationModel equation = getEquation();

        // Из таблицы переменных, которая в нем находится, пытаемся извлечь значение переменной по её имени
        Double value = equation.getVariablesHashMap().get(Name);

        // Если это сделать удалось,
        if (value != null){
            // берем атом-родитель и заменяем в нем данную переменную на числовое значение
            AtomModel parent = (AtomModel) Parent;
            parent.setExpression(new NumberModel(parent, value));
            // TODO: Если значение отрицательное, возможно, стоит пробросить минус в SignedAtom, а в NumberModel держать только модуль числа
            // TODO: Если значение отрицательное, необходимо взять основание степени в скобки!
        } else {
            System.out.println("Не удалось найти значение переменной в таблице");
        }
    }

    @Override
    protected EquationModel getEquation() {
        return super.getEquation(); // TODO: Возможно, этот метод вообще не нужен
    }

    // Методы-мутаторы
    /**
     * Возвращает значение
     * @return Значение
     */
    public String getName() {
        return Name;
    }

    public void setName(String name, String index) {
        setName(name);
        Index = index;
    }
}
