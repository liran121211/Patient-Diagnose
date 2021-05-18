import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame implements ActionListener {
    private final ImageIcon icon = new ImageIcon("src/icon.png");
    private JFrame loginFrame, patientListFrame;
    private PatientListPanel patientListPanel;
    private JPanel loginPanel;

    //Constructor
    protected MainFrame() {
        //Initializers
        loginFrame = new JFrame("Login Panel");
        loginPanel = new LoginPanel(this);
        patientListPanel = new PatientListPanel();
        patientListFrame = new JFrame("Patient Panel");
    }

    protected void init() {
        //Login Frame Settings
        ((LoginPanel) loginPanel).init();
        loginFrame.setSize(300, 200);
        loginFrame.setLocationRelativeTo(null); //set location of frame on center
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setIconImage(icon.getImage());
        loginFrame.setResizable(false);
        loginFrame.setVisible(true);
        loginFrame.add(loginPanel);
        loginFrame.revalidate();

        //Patient Frame Settings
        patientListPanel.init();
        patientListFrame.setSize(800, 800);
        patientListFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        patientListFrame.setLocationRelativeTo(null); //set location of frame on center
        patientListFrame.setIconImage(icon.getImage());
        patientListFrame.setResizable(false);
        patientListFrame.setVisible(false);
        patientListFrame.add(patientListPanel);
        patientListFrame.revalidate();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((LoginPanel) loginPanel).isAuthorized()) {
            loginFrame.setVisible(false);
            patientListFrame.setVisible(true);
        }
    }

    public static void main(String[] args) {
        MainFrame test = new MainFrame();
        test.init();
    }
}
