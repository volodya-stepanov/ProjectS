package DataModels;

import Helpers.ClassHelper;
import org.docx4j.XmlUtils;
import org.docx4j.math.CTOMath;
import org.docx4j.math.CTOMathPara;
import org.docx4j.math.CTR;
import org.docx4j.math.CTText;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.math.BigInteger;


/**
 * Модель задания
 */
public class TaskModel {

    /**
     * Описание (текстовая часть) задания
     */
    private String Description;

    /**
     * Формула, введённая пользователем в виде строки
     */
    private String FormulaString;

    /**
     * Формула в объектном виде, разобранная парсером
     */
    private FormulaModel Formula;

    /**
     * Инициализирует экземпляр класса
     * @param description Описание (текстовая часть) задания
     * @param formulaString Формула, введённая пользователем в виде строки
     */
    public TaskModel(String description, String formulaString) {
        Description = description;
        FormulaString = formulaString;
    }

    /**
     * Возвращает описание (текстовую часть) задания
     * @return Описание (текстовая часть) задания
     */
    public String getDescription() {
        return Description;
    }

    /**
     * Задаёт описание (текстовую часть) задания
     * @param description Описание (текстовая часть) задания
     */
    public void setDescription(String description) {
        Description = description;
    }

    /**
     * Возвращает формулу, введённую пользователем в виде строки
     * @return Формула, введённая пользователем в виде строки
     */
    public String getFormulaString() {
        return FormulaString;
    }

    /**
     * Задаёт формулу, введённую пользователем в виде строки
     * @param formulaString Формула, введённая пользователем в виде строки
     */
    public void setFormulaString(String formulaString) {
        FormulaString = formulaString;
    }

    /**
     * Сохраняет задание и его решение в файл .docx
     */
    public void save() {
        // Создаём пакет
        WordprocessingMLPackage wordMLPackage = null;

        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();

            Document document = createDocument();
            Body body = document.getBody();
            addHeader(body);
            addFormula(body);
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
            //addFormula(mainDocumentPart);
            mainDocumentPart.setJaxbElement(document);

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        // Сохранить его
        try {
            wordMLPackage.save(new java.io.File("D:\\Test\\Test.docx") );
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }

    private void addHeader(Body body) {
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();

        // Create object for p
        P p = wmlObjectFactory.createP();
        body.getContent().add( p);
        // Create object for pPr
        PPr ppr = wmlObjectFactory.createPPr();
        p.setPPr(ppr);
        // Create object for rPr
        ParaRPr pararpr = wmlObjectFactory.createParaRPr();
        ppr.setRPr(pararpr);
        // Create object for b
        BooleanDefaultTrue booleandefaulttrue = wmlObjectFactory.createBooleanDefaultTrue();
        pararpr.setB(booleandefaulttrue);
        // Create object for rFonts
        RFonts rfonts = wmlObjectFactory.createRFonts();
        pararpr.setRFonts(rfonts);
        rfonts.setAscii( "Times New Roman");
        rfonts.setCs( "Times New Roman");
        rfonts.setHAnsi( "Times New Roman");
        // Create object for lang
        CTLanguage language = wmlObjectFactory.createCTLanguage();
        pararpr.setLang(language);
        language.setVal( "ru-RU");
        // Create object for sz
        HpsMeasure hpsmeasure = wmlObjectFactory.createHpsMeasure();
        pararpr.setSz(hpsmeasure);
        hpsmeasure.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure2 = wmlObjectFactory.createHpsMeasure();
        pararpr.setSzCs(hpsmeasure2);
        hpsmeasure2.setVal( BigInteger.valueOf( 28) );
        // Create object for ind
        PPrBase.Ind pprbaseind = wmlObjectFactory.createPPrBaseInd();
        ppr.setInd(pprbaseind);
        pprbaseind.setFirstLine( BigInteger.valueOf( 709) );
        // Create object for spacing
        PPrBase.Spacing pprbasespacing = wmlObjectFactory.createPPrBaseSpacing();
        ppr.setSpacing(pprbasespacing);
        pprbasespacing.setAfter( BigInteger.valueOf( 0) );
        pprbasespacing.setLine( BigInteger.valueOf( 360) );
        pprbasespacing.setLineRule(org.docx4j.wml.STLineSpacingRule.AUTO);
        // Create object for jc
        Jc jc = wmlObjectFactory.createJc();
        ppr.setJc(jc);
        jc.setVal(org.docx4j.wml.JcEnumeration.BOTH);
        p.setParaId( "1C6B40AF");
        p.setTextId( "1DFF084C");
        // Create object for r
        R r = wmlObjectFactory.createR();
        p.getContent().add( r);
        // Create object for rPr
        RPr rpr = wmlObjectFactory.createRPr();
        r.setRPr(rpr);
        // Create object for b
        BooleanDefaultTrue booleandefaulttrue2 = wmlObjectFactory.createBooleanDefaultTrue();
        rpr.setB(booleandefaulttrue2);
        // Create object for rFonts
        RFonts rfonts2 = wmlObjectFactory.createRFonts();
        rpr.setRFonts(rfonts2);
        rfonts2.setAscii( "Times New Roman");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Times New Roman");
        // Create object for sz
        HpsMeasure hpsmeasure3 = wmlObjectFactory.createHpsMeasure();
        rpr.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure4 = wmlObjectFactory.createHpsMeasure();
        rpr.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );
        // Create object for t (wrapped in JAXBElement)
        Text text = wmlObjectFactory.createText();
        JAXBElement<org.docx4j.wml.Text> textWrapped = wmlObjectFactory.createRT(text);
        r.getContent().add( textWrapped);
        text.setValue(Description);
    }

    /**
     * Добавляет формулу к телу документа
     * @param body Тело документа
     */
    private void addFormula(Body body) {
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();

        P p = wmlObjectFactory.createP();
        // Create object for pPr
        PPr ppr = wmlObjectFactory.createPPr();
        p.setPPr(ppr);
        // Create object for rPr
        ParaRPr pararpr = wmlObjectFactory.createParaRPr();
        ppr.setRPr(pararpr);
        // Create object for rFonts
        RFonts rfonts = wmlObjectFactory.createRFonts();
        pararpr.setRFonts(rfonts);
        rfonts.setAscii( "Times New Roman");
        rfonts.setCs( "Times New Roman");
        rfonts.setHAnsi( "Times New Roman");
        // Create object for lang
        CTLanguage language = wmlObjectFactory.createCTLanguage();
        pararpr.setLang(language);
        language.setVal( "en-US");
        // Create object for sz
        HpsMeasure hpsmeasure = wmlObjectFactory.createHpsMeasure();
        pararpr.setSz(hpsmeasure);
        hpsmeasure.setVal( BigInteger.valueOf( 28) );
        // Create object for i
        BooleanDefaultTrue booleandefaulttrue = wmlObjectFactory.createBooleanDefaultTrue();
        pararpr.setI(booleandefaulttrue);
        // Create object for szCs
        HpsMeasure hpsmeasure2 = wmlObjectFactory.createHpsMeasure();
        pararpr.setSzCs(hpsmeasure2);
        hpsmeasure2.setVal( BigInteger.valueOf( 28) );
        // Create object for ind
        PPrBase.Ind pprbaseind = wmlObjectFactory.createPPrBaseInd();
        ppr.setInd(pprbaseind);
        pprbaseind.setFirstLine( BigInteger.valueOf( 709) );
        // Create object for spacing
        PPrBase.Spacing pprbasespacing = wmlObjectFactory.createPPrBaseSpacing();
        ppr.setSpacing(pprbasespacing);
        pprbasespacing.setAfter( BigInteger.valueOf( 0) );
        pprbasespacing.setLine( BigInteger.valueOf( 360) );
        pprbasespacing.setLineRule(org.docx4j.wml.STLineSpacingRule.AUTO);
        // Create object for jc
        Jc jc = wmlObjectFactory.createJc();
        ppr.setJc(jc);
        jc.setVal(org.docx4j.wml.JcEnumeration.BOTH);
        p.setParaId( "207B6B5A");
        p.setTextId( "185ECC1B");
        org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
        // Create object for oMathPara (wrapped in JAXBElement)
        CTOMathPara omathpara = mathObjectFactory.createCTOMathPara();
        JAXBElement<org.docx4j.math.CTOMathPara> omathparaWrapped = mathObjectFactory.createOMathPara(omathpara);
        p.getContent().add( omathparaWrapped);
        // Create object for oMath
        CTOMath omath = mathObjectFactory.createCTOMath();
        omathpara.getOMath().add( omath);
        // Create object for r (wrapped in JAXBElement)

        JAXBElement rWrapped = Formula.toOpenXML();
        omath.getEGOMathElements().add( rWrapped);

        // Create object for bookmarkStart (wrapped in JAXBElement)
        CTBookmark bookmark = wmlObjectFactory.createCTBookmark();
        JAXBElement<org.docx4j.wml.CTBookmark> bookmarkWrapped = wmlObjectFactory.createPBookmarkStart(bookmark);
        p.getContent().add( bookmarkWrapped);
        bookmark.setName( "_GoBack");
        bookmark.setId( BigInteger.valueOf( 0) );
        // Create object for bookmarkEnd (wrapped in JAXBElement)
        CTMarkupRange markuprange = wmlObjectFactory.createCTMarkupRange();
        JAXBElement<org.docx4j.wml.CTMarkupRange> markuprangeWrapped = wmlObjectFactory.createPBookmarkEnd(markuprange);
        p.getContent().add( markuprangeWrapped);
        markuprange.setId( BigInteger.valueOf( 0) );

        body.getContent().add(p);
    }

    public Document createDocument(){
        org.docx4j.wml.ObjectFactory wmlObjectFactory = new org.docx4j.wml.ObjectFactory();

        Document document = wmlObjectFactory.createDocument();
        // Create object for body
        Body body = wmlObjectFactory.createBody();
        document.setBody(body);
        // Create object for sectPr
        SectPr sectpr = wmlObjectFactory.createSectPr();
        body.setSectPr(sectpr);
        // Create object for pgMar
        SectPr.PgMar sectprpgmar = wmlObjectFactory.createSectPrPgMar();
        sectpr.setPgMar(sectprpgmar);
        sectprpgmar.setFooter( BigInteger.valueOf( 708) );
        sectprpgmar.setLeft( BigInteger.valueOf( 1701) );
        sectprpgmar.setRight( BigInteger.valueOf( 850) );
        sectprpgmar.setTop( BigInteger.valueOf( 1134) );
        sectprpgmar.setBottom( BigInteger.valueOf( 1134) );
        sectprpgmar.setGutter( BigInteger.valueOf( 0) );
        sectprpgmar.setHeader( BigInteger.valueOf( 708) );
        // Create object for pgSz
        SectPr.PgSz sectprpgsz = wmlObjectFactory.createSectPrPgSz();
        sectpr.setPgSz(sectprpgsz);
        sectprpgsz.setH( BigInteger.valueOf( 16838) );
        sectprpgsz.setW( BigInteger.valueOf( 11906) );
        // Create object for cols
        CTColumns columns = wmlObjectFactory.createCTColumns();
        sectpr.setCols(columns);
        columns.setSpace( BigInteger.valueOf( 708) );
        // Create object for docGrid
        CTDocGrid docgrid = wmlObjectFactory.createCTDocGrid();
        sectpr.setDocGrid(docgrid);
        docgrid.setLinePitch( BigInteger.valueOf( 360) );

//        // Create object for p
//        P p2 = wmlObjectFactory.createP();
//        body.getContent().add( p2);
//        // Create object for pPr
//        PPr ppr2 = wmlObjectFactory.createPPr();
//        p2.setPPr(ppr2);
//        // Create object for rPr
//        ParaRPr pararpr2 = wmlObjectFactory.createParaRPr();
//        ppr2.setRPr(pararpr2);
//        // Create object for rFonts
//        RFonts rfonts4 = wmlObjectFactory.createRFonts();
//        pararpr2.setRFonts(rfonts4);
//        rfonts4.setAscii( "Times New Roman");
//        rfonts4.setCs( "Times New Roman");
//        rfonts4.setHAnsi( "Times New Roman");
//        // Create object for lang
//        CTLanguage language2 = wmlObjectFactory.createCTLanguage();
//        pararpr2.setLang(language2);
//        language2.setVal( "en-US");
//        // Create object for sz
//        HpsMeasure hpsmeasure7 = wmlObjectFactory.createHpsMeasure();
//        pararpr2.setSz(hpsmeasure7);
//        hpsmeasure7.setVal( BigInteger.valueOf( 28) );
//        // Create object for i
//        BooleanDefaultTrue booleandefaulttrue4 = wmlObjectFactory.createBooleanDefaultTrue();
//        pararpr2.setI(booleandefaulttrue4);
//        // Create object for szCs
//        HpsMeasure hpsmeasure8 = wmlObjectFactory.createHpsMeasure();
//        pararpr2.setSzCs(hpsmeasure8);
//        hpsmeasure8.setVal( BigInteger.valueOf( 28) );
//        // Create object for ind
//        PPrBase.Ind pprbaseind2 = wmlObjectFactory.createPPrBaseInd();
//        ppr2.setInd(pprbaseind2);
//        pprbaseind2.setFirstLine( BigInteger.valueOf( 709) );
//        // Create object for spacing
//        PPrBase.Spacing pprbasespacing2 = wmlObjectFactory.createPPrBaseSpacing();
//        ppr2.setSpacing(pprbasespacing2);
//        pprbasespacing2.setAfter( BigInteger.valueOf( 0) );
//        pprbasespacing2.setLine( BigInteger.valueOf( 360) );
//        pprbasespacing2.setLineRule(org.docx4j.wml.STLineSpacingRule.AUTO);
//        // Create object for jc
//        Jc jc2 = wmlObjectFactory.createJc();
//        ppr2.setJc(jc2);
//        jc2.setVal(org.docx4j.wml.JcEnumeration.BOTH);
//        p2.setParaId( "207B6B5A");
//        p2.setTextId( "185ECC1B");
//        org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
//        // Create object for oMathPara (wrapped in JAXBElement)
//        CTOMathPara omathpara = mathObjectFactory.createCTOMathPara();
//        JAXBElement<org.docx4j.math.CTOMathPara> omathparaWrapped = mathObjectFactory.createOMathPara(omathpara);
//        p2.getContent().add( omathparaWrapped);
//        // Create object for oMath
//        CTOMath omath = mathObjectFactory.createCTOMath();
//        omathpara.getOMath().add( omath);
//        // Create object for r (wrapped in JAXBElement)
//        CTR r3 = mathObjectFactory.createCTR();
//        JAXBElement<org.docx4j.math.CTR> rWrapped = mathObjectFactory.createCTOMathR(r3);
//        omath.getEGOMathElements().add( rWrapped);
//        // Create object for rPr (wrapped in JAXBElement)
//        RPr rpr3 = wmlObjectFactory.createRPr();
//        JAXBElement<org.docx4j.wml.RPr> rprWrapped = wmlObjectFactory.createSdtPrRPr(rpr3);
//        r3.getContent().add( rprWrapped);
//        // Create object for rFonts
//        RFonts rfonts5 = wmlObjectFactory.createRFonts();
//        rpr3.setRFonts(rfonts5);
//        rfonts5.setAscii( "Cambria Math");
//        rfonts5.setCs( "Times New Roman");
//        rfonts5.setHAnsi( "Cambria Math");
//        // Create object for lang
//        CTLanguage language3 = wmlObjectFactory.createCTLanguage();
//        rpr3.setLang(language3);
//        language3.setVal( "en-US");
//        // Create object for sz
//        HpsMeasure hpsmeasure9 = wmlObjectFactory.createHpsMeasure();
//        rpr3.setSz(hpsmeasure9);
//        hpsmeasure9.setVal( BigInteger.valueOf( 28) );
//        // Create object for szCs
//        HpsMeasure hpsmeasure10 = wmlObjectFactory.createHpsMeasure();
//        rpr3.setSzCs(hpsmeasure10);
//        hpsmeasure10.setVal( BigInteger.valueOf( 28) );
//        // Create object for t  //TODO: ИСключение возникает вот сдесьЙ
//        //CTText text3 = mathObjectFactory.createCTText();
//        Text text3 = wmlObjectFactory.createText();
//        r3.getContent().add( text3);
//        text3.setValue( "15");
//        // Create object for bookmarkStart (wrapped in JAXBElement)
//        CTBookmark bookmark = wmlObjectFactory.createCTBookmark();
//        JAXBElement<org.docx4j.wml.CTBookmark> bookmarkWrapped = wmlObjectFactory.createPBookmarkStart(bookmark);
//        p2.getContent().add( bookmarkWrapped);
//        bookmark.setName( "_GoBack");
//        bookmark.setId( BigInteger.valueOf( 0) );
//        // Create object for bookmarkEnd (wrapped in JAXBElement)
//        CTMarkupRange markuprange = wmlObjectFactory.createCTMarkupRange();
//        JAXBElement<org.docx4j.wml.CTMarkupRange> markuprangeWrapped = wmlObjectFactory.createPBookmarkEnd(markuprange);
//        p2.getContent().add( markuprangeWrapped);
//        markuprange.setId( BigInteger.valueOf( 0) );
        document.setIgnorable( "w14 wp14");

        return document;
    }

    /**
     * Задаёт формулу
     * @param formula Формула
     */
    public void setFormula(FormulaModel formula) {
        Formula = formula;
    }

    /**
     * Возвращает формулу
     * @return Формула
     */
    public FormulaModel getFormula(){
        return Formula;
    }
}
