package com.razrabotkin.systematics;

import com.razrabotkin.systematics.DataModels.Formulas.*;
import com.razrabotkin.systematics.Helpers.ClassHelper;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import com.razrabotkin.systematics.gen.*;

import java.util.HashMap;

/**
 * Гуляет по дереву объектов, созданному в результате парсинга формулы
 */
public class ArithmeticWalker implements ArithmeticListener {

    /** Уравнение, полученное в результате парсинга */
    public EquationModel Equation;

    /** Выражение, полученное в результате парсинга */
    public ExpressionModel Expression;

    /** Член, полученный в результате парсинга */
    public TermModel Term;

    /** Для определения классов объектов */
    private ClassHelper Helper;

    /** Таблица, в которой хранятся объекты. Ключи - объекты дерева, сформированного парсером; значения - объекты классов, унаследованных от FormulaModel, используемые в программе */
    private HashMap<ParserRuleContext, FormulaModel> NodesHashMap;

    /** Конструктор класса */
    public ArithmeticWalker(){
        Helper = new ClassHelper();
        NodesHashMap = new HashMap<ParserRuleContext, FormulaModel>();
    }

    /**
     * Вход в уравнение
     * @param ctx Уравнение в абстрактном синтаксическом дереве
     */
    public void enterEquation(ArithmeticParser.EquationContext ctx) {
        // Создаём модель уравнения и помещаем её в таблицу
        EquationModel equation = new EquationModel(null);
        NodesHashMap.put(ctx, equation);
    }

    /**
     * Выход из уравнения
     * @param ctx Уравнение в абстрактном синтаксическом дереве
     */
    public void exitEquation(ArithmeticParser.EquationContext ctx) {
        // Получаем модель уравнения из таблицы
        EquationModel equation = (EquationModel) NodesHashMap.get(ctx);

        // Устанавливаем знак между частями уравнения
        String sign = ctx.getChild(1).getText();
        if (sign.contains("=")){
            equation.Expressions.get(1).setRelation(RelOpModel.Equals);
        } else if (sign.contains(">")){
            equation.Expressions.get(1).setRelation(RelOpModel.GreaterThan);
        } else if (sign.contains("<")){
            equation.Expressions.get(1).setRelation(RelOpModel.LessThan);
        }

        // Записываем полученное уравнение в поле-результат
        Equation = equation;
    }

    /**
     * Вход в выражение
     * @param ctx Выражение в абстрактном синтаксическом дереве
     */
    public void enterExpression(ArithmeticParser.ExpressionContext ctx) {

        // Создаём модель выражения
        ExpressionModel expression = null;

        // Определяем родителя
        if (ctx.parent != null) {
            if (Helper.isTypeOf(ctx.parent, ArithmeticParser.EquationContext.class)) {
                ArithmeticParser.EquationContext equationContext = (ArithmeticParser.EquationContext) ctx.parent;
                EquationModel equation = (EquationModel) NodesHashMap.get(equationContext);
                expression = new ExpressionModel(equation);
            } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.AtomContext.class)) {
                ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
                AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
                expression = new ExpressionModel(atom);
            } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.DerivativeContext.class)) {
                ArithmeticParser.DerivativeContext derivativeContext = (ArithmeticParser.DerivativeContext) ctx.parent;
                Derivative derivative = (Derivative) NodesHashMap.get(derivativeContext);
                expression = new ExpressionModel(derivative);
            }
        } else {
            expression = new ExpressionModel(null);
        }

        // Помещаем уравнение в таблицу
        NodesHashMap.put(ctx, expression);
    }

    /**
     * Выход из выражения
     * @param ctx Выражение в абстрактном синтаксическом дереве
     */
    public void exitExpression(ArithmeticParser.ExpressionContext ctx) {
        // Получаем модель выражения из таблицы
        ExpressionModel expression = (ExpressionModel) NodesHashMap.get(ctx);

        // Расставляем знаки перед членами
        if (expression!=null) {
            if (expression.Terms.size() > 0) {
                // В выражении из АСД члены и знаки между ними являются отдельными элементами,
                // в нашей же модели элементами массива являются только члены,
                // и каждый член хранит информацию о знаке, стоящем перед ним.
                // Индекс j предназначен для перебора членов в объектной модели выражения,
                // и начинается он не с 0, а с 1, т.к. перед нулевым членом не может стоять знака
                int j = 1;

                // Индекс i предназначен для перебора членов в массиве из АСД.
                // Перебор начинаем также с 1 и с каждой итерацией увеличиваем индекс на 2,
                // т.к. нулевой, второй, четвёртый и т.д. элементы - это члены,
                // а первый, третий, пятый и т.д. - это знаки
                for (int i = 1; i < ctx.children.size(); i += 2) {
                    String sign = ctx.children.get(i).getText();
                    if (sign.contains("+")) {
                        expression.Terms.get(j).setMathOperation(MathOpModel.Plus);
                    } else if (sign.contains("-")) {
                        expression.Terms.get(j).setMathOperation(MathOpModel.Minus);
                    }
                    j++;
                }
            }

            // Определяем родителя данного выражения и добавляем выражение в объект-родитель
            if (expression.getParent() != null) {
                if (Helper.isTypeOf(expression.getParent(), EquationModel.class)) {
                    EquationModel equation = (EquationModel) expression.getParent();
                    equation.Expressions.add(expression);
                } else if (Helper.isTypeOf(expression.getParent(), AtomModel.class)) {
                    AtomModel atom = (AtomModel) expression.getParent();
                    atom.setExpression(expression);
                } else if (Helper.isTypeOf(expression.getParent(), Derivative.class)) {
                    Derivative derivative = (Derivative) expression.getParent();
                    derivative.setFunction(expression);
                }
            }

            // Записываем полученное выражение в поле-результат
            Expression = expression;
        }
    }

    /**
     * Вход в член
     * @param ctx Член в абстрактном синтаксическом дереве
     */
    public void enterTerm(ArithmeticParser.TermContext ctx) {
        // Определяем родителя данного члена, создаём объектную модель члена с указанием этого родителя и помещаем член в таблицу.
        ArithmeticParser.ExpressionContext expressionContext = (ArithmeticParser.ExpressionContext) ctx.parent;
        ExpressionModel expression = (ExpressionModel) NodesHashMap.get(expressionContext);
        TermModel term = new TermModel(expression);
        NodesHashMap.put(ctx, term);
    }

    /**
     * Выход из члена
     * @param ctx Член в абстрактном синтаксическом дереве
     */
    public void exitTerm(ArithmeticParser.TermContext ctx) {
        // Получаем модель члена из таблицы
        TermModel term = (TermModel) NodesHashMap.get(ctx);

        // Расставляем знаки перед членами (пояснение см. в методе exitExpression)
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

        // Определяем родителя данного члена и добавляем член в выражение-родитель
        if (term.getParent() != null) {
            ExpressionModel expression = (ExpressionModel) term.getParent();
            expression.Terms.add(term);
        }

        Term = term;
    }

    /**
     * Вход в множитель
     * @param ctx Множитель в абстрактном синтаксическом дереве
     */
    public void enterFactor(ArithmeticParser.FactorContext ctx) {
        // Определяем родителя данного множителя, создаём объектную модель множителя с указанием этого родителя и помещаем множитель в таблицу.
        ArithmeticParser.TermContext termContext = (ArithmeticParser.TermContext) ctx.getParent();
        TermModel term = (TermModel) NodesHashMap.get(termContext);
        FactorModel factor = new FactorModel(term);
        NodesHashMap.put(ctx, factor);
    }

    /**
     * Выход из множителя
     * @param ctx Множитель в абстрактном синтаксическом дереве
     */
    public void exitFactor(ArithmeticParser.FactorContext ctx) {
        // Получаем модель множителя из таблицы, определяем родителя данного множителя и добавляем множитель в член-родитель
        FactorModel factor = (FactorModel) NodesHashMap.get(ctx);
        TermModel term = (TermModel) factor.getParent();
        term.Factors.add(factor);
    }

    /**
     * Вход в атом со знаком
     * @param ctx Атом со знаком в абстрактном синтаксическом дереве
     */
    public void enterSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        // Определяем родителя данного атома со знаком, создаём объектную модель атома со знаком с указанием этого родителя и помещаем атом со знаком в таблицу.
        SignedAtomModel signedAtom = null;

        if (Helper.isTypeOf(ctx.parent, ArithmeticParser.FactorContext.class)){
            FactorModel factor = (FactorModel) NodesHashMap.get(ctx.getParent());
            signedAtom = new SignedAtomModel(factor);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.SqrtContext.class)){
            ArithmeticParser.SqrtContext sqrtContext = (ArithmeticParser.SqrtContext) ctx.parent;
            SquareRoot squareRoot = (SquareRoot) NodesHashMap.get(sqrtContext);
            signedAtom = new SignedAtomModel(squareRoot);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.ExponentialFunctionContext.class)){
            ArithmeticParser.ExponentialFunctionContext exponentialFunctionContext = (ArithmeticParser.ExponentialFunctionContext) ctx.parent;
            ExponentialFunction exponentialFunction = (ExponentialFunction) NodesHashMap.get(exponentialFunctionContext);
            signedAtom = new SignedAtomModel(exponentialFunction);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.LogarithmContext.class)){
            ArithmeticParser.LogarithmContext logarithmContext = (ArithmeticParser.LogarithmContext) ctx.parent;
            Logarithm logarithm = (Logarithm) NodesHashMap.get(logarithmContext);
            signedAtom = new SignedAtomModel(logarithm);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.NaturalLogarithmContext.class)){
            ArithmeticParser.NaturalLogarithmContext naturalLogarithmContext = (ArithmeticParser.NaturalLogarithmContext) ctx.parent;
            NaturalLogarithm naturalLogarithm = (NaturalLogarithm) NodesHashMap.get(naturalLogarithmContext);
            signedAtom = new SignedAtomModel(naturalLogarithm);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.SinusContext.class)){
            ArithmeticParser.SinusContext sinusContext = (ArithmeticParser.SinusContext) ctx.parent;
            Sinus sinus = (Sinus) NodesHashMap.get(sinusContext);
            signedAtom = new SignedAtomModel(sinus);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.CosineContext.class)){
            ArithmeticParser.CosineContext cosineContext = (ArithmeticParser.CosineContext) ctx.parent;
            Cosine cosine = (Cosine) NodesHashMap.get(cosineContext);
            signedAtom = new SignedAtomModel(cosine);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.TangentContext.class)){
            ArithmeticParser.TangentContext tangentContext = (ArithmeticParser.TangentContext) ctx.parent;
            Tangent tangent = (Tangent) NodesHashMap.get(tangentContext);
            signedAtom = new SignedAtomModel(tangent);
        } else if (Helper.isTypeOf(ctx.parent, ArithmeticParser.CotangentContext.class)){
            ArithmeticParser.CotangentContext cotangentContext = (ArithmeticParser.CotangentContext) ctx.parent;
            Cotangent cotangent = (Cotangent) NodesHashMap.get(cotangentContext);
            signedAtom = new SignedAtomModel(cotangent);
        }

        NodesHashMap.put(ctx, signedAtom);
    }

    /**
     * Выход из атома со знаком
     * @param ctx Атом со знаком в абстрактном синтаксическом дереве
     */
    public void exitSignedAtom(ArithmeticParser.SignedAtomContext ctx) {
        // Получаем модель атома со знаком из таблицы
        SignedAtomModel signedAtom = (SignedAtomModel) NodesHashMap.get(ctx);

        // Устанавливаем знак
        String text = ctx.getText();
        if (text.startsWith("-")){
            signedAtom.setNegative(true);
        }

        // Определяем родителя атома со знаком и добавляем этот атом со знаком в объект-родитель
        if (Helper.isTypeOf(signedAtom.getParent(), FactorModel.class)) {
            FactorModel factor = (FactorModel) signedAtom.getParent();

            // Данный атом со знаком может являться как основанием, так и показателем степени члена-родителя.
            // Исходим из того, что в первую очередь устанавливается основание степени, а затем - показатель.
            // Поэтому если основание степени ещё отсутствует, устанавливаем данный атом со знаком в качестве основания,
            // а если основание уже установлено - тогда в качестве показателя степени
            if (factor.getBase() == null) {
                factor.setBase(signedAtom);
            } else {
                factor.setExponent(signedAtom);
            }

        } else if (Helper.isTypeOf(signedAtom.getParent(), SquareRoot.class)) {
            SquareRoot sqrt = (SquareRoot) signedAtom.getParent();
            sqrt.setRadicalExpression(signedAtom);
        } else if (Helper.isTypeOf(signedAtom.getParent(), ExponentialFunction.class)) {
            ExponentialFunction exponentialFunction = (ExponentialFunction) signedAtom.getParent();
            exponentialFunction.setExponent(signedAtom);
        } else if (Helper.isTypeOf(signedAtom.getParent(), Logarithm.class)) {
            Logarithm logarithm = (Logarithm) signedAtom.getParent();

            if (logarithm.getBase() == null) {
                logarithm.setBase(signedAtom);
            } else {
                logarithm.setArgument(signedAtom);
            }
        } else if (Helper.isTypeOf(signedAtom.getParent(), NaturalLogarithm.class)) {
            NaturalLogarithm naturalLogarithm = (NaturalLogarithm) signedAtom.getParent();
            naturalLogarithm.setArgument(signedAtom);
        } else if (Helper.isTypeOf(signedAtom.getParent(), Sinus.class)) {
            Sinus sinus = (Sinus) signedAtom.getParent();
            sinus.setArgument(signedAtom);
        } else if (Helper.isTypeOf(signedAtom.getParent(), Cosine.class)) {
            Cosine cosine = (Cosine) signedAtom.getParent();
            cosine.setArgument(signedAtom);
        } else if (Helper.isTypeOf(signedAtom.getParent(), Tangent.class)) {
            Tangent tangent = (Tangent) signedAtom.getParent();
            tangent.setArgument(signedAtom);
        } else if (Helper.isTypeOf(signedAtom.getParent(), Cotangent.class)) {
            Cotangent cotangent = (Cotangent) signedAtom.getParent();
            cotangent.setArgument(signedAtom);
        }
    }

    /**
     * Вход в атом
     * @param ctx Атом в абстрактном синтаксическом дереве
     */
    public void enterAtom(ArithmeticParser.AtomContext ctx) {
        // Определяем родителя данного атома, создаём объектную модель атома с указанием этого родителя и помещаем атом в таблицу.
        ArithmeticParser.SignedAtomContext signedAtomContext = (ArithmeticParser.SignedAtomContext) ctx.parent;
        SignedAtomModel signedAtom = (SignedAtomModel) NodesHashMap.get(signedAtomContext);
        AtomModel atom = new AtomModel(signedAtom);
        NodesHashMap.put(ctx, atom);
    }

    /**
     * Выход из атома
     * @param ctx Атом в абстрактном синтаксическом дереве
     */
    public void exitAtom(ArithmeticParser.AtomContext ctx) {
        // Получаем модель атома из таблицы, определяем родителя данного атома и добавляем атом в атом со знаком - родитель
        AtomModel atom = (AtomModel) NodesHashMap.get(ctx);
        SignedAtomModel signedAtom = (SignedAtomModel) atom.getParent();
        signedAtom.setAtom(atom);
    }

    /**
     * Вход в число
     * @param ctx Число в абстрактном синтаксическом дереве
     */
    public void enterScientific(ArithmeticParser.ScientificContext ctx) {
        // Определяем родителя данного числа, создаём объектную модель числа с указанием этого родителя и помещаем число в таблицу.
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        NumberModel number = new NumberModel(atom, 0);
        NodesHashMap.put(ctx, number);
    }

    /**
     * Выход из числа
     * @param ctx Число в абстрактном синтаксическом дереве
     */
    public void exitScientific(ArithmeticParser.ScientificContext ctx) {
        // Получаем модель числа из таблицы
        NumberModel number = (NumberModel) NodesHashMap.get(ctx);

        // Считываем и устанавливаем значение объекта-числа
        double value = Double.parseDouble(ctx.getText());
        number.setValue(value);

        // Определяем родителя данного числа и добавляем число в атом-родитель
        AtomModel atom = (AtomModel) number.getParent();
        atom.setExpression(number);
    }

    /**
     * Вход в переменную
     * @param ctx Переменная в абстрактном синтаксическом дереве
     */
    public void enterVariable(ArithmeticParser.VariableContext ctx) {
        // Определяем родителя данной переменной, создаём объектную модель переменной с указанием этого родителя и помещаем переменную в таблицу.
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        VariableModel variable = new VariableModel(atom, "");
        NodesHashMap.put(ctx, variable);
    }

    /**
     * Выход из переменной
     * @param ctx Переменная в абстрактном синтаксическом дереве
     */
    public void exitVariable(ArithmeticParser.VariableContext ctx) {
        // Получаем модель переменной из таблицы
        VariableModel variable = (VariableModel) NodesHashMap.get(ctx);

        // Считываем и устанавливаем имя объекта-переменной
        String value = ctx.getText();
        variable.setName(value);

        // Определяем родителя данной переменной и добавляем переменную в атом-родитель
        AtomModel atom = (AtomModel) variable.getParent();
        atom.setExpression(variable);
    }

    public void enterRelop(ArithmeticParser.RelopContext ctx) {

    }

    public void exitRelop(ArithmeticParser.RelopContext ctx) {

    }

    /**
     * Вход в квадратный корень
     * @param ctx Квадратный корень в абстрактном синтаксическом дереве
     */
    public void enterSqrt(ArithmeticParser.SqrtContext ctx) {
        // Определяем родителя данного квадратного корня, создаём объектную модель квадратного корня с указанием этого родителя и помещаем квадратный корень в таблицу.
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        SquareRoot squareRoot = new SquareRoot(atom);
        NodesHashMap.put(ctx, squareRoot);
    }

    /**
     * Выход из квадратного корня
     * @param ctx Квадратный корень в абстрактном синтаксическом дереве
     */
    public void exitSqrt(ArithmeticParser.SqrtContext ctx) {
        // Получаем модель квадратного корня из таблицы, определяем родителя данного квадратного корня и добавляем квадратный корень в атом-родитель
        SquareRoot sqrt = (SquareRoot) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) sqrt.getParent();
        atom.setExpression(sqrt);
    }

    /**
     * Вход в производную
     * @param ctx Производная в абстрактном синтаксическом дереве
     */
    public void enterDerivative(ArithmeticParser.DerivativeContext ctx) {
        // Определяем родителя данной производной, создаём объектную модель производной с указанием этого родителя и помещаем производную в таблицу.
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        Derivative derivative = new Derivative(atom);
        NodesHashMap.put(ctx, derivative);
    }

    /**
     * Выход из производной
     * @param ctx Производная в абстрактном синтаксическом дереве
     */
    public void exitDerivative(ArithmeticParser.DerivativeContext ctx) {
        // Получаем модель производной из таблицы, определяем родителя данной производной и добавляем производную в атом-родитель
        Derivative derivative = (Derivative) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) derivative.getParent();
        atom.setExpression(derivative);
    }

    /**
     * Вход в экспоненту
     * @param ctx Экспонента в абстрактном синтаксическом дереве
     */
    public void enterExponentialFunction(ArithmeticParser.ExponentialFunctionContext ctx) {
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        ExponentialFunction exponentialFunction = new ExponentialFunction(atom);
        NodesHashMap.put(ctx, exponentialFunction);
    }

    /**
     * Выход из экспоненты
     * @param ctx Экспонента в абстрактном синтаксическом дереве
     */
    public void exitExponentialFunction(ArithmeticParser.ExponentialFunctionContext ctx) {
        ExponentialFunction exponentialFunction = (ExponentialFunction) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) exponentialFunction.getParent();
        atom.setExpression(exponentialFunction);
    }

    public void enterLogarithm(ArithmeticParser.LogarithmContext ctx) {
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        Logarithm logarithm = new Logarithm(atom);
        NodesHashMap.put(ctx, logarithm);
    }

    public void exitLogarithm(ArithmeticParser.LogarithmContext ctx) {
        Logarithm logarithm = (Logarithm) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) logarithm.getParent();
        atom.setExpression(logarithm);
    }

    public void enterNaturalLogarithm(ArithmeticParser.NaturalLogarithmContext ctx) {
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        NaturalLogarithm naturalLogarithm = new NaturalLogarithm(atom);
        NodesHashMap.put(ctx, naturalLogarithm);
    }

    public void exitNaturalLogarithm(ArithmeticParser.NaturalLogarithmContext ctx) {
        NaturalLogarithm naturalLogarithm = (NaturalLogarithm) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) naturalLogarithm.getParent();
        atom.setExpression(naturalLogarithm);
    }

    public void enterSinus(ArithmeticParser.SinusContext ctx) {
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        Sinus sinus = new Sinus(atom);
        NodesHashMap.put(ctx, sinus);
    }

    public void exitSinus(ArithmeticParser.SinusContext ctx) {
        Sinus sinus = (Sinus) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) sinus.getParent();
        atom.setExpression(sinus);
    }

    public void enterCosine(ArithmeticParser.CosineContext ctx) {
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        Cosine cosine = new Cosine(atom);
        NodesHashMap.put(ctx, cosine);
    }

    public void exitCosine(ArithmeticParser.CosineContext ctx) {
        Cosine cosine = (Cosine) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) cosine.getParent();
        atom.setExpression(cosine);
    }

    public void enterTangent(ArithmeticParser.TangentContext ctx) {
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        Tangent tangent = new Tangent(atom);
        NodesHashMap.put(ctx, tangent);
    }

    public void exitTangent(ArithmeticParser.TangentContext ctx) {
        Tangent tangent = (Tangent) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) tangent.getParent();
        atom.setExpression(tangent);
    }

    public void enterCotangent(ArithmeticParser.CotangentContext ctx) {
        ArithmeticParser.AtomContext atomContext = (ArithmeticParser.AtomContext) ctx.parent;
        AtomModel atom = (AtomModel) NodesHashMap.get(atomContext);
        Cotangent cotangent = new Cotangent(atom);
        NodesHashMap.put(ctx, cotangent);
    }

    public void exitCotangent(ArithmeticParser.CotangentContext ctx) {
        Cotangent cotangent = (Cotangent) NodesHashMap.get(ctx);
        AtomModel atom = (AtomModel) cotangent.getParent();
        atom.setExpression(cotangent);
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
