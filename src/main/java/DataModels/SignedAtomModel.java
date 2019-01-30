package DataModels;

import Helpers.ClassHelper;
import org.docx4j.math.CTR;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;

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
