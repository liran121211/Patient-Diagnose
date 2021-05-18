import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;

public class LoginPanel extends JPanel implements ActionListener, DocumentListener, KeyListener {
    private static final Pattern VALID_PASSWORD = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[@#$%!]).{8,10})");
    private static final Pattern VALID_IDENTIFIER = Pattern.compile("[0-9]{9}");
    private static final Pattern VALID_USERNAME = Pattern.compile("(?!\\d{3})[a-z0-9]{6,8}");
    private JTextField userField, idField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;
    private JLabel messageLabel;
    private ActionListener mainListener;
    private boolean authorized;

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
        cancelButton = new JButton("Cancel");
        loginButton = new JButton("Login");
        JLabel idLabel = new JLabel("Identifier:");
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        idLabel.setFont(new Font("Leelawadee", Font.PLAIN, 14));
        usernameLabel.setFont(new Font("Leelawadee", Font.PLAIN, 14));
        passwordLabel.setFont(new Font("Leelawadee", Font.PLAIN, 14));
        userField.setFont(new Font("Leelawadee", Font.PLAIN, 13));
        idField.setFont(new Font("Leelawadee", Font.PLAIN, 13));
        passwordField.setFont(new Font("Leelawadee", Font.PLAIN, 13));
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

        //KeyListeners
        userField.addKeyListener(this);
        idField.addKeyListener(this);
        passwordField.addKeyListener(this);

        //Set Components Locations
        idField.setBounds(125, 20, 100, 20);
        userField.setBounds(125, 45, 100, 20);
        passwordField.setBounds(125, 70, 100, 20);
        idLabel.setBounds(55, 20, 300, 20);
        usernameLabel.setBounds(55, 45, 100, 20);
        passwordLabel.setBounds(55, 70, 100, 20);
        messageLabel.setBounds(25, 130, 300, 20);
        loginButton.setBounds(55, 100, 80, 20);
        cancelButton.setBounds(145, 100, 80, 20);


    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String username = userField.getText();
        String password = passwordField.getText();
        String id = idField.getText();

        if (event.getSource() == loginButton) {
            if (!VALID_IDENTIFIER.matcher(id).matches())
                textValidator(idField);
            if (!VALID_USERNAME.matcher(username).matches())
                textValidator(userField);
            if (!VALID_PASSWORD.matcher(password).matches())
                textValidator(passwordField);
        }
        if ((username.trim().equals("tamaram") && password.trim().equals("password@7") && id.trim().equals("209153501")) || (username.trim().equals("liransm") && password.trim().equals("password@6") && id.trim().equals("311370092")))
            authorized = true;
        else {
            messageLabel.setForeground(Color.RED);
            messageLabel.setText("Login failed: Invalid username or password.");
            messageLabel.setVisible(true);
        }


        if (event.getSource() == cancelButton)
            System.exit(0);
    }

    protected boolean isAuthorized() {
        return authorized;
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        //None
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        //None
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        //None
    }

    private void textValidator(JTextField field) {
        Runnable doAssist = () -> field.setText("");
        SwingUtilities.invokeLater(doAssist);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER)
            loginButton.doClick();
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            cancelButton.doClick();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}


