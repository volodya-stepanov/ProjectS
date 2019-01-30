package DataModels;

import Helpers.ClassHelper;
import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
    }
}
