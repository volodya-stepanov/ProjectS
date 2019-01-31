package DataModels.Objects;

import DataModels.Formulas.FormulaModel;
import DataModels.Tasks.TaskModel;
import org.docx4j.math.CTOMath;
import org.docx4j.math.CTOMathPara;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Документ, содержащий данные о заданиях и методы для их выполнения и сохранения в файл.
 */
public class DocumentModel {
    /** Список заданий */
    private ArrayList<TaskModel> Tasks;

    /** Путь к файлу */
    private String Path;

    /** Тело документа для выполнения операций над ним */
    private Body Body;

    /** Пакет для создания и сохранения документа */
    private WordprocessingMLPackage WordMLPackage;

    /** Для генерации формул */
    org.docx4j.wml.ObjectFactory WmlObjectFactory;

    /**
     * Инициализирует экземпляр класса
     */
    public DocumentModel(){
        Tasks = new ArrayList<TaskModel>();
        WmlObjectFactory = new org.docx4j.wml.ObjectFactory();
    }

    /**
     * Инициализирует экземпляр класса
     * @param path Путь к файлу
     */
    public DocumentModel(String path){
        this();
        Path = path;
    }

    /**
     * Создаёт документ
     * @return Истина в случае успешного выполнения операции, иначе ложь
     */
    public boolean create(){
        try {
            // Создаём пакет
            WordMLPackage = WordprocessingMLPackage.createPackage();

            // Создаём документ
            Document document = createDocument();

            // Извлекаем тело документа для дальнейшей обработки
            Body = document.getBody();

            // Извлекаем главную часть документа и вставляем туда ранее созданный документ
            MainDocumentPart mainDocumentPart = WordMLPackage.getMainDocumentPart();
            mainDocumentPart.setJaxbElement(document);

            return true;
        } catch (InvalidFormatException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Вспомогательный метод для создания документа - экземпляра класса Document
     * @return Документ - экземпляр класса Document
     */
    private Document createDocument(){
        Document document = WmlObjectFactory.createDocument();
        // Create object for body
        Body body = WmlObjectFactory.createBody();
        document.setBody(body);
        // Create object for sectPr
        SectPr sectpr = WmlObjectFactory.createSectPr();
        body.setSectPr(sectpr);
        // Create object for pgMar
        SectPr.PgMar sectprpgmar = WmlObjectFactory.createSectPrPgMar();
        sectpr.setPgMar(sectprpgmar);
        sectprpgmar.setFooter( BigInteger.valueOf( 708) );
        sectprpgmar.setLeft( BigInteger.valueOf( 1701) );
        sectprpgmar.setRight( BigInteger.valueOf( 850) );
        sectprpgmar.setTop( BigInteger.valueOf( 1134) );
        sectprpgmar.setBottom( BigInteger.valueOf( 1134) );
        sectprpgmar.setGutter( BigInteger.valueOf( 0) );
        sectprpgmar.setHeader( BigInteger.valueOf( 708) );
        // Create object for pgSz
        SectPr.PgSz sectprpgsz = WmlObjectFactory.createSectPrPgSz();
        sectpr.setPgSz(sectprpgsz);
        sectprpgsz.setH( BigInteger.valueOf( 16838) );
        sectprpgsz.setW( BigInteger.valueOf( 11906) );
        // Create object for cols
        CTColumns columns = WmlObjectFactory.createCTColumns();
        sectpr.setCols(columns);
        columns.setSpace( BigInteger.valueOf( 708) );
        // Create object for docGrid
        CTDocGrid docgrid = WmlObjectFactory.createCTDocGrid();
        sectpr.setDocGrid(docgrid);
        docgrid.setLinePitch( BigInteger.valueOf( 360) );
        document.setIgnorable( "w14 wp14");

        return document;
    }

    /**
     * Вставляет в документ абзац текста
     * @param text Текст для вставки
     * @param bold Признак того, что шрифт должен быть жирным
     */
    public void addText(String text, boolean bold){
        // Create object for p
        P p = WmlObjectFactory.createP();
        Body.getContent().add( p);
        // Create object for pPr
        PPr ppr = WmlObjectFactory.createPPr();
        p.setPPr(ppr);
        // Create object for rPr
        ParaRPr pararpr = WmlObjectFactory.createParaRPr();
        ppr.setRPr(pararpr);
        // Create object for b
        // Устанавливаем жирность шрифта
        if (bold) {
            BooleanDefaultTrue booleandefaulttrue = WmlObjectFactory.createBooleanDefaultTrue();
            pararpr.setB(booleandefaulttrue);
        }
        // Create object for rFonts
        RFonts rfonts = WmlObjectFactory.createRFonts();
        pararpr.setRFonts(rfonts);
        rfonts.setAscii( "Times New Roman");
        rfonts.setCs( "Times New Roman");
        rfonts.setHAnsi( "Times New Roman");
        // Create object for lang
        CTLanguage language = WmlObjectFactory.createCTLanguage();
        pararpr.setLang(language);
        language.setVal( "ru-RU");
        // Create object for sz
        HpsMeasure hpsmeasure = WmlObjectFactory.createHpsMeasure();
        pararpr.setSz(hpsmeasure);
        hpsmeasure.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure2 = WmlObjectFactory.createHpsMeasure();
        pararpr.setSzCs(hpsmeasure2);
        hpsmeasure2.setVal( BigInteger.valueOf( 28) );
        // Create object for ind
        PPrBase.Ind pprbaseind = WmlObjectFactory.createPPrBaseInd();
        ppr.setInd(pprbaseind);
        pprbaseind.setFirstLine( BigInteger.valueOf( 709) );
        // Create object for spacing
        PPrBase.Spacing pprbasespacing = WmlObjectFactory.createPPrBaseSpacing();
        ppr.setSpacing(pprbasespacing);
        pprbasespacing.setAfter( BigInteger.valueOf( 0) );
        pprbasespacing.setLine( BigInteger.valueOf( 360) );
        pprbasespacing.setLineRule(STLineSpacingRule.AUTO);
        // Create object for jc
        Jc jc = WmlObjectFactory.createJc();
        ppr.setJc(jc);
        jc.setVal(JcEnumeration.BOTH);
        p.setParaId( "1C6B40AF");
        p.setTextId( "1DFF084C");
        // Create object for r
        R r = WmlObjectFactory.createR();
        p.getContent().add( r);
        // Create object for rPr
        RPr rpr = WmlObjectFactory.createRPr();
        r.setRPr(rpr);
        // Create object for b
        if (bold) {
            BooleanDefaultTrue booleandefaulttrue2 = WmlObjectFactory.createBooleanDefaultTrue();
            rpr.setB(booleandefaulttrue2);
        }
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr.setRFonts(rfonts2);
        rfonts2.setAscii( "Times New Roman");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Times New Roman");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );
        // Create object for t (wrapped in JAXBElement)
        Text textElement = WmlObjectFactory.createText();
        JAXBElement<Text> textWrapped = WmlObjectFactory.createRT(textElement);
        r.getContent().add( textWrapped);
        textElement.setValue(text);
    }

    /**
     * Вставляет в документ разрыв страницы
     */
    public void addBreak(){
        P p = WmlObjectFactory.createP();
        Body.getContent().add( p);
        // Create object for pPr
        PPr ppr = WmlObjectFactory.createPPr();
        p.setPPr(ppr);
        // Create object for rPr
        ParaRPr pararpr = WmlObjectFactory.createParaRPr();
        ppr.setRPr(pararpr);
        // Create object for b
        BooleanDefaultTrue booleandefaulttrue = WmlObjectFactory.createBooleanDefaultTrue();
        pararpr.setB(booleandefaulttrue);
        // Create object for rFonts
        RFonts rfonts = WmlObjectFactory.createRFonts();
        pararpr.setRFonts(rfonts);
        rfonts.setAscii( "Times New Roman");
        rfonts.setCs( "Times New Roman");
        rfonts.setHAnsi( "Times New Roman");
        // Create object for sz
        HpsMeasure hpsmeasure = WmlObjectFactory.createHpsMeasure();
        pararpr.setSz(hpsmeasure);
        hpsmeasure.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure2 = WmlObjectFactory.createHpsMeasure();
        pararpr.setSzCs(hpsmeasure2);
        hpsmeasure2.setVal( BigInteger.valueOf( 28) );
        p.setParaId( "7251E4A0");
        p.setTextId( "77777777");
        // Create object for r
        R r = WmlObjectFactory.createR();
        p.getContent().add( r);
        // Create object for rPr
        RPr rpr = WmlObjectFactory.createRPr();
        r.setRPr(rpr);
        // Create object for b
        BooleanDefaultTrue booleandefaulttrue2 = WmlObjectFactory.createBooleanDefaultTrue();
        rpr.setB(booleandefaulttrue2);
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr.setRFonts(rfonts2);
        rfonts2.setAscii( "Times New Roman");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Times New Roman");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );
        // Create object for br
        Br br = WmlObjectFactory.createBr();
        r.getContent().add( br);
        br.setType(STBrType.PAGE);
    }

    /**
     * Вставляет документ блок с ответом - фразу "Ответ: " жирным шрифтом, а затем ответ пользователя
     * @param answer Текст ответа
     */
    public void addAnswer(String answer){
        P p = WmlObjectFactory.createP();
        Body.getContent().add(p);
        // Create object for pPr
        PPr ppr = WmlObjectFactory.createPPr();
        p.setPPr(ppr);
        // Create object for rPr
        ParaRPr pararpr = WmlObjectFactory.createParaRPr();
        ppr.setRPr(pararpr);
        // Create object for rFonts
        RFonts rfonts = WmlObjectFactory.createRFonts();
        pararpr.setRFonts(rfonts);
        rfonts.setAscii( "Times New Roman");
        rfonts.setCs( "Times New Roman");
        rfonts.setHAnsi( "Times New Roman");
        // Create object for lang
        CTLanguage language = WmlObjectFactory.createCTLanguage();
        pararpr.setLang(language);
        language.setVal( "ru-RU");
        // Create object for sz
        HpsMeasure hpsmeasure = WmlObjectFactory.createHpsMeasure();
        pararpr.setSz(hpsmeasure);
        hpsmeasure.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure2 = WmlObjectFactory.createHpsMeasure();
        pararpr.setSzCs(hpsmeasure2);
        hpsmeasure2.setVal( BigInteger.valueOf( 28) );
        // Create object for ind
        PPrBase.Ind pprbaseind = WmlObjectFactory.createPPrBaseInd();
        ppr.setInd(pprbaseind);
        pprbaseind.setFirstLine( BigInteger.valueOf( 709) );
        // Create object for spacing
        PPrBase.Spacing pprbasespacing = WmlObjectFactory.createPPrBaseSpacing();
        ppr.setSpacing(pprbasespacing);
        pprbasespacing.setAfter( BigInteger.valueOf( 0) );
        pprbasespacing.setLine( BigInteger.valueOf( 360) );
        pprbasespacing.setLineRule(STLineSpacingRule.AUTO);
        // Create object for jc
        Jc jc = WmlObjectFactory.createJc();
        ppr.setJc(jc);
        jc.setVal(JcEnumeration.BOTH);
        p.setParaId( "2E4F2010");
        p.setTextId( "0935B66E");
        // Create object for bookmarkStart (wrapped in JAXBElement)
        CTBookmark bookmark = WmlObjectFactory.createCTBookmark();
        JAXBElement<CTBookmark> bookmarkWrapped = WmlObjectFactory.createPBookmarkStart(bookmark);
        p.getContent().add( bookmarkWrapped);
        bookmark.setName( "_GoBack");
        bookmark.setId( BigInteger.valueOf( 0) );
        // Create object for r
        R r = WmlObjectFactory.createR();
        p.getContent().add( r);
        // Create object for rPr
        RPr rpr = WmlObjectFactory.createRPr();
        r.setRPr(rpr);
        // Create object for b
        BooleanDefaultTrue booleandefaulttrue = WmlObjectFactory.createBooleanDefaultTrue();
        rpr.setB(booleandefaulttrue);
        // Create object for rFonts
        RFonts rfonts2 = WmlObjectFactory.createRFonts();
        rpr.setRFonts(rfonts2);
        rfonts2.setAscii( "Times New Roman");
        rfonts2.setCs( "Times New Roman");
        rfonts2.setHAnsi( "Times New Roman");
        // Create object for lang
        CTLanguage language2 = WmlObjectFactory.createCTLanguage();
        rpr.setLang(language2);
        language2.setVal( "ru-RU");
        // Create object for sz
        HpsMeasure hpsmeasure3 = WmlObjectFactory.createHpsMeasure();
        rpr.setSz(hpsmeasure3);
        hpsmeasure3.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure4 = WmlObjectFactory.createHpsMeasure();
        rpr.setSzCs(hpsmeasure4);
        hpsmeasure4.setVal( BigInteger.valueOf( 28) );
        // Create object for t (wrapped in JAXBElement)
        Text text = WmlObjectFactory.createText();
        JAXBElement<Text> textWrapped = WmlObjectFactory.createRT(text);
        r.getContent().add( textWrapped);
        text.setValue( "Ответ:");
        // Create object for bookmarkEnd (wrapped in JAXBElement)
        CTMarkupRange markuprange = WmlObjectFactory.createCTMarkupRange();
        JAXBElement<CTMarkupRange> markuprangeWrapped = WmlObjectFactory.createPBookmarkEnd(markuprange);
        p.getContent().add( markuprangeWrapped);
        markuprange.setId( BigInteger.valueOf( 0) );
        // Create object for r
        R r2 = WmlObjectFactory.createR();
        p.getContent().add( r2);
        // Create object for rPr
        RPr rpr2 = WmlObjectFactory.createRPr();
        r2.setRPr(rpr2);
        // Create object for rFonts
        RFonts rfonts3 = WmlObjectFactory.createRFonts();
        rpr2.setRFonts(rfonts3);
        rfonts3.setAscii( "Times New Roman");
        rfonts3.setCs( "Times New Roman");
        rfonts3.setHAnsi( "Times New Roman");
        // Create object for lang
        CTLanguage language3 = WmlObjectFactory.createCTLanguage();
        rpr2.setLang(language3);
        language3.setVal( "ru-RU");
        // Create object for sz
        HpsMeasure hpsmeasure5 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSz(hpsmeasure5);
        hpsmeasure5.setVal( BigInteger.valueOf( 28) );
        // Create object for szCs
        HpsMeasure hpsmeasure6 = WmlObjectFactory.createHpsMeasure();
        rpr2.setSzCs(hpsmeasure6);
        hpsmeasure6.setVal( BigInteger.valueOf( 28) );
        // Create object for t (wrapped in JAXBElement)
        Text text2 = WmlObjectFactory.createText();
        JAXBElement<Text> textWrapped2 = WmlObjectFactory.createRT(text2);
        r2.getContent().add( textWrapped2);
        text2.setValue(" " + answer);
        text2.setSpace( "preserve");
    }

    /**
     * Вставляет в документ формулу
     * @param formula Формула для вставки
     */
    public void addFormula(FormulaModel formula){
        P p = WmlObjectFactory.createP();
        // Create object for pPr
        PPr ppr = WmlObjectFactory.createPPr();
        p.setPPr(ppr);
        // Create object for rPr
        ParaRPr pararpr = WmlObjectFactory.createParaRPr();
        ppr.setRPr(pararpr);
        // Create object for rFonts
        RFonts rfonts = WmlObjectFactory.createRFonts();
        pararpr.setRFonts(rfonts);
        rfonts.setAscii( "Times New Roman");
        rfonts.setCs( "Times New Roman");
        rfonts.setHAnsi( "Times New Roman");
        // Create object for lang
        CTLanguage language = WmlObjectFactory.createCTLanguage();
        pararpr.setLang(language);
        language.setVal( "en-US");
        // Create object for sz
        HpsMeasure hpsmeasure = WmlObjectFactory.createHpsMeasure();
        pararpr.setSz(hpsmeasure);
        hpsmeasure.setVal( BigInteger.valueOf( 28) );
        // Create object for i
        BooleanDefaultTrue booleandefaulttrue = WmlObjectFactory.createBooleanDefaultTrue();
        pararpr.setI(booleandefaulttrue);
        // Create object for szCs
        HpsMeasure hpsmeasure2 = WmlObjectFactory.createHpsMeasure();
        pararpr.setSzCs(hpsmeasure2);
        hpsmeasure2.setVal( BigInteger.valueOf( 28) );
        // Create object for ind
        PPrBase.Ind pprbaseind = WmlObjectFactory.createPPrBaseInd();
        ppr.setInd(pprbaseind);
        pprbaseind.setFirstLine( BigInteger.valueOf( 709) );
        // Create object for spacing
        PPrBase.Spacing pprbasespacing = WmlObjectFactory.createPPrBaseSpacing();
        ppr.setSpacing(pprbasespacing);
        pprbasespacing.setAfter( BigInteger.valueOf( 0) );
        pprbasespacing.setLine( BigInteger.valueOf( 360) );
        pprbasespacing.setLineRule(STLineSpacingRule.AUTO);
        // Create object for jc
        Jc jc = WmlObjectFactory.createJc();
        ppr.setJc(jc);
        jc.setVal(JcEnumeration.BOTH);
        p.setParaId( "207B6B5A");
        p.setTextId( "185ECC1B");
        org.docx4j.math.ObjectFactory mathObjectFactory = new org.docx4j.math.ObjectFactory();
        // Create object for oMathPara (wrapped in JAXBElement)
        CTOMathPara omathpara = mathObjectFactory.createCTOMathPara();
        JAXBElement<CTOMathPara> omathparaWrapped = mathObjectFactory.createOMathPara(omathpara);
        p.getContent().add( omathparaWrapped);
        // Create object for oMath
        CTOMath omath = mathObjectFactory.createCTOMath();
        omathpara.getOMath().add( omath);
        // Create object for r (wrapped in JAXBElement)

        ArrayList<JAXBElement> arrayList = formula.toOpenXML();
        for (JAXBElement rWrapped : arrayList) {
            omath.getEGOMathElements().add(rWrapped);
        }

        // Create object for bookmarkStart (wrapped in JAXBElement)
        CTBookmark bookmark = WmlObjectFactory.createCTBookmark();
        JAXBElement<CTBookmark> bookmarkWrapped = WmlObjectFactory.createPBookmarkStart(bookmark);
        p.getContent().add( bookmarkWrapped);
        bookmark.setName( "_GoBack");
        bookmark.setId( BigInteger.valueOf( 0) );
        // Create object for bookmarkEnd (wrapped in JAXBElement)
        CTMarkupRange markuprange = WmlObjectFactory.createCTMarkupRange();
        JAXBElement<CTMarkupRange> markuprangeWrapped = WmlObjectFactory.createPBookmarkEnd(markuprange);
        p.getContent().add( markuprangeWrapped);
        markuprange.setId( BigInteger.valueOf( 0) );

        Body.getContent().add(p);
    }

    /**
     * Сохраняет документ в файл
     * @return Истина в случае успешного выполнения операции, иначе ложь
     */
    public boolean save(){
        try {
            WordMLPackage.save(new java.io.File(Path));
            return true;
        } catch (Docx4JException e) {
            e.printStackTrace();
            return false;
        }
    }
}
