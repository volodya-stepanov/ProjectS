package DataModels.Formulas;

import org.docx4j.math.CTR;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Атом со знаком
 */
public class SignedAtomModel extends FormulaModel {
    private boolean IsNegative = false;

    private AtomModel Atom;

    public void setNegative(boolean negative) {
        IsNegative = negative;
    }

    public boolean isNegative(){
        return IsNegative;
    }

    public void setAtom(AtomModel atom) {
        Atom = atom;
    }

    public AtomModel getAtom(){
        return Atom;
    }

    @Override
    public String toString() {
        String str = "";
        if (IsNegative){
            str = "-";
        }
        str += Atom.toString();
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
