package DataModels.Objects;

import DataModels.Formulas.FactorModel;
import DataModels.Formulas.NumberModel;
import DataModels.Formulas.SignedAtomModel;
import org.docx4j.math.*;
import org.docx4j.sharedtypes.STOnOff;
import org.docx4j.wml.*;
import org.docx4j.wml.ObjectFactory;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Вспомогательный класс для создания элементов в формате OpenXML для вставки в документ
 */
public class DocumentHelper {

    org.docx4j.wml.ObjectFactory WmlObjectFactory;
    org.docx4j.math.ObjectFactory MathObjectFactory;

    public DocumentHelper(){
        WmlObjectFactory = new org.docx4j.wml.ObjectFactory();
        MathObjectFactory = new org.docx4j.math.ObjectFactory();
    }

    /**
     * Создаёт текстовую строку (объект R) для вставки в формулу
     * @param str Строка, которую необходимо поместить в формулу
     * @return Объект R, полученный из исходной строки
     */
    public JAXBElement createRun(String str){

        Text text = WmlObjectFactory.createText();
        text.setValue(str);

        CTR r = MathObjectFactory.createCTR();
        r.getContent().add(text);
        JAXBElement<CTR> rWrapped = MathObjectFactory.createCTOMathR(r);
        // Create object for rPr (wrapped in JAXBElement)
        RPr rpr = WmlObjectFactory.createRPr();
        JAXBElement<RPr> rprWrapped = WmlObjectFactory.createSdtPrRPr(rpr);
        r.getContent().add( rprWrapped);
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr.setRFonts(rfonts2);
        rfonts2.setAscii( "Cambria Math");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Cambria Math");
        // Create object for lang
        CTLanguage language2 = WmlObjectFactory.createCTLanguage();
        rpr.setLang(language2);
        language2.setVal( "en-US");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );

        return rWrapped;
    }

    /**
     * Создаёт массив текстовых строк (объектов R) для вставки в формулу
     * @param str Строка, которую необходимо поместить в формулу
     * @return Массив объектов R, полученный из исходной строки
     */
    public ArrayList<JAXBElement> createRunArray(String str){

        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        Text text = WmlObjectFactory.createText();
        text.setValue(str);

        CTR r = MathObjectFactory.createCTR();
        r.getContent().add(text);
        JAXBElement<CTR> rWrapped = MathObjectFactory.createCTOMathR(r);
        // Create object for rPr (wrapped in JAXBElement)
        RPr rpr = WmlObjectFactory.createRPr();
        JAXBElement<RPr> rprWrapped = WmlObjectFactory.createSdtPrRPr(rpr);
        r.getContent().add( rprWrapped);
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr.setRFonts(rfonts2);
        rfonts2.setAscii( "Cambria Math");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Cambria Math");
        // Create object for lang
        CTLanguage language2 = WmlObjectFactory.createCTLanguage();
        rpr.setLang(language2);
        language2.setVal( "en-US");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );

        arrayList.add(rWrapped);

        return arrayList;
    }

    public ArrayList<JAXBElement> createParenthesis(ArrayList<JAXBElement> runArrayList){

        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        CTD d = MathObjectFactory.createCTD();
        JAXBElement<CTD> dWrapped = MathObjectFactory.createCTOMathArgD(d);

        // Create object for e
        CTOMathArg omatharg2 = MathObjectFactory.createCTOMathArg();
        d.getE().add( omatharg2);
        // Create object for r (wrapped in JAXBElement)

        for (JAXBElement<CTR> rWrapped : runArrayList){
            omatharg2.getEGOMathElements().add( rWrapped);
        }

        // Create object for dPr
        CTDPr dpr = MathObjectFactory.createCTDPr();
        d.setDPr(dpr);
        // Create object for ctrlPr
        CTCtrlPr ctrlpr = MathObjectFactory.createCTCtrlPr();
        dpr.setCtrlPr(ctrlpr);
        // Create object for rPr
        RPr rpr2 = WmlObjectFactory.createRPr();
        ctrlpr.setRPr(rpr2);
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr2.setRFonts(rfonts2);
        rfonts2.setAscii( "Cambria Math");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Cambria Math");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for i
        BooleanDefaultTrue booleandefaulttrue = WmlObjectFactory.createBooleanDefaultTrue();
        rpr2.setI(booleandefaulttrue);
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );

        arrayList.add(dWrapped);

        return arrayList;
    }

    public ArrayList<JAXBElement> createPower(SignedAtomModel base, SignedAtomModel exponent) {
        CTSSup ssup = MathObjectFactory.createCTSSup();
        JAXBElement<CTSSup> ssupWrapped = MathObjectFactory.createCTOMathSSup(ssup);
        // Create object for e
        CTOMathArg omatharg = MathObjectFactory.createCTOMathArg();
        ssup.setE(omatharg);
        // Create object for r (wrapped in JAXBElement)
        ObjectFactory wmlObjectFactory = new ObjectFactory();

        // Заполняем основание степени
        if (base.isNumber()){
            NumberModel number = (NumberModel) base.getAtom().getExpression();
            if (number.getValue() < 0){
                ArrayList<JAXBElement> parenthesisArrayList = createParenthesis(base.toOpenXML());
                for (JAXBElement dWrapped : parenthesisArrayList){
                    omatharg.getEGOMathElements().add(dWrapped);
                }
            } else {
                // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
                ArrayList<JAXBElement> arrayList1 = base.toOpenXML();
                for (JAXBElement<CTR> rWrapped : arrayList1){
                    omatharg.getEGOMathElements().add( rWrapped);
                }
            }
        } else {
            // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
            ArrayList<JAXBElement> arrayList1 = base.toOpenXML();
            for (JAXBElement<CTR> rWrapped : arrayList1){
                omatharg.getEGOMathElements().add( rWrapped);
            }
        }

        // Create object for sup
        CTOMathArg omatharg2 = MathObjectFactory.createCTOMathArg();
        ssup.setSup(omatharg2);
        // Create object for r (wrapped in JAXBElement)

        // Получаем массив элементов rWrapped (OpenXML), из каждого элемента извлекаем элемент r, добавляем к нему элемент text и возвращаем его
        ArrayList<JAXBElement> arrayList2 = exponent.toOpenXML();
        for (JAXBElement<CTR> rWrapped : arrayList2){
            omatharg2.getEGOMathElements().add( rWrapped);
        }

        // Create object for sSupPr
        CTSSupPr ssuppr = MathObjectFactory.createCTSSupPr();
        ssup.setSSupPr(ssuppr);
        // Create object for ctrlPr
        CTCtrlPr ctrlpr = MathObjectFactory.createCTCtrlPr();
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
    }

    public JAXBElement createFraction(ArrayList<JAXBElement> numeratorArrayList, ArrayList<JAXBElement> denominatorArrayList) {
        CTF f = MathObjectFactory.createCTF();
        JAXBElement<CTF> fWrapped = MathObjectFactory.createCTOMathF(f);
        //omath.getEGOMathElements().add( fWrapped);
        // Create object for num
        CTOMathArg omatharg = MathObjectFactory.createCTOMathArg();
        f.setNum(omatharg);

        for (JAXBElement rWrapped : numeratorArrayList){
            omatharg.getEGOMathElements().add( rWrapped);
        }

        // Create object for fPr
        CTFPr fpr = MathObjectFactory.createCTFPr();
        f.setFPr(fpr);
        // Create object for ctrlPr
        CTCtrlPr ctrlpr = MathObjectFactory.createCTCtrlPr();
        fpr.setCtrlPr(ctrlpr);
        // Create object for rPr
        RPr rpr2 = WmlObjectFactory.createRPr();
        ctrlpr.setRPr(rpr2);
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr2.setRFonts(rfonts2);
        rfonts2.setAscii( "Cambria Math");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Cambria Math");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for i
        BooleanDefaultTrue booleandefaulttrue = WmlObjectFactory.createBooleanDefaultTrue();
        rpr2.setI(booleandefaulttrue);
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );
        // Create object for den
        CTOMathArg omatharg2 = MathObjectFactory.createCTOMathArg();
        f.setDen(omatharg2);

        for (JAXBElement rWrapped2 : denominatorArrayList){
            omatharg2.getEGOMathElements().add( rWrapped2);
        }

        return fWrapped;
    }

    public ArrayList<JAXBElement> createRadical(ArrayList<JAXBElement> expressions){
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

        // Create object for rad (wrapped in JAXBElement)
        CTRad rad = MathObjectFactory.createCTRad();
        JAXBElement<CTRad> radWrapped = MathObjectFactory.createCTOMathRad(rad);
        arrayList.add(radWrapped);
        // Create object for e
        CTOMathArg omatharg = MathObjectFactory.createCTOMathArg();
        rad.setE(omatharg);
        // Create object for r (wrapped in JAXBElement)

        for (JAXBElement rWrapped : expressions){
            omatharg.getEGOMathElements().add( rWrapped);
        }

        // Create object for radPr
        CTRadPr radpr = MathObjectFactory.createCTRadPr();
        rad.setRadPr(radpr);
        // Create object for ctrlPr
        CTCtrlPr ctrlpr = MathObjectFactory.createCTCtrlPr();
        radpr.setCtrlPr(ctrlpr);
        // Create object for rPr
        RPr rpr2 = WmlObjectFactory.createRPr();
        ctrlpr.setRPr(rpr2);
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr2.setRFonts(rfonts2);
        rfonts2.setAscii( "Cambria Math");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Cambria Math");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for i
        BooleanDefaultTrue booleandefaulttrue = WmlObjectFactory.createBooleanDefaultTrue();
        rpr2.setI(booleandefaulttrue);
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );
        // Create object for degHide
        CTOnOff onoff = MathObjectFactory.createCTOnOff();
        radpr.setDegHide(onoff);
        onoff.setVal(STOnOff.ONE);
        // Create object for deg
        CTOMathArg omatharg2 = MathObjectFactory.createCTOMathArg();
        rad.setDeg(omatharg2);

        return arrayList;
    }
}
