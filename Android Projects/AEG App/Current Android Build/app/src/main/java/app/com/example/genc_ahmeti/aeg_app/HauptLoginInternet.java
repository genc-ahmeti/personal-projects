package app.com.example.genc_ahmeti.aeg_app;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.concurrent.CountDownLatch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * Created by Genc_Ahmeti on 07.05.2017.
 */

public class HauptLoginInternet extends AsyncTask<Void, Void, Void[]> {

    private Context context;

    private String url;
    private Connection.Response loginForm;
    private Connection.Response response;

    private String benutzername;
    private String passwort;

    private enum Verbindungsstatus {VERBINDUNG_HAT_NICHT_GEKLAPPT, FALSCHE_LOGINDATEN, LOGIN_HAT_GEKLAPPT}
    private Verbindungsstatus verbindungsstatus;

    private ProgressDialog webProgressDialog;

    public HauptLoginInternet(Context context, String benutzername, String passwort)
    {
        this.context = context;
        this.url = "https://www.aeg-reutlingen.de/moodle2/login/index.php";
        this.loginForm = null;
        this.response = null;
        this.benutzername = benutzername;
        this.passwort = passwort;
        this.webProgressDialog = new ProgressDialog(context);
    }

    @Override
    protected void onPreExecute() {
        webProgressDialog.setTitle("Bitte warten.");
        webProgressDialog.setMessage("Login wird überprüft...");
        if (!webProgressDialog.isShowing())
            webProgressDialog.show();
        super.onPreExecute();
    }


    //protected Memberfunktionen (für zweiten Thread)
    @Override
    protected Void[] doInBackground(Void... params) {

        try
        {
            loginForm = Jsoup
                    .connect(url)
                    .method(Connection.Method.GET)
                    .execute();

            response = Jsoup.connect(url)
                    .data("username", benutzername)
                    .data("password", passwort)
                    .data("anchor", "")
                    .cookies(loginForm.cookies())
                    .method(Connection.Method.POST)
                    .execute();

            String profilseite = (Jsoup.connect("https://www.aeg-reutlingen.de/moodle2/user/profile.php?id=0")
                    .cookies(response.cookies())
                    .get().toString());

            verbindungsstatus = Verbindungsstatus.LOGIN_HAT_GEKLAPPT;
            MoodleSeite.moodleLoginSpeichern(benutzername, passwort);

            int index1 = profilseite.indexOf("<title>");
            int index2 = profilseite.indexOf(":",index1);
            String name = profilseite.substring(index1+7, index2);
            HauptLoginSeite.nameVonBenutzerSpeichern(name);
        }
        catch(SocketTimeoutException ste)
        {
            verbindungsstatus = Verbindungsstatus.FALSCHE_LOGINDATEN;
            Log.i("ste", "Falsche Logindaten!");
        }
        catch(IOException e)
        {
            verbindungsstatus = Verbindungsstatus.VERBINDUNG_HAT_NICHT_GEKLAPPT;
            Log.i("ioe", "Keine Verbindung mit Loginseite möglich!");
        }

        return params;
    }

    @Override
    protected void onPostExecute(Void[] result) {

        int a = 0;
        a++;
        a++;

        if (webProgressDialog.isShowing())
            webProgressDialog.dismiss();

        switch(verbindungsstatus)
        {
            case VERBINDUNG_HAT_NICHT_GEKLAPPT:
                nachricht("Es konnte keine Verbindung hergestellt werden.");
                break;
            case FALSCHE_LOGINDATEN:
                nachricht("Die Logindaten sind falsch!");
                break;
            case LOGIN_HAT_GEKLAPPT:
                HauptLoginSeite.loginSpeichern(benutzername);
                StartSeite.setErsterLogin(true);
                Intent myIntent = new Intent(context, StartSeite.class);
                context.startActivity(myIntent);
                break;
        }

        HauptLoginSeite.loginButtonKlickbar(true);

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

}