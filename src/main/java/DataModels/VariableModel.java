package DataModels;

import Helpers.ClassHelper;
import org.docx4j.math.CTR;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;

/**
 * Переменная
 */
public class VariableModel extends ExpressionModel{
    /**
     * Имя переменной
     */
    private String mValue;

    /**
     * Инициализирует экземпляр класса
     * @param value Значение
     */
    public VariableModel(String value) {
        mValue = value;
    }

    /**
     * Возвращает значение
     * @return Значение
     */
    public String getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return mValue;
    }

    @Override
    public JAXBElement toOpenXML() {
        // Создаём элемент text (OpenXML). Присваиваем ему значение, получаемое в результате вызова метода toString()
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        Text text = wmlObjectFactory.createText();
        text.setValue(toString());

        // Получаем элемент rWrapped (OpenXML), извлекаем из него элемент r, добавляем к нему элемент text и возвращаем его
        JAXBElement<org.docx4j.math.CTR> rWrapped = super.toOpenXML();
        CTR r = rWrapped.getValue();
        r.getContent().add(text);

        return rWrapped;

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
}
