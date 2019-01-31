package DataModels.Formulas;

import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Число
 */
public class NumberModel extends ExpressionModel{

    /**
     * Значение
     */
    private double mValue;

    /**
     * Инициализирует экземпляр класса
     * @param value Значение
     */
    public NumberModel(double value) {
        mValue = value;
    }

    /**
     * Возвращает значение
     * @return Значение
     */
    public double getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        NumberFormat nf = DecimalFormat.getInstance();
        nf.setMaximumFractionDigits(0);
        String str = nf.format(mValue);
        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        // Создаём элемент text (OpenXML). Присваиваем ему значение, получаемое в результате вызова метода toString()
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        Text text = wmlObjectFactory.createText();
        text.setValue(toString());

        // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
        ArrayList<JAXBElement> arrayList = super.toOpenXML();
        for (JAXBElement<org.docx4j.math.CTR> rWrapped : arrayList){
            CTR r = rWrapped.getValue();
            r.getContent().add(text);
        }
        return arrayList;
    }
}
