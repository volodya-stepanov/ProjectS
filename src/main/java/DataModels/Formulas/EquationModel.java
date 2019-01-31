package DataModels.Formulas;

import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Уравнение
 */
public class EquationModel extends FormulaModel {

    /**
     * Левая часть
     */
    private ExpressionModel LeftSide;

    /**
     * Правая часть
     */
    private ExpressionModel RightSide;

    /**
     * Отношение между частями
     */
    private RelOpModel Relation;

    /**
     * Возвращает левую часть
     * @return Левая часть
     */
    public ExpressionModel getLeftSide() {
        return LeftSide;
    }

    /**
     * Задаёт левую часть
     * @param leftSide Левая часть
     */
    public void setLeftSide(ExpressionModel leftSide) {
        LeftSide = leftSide;
    }

    /**
     * Возвращает правую часть
     * @return Правая часть
     */
    public ExpressionModel getRightSide() {
        return RightSide;
    }

    /**
     * Задаёт правую часть
     * @param rightSide Правая часть
     */
    public void setRightSide(ExpressionModel rightSide) {
        RightSide = rightSide;
    }

    /**
     * Возвращает отношение между частями
     * @return Отношение между частями
     */
    public RelOpModel getRelation() {
        return Relation;
    }

    /**
     * Задаёт отношение между частями
     * @param relation Отношение между частями
     */
    public void setRelation(RelOpModel relation) {
        Relation = relation;
    }

    @Override
    public String toString() {
        String str = LeftSide.toString();

        switch (Relation) {
            case Equals:
                str+="=";
                break;
            case GreaterThan:
                str+=">";
                break;
            case LessThan:
                str+="<";
                break;
        }

        str+=RightSide.toString();

        return str;
    }


        @Override
        public ArrayList<JAXBElement> toOpenXML() {

            // Создаём ArrayList для возврата из этого метода
            ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

            // Добавляем левую часть
            for (JAXBElement rWrapped : LeftSide.toOpenXML()) {
                arrayList.add(rWrapped);
            }

            // Устанавливаем знак между двумя частями
            // Создаём JAXBElement со знаком для подставления в произведение
            org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
            org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
            CTR r = mathObjectFactory.createCTR();
            JAXBElement<org.docx4j.math.CTR> rWrappedSign = mathObjectFactory.createCTOMathR(r);
            // Create object for rPr (wrapped in JAXBElement)
            RPr rpr = wmlObjectFactory.createRPr();
            JAXBElement<RPr> rprWrapped = wmlObjectFactory.createSdtPrRPr(rpr);
            r.getContent().add(rprWrapped);
            // Create object for rFonts
            RFonts rfonts2 = wmlObjectFactory.createRFonts();
            rpr.setRFonts(rfonts2);
            rfonts2.setAscii("Cambria Math");
            rfonts2.setCs("Times New Roman");
            rfonts2.setHAnsi("Cambria Math");
            // Create object for lang
            CTLanguage language2 = wmlObjectFactory.createCTLanguage();
            rpr.setLang(language2);
            language2.setVal("en-US");
            // Create object for sz
            HpsMeasure hpsmeasure3 = wmlObjectFactory.createHpsMeasure();
            rpr.setSz(hpsmeasure3);
            hpsmeasure3.setVal(BigInteger.valueOf(28));
            // Create object for szCs
            HpsMeasure hpsmeasure4 = wmlObjectFactory.createHpsMeasure();
            rpr.setSzCs(hpsmeasure4);
            hpsmeasure4.setVal(BigInteger.valueOf(28));

            Text text = wmlObjectFactory.createText();

            switch (Relation) {
                case Equals:
                    text.setValue("=");
                    break;
                case GreaterThan:
                    text.setValue(">");
                    break;
                case LessThan:
                    text.setValue("<");
                    break;
            }

            r.getContent().add(text);
            arrayList.add(rWrappedSign);

            // Добавляем правую часть
            for (JAXBElement rWrapped : RightSide.toOpenXML()) {
                arrayList.add(rWrapped);
            }

            return arrayList;
        }

}
