package DataModels.Formulas;

import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Выражение
 */
public class ExpressionModel extends FormulaModel{

    /** Члены */
    public ArrayList<TermModel> Terms;

    /** Операция отношения, стоящая перед выражением, если оно является частью уравнения */
    private RelOpModel Relation;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель
     */
    public ExpressionModel(FormulaModel parent){
        super(parent);
        Terms = new ArrayList<TermModel>();
    }

    @Override
    public String toString() {
        String str = "";

        for (int i = 0; i < Terms.size(); i++){
            TermModel termModel = Terms.get(i);

            if (termModel.getMathOperation().equals(MathOpModel.Plus)){
                str+="+";
            } else if (termModel.getMathOperation().equals(MathOpModel.Minus)){
                str+="-";
            }

            str += termModel.toString();
        }

        return str;
    }

    @Override
    public ArrayList<JAXBElement> toOpenXML() {

        // Создаём ArrayList для возврата из этого метода
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        // Перебираем члены
        for (TermModel termModel : Terms){

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

            // В зависимости от знака, стоящего перед членом, добавляем соответствующий в ArrayList, который будет записан в документ
            if (termModel.getMathOperation().equals(MathOpModel.Plus)){
                Text text = wmlObjectFactory.createText();
                text.setValue("+");
                r.getContent().add(text);
            } else if (termModel.getMathOperation().equals(MathOpModel.Minus)){
                Text text = wmlObjectFactory.createText();
                text.setValue("-");
                r.getContent().add(text);
            }

            arrayList.add(rWrappedSign);

            // Теперь добавляем в массива все элементы массива, возвращенного методом toOpenXML() множителя
            for (JAXBElement rWrapped : termModel.toOpenXML()){
                arrayList.add(rWrapped);
            }
        }

        return arrayList;
    }












    public RelOpModel getRelation() {
        return Relation;
    }

    public void setRelation(RelOpModel relation) {
        Relation = relation;
    }
}
