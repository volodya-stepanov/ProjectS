import DataModels.Formulas.FormulaModel;
import DataModels.Objects.DocumentModel;
import DataModels.Tasks.QuadraticEquation;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CreateTaskForm {
    private JButton button_msg;
    private JPanel panelMain;
    private JTextField text_field_formula;
    private JLabel label_result;
    private JTextField text_field_description;

    private DocumentModel CurrentDocument;

    public CreateTaskForm(){
        JFrame frame = new JFrame("Create Task");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);  // Для отображения в центре экрана
        frame.setVisible(true);

        CurrentDocument = new DocumentModel("D:\\Test\\Test.docx");

        button_msg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleTask();
                //CreateTaskForm form = new CreateTaskForm();

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
        // Получаем описание и формулу, введенные пользователем
        String description = text_field_description.getText();
        String formulaString = text_field_formula.getText();

        // Создаём задание
        QuadraticEquation equation = new QuadraticEquation(CurrentDocument, description, formulaString);

        // Парсим формулу и устанавливаем её документу
        FormulaModel formula = parseFormula(formulaString);
        equation.setFormula(formula);
        equation.solve();
        equation.saveToDocument();
    }

    private FormulaModel parseFormula(String expression){
        //TODO: Если этот метод поместить в класс TaskModel, то класс ArithmeticLexer и другие классы почему-то не видны
        ArithmeticLexer lexer = new ArithmeticLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArithmeticParser parser = new ArithmeticParser(tokens);
        ParseTree tree = parser.equation();   // Здесь переключаются правила!
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        ArithmeticWalker arithmeticWalker = new ArithmeticWalker();
        parseTreeWalker.walk(arithmeticWalker, tree);
        //label_result.setText(arithmeticWalker.CurrentEquation.toString());
        return arithmeticWalker.CurrentEquation;
    }
}
