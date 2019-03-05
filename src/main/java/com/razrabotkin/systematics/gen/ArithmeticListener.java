// Generated from D:/version-control/ProjectS/src/main\Arithmetic.g4 by ANTLR 4.7.2
package com.razrabotkin.systematics.gen;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ArithmeticParser}.
 */
public interface ArithmeticListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#equation}.
	 * @param ctx the parse tree
	 */
	void enterEquation(ArithmeticParser.EquationContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#equation}.
	 * @param ctx the parse tree
	 */
	void exitEquation(ArithmeticParser.EquationContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(ArithmeticParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(ArithmeticParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(ArithmeticParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(ArithmeticParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(ArithmeticParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(ArithmeticParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#signedAtom}.
	 * @param ctx the parse tree
	 */
	void enterSignedAtom(ArithmeticParser.SignedAtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#signedAtom}.
	 * @param ctx the parse tree
	 */
	void exitSignedAtom(ArithmeticParser.SignedAtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(ArithmeticParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(ArithmeticParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#scientific}.
	 * @param ctx the parse tree
	 */
	void enterScientific(ArithmeticParser.ScientificContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#scientific}.
	 * @param ctx the parse tree
	 */
	void exitScientific(ArithmeticParser.ScientificContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ArithmeticParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ArithmeticParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#relop}.
	 * @param ctx the parse tree
	 */
	void enterRelop(ArithmeticParser.RelopContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#relop}.
	 * @param ctx the parse tree
	 */
	void exitRelop(ArithmeticParser.RelopContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#sqrt}.
	 * @param ctx the parse tree
	 */
	void enterSqrt(ArithmeticParser.SqrtContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#sqrt}.
	 * @param ctx the parse tree
	 */
	void exitSqrt(ArithmeticParser.SqrtContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#derivative}.
	 * @param ctx the parse tree
	 */
	void enterDerivative(ArithmeticParser.DerivativeContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#derivative}.
	 * @param ctx the parse tree
	 */
	void exitDerivative(ArithmeticParser.DerivativeContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#exponentialFunction}.
	 * @param ctx the parse tree
	 */
	void enterExponentialFunction(ArithmeticParser.ExponentialFunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#exponentialFunction}.
	 * @param ctx the parse tree
	 */
	void exitExponentialFunction(ArithmeticParser.ExponentialFunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#logarithm}.
	 * @param ctx the parse tree
	 */
	void enterLogarithm(ArithmeticParser.LogarithmContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#logarithm}.
	 * @param ctx the parse tree
	 */
	void exitLogarithm(ArithmeticParser.LogarithmContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#naturalLogarithm}.
	 * @param ctx the parse tree
	 */
	void enterNaturalLogarithm(ArithmeticParser.NaturalLogarithmContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#naturalLogarithm}.
	 * @param ctx the parse tree
	 */
	void exitNaturalLogarithm(ArithmeticParser.NaturalLogarithmContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#sinus}.
	 * @param ctx the parse tree
	 */
	void enterSinus(ArithmeticParser.SinusContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#sinus}.
	 * @param ctx the parse tree
	 */
	void exitSinus(ArithmeticParser.SinusContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#cosine}.
	 * @param ctx the parse tree
	 */
	void enterCosine(ArithmeticParser.CosineContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#cosine}.
	 * @param ctx the parse tree
	 */
	void exitCosine(ArithmeticParser.CosineContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#tangent}.
	 * @param ctx the parse tree
	 */
	void enterTangent(ArithmeticParser.TangentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#tangent}.
	 * @param ctx the parse tree
	 */
	void exitTangent(ArithmeticParser.TangentContext ctx);
	/**
	 * Enter a parse tree produced by {@link ArithmeticParser#cotangent}.
	 * @param ctx the parse tree
	 */
	void enterCotangent(ArithmeticParser.CotangentContext ctx);
	/**
	 * Exit a parse tree produced by {@link ArithmeticParser#cotangent}.
	 * @param ctx the parse tree
	 */
	void exitCotangent(ArithmeticParser.CotangentContext ctx);
}