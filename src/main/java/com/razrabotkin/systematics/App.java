package com.razrabotkin.systematics;

import com.razrabotkin.systematics.DataModels.Objects.DocumentModel;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.FormulaBlock;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.SolutionBlock;
import com.razrabotkin.systematics.DataModels.SolutionBlocks.TextBlock;
import com.razrabotkin.systematics.DataModels.Tasks.TaskModel;
import com.razrabotkin.systematics.Helpers.ClassHelper;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.sun.org.apache.xerces.internal.utils.SecuritySupport.getResourceAsStream;

public class App {
    private JPanel panelMain;
    private JTree tree;
    private JTextPane textPane1;
    private JButton createTaskButton;
    private JButton saveButton;
    private JMenuBar menuBar;

    public DocumentModel CurrentDocument;

    public static void main(String[] args) {
        // Это мой первый код, написанный в этом приложении. Код написан 9 января 2019 года в 19:50 и взят из урока, расположенного по ссылке: https://youtu.be/5vSyylPPEko
        JFrame frame = new JFrame("Систематика");

        App app = new App();

        frame.setContentPane(app.panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);  // Для отображения на весь экран
        frame.setVisible(true);

        frame.setJMenuBar(app.menuBar);
    }

    public App() {

        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // CreateTaskForm form = new CreateTaskForm();
        CurrentDocument = new DocumentModel("D:\\Test\\Test.docx");

        createMenu();

        createTree();

        createTextPane();
        createTaskButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createTask();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
    }

    private void createMenu() {
        menuBar = new JMenuBar();

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

                createTask();
            }
        });

        createMenu.add(createDocMenuItem);
        createMenu.add(createTaskMenuItem);

        JMenuItem openMenuItem = new JMenuItem("Открыть");
        // Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() возвращает Ctrl
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit ().getMenuShortcutKeyMask()));


        JMenuItem saveMenuItem = new JMenuItem("Сохранить");
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });

        JMenuItem saveAsMenuItem = new JMenuItem("Сохранить как...");
        JMenuItem exitMenuItem = new JMenuItem("Выход");

        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(1);
            }
        });

        JMenuItem updateMenuItem = new JMenuItem("Обновить");
        updateMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshTree();
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

        editMenu.add(updateMenuItem);
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


    }

    private void createTask() {
        //CreateTaskForm form = new CreateTaskForm(App.this);

        CreateTaskWizard wizard = new CreateTaskWizard();
        wizard.setSize(new Dimension(600, 300));
        wizard.setModalityType(Dialog.ModalityType.APPLICATION_MODAL); // prevent user from doing something else
        wizard.setLocationRelativeTo(null);
        wizard.setVisible(true);
    }

    private void createTree(){
        int hashcode = App.this.hashCode();
        System.out.println("Зашли в метод создания дерева. Хэш-код: " + String.valueOf(hashcode));

        // Создаём корневой узел
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Работа №1");

        // Создаём дочерние узлы
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("Квадратное уравнение");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Производная");
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("Неопределённый интеграл");

        // Добавляем дочерние узлы к корневому
//

        // Создаём дерево с нужным корнем
        ((DefaultTreeModel)tree.getModel()).setRoot(root);

        tree.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                TaskModel selectedTask = (TaskModel) selectedNode.getUserObject();

                fillTextPane(selectedTask);
            }
        });

        //refreshTree();
    }



    public void refreshTree(){
        System.out.println("Зашли в метод обновления дерева");

        // Создаём корневой узел
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Работа №1");

        for(TaskModel task : CurrentDocument.Tasks){
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(task);
            root.add(node);
        }

        // Создаём дерево с нужным корнем
        ((DefaultTreeModel)tree.getModel()).setRoot(root);
        ((DefaultTreeModel)tree.getModel()).reload();

        tree.setVisible(false);
        tree.revalidate();
        tree.setVisible(true);


        System.out.println("Обновили дерево");
    }

    private void createTextPane(){
        SimpleAttributeSet headerSet = new SimpleAttributeSet();
        StyleConstants.setBold(headerSet, true);
        textPane1.setCharacterAttributes(headerSet, true);
        //textPane1.setText("Решите квадратное уравнение: \n\n");

        SimpleAttributeSet bodySet = new SimpleAttributeSet();
        Document doc = textPane1.getStyledDocument();
//        try {
//            //doc.insertString(doc.getLength(), "Сдесь будет уравнение ", bodySet);
//        } catch (BadLocationException e) {
//            e.printStackTrace();
//        }
    }

    private void fillTextPane(TaskModel selectedTask) {
        SimpleAttributeSet headerSet = new SimpleAttributeSet();
        SimpleAttributeSet bodySet = new SimpleAttributeSet();

        StyleConstants.setBold(headerSet, true);
        textPane1.setCharacterAttributes(headerSet, true);
        textPane1.setText(selectedTask.getDescription() + "\n\n");

        Document doc = textPane1.getStyledDocument();
        try {
            doc.insertString(doc.getLength(), selectedTask.getFormula().toString() + "\n\n", bodySet);
            doc.insertString(doc.getLength(), "Решение\n\n", headerSet);

            ClassHelper helper = new ClassHelper();

            for (SolutionBlock solutionBlock : selectedTask.SolutionBlocks){
                if (helper.isTypeOf(solutionBlock, TextBlock.class)){
                    TextBlock textBlock = (TextBlock) solutionBlock;
                    doc.insertString(doc.getLength(), textBlock.getValue() + "\n\n", bodySet);
                } else if (helper.isTypeOf(solutionBlock, FormulaBlock.class)){
                    FormulaBlock formulaBlock = (FormulaBlock) solutionBlock;
                    doc.insertString(doc.getLength(), formulaBlock.getFormula() + "\n\n", bodySet);
                }
            }

            doc.insertString(doc.getLength(), "Ответ: ", headerSet);
            doc.insertString(doc.getLength(), selectedTask.getAnswerString(), bodySet);

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void save(){
        for (TaskModel task : CurrentDocument.Tasks){
            task.saveToDocument();
        }
    }
}
