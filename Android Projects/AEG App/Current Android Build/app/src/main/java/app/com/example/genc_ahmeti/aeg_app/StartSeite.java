package app.com.example.genc_ahmeti.aeg_app;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;

/**
 * Created by Genc_Ahmeti on 30.12.2017.
 */

public class StartSeite extends ListActivity{

    private Context context;

    private static boolean ersterLogin;

    private static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_seite);

        context = StartSeite.this;

        if(ersterLogin)
        {
            ersterLogin = false;
            nachricht("Hallo " + HauptLoginSeite.getNameVonBenutzer() + ", willkommen zurück!");
        }

        sharedPreferences = getSharedPreferences("klasse", 0); // 0 - for private mode

        String[] überschriften = {"Tagesablauf", "Mein Stundenplan", "Moodle", "Mensa"};

        ListView listView = getListView();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, überschriften);
        listView.setAdapter(adapter);}

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        Intent myIntent = null;

        switch(position)
        {
            case 0:
                if (stundenplanEingegeben())
                {
                    StundenplanSeite.wochentagBestimmen();

                    try{StundenplanSeite.getStundenplanFromIS(context);}
                    catch(IOException e){nachricht("Es gab einen Fehler bei der letzten Speicherung des Stundenplans."); break;}
                    klasseAusSpeicherHolen();
                    if(StundenplanSeite.StundenMitMehrerenFächernExistieren())
                    {
                        nachricht("Es gibt noch Stunden, in den du ein bestimmtes Fach auswählen musst!");
                        break;
                    }

                    myIntent = new Intent(StartSeite.this, VertretungSeite.class);
                }
                else
                    nachricht("Klasse wurde nicht eingegeben!");
                break;
            case 1: myIntent = new Intent(StartSeite.this, StundenplanSeite.class); break;
            case 2:
                nachricht("Erst in einem späteren Update verfügbar :D");
                //myIntent = new Intent(StartSeite.this, MoodleSeite.class);
                break;
            case 3:
                myIntent = new Intent(StartSeite.this, MensaSeite.class);
                break;
        }

        if(myIntent != null)
            startActivity(myIntent);
    }

    public void nachricht(String text)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setMessage(text);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    public static boolean stundenplanEingegeben() { return sharedPreferences.contains("klasse"); }

    public static void klasseAusSpeicherHolen() { StundenplanSeite.setKlasseName(sharedPreferences.getString("klasse", null));}

    public static void klasseSpeichern(String klasse)
    {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("klasse", klasse); // Storing string
        editor.commit(); // commit changes
    }

    public static void setErsterLogin(boolean ersterLoginGewesen) { ersterLogin = ersterLoginGewesen; }

}


