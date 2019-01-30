package DataModels;

import Helpers.ClassHelper;
import org.docx4j.math.*;
import org.docx4j.wml.BooleanDefaultTrue;
import org.docx4j.wml.RFonts;
import org.docx4j.wml.RPr;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;

/**
 * Множитель (атом со знаком, возведённый в степень)
 */
public class FactorModel extends FormulaModel {

    /**
     * Основание степени
     */
    private SignedAtomModel Base;

    /**
     * Показатель степени
     */
    private SignedAtomModel Exponent;

    /**
     * Математическая операция, которая стоит перед множителем (умножить или разделить)
     */
    private MathOpModel MathOperation;

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

    public FactorModel(){
        MathOperation = MathOpModel.None;
    }

    @Override
    public String toString() {
        if (showExponent()){
            return Base.toString() + "^" + Exponent.toString();
        }
        return Base.toString();
    }

    @Override
    public JAXBElement toOpenXML() {
        if (showExponent()){
            org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
            CTSSup ssup = mathObjectFactory.createCTSSup();
            JAXBElement<org.docx4j.math.CTSSup> ssupWrapped = mathObjectFactory.createCTOMathSSup(ssup);
            // Create object for e
            CTOMathArg omatharg = mathObjectFactory.createCTOMathArg();
            ssup.setE(omatharg);
            // Create object for r (wrapped in JAXBElement)
            org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
            JAXBElement rWrapped = Base.toOpenXML();
            omatharg.getEGOMathElements().add( rWrapped);
            // Create object for sup
            CTOMathArg omatharg2 = mathObjectFactory.createCTOMathArg();
            ssup.setSup(omatharg2);
            // Create object for r (wrapped in JAXBElement)
            JAXBElement rWrapped2 = Exponent.toOpenXML();
            omatharg2.getEGOMathElements().add( rWrapped2);
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

            return ssupWrapped;
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
        }

        return true;
    }
}
