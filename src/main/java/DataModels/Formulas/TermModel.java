package DataModels.Formulas;

import Helpers.ClassHelper;
import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Член
 */
public class TermModel extends FormulaModel {

    /** Множители */
    public ArrayList<FactorModel> Factors;

    /** Математическая операция, которая стоит перед членом (плюс или минус) */
    private MathOpModel MathOperation;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     */
    public TermModel(ExpressionModel parent){
        super(parent);
        Factors = new ArrayList<FactorModel>();
        MathOperation = MathOpModel.None;
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < Factors.size(); i++){
            FactorModel factorModel = Factors.get(i);

            if (factorModel.getMathOperation().equals(MathOpModel.Multiply)){
                str+="*";
            } else if (factorModel.getMathOperation().equals(MathOpModel.Divide)){
                str+="/";
            }

            str += factorModel.toString();
        }

        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {

        // Создаём ArrayList для возврата из этого метода
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        // Перебираем множители
        for (FactorModel factorModel : Factors){

            // Создаём JAXBElement со знаком для подставления в произведение
            org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
            org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
            CTR r = mathObjectFactory.createCTR();
            JAXBElement<org.docx4j.math.CTR> rWrappedSign = mathObjectFactory.createCTOMathR(r);
            // Create object for rPr (wrapped in JAXBElement)
            RPr rpr = wmlObjectFactory.createRPr();
            JAXBElement<RPr> rprWrapped = wmlObjectFactory.createSdtPrRPr(rpr);
            r.getContent().add( rprWrapped);
            // Create object for rFonts
            RFonts rfonts2 = wmlObjectFactory.createRFonts();
            rpr.setRFonts(rfonts2);
            rfonts2.setAscii( "Cambria Math");
            rfonts2.setCs( "Times New Roman");
            rfonts2.setHAnsi( "Cambria Math");
            // Create object for lang
            CTLanguage language2 = wmlObjectFactory.createCTLanguage();
            rpr.setLang(language2);
            language2.setVal( "en-US");
            // Create object for sz
            HpsMeasure hpsmeasure3 = wmlObjectFactory.createHpsMeasure();
            rpr.setSz(hpsmeasure3);
            hpsmeasure3.setVal( BigInteger.valueOf( 28) );
            // Create object for szCs
            HpsMeasure hpsmeasure4 = wmlObjectFactory.createHpsMeasure();
            rpr.setSzCs(hpsmeasure4);
            hpsmeasure4.setVal( BigInteger.valueOf( 28) );

            // В зависимости от знака, стоящего перед множителем, добавляем соответствующий в ArrayList, который будет записан в документ
            if (factorModel.getMathOperation().equals(MathOpModel.Multiply)){
                // Знак умножения ставим только в том случае, если следующий множитель - число. Перед переменными знак умножения не ставим
                // Определяем тип множителя
                Object obj = factorModel.getBase().getAtom().getExpression();
                ClassHelper helper = new ClassHelper();
                if (helper.isTypeOf(obj, NumberModel.class)) {
                    Text text = wmlObjectFactory.createText();
                    text.setValue("∙");
                    r.getContent().add(text);
                }
            } else if (factorModel.getMathOperation().equals(MathOpModel.Divide)){
                Text text = wmlObjectFactory.createText();
                text.setValue(":");
                r.getContent().add(text);
            }

            arrayList.add(rWrappedSign);

            // Теперь добавляем в массива все элементы массива, возвращенного методом toOpenXML() множителя
            for (JAXBElement rWrapped : factorModel.toOpenXML()){
                arrayList.add(rWrapped);
            }
        }

        return arrayList;
    }

    @Override
    public boolean isNumber() {
        // Произведение считается числом тогда, когда оно имеет только один множитель, и он является числом
        return Factors.size() == 1 && Factors.get(0).isNumber();
    }

    public FormulaModel copy(FormulaModel parent) {
        TermModel term = new TermModel((ExpressionModel) parent);
        term.setMathOperation(MathOperation);

        for (FactorModel factor : Factors){
            term.Factors.add((FactorModel) factor.copy(term));
        }

        return term;
    }

    public boolean canSolve() {
        boolean canSolve = true;

        for (FactorModel factor : Factors){
            if (!factor.isNumber()){
                canSolve = false;
                break;
            }
        }

        return canSolve;
    }

    public void solve() {
        if(canSolve()){
            double result = Factors.get(0).getValue();

            for(int i = 1; i < Factors.size(); i++){
                FactorModel factor = Factors.get(i);
                if (factor.getMathOperation().equals(MathOpModel.Multiply)) {
                    result *= factor.getValue();
                } else if (factor.getMathOperation().equals(MathOpModel.Divide)){
                    result /= factor.getValue();
                }
            }

            setValue(result);
        } else {
            for(FactorModel factor : Factors){
                factor.solve();
            }
        }
    }

    // Методы-мутаторы
    public MathOpModel getMathOperation() {
        return MathOperation;
    }

    public void setMathOperation(MathOpModel mathOperation) {
        MathOperation = mathOperation;
    }

    public double getValue() {
        if (isNumber()){
            return Factors.get(0).getValue();
        } else {
            System.out.println("Не удаётся получить значение, так как выражение не является числом");
            return 0;
        }
    }

    public void setValue(double value) {
        Factors.get(0).setValue(value);

        for (int i = Factors.size() - 1; i > 0; i--){
            Factors.remove(i);
        }
    }
}
