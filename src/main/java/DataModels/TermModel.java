package DataModels;

import java.util.ArrayList;

/**
 * Член
 */
public class TermModel extends FormulaModel {

    /**
     * Множители
     */
    public ArrayList<FactorModel> Factors;

    /**
     * Математическая операция, которая стоит перед членом (плюс или минус)
     */
    private MathOpModel MathOperation;

    /**
     * Инициализирует экземпляр класса
     */
    public TermModel(){
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

    public MathOpModel getMathOperation() {
        return MathOperation;
    }

    public void setMathOperation(MathOpModel mathOperation) {
        MathOperation = mathOperation;
    }
}
