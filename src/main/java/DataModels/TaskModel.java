package DataModels;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
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
            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
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

    public Document createDocument() {

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
        // Create object for p
        P p = wmlObjectFactory.createP();
        body.getContent().add( p);
        p.setParaId( "741DF8C0");
        p.setTextId( "17D1236E");
        // Create object for r
        R r = wmlObjectFactory.createR();
        p.getContent().add( r);
        // Create object for t (wrapped in JAXBElement)
        Text text = wmlObjectFactory.createText();
        JAXBElement<Text> textWrapped = wmlObjectFactory.createRT(text);
        r.getContent().add( textWrapped);
        text.setValue(Description);
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
        document.setIgnorable( "w14 w15 w16se w16cid wp14");
        // Create object for body
        P p2 = wmlObjectFactory.createP();
        document.getContent().add( p2);
        p2.setParaId( "741DF8C0");
        p2.setTextId( "17D1236E");

        return document;
    }
}
