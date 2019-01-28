import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.exceptions.InvalidFormatException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.Document;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App {
    private JButton button_msg;
    private JPanel panelMain;
    private JTextField text_field_equation;
    private JLabel label_result;

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
                handleDocument();
            }
        });
        text_field_equation.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                if (code == 10){
                    String expression = text_field_equation.getText();
                    parseExpression(expression);
                }
            }
        });
    }

    private void parseExpression(String expression){
        ArithmeticLexer lexer = new ArithmeticLexer(CharStreams.fromString(expression));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ArithmeticParser parser = new ArithmeticParser(tokens);
        ParseTree tree = parser.equation();   // Здесь переключаются правила!
        ParseTreeWalker parseTreeWalker = new ParseTreeWalker();
        ArithmeticWalker arithmeticWalker = new ArithmeticWalker();
        parseTreeWalker.walk(arithmeticWalker, tree);
        label_result.setText(arithmeticWalker.CurrentEquation.toString());
    }

    private void handleDocument(){



        // Создать пакет
        WordprocessingMLPackage wordMLPackage = null;

        try {
            wordMLPackage = WordprocessingMLPackage.createPackage();

//            Document document = createIt();
//            document.getBody();
//            MainDocumentPart mainDocumentPart = wordMLPackage.getMainDocumentPart();
//            //mainDocumentPart.addParagraphOfText("Волдя");
//            mainDocumentPart.setJaxbElement(document);

        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }

        // Сохранить его
        try {
            wordMLPackage.save(new java.io.File("D:\\Test\\Test.docx") );
        } catch (Docx4JException e) {
            e.printStackTrace();
        }
    }
}
