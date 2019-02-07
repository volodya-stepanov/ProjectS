package DataModels.Formulas;

import DataModels.Objects.DocumentHelper;
import org.docx4j.math.CTR;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Переменная
 */
public class VariableModel extends ExpressionModel{
    /** Имя переменной */
    private String Name;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     * @param value Значение
     */
    public VariableModel(AtomModel parent, String value) {
        super(parent);
        Name = value;
    }



    @Override
    public String toString() {
        return Name;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        DocumentHelper helper =  new DocumentHelper();
        return helper.createRunArray(toString());

//        // Создаём элемент text (OpenXML). Присваиваем ему значение, получаемое в результате вызова метода toString()
//        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
//        Text text = wmlObjectFactory.createText();
//        text.setValue(toString());
//
//        // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
//        ArrayList<JAXBElement> arrayList = super.toOpenXML();
//        for (JAXBElement<org.docx4j.math.CTR> rWrapped : arrayList){
//            CTR r = rWrapped.getValue();
//            r.getContent().add(text);
//        }
//        return arrayList;

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
        return super.getEquation();

//        AtomModel atom = (AtomModel) getParent();
//        SignedAtomModel signedAtom = (SignedAtomModel) atom.getParent();
//        FactorModel factor = (FactorModel) signedAtom.getParent();
//        TermModel term = (TermModel) factor.getParent();
//        ExpressionModel expression = (ExpressionModel) term.getParent();
//        EquationModel equation = (EquationModel) expression.getParent();
//        return equation;

    }

    // Методы-мутаторы
    /**
     * Возвращает значение
     * @return Значение
     */
    public String getName() {
        return Name;
    }
}
