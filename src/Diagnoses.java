import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Diagnoses {
    private ArrayList<String> wbc, neut, lymp, rbc, hct, urea, hb, creat, iron, hdl, alk;

    private Dictionary treatments_dict = new Hashtable();
    private final String[][] treatments_data = {
            {"Anemia", "Two 10 mg pills of B12 a day for a month"},
            {"Diet", "Schedule an appointment with a nutritionist"},
            {"Bleeding", "To be evacuated urgently to the hospital"},
            {"Hyperlipidemia", "Schedule an appointment with a nutritionist, a 5 mg pill of Simobil daily for a week"},
            {"Disorder Of Blood Formation", "10 mg pill of B12 a day for a month mg pill of folic acid a day for a month"},
            {"Hematological Disorder", "An injection of a hormone to encourage red blood cell production"},
            {"Iron Poisoning", "To be evacuated to the hospital"},
            {"Dehydration", "Complete rest lying down, returning fluids by drinking"},
            {"Infection", "Dedicated antibiotics"},
            {"Vitamin Deficiency", "Referral for a blood test to identify the missing vitamins"},
            {"Viral Disease", "Rest at home"},
            {"Diseases Of The Biliary Tract", "Referral to surgical treatment"},
            {"Heart Diseases", "Schedule an appointment with a nutritionist"},
            {"Blood Disease", "A combination of cyclophosphamide and corticosteroids"},
            {"Liver Disease", "Referral to a specific diagnosis for the purpose of determining treatment"},
            {"Kidney Disease", "Balance blood sugar levels"},
            {"Iron Deficiency", "Two 10 mg pills of B12 a day for a month"},
            {"Muscle Diseases", "Two 5 mg pills of Altman c3 turmeric a day for a month"},
            {"Smokers", "Stop Smoking"},
            {"Lung Disease", "Refer to X-ray of the lungs"}, // Needs to be checked!! missing diagnose
            {"Overactive Thyroid Gland", "Propylthiouracil to reduce thyroid activity"},
            {"Adult Diabetes", "Insulin adjustment for the patient"},
            {"Cancer", "Entrectinib"},
            {"Increased Consumption Of Meat", "Schedule an appointment with a nutritionist"},
            {"Use Of Various Medications", "Referral to a family doctor for a match between medications"},
            {"Malnutrition", "Schedule an appointment with a nutritionist"},
            {"No Diagnoses", "No treatments are required."}};

    //Constructor
    protected Diagnoses() {
        //Fill the dictionary with treatments data
        for (int i = 0; i < treatments_data.length; i++)
            treatments_dict.put(treatments_data[i][0], treatments_data[i][1]);
    }

    private void calc_wbc(double age, double value, String fever) {
        this.wbc = new ArrayList<>();//Reset array
        if (age > 0 && age <= 3) {
            if (value < 6000) {
                wbc.add("Viral Disease");
                wbc.add("Cancer");
            }
            if (value >= 6000 && value <= 17500)
                wbc.add("No Diagnoses");

            if (value > 17500) {
                if (fever.compareTo("Fever") == 0)
                    wbc.add("Infection"); //If patient has fever
                wbc.add("Blood Disease");
                wbc.add("Cancer");
            }
        }

        if (age > 4 && age <= 17) {
            if (value < 5500) {
                wbc.add("Viral Disease");
                wbc.add("Cancer");
            }
            if (value >= 5500 && value <= 15500)
                wbc.add("No Diagnoses");

            if (value > 15500) {
                wbc.add("Infection"); //If patient has fever
                wbc.add("Blood Disease");
                wbc.add("Cancer");
            }
        }

        if (age >= 18) {
            if (value < 4500) {
                wbc.add("Viral Disease");
                wbc.add("Cancer");
            }
            if (value >= 4500 && value <= 11000)
                wbc.add("No Diagnoses");

            if (value > 11000) {
                wbc.add("Infection"); //If patient has fever
                wbc.add("Blood Disease");
                wbc.add("Cancer");
            }
        }
    }

    private void calc_neut(double value, double wbc) {
        this.neut = new ArrayList<>();//Reset array
        if (value / wbc < 0.28) {
            neut.add("Infection");
            neut.add("Disorder Of Blood Formation");
            neut.add("Cancer");
        }
        if ((value / wbc >= 0.28) && (value / wbc <= 0.54))
            neut.add("No Diagnoses");

        if ((value / wbc > 0.54)) {
            neut.add("Infection");
        }
    }

    private void calc_lymp(double value, double wbc) {
        this.lymp = new ArrayList<>();//Reset array
        if (value / wbc < 0.36)
            lymp.add("Disorder Of Blood Formation");

        if (value / wbc >= 0.36 && value / wbc <= 0.52)
            lymp.add("No Diagnoses");

        if (value / wbc > 0.52) {
            lymp.add("Infection");
            lymp.add("Cancer");
        }
    }

    private void calc_rbc(double value, String smoke) {
        this.rbc = new ArrayList<>();//Reset array

        if (value < 4.5) {
            this.rbc.add("Anemia");
            this.rbc.add("Bleeding");
        }

        if (value >= 4.5 && value <= 6)
            this.rbc.add("No Diagnoses");

        if (value > 6)
            if (smoke.compareTo("Smoke") == 0)
                this.rbc.add("Disorder Of Blood Formation");
            else
                this.rbc.add("Lung Disease");
    }

    private void calc_hct(double value, double rbc, String sex) {
        this.hct = new ArrayList<>();//Reset array
        if (sex.compareTo("Male") == 0) {
            if ((value / rbc) < 0.37) {
                this.hct.add("Anemia");
                this.hct.add("Bleeding");
            }
            if ((value / rbc) >= 0.37 && (value / rbc) <= 0.54)
                this.hct.add("No Diagnoses");

            if ((value / rbc) > 0.54)
                this.hct.add("Smokers");
        }
        if (sex.compareTo("Female") == 0) {
            if ((value / rbc) < 0.33) {
                this.hct.add("Anemia");
                this.hct.add("Bleeding");
            }
            if ((value / rbc) >= 0.33 && (value / rbc) <= 0.47)
                this.hct.add("No Diagnoses");

            if ((value / rbc) > 0.47)
                this.hct.add("Smokers");
        }
    }

    private void calc_urea(double value, String ethnic) {
        this.urea = new ArrayList<>();//Reset array
        if (ethnic.compareTo("Easterners") == 0) {
            if (value < 18.7) {
                this.urea.add("Malnutrition");
                this.urea.add("Diet");
                this.urea.add("Liver Disease");
            }

            if (value >= 18.7 && value <= 47.3)
                this.urea.add("No Diagnoses");

            if (value > 47.3) {
                this.urea.add("Kidney Disease");
                this.urea.add("Dehydration");
                this.urea.add("Diet");
            }
        } else {
            if (value < 17) {
                this.urea.add("Malnutrition");
                this.urea.add("Diet");
                this.urea.add("Liver Disease");
            }

            if (value >= 17 && value <= 43)
                this.urea.add("No Diagnoses");

            if (value > 43) {
                this.urea.add("Kidney Disease");
                this.urea.add("Dehydration");
                this.urea.add("Diet");
            }
        }

    }

    private void calc_hb(double value, double age, String sex) {
        this.hb = new ArrayList<>();//Reset array
        if (sex.compareTo("Male") == 0 && age >= 18) {
            if (value < 12.0) {
                this.hb.add("Anemia");
                this.hb.add("Iron Deficiency");
                this.hb.add("Bleeding");
                this.hb.add("Hematological Disorder");
            }
            if (value >= 12.0 && value <= 18.0)
                this.hb.add("No Diagnoses");
        }
        if (sex.compareTo("Female") == 0 && age >= 18) {
            if (value < 12.0) {
                this.hb.add("Anemia");
                this.hb.add("Iron Deficiency");
                this.hb.add("Bleeding");
                this.hb.add("Hematological Disorder");
            }
            if (value >= 12.0 && value <= 16.0)
                this.hb.add("No Diagnoses");
        }
        if (age < 18) {
            if (value < 11.5) {
                this.hb.add("Anemia");
                this.hb.add("Iron Deficiency");
                this.hb.add("Bleeding");
            }
            if (value >= 11.5 && value <= 15.5)
                this.hb.add("No Diagnoses");
        }
    }

    private void calc_creatinine(double value, double age) {
        this.creat = new ArrayList<>();//Reset array
        if (age >= 0 && age <= 2) {
            if (value < 0.2) {
                this.creat.add("Muscle Diseases");
                this.creat.add("Malnutrition");
            }
            if (value >= 0.2 && value <= 0.5)
                this.creat.add("No Diagnoses");

            if (value > 0.5) {
                this.creat.add("Kidney Disease");
                this.creat.add("Muscle Diseases");
                this.creat.add("Increased Consumption Of Meat");
            }
        }
        if (age >= 3 && age <= 17) {
            if (value < 0.5) {
                this.creat.add("Muscle Diseases");
                this.creat.add("Malnutrition");
            }
            if (value >= 0.5 && value <= 1.0)
                this.creat.add("No Diagnoses");

            if (value > 1.0) {
                this.creat.add("Kidney Disease");
                this.creat.add("Muscle Diseases");
                this.creat.add("Increased Consumption Of Meat");
            }
        }
        if (age >= 18 && age <= 59) {
            if (value < 0.6) {
                this.creat.add("Muscle Diseases");
                this.creat.add("Malnutrition");
            }
            if (value >= 0.6 && value <= 1.0)
                this.creat.add("No Diagnoses");

            if (value > 1.0) {
                this.creat.add("Kidney Disease");
                this.creat.add("Muscle Diseases");
                this.creat.add("Increased Consumption Of Meat");
            }
        }
        if (age >= 60) {
            if (value < 0.6) {
                this.creat.add("Muscle Diseases");
                this.creat.add("Malnutrition");
            }
            if (value >= 0.6 && value <= 1.2)
                this.creat.add("No Diagnoses");

            if (value > 1.2) {
                this.creat.add("Kidney Disease");
                this.creat.add("Muscle Diseases");
                this.creat.add("Increased Consumption Of Meat");
            }
        }
    }

    private void calc_iron(double value, String sex, String pregnancy) {
        this.iron = new ArrayList<>();//Reset array
        if (sex.compareTo("Male") == 0) {
            if (value < 60) {
                this.iron.add("Malnutrition");
                this.iron.add("Iron Deficiency");
                this.iron.add("Bleeding");
            }

            if (value >= 60 && value <= 160)
                this.iron.add("No Diagnoses");

            if (value > 160) {
                this.iron.add("Iron Poisoning");
            }
        }
        if (sex.compareTo("Female") == 0) {
            if (value < 48) {
                if (pregnancy.compareTo("Pregnancy") == 0)
                    this.iron.add("Iron Deficiency");
                this.iron.add("Malnutrition");
                this.iron.add("Bleeding");
            }

            if (value >= 48 && value <= 128)
                this.iron.add("No Diagnoses");

            if (value > 128) {
                this.iron.add("Iron Poisoning");
            }
        }
    }

    private void calc_hdl(double value, String sex, String ethnic) {
        this.hdl = new ArrayList<>();//Reset array
        if (ethnic.compareTo("Ethiopian") == 0) {
            if (sex.compareTo("Male") == 0) {
                if (value < 29 * 1.2) {
                    this.hdl.add("Heart Diseases");
                    this.hdl.add("Hyperlipidemia");
                    this.hdl.add("Adult Diabetes");
                }

                if (value >= 29 * 1.2 && value <= 62 * 1.2)
                    this.hdl.add("No Diagnoses");

                if (value > 62 * 1.2) {
                    this.hdl.add("No Diagnoses");
                }
            }
            if (sex.compareTo("Female") == 0) {
                if (value < 34 * 1.2) {
                    this.hdl.add("Heart Diseases");
                    this.hdl.add("Hyperlipidemia");
                    this.hdl.add("Adult Diabetes");
                }

                if (value >= 34 * 1.2 && value <= 82 * 1.2)
                    this.hdl.add("No Diagnoses");

                if (value > 82 * 1.2) {
                    this.hdl.add("No Diagnoses");
                }
            } else if (ethnic.compareTo("Other") == 0) {
                if (sex.compareTo("Male") == 0) {
                    if (value < 29) {
                        this.hdl.add("Heart Diseases");
                        this.hdl.add("Hyperlipidemia");
                        this.hdl.add("Adult Diabetes");
                    }

                    if (value >= 29 && value <= 62)
                        this.hdl.add("No Diagnoses");

                    if (value > 62) {
                        this.hdl.add("No Diagnoses");
                    }
                }
                if (sex.compareTo("Female") == 0) {
                    if (value < 34) {
                        this.hdl.add("Heart Diseases");
                        this.hdl.add("Hyperlipidemia");
                        this.hdl.add("Adult Diabetes");
                    }

                    if (value >= 34 && value <= 82)
                        this.hdl.add("No Diagnoses");

                    if (value > 82) {
                        this.hdl.add("No Diagnoses");
                    }
                }
            }
        }

    }

    private ArrayList<String> calc_alkaline(double value, String ethnic) {
        this.alk = new ArrayList<>();//Reset array
        if (ethnic.compareTo("Easterners") == 0) {
            if (value < 60) {
                this.alk.add("Malnutrition");
                this.alk.add("Vitamin Deficiency");
            }

            if (value >= 60 && value <= 120)
                this.alk.add("No Diagnoses");

            if (value > 120) {
                this.alk.add("Liver Disease");
                this.alk.add("Diseases Of The Biliary Tract");
                this.alk.add("Overactive Thyroid Gland");
                this.alk.add("Use Of Various Medications");
            }
        }
        if (ethnic.compareTo("Easterners") != 0) {
            if (value < 30) {
                this.alk.add("Malnutrition");
                this.alk.add("Vitamin Deficiency");
            }

            if (value >= 30 && value <= 90)
                this.alk.add("No Diagnoses");

            if (value > 90) {
                this.alk.add("Liver Disease");
                this.alk.add("Diseases Of The Biliary Tract");
                this.alk.add("Overactive Thyroid Gland");
                this.alk.add("Use Of Various Medications");
            }
        }
        return this.alk;
    }

    protected void init(String calculator, double value_1, double value_2, String value_3, String value_4) {
        if (calculator.compareTo("wbc") == 0)
            calc_wbc(value_1, value_2, value_3);
        if (calculator.compareTo("neut") == 0)
            calc_neut(value_1, value_2);
        if (calculator.compareTo("lymp") == 0)
            calc_lymp(value_1, value_2);
        if (calculator.compareTo("rbc") == 0)
            calc_rbc(value_1, value_3);
        if (calculator.compareTo("hct") == 0)
            calc_hct(value_1, value_2, value_3);
        if (calculator.compareTo("urea") == 0)
            calc_urea(value_1, value_3);
        if (calculator.compareTo("hb") == 0)
            calc_hb(value_1, value_2, value_3);
        if (calculator.compareTo("creatinine") == 0)
            calc_creatinine(value_1, value_2);
        if (calculator.compareTo("iron") == 0)
            calc_iron(value_1, value_3, value_4);
        if (calculator.compareTo("hdl") == 0)
            calc_hdl(value_1, value_3, value_4);
        if (calculator.compareTo("alkaline") == 0)
            calc_alkaline(value_1, value_3);
    }

    protected Dictionary get_treatments_dict() {
        return treatments_dict;
    }

    public ArrayList<String> wbc_arr() {
        return wbc;
    }

    public ArrayList<String> neut_arr() {
        return neut;
    }

    public ArrayList<String> lymp_arr() {
        return lymp;
    }

    public ArrayList<String> rbc_arr() {
        return rbc;
    }

    public ArrayList<String> hct_arr() {
        return hct;
    }

    public ArrayList<String> urea_arr() {
        return urea;
    }

    public ArrayList<String> hb_arr() {
        return hb;
    }

    public ArrayList<String> creat_arr() {
        return creat;
    }

    public ArrayList<String> iron_arr() {
        return iron;
    }

    public ArrayList<String> hdl_arr() {
        return hdl;
    }

    public ArrayList<String> alkaline_arr() {
        return alk;
    }
}