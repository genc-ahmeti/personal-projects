package app.com.example.genc_ahmeti.aeg_app;

import java.io.IOException;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

/**
 * Created by Genc_Ahmeti on 07.05.2017.
 */

public class VertretungExtrahieren extends AsyncTask<Void, Void, Void[]> {

    Context context;

    public VertretungExtrahieren(Context context)
    {
        this.context = context;
    }
    //protected Memberfunktionen (für zweiten Thread)
    @Override
    protected Void[] doInBackground(Void... params) {
        VertretungCreator.setKlasseName();
        if (VertretungCreator.getKlasseZahl() <= 10) {
            try {
                VertretungCreator.setVertretungen(VertretungCreator.getVplanText());
            } catch (IOException ex) {
                //Handle IOException!!!
                String a = "";
            }
        } else
            {
            try {
                VertretungCreator.setVertretungenOS(VertretungCreator.getVplanText());
            } catch (IOException ex) {
                //Handle IOException!!!
            }
        }
        return params;
    }

    @Override
    protected void onPostExecute(Void[] result) {
        //VertretungCreator.setVertretungenGeteilt();
        ArrayList<ArrayList<String>> a = VertretungCreator.vertretungenHeuteGeteilt;
        ArrayList<ArrayList<String>> b = VertretungCreator.vertretungenMorgenGeteilt;

       /* if(VertretungCreator.getKlasseZahl() < 11) {
            // VERTRETUNGEN
            for (int heuteOderMorgen = 0; heuteOderMorgen < 2; heuteOderMorgen++)
                for (int n = 0; n < VertretungCreator.getVertretungenGeteilt(heuteOderMorgen).size(); n++) {
                    if (VertretungCreator.stundeEntfällt(n, heuteOderMorgen))
                        VertretungCreator.stundeEntfälltVertretung(n, heuteOderMorgen);

                    else if (VertretungCreator.stundeVerlegt(n, heuteOderMorgen))
                        VertretungCreator.stundeVerlegtVertretung(n, heuteOderMorgen);

                    else if (VertretungCreator.stundeZusatzstunde(n, heuteOderMorgen))
                        VertretungCreator.stundeZusatzstundeVertretung(n, heuteOderMorgen);

                    else if (VertretungCreator.stundeVertretung(n, heuteOderMorgen))
                        VertretungCreator.stundeVertretungVertretung(n, heuteOderMorgen);

                    else if (VertretungCreator.stundeRaumänderung(n, heuteOderMorgen))
                        VertretungCreator.stundeRaumänderungVertretung(n, heuteOderMorgen);
                }
        }
        else
        {*/
        String vertretungStr;
        String fachStundenplan;
        String fachVertretung;
        int vertretungStunde;

        boolean tagesablaufFürHeuteHatSichGeändert = false;
        boolean tagesablaufFürMorgenHatSichGeändert = false;

        for(int heuteOderMorgen = 0; heuteOderMorgen < 2; heuteOderMorgen++)
            for (int stunde = 0; stunde < 11; stunde++)
            {
                vertretungStr = VertretungSeite.getTvVertretungStrCopy(heuteOderMorgen, stunde);
                if(vertretungStr.length() == 0)
                    continue;
                fachStundenplan = vertretungStr.substring(0, vertretungStr.indexOf(' '));
                for (int n = 0; n < VertretungCreator.getVertretungenGeteilt(heuteOderMorgen).size(); n++)
                {
                    //Vertretung muss diese Stunde beeinflussen
                    vertretungStunde = Integer.parseInt(VertretungCreator.getVertretungenGeteilt(heuteOderMorgen).get(n).get(1).substring(0, VertretungCreator.getVertretungenGeteilt(heuteOderMorgen).get(n).get(1).length() - 1));
                    fachVertretung = VertretungCreator.getVertretungenGeteilt(heuteOderMorgen).get(n).get(2);
                    if (vertretungStunde != stunde + 1 || !fachStundenplan.equals(fachVertretung))
                        continue;

                    if (VertretungCreator.stundeEntfällt(n, heuteOderMorgen))
                    {
                        VertretungCreator.stundeEntfälltVertretung(n, heuteOderMorgen);
                        if(heuteOderMorgen == 0)
                            tagesablaufFürHeuteHatSichGeändert = true;
                        else
                            tagesablaufFürMorgenHatSichGeändert = true;
                    }

                    else if (VertretungCreator.stundeVerlegt(n, heuteOderMorgen))
                    {  VertretungCreator.stundeVerlegtVertretung(n, heuteOderMorgen);
                    if(heuteOderMorgen == 0)
                        tagesablaufFürHeuteHatSichGeändert = true;
                    else
                        tagesablaufFürMorgenHatSichGeändert = true;}

                    else if (VertretungCreator.stundeZusatzstunde(n, heuteOderMorgen))
                { VertretungCreator.stundeZusatzstundeVertretung(n, heuteOderMorgen);
                    if(heuteOderMorgen == 0)
                        tagesablaufFürHeuteHatSichGeändert = true;
                    else
                        tagesablaufFürMorgenHatSichGeändert = true;}

                    else if (VertretungCreator.stundeVertretung(n, heuteOderMorgen))
                { VertretungCreator.stundeVertretungVertretung(n, heuteOderMorgen);
                    if(heuteOderMorgen == 0)
                        tagesablaufFürHeuteHatSichGeändert = true;
                    else
                        tagesablaufFürMorgenHatSichGeändert = true;}

                    else if (VertretungCreator.stundeRaumänderung(n, heuteOderMorgen))
                {VertretungCreator.stundeRaumänderungVertretung(n, heuteOderMorgen);
                    if(heuteOderMorgen == 0)
                        tagesablaufFürHeuteHatSichGeändert = true;
                    else
                        tagesablaufFürMorgenHatSichGeändert = true;}

                }
            }

            if(tagesablaufFürHeuteHatSichGeändert && tagesablaufFürMorgenHatSichGeändert)
                nachricht("Es gibt Vertretungen für heute und morgen!");
        else if(tagesablaufFürHeuteHatSichGeändert && !tagesablaufFürMorgenHatSichGeändert)
            nachricht("Es gibt Vertretungen nur für heute!");
        else if(!tagesablaufFürHeuteHatSichGeändert && tagesablaufFürMorgenHatSichGeändert)
            nachricht("Es gibt Vertretungen nur für morgen!");
        else
            nachricht("Es gibt keine Vertretungen für heute und morgen!");


        //  }
/*
        int ANZAHL_VERTRETUNGEN2 = VertretungCreator.AnzahlVertretungen(VertretungCreator.vertretungenMorgenGeteilt);
        for (int stunde = 0; stunde < 11; stunde++) {
                String vertretungStr = VertretungSeite.getTvVertretungStrCopy(1, stunde);
                if(vertretungStr.length() == context.getResources().getString(R.string.leereStunde).length())
                    continue;
                str1 = vertretungStr.substring(0, vertretungStr.indexOf(','));
                for (int n = 0; n < VertretungCreator.AnzahlVertretungen(VertretungCreator.vertretungenMorgenGeteilt); n++) {
                    //Vertretung muss diese Stunde beeinflussen
                    vertretungStunde = Integer.parseInt(VertretungCreator.vertretungenMorgenGeteilt.get(n).get(1).substring(0, VertretungCreator.vertretungenMorgenGeteilt.get(n).get(1).length() - 1));
                    str2 = VertretungCreator.vertretungenMorgenGeteilt.get(n).get(2);
                    if (vertretungStunde != stunde + 1 || !str1.equals(str2))
                        continue;
                    if (VertretungCreator.stundeFaelltAus(VertretungCreator.vertretungenMorgenGeteilt, n)) {
                        VertretungSeite.getTvVertretung(1, stunde).setText("Fällt aus!!!");
                        break;
                    }
            else if()
            else

                }
            }*/
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