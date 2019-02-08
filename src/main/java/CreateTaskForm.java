import javax.swing.*;

public class CreateTaskForm {
    public CreateTaskForm(){
        JFrame frame = new JFrame("Create Task");
        //frame.setContentPane(new App().panelMain);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
