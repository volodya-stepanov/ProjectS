import DataModels.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;

public class ArithmeticWalker implements ArithmeticListener{

    //TODO: Поменять на private, убрать букву m
    public AtomModel mCurrentAtom;

    public SignedAtomModel mCurrentSignedAtom;

    public FactorModel CurrentFactor;

    public TermModel CurrentTerm;

    public ExpressionModel CurrentExpression;

    public EquationModel CurrentEquation;

    public void enterEquation(ArithmeticParser.EquationContext ctx) {
        CurrentEquation = new EquationModel();
    }

    public void exitEquation(ArithmeticParser.EquationContext ctx) {
        String sign = ctx.getChild(1).getText();
        if (sign.contains("=")){
            CurrentEquation.setRelation(RelOpModel.Equals);
        } else if (sign.contains(">")){
            CurrentEquation.setRelation(RelOpModel.GreaterThan);
        } else if (sign.contains("<")){
            CurrentEquation.setRelation(RelOpModel.LessThan);
        }
    }

    public void enterExpression(ArithmeticParser.ExpressionContext ctx) {
        CurrentExpression = new ExpressionModel();
    }

    public void exitExpression(ArithmeticParser.ExpressionContext ctx) {
        int j = 1;

        // Расставляем знаки перед членами
        for (int i = 1; i < ctx.children.size(); i+=2){
            String sign = ctx.children.get(i).getText();
            if (sign.contains("+")) {
                CurrentExpression.Terms.get(j).setMathOperation(MathOpModel.Plus);
            } else if(sign.contains("-")){
                CurrentExpression.Terms.get(j).setMathOperation(MathOpModel.Minus);
            }
            j++;
        }

        if (CurrentEquation.getLeftSide() == null){
            CurrentEquation.setLeftSide(CurrentExpression);
        } else {
            CurrentEquation.setRightSide(CurrentExpression);
        }
    }

    public void enterTerm(ArithmeticParser.TermContext ctx) {
        CurrentTerm = new TermModel();
    }

    public void exitTerm(ArithmeticParser.TermContext ctx) {
        int j = 1;

        // Расставляем знаки перед множителями
        for (int i = 1; i < ctx.children.size(); i+=2){
            String sign = ctx.children.get(i).getText();
            if (sign.contains("*")) {
                CurrentTerm.Factors.get(j).setMathOperation(MathOpModel.Multiply);
            } else if(sign.contains("/")){
                CurrentTerm.Factors.get(j).setMathOperation(MathOpModel.Divide);
            }
            j++;
        }

        CurrentExpression.Terms.add(CurrentTerm);
    }

    public void enterFactor(ArithmeticParser.FactorContext ctx) {
        CurrentFactor = new FactorModel();

    }

    public void exitFactor(ArithmeticParser.FactorContext ctx) {
        CurrentTerm.Factors.add(CurrentFactor);
    }

    public void enterSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        mCurrentSignedAtom = new SignedAtomModel();

    }

    public void exitSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        // TODO: Следующий фрагмент кода, возможно, нужно перенести в выход из атома
        String text = ctx.getText();
        if (text.contains("-")){
            mCurrentSignedAtom.setNegative(true);
        }
        mCurrentSignedAtom.setAtom(mCurrentAtom);

        //TODO: При выходе из всех узлов занулить текущие узлы

        if (CurrentFactor.getBase() == null){
            CurrentFactor.setBase(mCurrentSignedAtom);
        } else {
            CurrentFactor.setExponent(mCurrentSignedAtom);
        }
    }

    public void enterAtom(ArithmeticParser.AtomContext ctx) {
        mCurrentAtom = new AtomModel();
    }

    public void exitAtom(ArithmeticParser.AtomContext ctx) {

    }

    public void enterScientific(ArithmeticParser.ScientificContext ctx) {

    }

    public void exitScientific(ArithmeticParser.ScientificContext ctx) {
        double value = Double.parseDouble(ctx.getText());
        mCurrentAtom.setExpression(new NumberModel(value));
    }

    public void enterVariable(ArithmeticParser.VariableContext ctx) {

    }

    public void exitVariable(ArithmeticParser.VariableContext ctx) {
        String value = ctx.getText();
        mCurrentAtom.setExpression(new VariableModel(value));
    }

    public void enterRelop(ArithmeticParser.RelopContext ctx) {

    }

    public void exitRelop(ArithmeticParser.RelopContext ctx) {

    }

    public void visitTerminal(TerminalNode terminalNode) {

    }

    public void visitErrorNode(ErrorNode errorNode) {

    }

    public void enterEveryRule(ParserRuleContext parserRuleContext) {

    }

    public void exitEveryRule(ParserRuleContext parserRuleContext) {

    }

}
