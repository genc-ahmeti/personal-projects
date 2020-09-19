package app.com.example.genc_ahmeti.aeg_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HauptLoginSeite extends AppCompatActivity {

    private Context context;

    private EditText benutzername;
    private EditText passwort;

    private static  Button login;

    private static SharedPreferences pref;

    // [0] entspricht Vorname
    // [1] entspricht Nachname
     private static String[] nameVonBenutzer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.haupt_login_seite);

        context = HauptLoginSeite.this;

        nameVonBenutzer = new String[2];

        benutzername = findViewById(R.id.benutzername);
        passwort = findViewById(R.id.passwort);

        pref = context.getSharedPreferences("MyPref", 0); // 0 - for private mode
        if(loginWurdeLetztesMalGespeichert())
            benutzername.setText(pref.getString("benutzername", null)); // getting String

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewIn) {

                loginButtonKlickbar(false);

                String benutzernameStr = benutzername.getText().toString();
                String passwortStr = passwort.getText().toString();

                new HauptLoginInternet(context, benutzernameStr, passwortStr).execute();
            }});
    }

    public static void loginButtonKlickbar(boolean istKlickbar) { login.setEnabled(istKlickbar);}

    public static void loginSpeichern(String benutzername)
    {
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("benutzername", benutzername); // Storing string
        editor.commit(); // commit changes
    }

    public boolean loginWurdeLetztesMalGespeichert() {return pref.contains("benutzername");}

    public static void nameVonBenutzerSpeichern(String name)
    {
        nameVonBenutzer = name.split(" ");
    }

    public static String getNameVonBenutzer()
    {
        return nameVonBenutzer[0] + " " + nameVonBenutzer[1];
    }
}
