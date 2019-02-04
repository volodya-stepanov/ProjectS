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

    /** Значение */
    private double mValue;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     * @param value Значение
     */
    public NumberModel(AtomModel parent, double value) {
        super(parent);
        mValue = value;
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

    @Override
    public boolean isNumber() {
        return true;
    }

    public FormulaModel copy(FormulaModel parent) {
        NumberModel number = new NumberModel((AtomModel) parent, mValue);

        return number;
    }

    public boolean canSolve() {
        return true;
    }

    // Методы-мутаторы
    /**
     * Возвращает значение
     * @return Значение
     */
    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        mValue = value;
    }
}
