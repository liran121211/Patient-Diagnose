import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Date;


public class PatientListPanel extends JPanel implements ActionListener, ListSelectionListener, DocumentListener {
    private static final Pattern VALID_INTEGER = Pattern.compile("0|[1-9][0-9]?");
    private static final Pattern VALID_DOUBLE = Pattern.compile("[0-9]{1,7}(\\.[0-9]*)?");
    private static final Pattern VALID_FLOAT = Pattern.compile("(\\.[0-9]*)?");

    private Diagnoses diagClass;
    private JList<String> patientsList;
    private JLabel[] patientLabels;
    private JLabel headlineLabel, listLabel, diagnoseLabel, treatmentLabel,timeLabel;
    private JTextField[] patientDataFields;
    private JPanel patientPanel;
    private JTextArea diagnoseTextArea, treatmentTextArea;
    private JComboBox sex, ethnic;
    private JCheckBox[] questions_cb;
    private JButton diagnoseButton, exportButton;
    private String[] questions;
    private JScrollPane diagnoseScroll, treatmentScroll;
    private JFileChooser fileLocation;
    private  Date time;
    private SwingWorker timeWorker;
    private final String[] names = {"Ehud Lea ", "Inbal Nitza", "Eliyahu Atara",
            "Yakira Basya", "Nissa Nachum", " Ariel Hanna", "Avram Saar", "Yemima Avram",
            "Yoram Itai", "Atarah Jaffe", "Kfir Pinchas", "Hodia Yehoshua", "Yael Pinchas", "Hebel Arieh", "Gili Amichai", "Adir Zivit", "Lavi Dori", "Binyamin Hodia", "Levi Avia"};
    private final String[] labelNames = {"Age:", "Sex:", "White Blood Cells:", "Neutrophil:", "Lymphocytes:", "Red Blood Cells:", "HCT:", "Urea:", "Hemoglobin:", "Creatinine:", "Iron:", "HDL:", "ALK Phos:", "Ethnic:"};
    private Timer clockTimer;

    protected PatientListPanel() {
        super();
        diagClass = new Diagnoses();
        patientsList = new JList<>(names);
        patientLabels = new JLabel[14];
        patientDataFields = new JTextField[14];
        sex = new JComboBox(new String[]{"Male", "Female"});
        ethnic = new JComboBox(new String[]{"Easterners", "Ethiopian", "Other"});
        questions_cb = new JCheckBox[3];
        diagnoseButton = new JButton("Diagnose");
        diagnoseButton.setEnabled(false);
        exportButton = new JButton("Export");
        exportButton.setEnabled(false);
        headlineLabel = new JLabel("<html><u><span style='font-size:12px;font-family:Leelawadee'>Patient Details:</span></u></html>");
        listLabel = new JLabel("<html><u>Patients:</u></html>");
        diagnoseTextArea = new JTextArea("Diagnose notes of the patinet will be shown here...");
        diagnoseTextArea.setFont(new Font("Leelawadee", Font.PLAIN, 14));
        treatmentTextArea = new JTextArea("Treatment notes of the patinet will be shown here...");
        treatmentTextArea.setFont(new Font("Leelawadee", Font.PLAIN, 14));
        diagnoseLabel = new JLabel("<html><u>Diagnose:</u></html>");
        treatmentLabel = new JLabel("<html><u>Treatment:</u></html>");
        patientPanel = new JPanel(new GridLayout(7, 2, 5, 7));
        questions = new String[]{"Fever?", "Smoke?", "Pregnancy?"};
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Leelawadee", Font.PLAIN, 12));
        time =new Date();
        clockTimer = new Timer(1000, this);
    }

    protected void init() {
        setLayout(null);
        for (int i = 0; i < labelNames.length; i++) {
            patientLabels[i] = new JLabel(labelNames[i]);
            patientLabels[i].setFont(new Font("Leelawadee", Font.PLAIN, 12));
            patientDataFields[i] = new JTextField();
            patientDataFields[i].setFont(new Font("Leelawadee", Font.PLAIN, 12));
            patientPanel.add(patientLabels[i]);
            patientPanel.add(patientDataFields[i]);
            patientLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            if (i == 1) {
                //Add sex combo list
                patientPanel.remove(patientDataFields[i]);
                patientPanel.add(sex);
            }
            if (i == 13) {
                //Add ethnic combo list
                patientPanel.remove(patientDataFields[i]);
                patientPanel.add(ethnic);
            }
        }
        for (int i = 0; i < questions.length; i++) {
            questions_cb[i] = new JCheckBox();
            questions_cb[i].setText(questions[i]);
            add(questions_cb[i]);
        }
        patientsList.setFont(new Font("Arial", Font.PLAIN, 18));

        //Add components to panel
        patientsList.setListData(names);
        add(patientPanel);
        add(patientsList);
        add(headlineLabel);
        add(listLabel);
        add(diagnoseLabel);
        add(treatmentLabel);
        add(diagnoseButton);
        add(exportButton);
        add(timeLabel);
        patientsList.setBorder(new LineBorder(Color.BLACK, 1));
        diagnoseTextArea.setEditable(false);
        treatmentTextArea.setEditable(false);
        clockTimer.setRepeats(true);
        clockTimer.start();

        //Diagnose Area
        add(diagnoseTextArea);
        add(treatmentTextArea);
        diagnoseScroll = new JScrollPane(diagnoseTextArea);
        treatmentScroll = new JScrollPane(treatmentTextArea);
        add(diagnoseScroll);
        add(treatmentScroll);

        //Listeners
        patientsList.addListSelectionListener(this);
        diagnoseButton.addActionListener(this);
        exportButton.addActionListener(this);
        for (JTextField field : patientDataFields)
            field.getDocument().addDocumentListener(this);

        //Set Components Locations
        patientsList.setBounds(10, 22, 200, 650);
        patientPanel.setBounds(220, 60, 500, 200);
        headlineLabel.setBounds(400, 10, 300, 40);
        listLabel.setBounds(10, 0, 50, 20);
        questions_cb[0].setBounds(370, 275, 80, 20);
        questions_cb[1].setBounds(450, 275, 80, 20);
        questions_cb[2].setBounds(530, 275, 100, 20);
        diagnoseButton.setBounds(625, 690, 100, 20);
        exportButton.setBounds(510, 690, 100, 20);
        diagnoseTextArea.setBounds(290, 340, 435, 150);
        treatmentTextArea.setBounds(290, 525, 435, 150);
        diagnoseScroll.setBounds(290, 340, 435, 150);
        treatmentScroll.setBounds(290, 525, 435, 150);
        diagnoseLabel.setBounds(290, 315, 80, 20);
        treatmentLabel.setBounds(290, 500, 80, 20);
        timeLabel.setBounds(630, 5, 200, 20);

    }

    private void writePatient() {
        fileLocation = new JFileChooser();
        fileLocation.setFileFilter(new FileNameExtensionFilter("*.txt", "txt"));
        if (fileLocation.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File patientFile = fileLocation.getSelectedFile();
            try {
                FileWriter w = new FileWriter(patientFile);
                w.write("Last modified: "+timeLabel.getText()+"\n");
                w.write(patientsList.getSelectedValue() + ":\n");
                for (int i = 0; i < patientDataFields.length; i++) {
                    if (i == 1)
                        w.write(patientLabels[i].getText() + " " + sex.getSelectedItem() + "\n");
                    else if (i == 13)
                        w.write(patientLabels[i].getText() + " " + ethnic.getSelectedItem() + "\n");
                    else
                        w.write(patientLabels[i].getText() + " " + patientDataFields[i].getText() + "\n");
                }
                for (JCheckBox cb : questions_cb) {
                    if (cb.isSelected())
                        w.write(cb.getText() + ": Yes\n");
                    else
                        w.write(cb.getText() + ": No\n");
                }
                w.write("\n\nDiagnose:\n" + diagnoseTextArea.getText());
                w.write("\n\nTreatment:\n" + treatmentTextArea.getText());
                w.close();
                JOptionPane.showMessageDialog(fileLocation, patientsList.getSelectedValue() + "'s data successfully exported!");
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        }
    }

    private void randomizeData() {
        Random rand = new Random();
        set_age_value_form(String.valueOf(rand.nextInt(100)));
        set_sex_value_form(String.valueOf(rand.nextInt(2)));
        set_wbc_value_form(String.valueOf(rand.nextInt(15000) + 3000));
        set_neut_value_form(String.valueOf(rand.nextInt((int) (wbc_value_form() * 0.70))));
        set_lymp_value_form(String.valueOf(rand.nextInt((int) (wbc_value_form() * 0.70))));
        set_rbc_value_form(new DecimalFormat("#.00").format((2 + rand.nextDouble() * (10 - 2))));
        set_hct_value_form(new DecimalFormat("#.00").format(rbc_value_form() * 0.6));
        set_urea_value_form(String.valueOf(rand.nextInt(50) + 10));
        set_hb_value_form(String.valueOf(rand.nextInt(15) + 5));
        set_creatinine_value_form(new DecimalFormat("#.00").format(rand.nextDouble() * (1.5)));
        set_iron_value_form(String.valueOf(rand.nextInt(140) + 40));
        set_hdl_value_form(String.valueOf(rand.nextInt(80) + 20));
        set_alkaline_value_form(String.valueOf(rand.nextInt(110) + 20));
        set_ethnic_value_form(String.valueOf(rand.nextInt(3)));
        questions_cb[0].setSelected(rand.nextBoolean());
        questions_cb[1].setSelected(rand.nextBoolean());
        questions_cb[2].setSelected(rand.nextBoolean());


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == clockTimer)
            liveTime();

        if (e.getSource() == diagnoseButton) {
            exportButton.setEnabled(true);
            //Calculate Data
            diagClass.init("wbc", age_value_form(), wbc_value_form(), fever_checkbox_form(), "");
            diagClass.init("neut", neut_value_form(), wbc_value_form(), "", "");
            diagClass.init("lymp", lymp_value_form(), wbc_value_form(), "", "");
            diagClass.init("rbc", rbc_value_form(), 0, smoke_checkbox_form(), "");
            diagClass.init("hct", hct_value_form(), rbc_value_form(), sex_value_form(), "");
            diagClass.init("urea", urea_value_form(), 0, ethnic_value_form(), "");
            diagClass.init("hb", hb_value_form(), age_value_form(), sex_value_form(), "");
            diagClass.init("creatinine", creatinine_value_form(), age_value_form(), "", "");
            diagClass.init("iron", iron_value_form(), 0, sex_value_form(), pregnancy_checkbox_form());
            diagClass.init("hdl", hdl_value_form(), 0, sex_value_form(), ethnic_value_form());
            diagClass.init("alkaline", alkaline_value_form(), 0, ethnic_value_form(), "");

            String diagnoseText = "";
            String treatmentText = "";
            Hashtable data_dict = (Hashtable) diagClass.get_treatments_dict();
            for (String value : diagClass.wbc_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.neut_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.lymp_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }

            for (String value : diagClass.rbc_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.hct_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.urea_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.hb_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.creat_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }

            for (String value : diagClass.iron_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.hdl_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            for (String value : diagClass.alkaline_arr()) {
                diagnoseText = diagnoseText.concat(value + "\n");
                treatmentText = treatmentText.concat(data_dict.get(value).toString() + "\n");
            }
            diagnoseTextArea.setText(replaceDup(diagnoseText));
            treatmentTextArea.setText(replaceDup(treatmentText));

            diagnoseTextArea.setText(fixText(diagnoseTextArea));
            treatmentTextArea.setText(fixText(treatmentTextArea));

        }
        if (e.getSource() == exportButton)
            writePatient();
    }


    private String fixText(JTextArea ta) {
        if (ta.getLineCount() != 1) {
            int end;
            try {
                end = ta.getLineEndOffset(0);
                ta.replaceRange("", 0, end);
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }

        }
        return ta.getText();
    }

    private String replaceDup(String s) {
        return Arrays.stream(s.split("\n")).distinct().collect(Collectors.joining("\n"));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        randomizeData();
        headlineLabel.setText("<html><u><span style='font-size:12px'> " + patientsList.getSelectedValue() + "'s Details:" + "</span></u></html>");
        diagnoseButton.setEnabled(true);
    }

    protected void set_age_value_form(String value) {
        patientDataFields[0].setText(value);
    }

    protected void set_sex_value_form(String value) {
        sex.setSelectedIndex(Integer.parseInt(value));
    }

    protected void set_ethnic_value_form(String value) {
        ethnic.setSelectedIndex(Integer.parseInt(value));
    }

    protected void set_wbc_value_form(String value) {
        patientDataFields[2].setText(value);
    }

    protected void set_neut_value_form(String value) {
        patientDataFields[3].setText(value);
    }

    protected void set_lymp_value_form(String value) {
        patientDataFields[4].setText(value);
    }

    protected void set_rbc_value_form(String value) {
        patientDataFields[5].setText(value);
    }

    protected void set_hct_value_form(String value) {
        patientDataFields[6].setText(value);
    }

    protected void set_urea_value_form(String value) {
        patientDataFields[7].setText(value);
    }

    protected void set_hb_value_form(String value) {
        patientDataFields[8].setText(value);
    }

    protected void set_creatinine_value_form(String value) {
        patientDataFields[9].setText(value);
    }

    protected void set_iron_value_form(String value) {
        patientDataFields[10].setText(value);
    }

    protected void set_hdl_value_form(String value) {
        patientDataFields[11].setText(value);
    }

    protected void set_alkaline_value_form(String value) {
        patientDataFields[12].setText(value);
    }


    protected int age_value_form() {
        return Integer.parseInt(patientDataFields[0].getText());
    }

    protected String sex_value_form() {
        return Objects.requireNonNull(sex.getSelectedItem()).toString();
    }

    protected String ethnic_value_form() {
        return Objects.requireNonNull(ethnic.getSelectedItem()).toString();
    }

    protected double wbc_value_form() {
        return Double.parseDouble(patientDataFields[2].getText());
    }

    protected double neut_value_form() {
        return Double.parseDouble(patientDataFields[3].getText());
    }

    protected double lymp_value_form() {
        return Double.parseDouble(patientDataFields[4].getText());
    }

    protected double rbc_value_form() {
        return Double.parseDouble(patientDataFields[5].getText());
    }

    protected double hct_value_form() {
        return Double.parseDouble(patientDataFields[6].getText());
    }

    protected double urea_value_form() {
        return Double.parseDouble(patientDataFields[7].getText());
    }

    protected double hb_value_form() {
        return Double.parseDouble(patientDataFields[8].getText());
    }

    protected double creatinine_value_form() {
        return Double.parseDouble(patientDataFields[9].getText());
    }

    protected double iron_value_form() {
        return Double.parseDouble(patientDataFields[10].getText());
    }

    protected double hdl_value_form() {
        return Double.parseDouble(patientDataFields[11].getText());
    }

    protected double alkaline_value_form() {
        return Double.parseDouble(patientDataFields[12].getText());
    }

    protected String fever_checkbox_form() {
        if (questions_cb[0].isSelected())
            return "Fever";
        return "";
    }

    protected String smoke_checkbox_form() {
        if (questions_cb[1].isSelected())
            return "Smoke";
        return "";
    }

    protected String pregnancy_checkbox_form() {
        if (questions_cb[2].isSelected())
            return "Pregnancy";
        return "";
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        diagnoseButton.setEnabled(true);
        for (int i = 0; i < patientDataFields.length; i++) { //Iterate through all textfields
            //Disable diagnose button if field is empty
            if (i != 1 && i != 13)
                if (patientDataFields[i].getText().compareTo("") == 0) {
                    diagnoseButton.setEnabled(false);
                    exportButton.setEnabled(false);
                }
            //Chekc if the age is correct
            if (e.getDocument() == patientDataFields[0].getDocument()) {
                if (!VALID_INTEGER.matcher(patientDataFields[0].getText()).matches())
                    textValidator(patientDataFields[0]);
            }
            //check if creatinine is correct
            else if (e.getDocument() == patientDataFields[9].getDocument()) {

                //if creatinine is above 1.0 decimal value
                if (Double.parseDouble(patientDataFields[9].getText()) >= 1.0) {
                    if (!VALID_DOUBLE.matcher(patientDataFields[9].getText()).matches())
                        textValidator(patientDataFields[9]);
                }
                //if creatinine is below 1.0 decimal value
                else if (Double.parseDouble(patientDataFields[9].getText()) < 1.0)
                    if (!VALID_FLOAT.matcher(patientDataFields[9].getText()).matches())
                        textValidator(patientDataFields[9]);

                //Check everything else
            } else if (e.getDocument() == patientDataFields[i].getDocument()) {
                if (!VALID_DOUBLE.matcher(patientDataFields[i].getText()).matches())
                    textValidator(patientDataFields[i]);
            }
        }

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        diagnoseButton.setEnabled(true);
        for (int i = 0; i < patientDataFields.length; i++) { //Iterate through all textfields
            //Disable diagnose button if field is empty
            if (i != 1 && i != 13)
                if (patientDataFields[i].getText().compareTo("") == 0) {
                    diagnoseButton.setEnabled(false);
                    exportButton.setEnabled(false);
                }
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    private void liveTime(){
        timeWorker = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                time.setTime(System.currentTimeMillis());
                timeLabel.setText(time.toString());
                return null;
            }
        };
        timeWorker.execute();
    }
    private void textValidator(JTextField field) {
        Runnable doAssist = () -> field.setText("");
        SwingUtilities.invokeLater(doAssist);
    }
}
