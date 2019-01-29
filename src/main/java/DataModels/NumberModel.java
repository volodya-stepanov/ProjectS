package DataModels;

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
    public CTR toOpenXML() {
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        Text text = wmlObjectFactory.createText();
        text.setValue(toString());

        CTR r = super.toOpenXML();
        r.getContent().add(text);

        return r;
    }
}
