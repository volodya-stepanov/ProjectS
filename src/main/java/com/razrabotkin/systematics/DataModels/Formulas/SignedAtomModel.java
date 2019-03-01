package com.razrabotkin.systematics.DataModels.Formulas;

import com.razrabotkin.systematics.Helpers.DocumentHelper;

import javax.xml.bind.JAXBElement;
import java.util.ArrayList;

/**
 * Атом со знаком
 */
public class SignedAtomModel extends FormulaModel {

    /** Признак того, что атом отрицательный */
    private boolean IsNegative = false;

    /** Атом (число, переменная или выражение в скобках), перед которым стоит знак */
    private AtomModel Atom;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     */
    public SignedAtomModel(FormulaModel parent) {
        super(parent);
    }

    /**
     * Определяет, нужно ли брать в скобки данную формулу
     * @return Истина, если данную формулу нужно брать в скобки, иначе ложь
     */
    private boolean needParenthesis(){
        return isNegative() || Atom.isNumber() && Atom.getValue() < 0;
    }

    @Override
    public String toString() {
        String str = "";
        if (IsNegative){
            str = "-";
        }

        if (needParenthesis()) {
            str += "(";
        }

        str += Atom.toString();

        if (needParenthesis()) {
            str += ")";
        }

        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        DocumentHelper helper = new DocumentHelper();
//        return helper.createRunArray(toString()); Вот сдесь собака зарыта!
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();
        if (isNegative()){
            arrayList.add(helper.createRun("-"));
        }

        if (needParenthesis()){
            arrayList.addAll(helper.createParenthesis(Atom.toOpenXML()));
        } else {
            arrayList.addAll(Atom.toOpenXML());
        }

        return arrayList;
    }

    @Override
    public boolean isNumber() {
        return Atom.isNumber();
    }

    public FormulaModel copy(FormulaModel parent) {
        SignedAtomModel signedAtom = new SignedAtomModel(parent);

        signedAtom.setNegative(IsNegative);

        signedAtom.setAtom((AtomModel) Atom.copy(signedAtom));

        return signedAtom;
    }

    public boolean canSolve() {
        return Atom.isNumber();
    }

    public void solve() {
        if (canSolve()){

        } else {
            Atom.solve();
        }
    }

    // Методы-мутаторы
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

    public double getValue() {
        if (isNumber()){
            //NumberModel number = (NumberModel) Atom.getFunction();
            double value = Atom.getValue();
            if (IsNegative){
                //return -number.getValue();
                return -value;
            } else {
                //return number.getValue();
                return value;
            }
        } else {
            System.out.println("Не удаётся получить значение, так как выражение не является числом");
            return 0;
        }
    }

    public void setValue(double value){
        if (Atom == null){
            Atom = new AtomModel(this);
        }

        Atom.setValue(value);
        IsNegative = false;
    }

    public void setName(String name) {
        if (Atom == null){
            Atom = new AtomModel(this);
        }

        Atom.setName(name);
        IsNegative = false;
    }

    public void setName(String name, String index) {
        if (Atom == null){
            Atom = new AtomModel(this);
        }

        Atom.setName(name, index);
        IsNegative = false;
    }
}
