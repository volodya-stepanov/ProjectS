package com.razrabotkin.systematics.DataModels.Tasks;

import com.razrabotkin.systematics.DataModels.Formulas.*;
import com.razrabotkin.systematics.DataModels.Objects.DocumentModel;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.FormulaBlock;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.TextBlock;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class FindDerivative extends TaskModel{

    /**
     * Инициализирует экземпляр класса
     *
     * @param document      Документ, в котором содержится данное задание
     * @param description   Описание (текстовая часть) задания
     * @param formulaString Формула, введённая пользователем в виде строки
     */
    public FindDerivative(DocumentModel document, String description, String formulaString) {
        super(document, description, formulaString);

        DisplayName = "Производная";
        Formula = Helper.parseExpression(formulaString);
    }

    @Override
    public void solve() {
        // TODO: Заменить все эти портянки парсингом!
        EquationModel equation = new EquationModel(null);
        ExpressionModel expression = new ExpressionModel(equation);
        equation.Expressions.add(expression);

        TermModel term = new TermModel(expression);
        expression.Terms.add(term);

        FactorModel factor = new FactorModel(term);
        term.Factors.add(factor);

        SignedAtomModel signedAtom = new SignedAtomModel(factor);
        factor.setBase(signedAtom);

        AtomModel atom = new AtomModel(signedAtom);
        signedAtom.setAtom(atom);

        Derivative derivative = new Derivative(equation);
        derivative.setFunction((ExpressionModel) Formula);
        atom.setExpression(derivative);

        equation.solve();

        SolutionBlocks.add(new FormulaBlock(equation));

        // Так как мы вычисляем производную, ответом будет выражение, а не число.
        // Поэтому ы просто берём последнее выражение из уравнения.
        ExpressionModel answer = equation.Expressions.get(equation.Expressions.size()-1);

        Answers.add(answer);

        AnswerString = answer.toString();
    }
}
