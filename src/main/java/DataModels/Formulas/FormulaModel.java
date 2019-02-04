package DataModels.Formulas;

import com.sun.istack.internal.Nullable;
import org.docx4j.math.CTR;
import org.docx4j.wml.*;

import javax.xml.bind.JAXBElement;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Модель формулы - уравнения, выражения, переменной и т.д.
 */
public abstract class FormulaModel {

    /** Родитель */
    protected FormulaModel Parent;

    /** Хэш-таблица для хранения значений переменных */
    protected HashMap<String, Double> VariablesHashMap;

    /**
     * Инициализирует экземпляр класса
     * @param parent Родитель - формула, в которую входит данная формула
     */
    protected FormulaModel(@Nullable FormulaModel parent) {
        VariablesHashMap = new HashMap<String, Double>();
        Parent = parent;
    }

    /**
     * Возвращает формулу в формате OpenXML для записи в документ
     * @return Формула в формате OpenXML для записи в документ
     */
    public ArrayList<JAXBElement> toOpenXML(){
        ArrayList<JAXBElement> arrayList = new ArrayList<JAXBElement>();

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

        arrayList.add(rWrapped);

        return arrayList;
    }

    /**
     * Определяет, состоит ли формула только из одного элемента, являющегося числом
     * @return Истина, если формула состоит только из одного элемента, являющегося числом, иначе ложь
     */
    public boolean isNumber(){
        return false;
    }

    /**
     * Создаёт и возвращает точно такой же объект, как и сама эта формула.
     * @param parent Родитель
     * @return Объект того же класса, что и текущая формула, с такими же значениями полей
     */
    public abstract FormulaModel copy(@Nullable FormulaModel parent);

    /**
     * Определяет, можно ли вычислить значение данной формулы
     * @return Истина, если все операнды являются числами, иначе ложь
     */
    public abstract boolean canSolve();

    public abstract void solve();

    /**
     * Возвращает уравнение, которое является родителем для данной формулы
     * @return Уравнение, которое является родителем для данной формулы
     */
    protected EquationModel getEquation(){
        return null;
    }


    // TODO: в кских случаях использовать абстрактные методв, а каких - обычные?



    public FormulaModel getParent() {
        return Parent;
    }

    public void setParent(FormulaModel parent) {
        Parent = parent;
    }

    public HashMap<String, Double> getVariablesHashMap(){
        return VariablesHashMap;
    }

    public void setVariablesHashMap(HashMap<String, Double> variablesHashMap){
        VariablesHashMap = variablesHashMap;
    }

    /**
     * Возвращает числовое значение формулы в том случае, когда для неё выполняется условие isNumber()
     * @return Числовое значение формулы
     */
    protected abstract double getValue();

    /**
     * Устанавливает формуле числовое значение при решении
     * @param value Значение
     */
    protected abstract void setValue(double value);
}
