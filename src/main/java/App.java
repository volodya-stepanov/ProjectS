import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

            }
        });
    }
}
