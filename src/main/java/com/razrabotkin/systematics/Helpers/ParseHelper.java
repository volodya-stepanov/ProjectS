package com.razrabotkin.systematics.Helpers;

import com.razrabotkin.systematics.DataModels.Formulas.ExpressionModel;
import com.razrabotkin.systematics.ArithmeticWalker;
import com.razrabotkin.systematics.gen.ArithmeticLexer;
import com.razrabotkin.systematics.gen.ArithmeticParser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;



/**
 * Вспомогательный класс для выполнения функций парсинга
 */
public class ParseHelper {

    public ExpressionModel parse(String expression){
        ArithmeticLexer lexer = new ArithmeticLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArithmeticParser parser = new ArithmeticParser(tokens);
        ParseTree tree = parser.expression();   // Здесь переключаются правила!
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        ArithmeticWalker arithmeticWalker = new ArithmeticWalker();
        parseTreeWalker.walk(arithmeticWalker, tree);
        return arithmeticWalker.CurrentExpression;
    }
}
