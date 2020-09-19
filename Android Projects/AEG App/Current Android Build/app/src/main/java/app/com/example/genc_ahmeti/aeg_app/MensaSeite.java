package app.com.example.genc_ahmeti.aeg_app;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Genc_Ahmeti on 02.03.2018.
 */

public class MensaSeite extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static TextView[] textViews = new TextView[13];
    private Button mensaButton1;
    private Button mensaButton2;
    private static Spinner mensaSpinner;
    private boolean internetAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensa);

        if( VertretungCreator.internetverbindungIstAktiv(MensaSeite.this))
            internetAn = true;
        else
            internetAn = false;

        if(internetAn)
            new MensaExtrahieren().execute();

        textViews[0] = (TextView) findViewById(R.id.mensa_textview1);
        textViews[1] = (TextView) findViewById(R.id.mensa_textview2);
        textViews[2] = (TextView) findViewById(R.id.mensa_textview3);
        textViews[3] = (TextView) findViewById(R.id.mensa_textview4);
        textViews[4] = (TextView) findViewById(R.id.mensa_textview5);
        textViews[5] = (TextView) findViewById(R.id.mensa_textview6);
        textViews[6] = (TextView) findViewById(R.id.mensa_textview7);
        textViews[7] = (TextView) findViewById(R.id.mensa_textview8);
        textViews[8] = (TextView) findViewById(R.id.mensa_textview9);
        textViews[9] = (TextView) findViewById(R.id.mensa_textview10);
        textViews[10] = (TextView) findViewById(R.id.mensa_textview11);
        textViews[11] = (TextView) findViewById(R.id.mensa_textview12);
        textViews[12] = (TextView) findViewById(R.id.mensa_textview13);

        for (int n = 0; n < textViews.length; n++) {
            if (n > 1 && n % 2 == 0)
                textViews[n].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View viewIn) {

                        int stelle = 0;
                        for (int n = 0; n < textViews.length; n++)
                            if (textViews[n] == viewIn) {
                                stelle = n;
                                break;
                            }
                        int tag = 0;
                        switch (mensaSpinner.getSelectedItem().toString()) {
                            case "Heute":
                                break;
                            case "Morgen":
                                tag = 1;
                                break;
                            case "Übermorgen":
                                tag = 2;
                                break;
                            case "In drei Tagen":
                                tag = 3;
                                break;
                            case "In vier Tagen":
                                tag = 4;
                                break;
                        }

                        try {
                            String content = MensaCreator.getMenü().get(tag).get(stelle);


                            MensaCreator.setIndexliste(content);
                            MensaCreator.setAllergienliste(content);

                            String allergien = MensaCreator.allergienAnzeigen();
                            if (!allergien.equals("")) {
                                AlertDialog alertDialog = new AlertDialog.Builder(MensaSeite.this).create();
                                alertDialog.setMessage(allergien);
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                        catch(IndexOutOfBoundsException e)
                        {
                            // soll nix passieren, wenn internet aus!
                        }
                    }
                });
        }


        mensaButton1 = (Button) findViewById(R.id.mensa_button1);
        mensaButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                try {
                    startActivity(new Intent(MensaSeite.this, MensaPreisSeite.class));
                    /* String temp = "";
                    for (int n = 0; n < MensaCreator.getPreisliste().size(); n++)
                        for (int i = 0; i < 2; i++)
                            temp += MensaCreator.getPreisliste().get(n).get(i) + "\n";

                    AlertDialog alertDialog = new AlertDialog.Builder(MensaSeite.this).create();
                    alertDialog.setMessage(temp);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();*/
                }
                catch(IndexOutOfBoundsException e)
                {
                    // wenn kein Internet nix passieren!
                }
            }
        });

        mensaButton2 = (Button) findViewById(R.id.mensa_button2);
        mensaButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {
                if(internetAn) {
                    new MensaExtrahieren().execute();

                    String selectedItem = mensaSpinner.getSelectedItem().toString();

                    switch (selectedItem) {
                        case "Heute":
                            for (int n = 0; n < MensaCreator.getMenü().get(0).size(); n++)
                                textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(0).get(n), n));
                            break;
                        case "Morgen":
                            for (int n = 0; n < MensaCreator.getMenü().get(1).size(); n++)
                                textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(1).get(n), n));
                            break;
                        case "Übermorgen":
                            for (int n = 0; n < MensaCreator.getMenü().get(2).size(); n++)
                                textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(2).get(n), n));
                            break;
                        case "In drei Tagen":
                            for (int n = 0; n < MensaCreator.getMenü().get(3).size(); n++)
                                textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(3).get(n), n));
                            break;
                        case "In vier Tagen":
                            for (int n = 0; n < MensaCreator.getMenü().get(4).size(); n++)
                                textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(4).get(n), n));
                            break;
                    }
                }
            }
        });

        mensaSpinner = (Spinner) findViewById(R.id.mensa_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mensa_menü, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        mensaSpinner.setAdapter(adapter);
        mensaSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        try {
            String selectedItem = adapterView.getItemAtPosition(i).toString();
            if (MensaCreator.getMenü().size() != 0)
                switch (selectedItem) {
                    case "Heute":
                        for (int n = 0; n < MensaCreator.getMenü().get(0).size(); n++)
                            textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(0).get(n), n));
                        break;
                    case "Morgen":
                        for (int n = 0; n < MensaCreator.getMenü().get(1).size(); n++)
                            textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(1).get(n), n));
                        break;
                    case "Übermorgen":
                        for (int n = 0; n < MensaCreator.getMenü().get(2).size(); n++)
                            textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(2).get(n), n));
                        break;
                    case "In drei Tagen":
                        for (int n = 0; n < MensaCreator.getMenü().get(3).size(); n++)
                            textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(3).get(n), n));
                        break;
                    case "In vier Tagen":
                        for (int n = 0; n < MensaCreator.getMenü().get(4).size(); n++)
                            textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(4).get(n), n));
                        break;
                }
        }
        catch(IndexOutOfBoundsException e)
        {
            // kein Internet soll nix passieren
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static void menüAnzeigen() {
        String str = mensaSpinner.getSelectedItem().toString();
        int tag = 0;
        switch(str)
        {
            case "Heute": break;
            case "Morgen": tag = 1; break;
            case "Übermorgen": tag = 2; break;
            case "In drei Tagen": tag = 3; break;
            case "In vier Tagen": tag = 4; break;
        }
        for (int n = 0; n < MensaCreator.getMenü().get(tag).size(); n++)
            textViews[n].setText(allergienKürzen(MensaCreator.getMenü().get(tag).get(n), n));
    }

    public static String allergienKürzen(String str, int stelle) {
        if(stelle % 2 == 1 || stelle == 0)
            return str;
        MensaCreator.setIndexliste(str);
        ArrayList<Integer> a = MensaCreator.getIndexliste();
        while(MensaCreator.getIndexliste().size() != 0)
        {
            str = str.replace(str.substring(MensaCreator.getIndexliste().get(0)-1, MensaCreator.getIndexliste().get(1)+1),"");
            MensaCreator.setIndexliste(str);
            a = MensaCreator.getIndexliste();
        }
        return str;
    }
}
