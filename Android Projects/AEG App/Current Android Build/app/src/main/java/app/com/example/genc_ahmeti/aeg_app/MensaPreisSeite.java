package app.com.example.genc_ahmeti.aeg_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MensaPreisSeite extends AppCompatActivity {

    private TextView[] menüs;
    private double[] kosten;
    private NumberPicker[] stückzahlen;
    private TextView preisangabe;

    private double preis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa_preis_seite);

        menüs = new TextView[13];
        kosten = new double[13];
        stückzahlen = new NumberPicker[13];

        menüs[0] = findViewById(R.id.menü1);
        menüs[1] = findViewById(R.id.menü2);
        menüs[2] = findViewById(R.id.menü3);
        menüs[3] = findViewById(R.id.menü4);
        menüs[4] = findViewById(R.id.menü5);
        menüs[5] = findViewById(R.id.menü6);
        menüs[6] = findViewById(R.id.menü7);
        menüs[7] = findViewById(R.id.menü8);
        menüs[8] = findViewById(R.id.menü9);
        menüs[9] = findViewById(R.id.menü10);
        menüs[10] = findViewById(R.id.menü11);
        menüs[11] = findViewById(R.id.menü12);
        menüs[12] = findViewById(R.id.menü13);

        stückzahlen[0] = findViewById(R.id.stückzahl1);
        stückzahlen[1] = findViewById(R.id.stückzahl2);
        stückzahlen[2] = findViewById(R.id.stückzahl3);
        stückzahlen[3] = findViewById(R.id.stückzahl4);
        stückzahlen[4] = findViewById(R.id.stückzahl5);
        stückzahlen[5] = findViewById(R.id.stückzahl6);
        stückzahlen[6] = findViewById(R.id.stückzahl7);
        stückzahlen[7] = findViewById(R.id.stückzahl8);
        stückzahlen[8] = findViewById(R.id.stückzahl9);
        stückzahlen[9] = findViewById(R.id.stückzahl10);
        stückzahlen[10] = findViewById(R.id.stückzahl11);
        stückzahlen[11] = findViewById(R.id.stückzahl12);
        stückzahlen[12] = findViewById(R.id.stückzahl13);

        for(int n = 0; n < 13; n++)
        {
            stückzahlen[n].setMinValue(0);
            stückzahlen[n].setMaxValue(5);
            stückzahlen[n].setOnValueChangedListener(onValueChangeListener);
        }

        preis = 0;
        preisangabe = findViewById(R.id.preis);
        preisangabe.setText("0 €");

        String preisVonListe;
        for(int n = 0; n < 13; n++)
        {
            ArrayList<ArrayList<String>> a = MensaCreator.getPreisliste();
            preisVonListe = MensaCreator.getPreisliste().get(n).get(1);
            menüs[n].setText(a.get(n).get(0) + " (" + preisVonListe + ")");
            if(!preisVonListe.equals("kostenlos"))
                kosten[n] = Double.parseDouble(preisVonListe.substring(0, preisVonListe.length()-2));
        }

    }

    NumberPicker.OnValueChangeListener onValueChangeListener =
            new 	NumberPicker.OnValueChangeListener(){
                @Override
                public void onValueChange(NumberPicker numberPicker, int i, int i1) {

                    String a1, a2;

                    preis = 0;
                    for(int n = 0; n < 13; n++)
                            preis += stückzahlen[n].getValue() * kosten[n];
                    int b2 = (int) (preis*100);
                    double b3 = ((double) b2) /100;
                    preisangabe.setText(korrektFormatieren(b3));
                }
            };

        public String korrektFormatieren(double zahl)
        {
            if(zahl == 0.0)
                return "0 €";
            String ergebnis;
            String a = Integer.toString((int) (zahl * 100));
                String b = Integer.toString((int) (zahl * 10)) + "0";
                if(a.equals(b))
                    ergebnis =  Double.toString(zahl) + "0" + " €";
                else
                    ergebnis = Double.toString(zahl) + " €";
                return ergebnis;
        }

}
