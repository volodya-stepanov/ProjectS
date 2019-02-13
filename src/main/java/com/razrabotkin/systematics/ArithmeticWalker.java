package com.razrabotkin.systematics;

import com.razrabotkin.systematics.DataModels.Formulas.*;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import com.razrabotkin.systematics.gen.*;


public class ArithmeticWalker implements ArithmeticListener {

    //TODO: Поменять на private, убрать букву m
    public AtomModel CurrentAtom;

    public SignedAtomModel CurrentSignedAtom;

    public FactorModel CurrentFactor;

    public TermModel CurrentTerm;

    public ExpressionModel CurrentExpression;

    public EquationModel CurrentEquation;

    public void enterEquation(ArithmeticParser.EquationContext ctx) {
        CurrentEquation = new EquationModel(null);
    }

    public void exitEquation(ArithmeticParser.EquationContext ctx) {
        String sign = ctx.getChild(1).getText();
        if (sign.contains("=")){
            CurrentEquation.Expressions.get(1).setRelation(RelOpModel.Equals);
        } else if (sign.contains(">")){
            CurrentEquation.Expressions.get(1).setRelation(RelOpModel.GreaterThan);
        } else if (sign.contains("<")){
            CurrentEquation.Expressions.get(1).setRelation(RelOpModel.LessThan);
        }
    }

    public void enterExpression(ArithmeticParser.ExpressionContext ctx) {
        CurrentExpression = new ExpressionModel(CurrentEquation);
    }

    public void exitExpression(ArithmeticParser.ExpressionContext ctx) {
        int j = 1;

        // Расставляем знаки перед членами
        if (CurrentExpression!=null && CurrentExpression.Terms.size() > 0) {
            for (int i = 1; i < ctx.children.size(); i += 2) {
                String sign = ctx.children.get(i).getText();
                if (sign.contains("+")) {
                    CurrentExpression.Terms.get(j).setMathOperation(MathOpModel.Plus);
                } else if (sign.contains("-")) {
                    CurrentExpression.Terms.get(j).setMathOperation(MathOpModel.Minus);
                }
                j++;
            }
        }

        if (CurrentEquation != null) {
//            if (CurrentEquation.getLeftSide() == null) {
//                CurrentEquation.setLeftSide(CurrentExpression);
//            } else {
//                CurrentEquation.setRightSide(CurrentExpression);
//            }
            CurrentEquation.Expressions.add(CurrentExpression);
        }
    }

    public void enterTerm(ArithmeticParser.TermContext ctx) {
        CurrentTerm = new TermModel(CurrentExpression);
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

        if (CurrentExpression != null) {
            CurrentExpression.Terms.add(CurrentTerm);
        }
    }

    public void enterFactor(ArithmeticParser.FactorContext ctx) {
        CurrentFactor = new FactorModel(CurrentTerm);

    }

    public void exitFactor(ArithmeticParser.FactorContext ctx) {
        if (CurrentTerm != null) {
            CurrentTerm.Factors.add(CurrentFactor);
        }
    }

    public void enterSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        CurrentSignedAtom = new SignedAtomModel(CurrentFactor);

    }

    public void exitSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        // TODO: Следующий фрагмент кода, возможно, нужно перенести в выход из атома
        String text = ctx.getText();
        if (text.contains("-")){
            CurrentSignedAtom.setNegative(true);
        }
        CurrentSignedAtom.setAtom(CurrentAtom);

        //TODO: При выходе из всех узлов занулить текущие узлы
        if (CurrentFactor!=null) {
            if (CurrentFactor.getBase() == null) {
                CurrentFactor.setBase(CurrentSignedAtom);
            } else {
                CurrentFactor.setExponent(CurrentSignedAtom);
            }
        }
    }

    public void enterAtom(ArithmeticParser.AtomContext ctx) {
        CurrentAtom = new AtomModel(CurrentSignedAtom);
    }

    public void exitAtom(ArithmeticParser.AtomContext ctx) {

    }

    public void enterScientific(ArithmeticParser.ScientificContext ctx) {

    }

    public void exitScientific(ArithmeticParser.ScientificContext ctx) {
        double value = Double.parseDouble(ctx.getText());
        CurrentAtom.setExpression(new NumberModel(CurrentAtom, value));
    }

    public void enterVariable(ArithmeticParser.VariableContext ctx) {

    }

    public void exitVariable(ArithmeticParser.VariableContext ctx) {
        String value = ctx.getText();
        CurrentAtom.setExpression(new VariableModel(CurrentAtom, value));
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
