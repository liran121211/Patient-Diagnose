import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginPanel extends JPanel implements ActionListener, DocumentListener {
    private static final Pattern VALID_PASSWORD = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{8,10})");
    private static final Pattern VALID_IDENTIFIER = Pattern.compile("[0-9]{9}");
    private static final Pattern VALID_USERNAME = Pattern.compile("(?!\\d{3})[a-z0-9]{6,8}");
    private JTextField userField, idField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;
    private JLabel usernameLabel, passwordLabel, messageLabel, idLabel;
    private ActionListener mainListener;
    private boolean authorized;


    private Pattern pattern;
    private Matcher matcher;

    protected LoginPanel(ActionListener listener) {
        super(new GridBagLayout());
        authorized = false;
        mainListener = listener;
    }

    protected void init() {
        setLayout(null);
        userField = new JTextField();
        idField = new JTextField();
        passwordField = new JPasswordField();
        cancelButton = new JButton("cancel");
        loginButton = new JButton("login");
        usernameLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        idLabel = new JLabel("Identifier:");
        messageLabel = new JLabel();
        add(usernameLabel);
        add(idLabel);
        add(idField);
        add(userField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
        add(cancelButton);
        add(messageLabel);
        messageLabel.setVisible(false);

        //ActionListeners
        loginButton.addActionListener(mainListener);
        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        //DocumentListeners
        passwordField.getDocument().addDocumentListener(this);
        idField.getDocument().addDocumentListener(this);
        userField.getDocument().addDocumentListener(this);

        //Set Components Locations
        idField.setBounds(260, 100, 100, 20);
        userField.setBounds(260, 130, 100, 20);
        passwordField.setBounds(260, 160, 100, 20);
        usernameLabel.setBounds(190, 130, 100, 20);
        passwordLabel.setBounds(190, 160, 100, 20);
        messageLabel.setBounds(160, 210, 300, 20);
        idLabel.setBounds(190, 100, 300, 20);
        loginButton.setBounds(190, 190, 80, 20);
        cancelButton.setBounds(280, 190, 80, 20);


    }


    @Override
    public void actionPerformed(ActionEvent event) {
        String username = userField.getText();
        String password = passwordField.getText();
        String id = idField.getText();

        if (event.getSource() == loginButton) {
            if (!VALID_PASSWORD.matcher(password).matches())
                textValidator(passwordField);
            if (!VALID_IDENTIFIER.matcher(id).matches())
                textValidator(idField);
            if (!VALID_USERNAME.matcher(username).matches())
                textValidator(userField);
        }
        if ((username.trim().equals("tamar") && password.trim().equals("password@7") && id.trim().equals("209153501")) || (username.trim().equals("liran") && password.trim().equals("password@6") && id.trim().equals("311370092")))
            authorized = true;
        else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Login failed: Invalid username or password.");
            messageLabel.setVisible(true);
        }


        if (event.getSource() == cancelButton)
            System.exit(0);
    }

    public boolean isAuthorized() {
        return authorized;
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }

    private void textValidator(JTextField field) {
        Runnable doAssist = new Runnable() {
            @Override
            public void run() {
                field.setText("");
            }
        };
        SwingUtilities.invokeLater(doAssist);
    }
}


