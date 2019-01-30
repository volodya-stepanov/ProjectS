import DataModels.FormulaModel;
import DataModels.TaskModel;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App {
    private JButton button_msg;
    private JPanel panelMain;
    private JTextField text_field_formula;
    private JLabel label_result;
    private JTextField text_field_description;

    public static void main(String[] args) {
        // Это мой первый код, написанный в этом приложении. Код написан 9 января 2019 года в 19:50 и взят из урока, расположенного по ссылке: https://youtu.be/5vSyylPPEko
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public App() {
        button_msg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTask();
            }
        });
        text_field_formula.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                if (code == 10){
                    handleTask();
                }
            }
        });
    }

    /**
     * Выполняет считывание исходных данных, создание задания и его обработку
     */
    private void handleTask(){
        String description = text_field_description.getText();
        String formulaString = text_field_formula.getText();
        TaskModel task = new TaskModel(description, formulaString);
        FormulaModel formula = parseFormula(formulaString);
        task.setFormula(formula);
        task.save();
    }

    private FormulaModel parseFormula(String expression){
        //TODO: Если этот метод поместить в класс TaskModel, то класс ArithmeticLexer и другие классы почему-то не видны
        ArithmeticLexer lexer = new ArithmeticLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArithmeticParser parser = new ArithmeticParser(tokens);
        ParseTree tree = parser.factor();   // Здесь переключаются правила!
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        ArithmeticWalker arithmeticWalker = new ArithmeticWalker();
        parseTreeWalker.walk(arithmeticWalker, tree);
        //label_result.setText(arithmeticWalker.CurrentEquation.toString());
        return arithmeticWalker.CurrentFactor;
    }

    private void handleDocument(){




    }
}
