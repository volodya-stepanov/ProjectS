package DataModels;

import java.util.ArrayList;

/**
 * Выражение
 */
public class ExpressionModel {

    /**
     * Члены
     */
    public ArrayList<TermModel> Terms;

    /**
     * Инициализирует экземпляр класса
     */
    public ExpressionModel(){
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
}
