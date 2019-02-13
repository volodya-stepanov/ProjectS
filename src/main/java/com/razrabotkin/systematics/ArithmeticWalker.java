package com.razrabotkin.systematics;

import com.razrabotkin.systematics.DataModels.Formulas.*;
import com.razrabotkin.systematics.Helpers.ClassHelper;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import com.razrabotkin.systematics.gen.*;

import java.util.ArrayList;
import java.util.HashMap;


public class ArithmeticWalker implements ArithmeticListener {

    //TODO: Поменять на private, убрать букву m
    public ArrayList<AtomModel> CurrentAtoms;

    public AtomModel CurrentAtom;

    public SignedAtomModel CurrentSignedAtom;

    public FactorModel CurrentFactor;

    public TermModel CurrentTerm;

    public ExpressionModel CurrentExpression;

    //public ArrayList<ExpressionModel> CurrentExpressions;

    public EquationModel CurrentEquation;

    ClassHelper Helper;

    HashMap<ParserRuleContext, FormulaModel> NodesHashMap;

    public ArithmeticWalker(){
        CurrentAtoms = new ArrayList<AtomModel>();
        Helper = new ClassHelper();
        NodesHashMap = new HashMap<ParserRuleContext, FormulaModel>();
    }

    public void enterEquation(ArithmeticParser.EquationContext ctx) {
        CurrentEquation = new EquationModel(null);
        NodesHashMap.put(ctx, CurrentEquation);
    }

    public void exitEquation(ArithmeticParser.EquationContext ctx) {
        EquationModel currentEquation = (EquationModel) NodesHashMap.get(ctx);

        String sign = ctx.getChild(1).getText();
        if (sign.contains("=")){
            currentEquation.Expressions.get(1).setRelation(RelOpModel.Equals);
        } else if (sign.contains(">")){
            currentEquation.Expressions.get(1).setRelation(RelOpModel.GreaterThan);
        } else if (sign.contains("<")){
            currentEquation.Expressions.get(1).setRelation(RelOpModel.LessThan);
        }
    }

    public void enterExpression(ArithmeticParser.ExpressionContext ctx) {
        //ExpressionModel currentExpression = null;

        if (Helper.isTypeOf(ctx.parent, ArithmeticParser.EquationContext.class)){
            //EquationModel currentEquation = (EquationModel) NodesHashMap.get(ctx);
            CurrentExpression = new ExpressionModel(CurrentEquation);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.AtomContext.class)){
            CurrentExpression = new ExpressionModel(CurrentAtom);
        }

        //CurrentExpressions.add(currentExpression);
        NodesHashMap.put(ctx, CurrentExpression);
    }

    public void exitExpression(ArithmeticParser.ExpressionContext ctx) {
        int j = 1;

//        int size = CurrentExpressions.size();
//        ExpressionModel currentExpression = CurrentExpressions.get(size - 1);
        ExpressionModel currentExpression = (ExpressionModel) NodesHashMap.get(ctx);

        // Расставляем знаки перед членами
        if (currentExpression!=null && currentExpression.Terms.size() > 0) {
            for (int i = 1; i < ctx.children.size(); i += 2) {
                String sign = ctx.children.get(i).getText();
                if (sign.contains("+")) {
                    currentExpression.Terms.get(j).setMathOperation(MathOpModel.Plus);
                } else if (sign.contains("-")) {
                    currentExpression.Terms.get(j).setMathOperation(MathOpModel.Minus);
                }
                j++;
            }
        }

        if (Helper.isTypeOf(currentExpression.getParent(), EquationModel.class)){
//            EquationModel currentEquation = (EquationModel) NodesHashMap.get(ctx);
//            if (currentEquation != null) {
//                currentEquation.Expressions.add(currentExpression);
//            }
            EquationModel currentEquation = (EquationModel) currentExpression.getParent();
            currentEquation.Expressions.add(currentExpression);
        } else if (Helper.isTypeOf(currentExpression.getParent(), AtomModel.class)){
            AtomModel currentAtom = (AtomModel) currentExpression.getParent();
            currentAtom.setExpression(currentExpression);
        }

        CurrentExpression = currentExpression;
    }

    public void enterTerm(ArithmeticParser.TermContext ctx) {
        CurrentTerm = new TermModel(CurrentExpression);
        NodesHashMap.put(ctx, CurrentTerm);
    }

    public void exitTerm(ArithmeticParser.TermContext ctx) {
        TermModel term = (TermModel) NodesHashMap.get(ctx);
        ExpressionModel expression = (ExpressionModel) term.getParent();

        // Расставляем знаки перед членами
        int j = 1;

        // Расставляем знаки перед множителями
        for (int i = 1; i < ctx.children.size(); i+=2){
            String sign = ctx.children.get(i).getText();
            if (sign.contains("*")) {
                term.Factors.get(j).setMathOperation(MathOpModel.Multiply);
            } else if(sign.contains("/")){
                term.Factors.get(j).setMathOperation(MathOpModel.Divide);
            }
            j++;
        }

        if (expression != null) {
            expression.Terms.add(term);
        }
    }

    public void enterFactor(ArithmeticParser.FactorContext ctx) {
        CurrentFactor = new FactorModel(CurrentTerm);
        NodesHashMap.put(ctx, CurrentFactor);
    }

    public void exitFactor(ArithmeticParser.FactorContext ctx) {
        FactorModel factor = (FactorModel) NodesHashMap.get(ctx);
        TermModel term = (TermModel) factor.getParent();
        term.Factors.add(factor);

//        if (CurrentTerm != null) {
//            CurrentTerm.Factors.add(CurrentFactor);
//        }
    }

    public void enterSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        CurrentSignedAtom = new SignedAtomModel(CurrentFactor);
        NodesHashMap.put(ctx, CurrentSignedAtom);
    }

    public void exitSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        SignedAtomModel signedAtom = (SignedAtomModel) NodesHashMap.get(ctx);
        FactorModel factor = (FactorModel) signedAtom.getParent();

        String text = ctx.getText();
        if (text.contains("-")){
            signedAtom.setNegative(true);
        }

        //TODO: При выходе из всех узлов занулить текущие узлы
        if (factor!=null) {
            if (factor.getBase() == null) {
                factor.setBase(signedAtom);
            } else {
                factor.setExponent(signedAtom);
            }
        }
    }

    public void enterAtom(ArithmeticParser.AtomContext ctx) {
        CurrentAtom = new AtomModel(CurrentSignedAtom);
        NodesHashMap.put(ctx, CurrentAtom);

        //CurrentAtoms.add(currentAtom);
    }

    public void exitAtom(ArithmeticParser.AtomContext ctx) {
        AtomModel atom = (AtomModel) NodesHashMap.get(ctx);
        SignedAtomModel signedAtom = (SignedAtomModel) atom.getParent();

        signedAtom.setAtom(atom);

        CurrentAtoms.remove(atom);
    }

    public void enterScientific(ArithmeticParser.ScientificContext ctx) {
        NumberModel currentNumber = new NumberModel(CurrentAtom, 0);
        NodesHashMap.put(ctx, currentNumber);
    }

    public void exitScientific(ArithmeticParser.ScientificContext ctx) {
        NumberModel currentNumber = (NumberModel) NodesHashMap.get(ctx);
        double value = Double.parseDouble(ctx.getText());
        currentNumber.setValue(value);

        AtomModel currentAtom = (AtomModel) currentNumber.getParent();
        currentAtom.setExpression(currentNumber);
    }

    public void enterVariable(ArithmeticParser.VariableContext ctx) {
        VariableModel currentVariable = new VariableModel(CurrentAtom, "");
        NodesHashMap.put(ctx, currentVariable);
    }

    public void exitVariable(ArithmeticParser.VariableContext ctx) {
        VariableModel variable = (VariableModel) NodesHashMap.get(ctx);

        String value = ctx.getText();
        variable.setName(value);

        AtomModel currentAtom = (AtomModel) variable.getParent();
        currentAtom.setExpression(variable);
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
