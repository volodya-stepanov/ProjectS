package DataModels;

/**
 * Множитель (атом со знаком, возведённый в степень)
 */
public class FactorModel extends FormulaModel {

    /**
     * Основание степени
     */
    private SignedAtomModel Base;

    /**
     * Показатель степени
     */
    private SignedAtomModel Exponent;

    /**
     * Математическая операция, которая стоит перед множителем (умножить или разделить)
     */
    private MathOpModel MathOperation;

    /**
     * Задаёт основание степени
     * @param base Основание степени
     */
    public void setBase(SignedAtomModel base) {
        Base = base;
    }

    /**
     * Возвращает основание степени
     * @return Основание степени
     */
    public SignedAtomModel getBase() {
        return Base;
    }

    /**
     * Задаёт показатель степени
     * @param exponent Показатель степени
     */
    public void setExponent(SignedAtomModel exponent) {
        Exponent = exponent;
    }

    /**
     * Возвращает показатель степени
     * @return Показатель степени
     */
    public SignedAtomModel getExponent(){
        return Exponent;
    }

    @Override
    public String toString() {
        boolean isPositive = false;

        if (Exponent != null) {
            isPositive = !Exponent.isNegative();

            NumberModel numberModel = null;

            try {
                numberModel = (NumberModel) Exponent.getAtom().getExpression();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isPositive && numberModel != null && numberModel.getValue() == 1) {
                return Base.toString();
            } else {
                return Base.toString() + "^" + Exponent.toString();
            }
        } else {
            return Base.toString(); //TODO: Отрефакторить!
        }
    }

    public MathOpModel getMathOperation() {
        return MathOperation;
    }

    public void setMathOperation(MathOpModel mathOperation) {
        MathOperation = mathOperation;
    }

    public FactorModel(){
        MathOperation = MathOpModel.None;
    }
}
