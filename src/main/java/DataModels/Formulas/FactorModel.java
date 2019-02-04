package DataModels.Formulas;

import Helpers.ClassHelper;
import org.docx4j.math.*;
import org.docx4j.math.ObjectFactory;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Множитель (атом со знаком, возведённый в степень)
 */
public class FactorModel extends FormulaModel {

    /** Основание степени */
    private SignedAtomModel Base;

    /** Показатель степени */
    private SignedAtomModel Exponent;

    /** Математическая операция, которая стоит перед множителем (умножить или разделить) */
    private MathOpModel MathOperation;

    /**
     * Инициализиурет экземпляр класса
     * @param parent Родитель
     */
    public FactorModel(TermModel parent){
        super(parent);
        MathOperation = MathOpModel.None;

        // TODO: Убрать везде проверку показателя степени на null
        // Инициализируем показатель степени единицей
        Exponent = new SignedAtomModel(this);

        AtomModel exponentAtom = new AtomModel(Exponent);
        Exponent.setAtom(exponentAtom);

        NumberModel exponentNumber = new NumberModel(exponentAtom, 1);
        exponentAtom.setExpression(exponentNumber);
    }

    @Override
    public String toString() {
        if (showExponent()){
            if (Base.isNumber()){
                NumberModel number = (NumberModel) Base.getAtom().getExpression();
                if (number.getValue() < 0){
                    return "(" + Base.toString() + ")^" + Exponent.toString();
                }
            }
            return Base.toString() + "^" + Exponent.toString();
        }
        return Base.toString();
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {
        if (showExponent()){
            org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
            CTSSup ssup = mathObjectFactory.createCTSSup();
            JAXBElement<org.docx4j.math.CTSSup> ssupWrapped = mathObjectFactory.createCTOMathSSup(ssup);
            // Create object for e
            CTOMathArg omatharg = mathObjectFactory.createCTOMathArg();
            ssup.setE(omatharg);
            // Create object for r (wrapped in JAXBElement)
            org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();

            // Заполняем основание степени
            if (Base.isNumber()){
                NumberModel number = (NumberModel) Base.getAtom().getExpression();
                if (number.getValue() < 0){
                    CTD d = mathObjectFactory.createCTD();
                    JAXBElement<org.docx4j.math.CTD> dWrapped = mathObjectFactory.createCTOMathArgD(d);
                    omatharg.getEGOMathElements().add( dWrapped);
                    // Create object for e
                    CTOMathArg omatharg2 = mathObjectFactory.createCTOMathArg();
                    d.getE().add( omatharg2);
                    // Create object for r (wrapped in JAXBElement)

                    // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
                    ArrayList<JAXBElement> arrayList1 = Base.toOpenXML();
                    for (JAXBElement<org.docx4j.math.CTR> rWrapped : arrayList1){
                        omatharg2.getEGOMathElements().add( rWrapped);
                    }

//                    CTR r = mathObjectFactory.createCTR();
//                    JAXBElement<org.docx4j.math.CTR> rWrapped = mathObjectFactory.createCTOMathArgR(r);
//                    omatharg2.getEGOMathElements().add( rWrapped);
//                    // Create object for rPr (wrapped in JAXBElement)
//                    RPr rpr = wmlObjectFactory.createRPr();
//                    JAXBElement<org.docx4j.wml.RPr> rprWrapped = wmlObjectFactory.createSdtPrRPr(rpr);
//                    r.getContent().add( rprWrapped);
//                    // Create object for rFonts
//                    RFonts rfonts = wmlObjectFactory.createRFonts();
//                    rpr.setRFonts(rfonts);
//                    rfonts.setAscii( "Cambria Math");
//                    rfonts.setCs( "Times New Roman");
//                    rfonts.setHAnsi( "Cambria Math");
//                    // Create object for sz
//                    HpsMeasure hpsmeasure = wmlObjectFactory.createHpsMeasure();
//                    rpr.setSz(hpsmeasure);
//                    hpsmeasure.setVal( BigInteger.valueOf( 28) );
//                    // Create object for szCs
//                    HpsMeasure hpsmeasure2 = wmlObjectFactory.createHpsMeasure();
//                    rpr.setSzCs(hpsmeasure2);
//                    hpsmeasure2.setVal( BigInteger.valueOf( 28) );
//                    // Create object for t
//                    Text text = wmlObjectFactory.createText();
//                    r.getContent().add( text);
//                    text.setValue( "-5");
                    // Create object for dPr
                    CTDPr dpr = mathObjectFactory.createCTDPr();
                    d.setDPr(dpr);
                    // Create object for ctrlPr
                    CTCtrlPr ctrlpr = mathObjectFactory.createCTCtrlPr();
                    dpr.setCtrlPr(ctrlpr);
                    // Create object for rPr
                    RPr rpr2 = wmlObjectFactory.createRPr();
                    ctrlpr.setRPr(rpr2);
                    // Create object for rFonts
                    RFonts rfonts2 = wmlObjectFactory.createRFonts();
                    rpr2.setRFonts(rfonts2);
                    rfonts2.setAscii( "Cambria Math");
                    rfonts2.setCs( "Times New Roman");
                    rfonts2.setHAnsi( "Cambria Math");
                    // Create object for sz
                    HpsMeasure hpsmeasure3 = wmlObjectFactory.createHpsMeasure();
                    rpr2.setSz(hpsmeasure3);
                    hpsmeasure3.setVal( BigInteger.valueOf( 28) );
                    // Create object for i
                    BooleanDefaultTrue booleandefaulttrue = wmlObjectFactory.createBooleanDefaultTrue();
                    rpr2.setI(booleandefaulttrue);
                    // Create object for szCs
                    HpsMeasure hpsmeasure4 = wmlObjectFactory.createHpsMeasure();
                    rpr2.setSzCs(hpsmeasure4);
                    hpsmeasure4.setVal( BigInteger.valueOf( 28) );
                } else {
                    // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
                    ArrayList<JAXBElement> arrayList1 = Base.toOpenXML();
                    for (JAXBElement<org.docx4j.math.CTR> rWrapped : arrayList1){
                        omatharg.getEGOMathElements().add( rWrapped);
                    }
                }
            } else {
                // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
                ArrayList<JAXBElement> arrayList1 = Base.toOpenXML();
                for (JAXBElement<org.docx4j.math.CTR> rWrapped : arrayList1){
                    omatharg.getEGOMathElements().add( rWrapped);
                }
            }

            // Create object for sup
            CTOMathArg omatharg2 = mathObjectFactory.createCTOMathArg();
            ssup.setSup(omatharg2);
            // Create object for r (wrapped in JAXBElement)

            // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
            ArrayList<JAXBElement> arrayList2 = Exponent.toOpenXML();
            for (JAXBElement<org.docx4j.math.CTR> rWrapped : arrayList2){
                omatharg2.getEGOMathElements().add( rWrapped);
            }

            // Create object for sSupPr
            CTSSupPr ssuppr = mathObjectFactory.createCTSSupPr();
            ssup.setSSupPr(ssuppr);
            // Create object for ctrlPr
            CTCtrlPr ctrlpr = mathObjectFactory.createCTCtrlPr();
            ssuppr.setCtrlPr(ctrlpr);
            // Create object for rPr
            RPr rpr3 = wmlObjectFactory.createRPr();
            ctrlpr.setRPr(rpr3);
            // Create object for rFonts
            RFonts rfonts3 = wmlObjectFactory.createRFonts();
            rpr3.setRFonts(rfonts3);
            rfonts3.setAscii( "Cambria Math");
            rfonts3.setHAnsi( "Cambria Math");
            // Create object for i
            BooleanDefaultTrue booleandefaulttrue = wmlObjectFactory.createBooleanDefaultTrue();
            rpr3.setI(booleandefaulttrue);

            ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();
            arrayList.add(ssupWrapped);
            return arrayList;
        } else{
            return Base.toOpenXML();
        }
    }

    /**
     * Определяет, нужно ли выводить показатель степени
     * @return Истина, если показатель степени нужно выводить, иначе ложь
     */
    private boolean showExponent(){
        boolean isPositive = false;

        if (Exponent != null) {

            // Определяем, нужно ли ставить минус перед показателем степени
            isPositive = !Exponent.isNegative();

            if (isPositive) {
                // Определяем, является ли показатель степени числом
                ClassHelper classHelper = new ClassHelper();
                Object obj = Exponent.getAtom().getExpression();
                if (classHelper.isTypeOf(obj, NumberModel.class)) {
                    NumberModel numberModel = (NumberModel) Exponent.getAtom().getExpression();

                    // Если показатель степени - единица, выводим только основание
                    if (numberModel.getValue() == 1) {
                        return false;
                    }
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean isNumber() {
        // Множитель является числом тогда, когда основание степени является числом,
        // а показатель степени либо отсутствует, либо тоже является числом и равен единице
        if (Base.isNumber()){
            if (Exponent == null) {
                return true;
            } else if (Exponent.isNumber()){
                NumberModel number = (NumberModel) Exponent.getAtom().getExpression();
                if (number.getValue() == 1){
                    return true;
                }
            }
        }
        return false;
    }

    public FormulaModel copy(FormulaModel parent) {
        FactorModel factor = new FactorModel((TermModel) parent);

        factor.setMathOperation(MathOperation);

        factor.setBase((SignedAtomModel) Base.copy(factor));

        if (Exponent != null){
            factor.setExponent((SignedAtomModel) Exponent.copy(factor));
        }

        return factor;
    }

    public boolean canSolve() {
        // Множитель можно вычислить, если основание степени является числом,
        // а показатель степени либо отсутствует, либо тоже является числом
        if (Base.isNumber()){
            if (Exponent == null || Exponent.isNumber()){
                return true;
            }
            return false;
        }
        return false;
    }

    public void solve() {
        if(canSolve()) {
            // Получаем значения основания и показателя степени
            double baseValue = Base.getValue();
            double exponentValue = Exponent.getValue();

            // Вычисляем степень и устанавливаем основанию новое значение, а показателю - единицу
            Base.setValue(Math.pow(baseValue, exponentValue));
            Exponent.setValue(1);
        } else {
            Base.solve();

            if (Exponent != null){
                Exponent.solve();
            }
        }
    }





    // Методы-мутаторы
    /**
     * Задаёт основание степени
     * @param base Основание степени
     */
    public void setBase(SignedAtomModel base) {
        Base = base;
    }

    /**
     * Возвращает основание степени
     * @return Основание степени
     */
    public SignedAtomModel getBase() {
        return Base;
    }

    /**
     * Задаёт показатель степени
     * @param exponent Показатель степени
     */
    public void setExponent(SignedAtomModel exponent) {
        Exponent = exponent;
    }

    /**
     * Возвращает показатель степени
     * @return Показатель степени
     */
    public SignedAtomModel getExponent(){
        return Exponent;
    }

    public MathOpModel getMathOperation() {
        return MathOperation;
    }

    public void setMathOperation(MathOpModel mathOperation) {
        MathOperation = mathOperation;
    }

    public double getValue() {
        if (isNumber()){
            return Base.getValue();
        } else {
            System.out.println("Не удаётся получить значение, так как выражение не является числом");
            return 0;
        }
    }

    public void setValue(double value) {
        Base.setValue(value);
    }
}
