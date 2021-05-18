
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame implements ActionListener{
    private JFrame loginFrame,patientListFrame;
    private JPanel loginPanel, patientListPanel;

    //Constructor
    protected MainFrame() {
    }

    protected void init() {
        //Initializers
        loginFrame = new JFrame("login blatt");
        loginPanel = new LoginPanel(this);
        patientListFrame = new JFrame("patient blatt");
        patientListPanel = new PatientListPanel(this);

        //Login Frame Settings
        ((LoginPanel) loginPanel).init();
        loginFrame.setSize(600, 400);
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);
        loginFrame.add(loginPanel);
        loginFrame.revalidate();

        //Patient Frame Settings
        ((PatientListPanel) patientListPanel).init();
        patientListFrame.setSize(800, 800);
        patientListFrame.setResizable(false);
        patientListFrame.setVisible(false);
        patientListFrame.add(patientListPanel);
        patientListFrame.revalidate();

    }

    public static void main(String[] args) {
        MainFrame test = new MainFrame();
        test.init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((LoginPanel)loginPanel).isAuthorized()){
            loginFrame.setVisible(false);
            patientListFrame.setVisible(true);
        }
    }
}
