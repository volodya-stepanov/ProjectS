import DataModels.Objects.DocumentModel;
import DataModels.Formulas.FormulaModel;
import DataModels.Tasks.QuadraticEquation;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import static com.sun.org.apache.xerces.internal.utils.SecuritySupport.getResourceAsStream;

public class App {
    private JPanel panelMain;
    private JTree tree1;
    private JEditorPane editorPane1;

    public static void main(String[] args) {
        // Это мой первый код, написанный в этом приложении. Код написан 9 января 2019 года в 19:50 и взят из урока, расположенного по ссылке: https://youtu.be/5vSyylPPEko
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Для отображения на весь экран
        frame.setVisible(true);

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Файл");
        JMenu editMenu = new JMenu("Правка");
        JMenu searchMenu = new JMenu("Поиск");
        JMenu helpMenu = new JMenu("Справка");

        JMenu createMenu = new JMenu("Создать");
        JMenuItem createDocMenuItem = new JMenuItem("Документ");

        // TODO: Не получилось задать иконки пунктам меню
//        String path = "ic_file.png";
//        URL imgURL = App.class.getResource(path);
//        ImageIcon icon = new ImageIcon(imgURL);
//
//        createDocMenuItem.setIcon(icon);

        JMenuItem createTaskMenuItem = new JMenuItem("Задание");

        createTaskMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                CreateTaskForm form = new CreateTaskForm();
            }
        });

        createMenu.add(createDocMenuItem);
        createMenu.add(createTaskMenuItem);

        JMenuItem openMenuItem = new JMenuItem("Открыть");
        // Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() возвращает Ctrl
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));


        JMenuItem saveMenuItem = new JMenuItem("Сохранить");
        JMenuItem saveAsMenuItem = new JMenuItem("Сохранить как...");
        JMenuItem exitMenuItem = new JMenuItem("Выход");

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        JMenuItem undoMenuItem = new JMenuItem("Отменить");
        JMenuItem redoMenuItem = new JMenuItem("Повторить");
        JMenuItem cutMenuItem = new JMenuItem("Вырезать");
        JMenuItem copyMenuItem = new JMenuItem("Копировать");
        JMenuItem pasteMenuItem = new JMenuItem("Вставить");

        fileMenu.add(createMenu);
        fileMenu.add(openMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu.add(undoMenuItem);
        editMenu.add(redoMenuItem);
        editMenu.addSeparator();
        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(searchMenu);
        menuBar.add(helpMenu);

        frame.setJMenuBar(menuBar);
    }

    public App() {
        // CreateTaskForm form = new CreateTaskForm();
    }


}
