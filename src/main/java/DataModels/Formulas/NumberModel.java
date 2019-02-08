package DataModels.Formulas;

import Helpers.DocumentHelper;

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
        NumberFormat nf = new DecimalFormat("#.######");
        String str = nf.format(mValue);
        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        DocumentHelper helper =  new DocumentHelper();
        return helper.createRunArray(toString());
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
