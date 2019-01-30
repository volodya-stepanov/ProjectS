package DataModels;

import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;

/**
 * Модель формулы - уравнения, выражения, переменной и т.д.
 */
public abstract class FormulaModel {

    /**
     * Возвращает формулу в формате OpenXML для записи в документ
     * @return
     */
    public JAXBElement toOpenXML(){
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
        CTR r = mathObjectFactory.createCTR();
        JAXBElement<org.docx4j.math.CTR> rWrapped = mathObjectFactory.createCTOMathR(r);
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

        return rWrapped;
    }
}
