package app.com.example.genc_ahmeti.aeg_app;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Genc_Ahmeti on 02.03.2018.
 */

public class MensaExtrahieren extends AsyncTask<Void, Void, Void[]> {

    String urlPreisliste = "http://www.aeg.rt.bw.schule.de/aktuelles/ansprechpartner/";
    String urlMenü = "https://www.insiva-gmbh.de/catering/speiseplaene/schulen/albert-einstein-gymnasium-reutlingen/";

    ArrayList<ArrayList<String>> preisliste = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> menü = new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> hinweis = new ArrayList<ArrayList<String>>();

    //protected Memberfunktionen (für zweiten Thread)
    @Override
    protected Void[] doInBackground(Void... params) {

        try {
            //Verbindung mit Website von AEG Vertretungsplan: Die Seite wird als Dokument (HTML Datei)
            //in doc gespeichert
            Document doc = Jsoup.connect(urlPreisliste).userAgent("Mozilla").get();
            Document doc2 = Jsoup.connect(urlMenü).userAgent("Mozilla").get();
            //Suche nach dem body-BLock (dort stehen die Infos zu Vertretungen)
            Element body = doc.body();
            Element body2 = doc2.body();
            //Suche nach den Elementen mit dem Tag <td> im body-blick (in diesen stehen die eigentlichen Vertretungen)
            Elements elmsTagTd = body.getElementsByTag("td");
            Elements elmsTagTd2 = body2.getElementsByTag("td");
            Elements elmsTagTd3 = body2.getElementsByClass("canteen-customtext");

            int counter = 0;
            for (Element elmTagTd : elmsTagTd)
                // System.out.println(unterelement.text()); das zeigt den Text in dem block an (wichtig)
                switch (counter) {
                    case 0:
                        preisliste.add(new ArrayList<String>(0));
                        preisliste.get(preisliste.size() - 1).add(elmTagTd.text());
                        counter++;
                        break;
                    case 1:
                        preisliste.get(preisliste.size() - 1).add(elmTagTd.text());
                        counter--;
                        break;
                }

            menü.add(new ArrayList<String>(0));
            for (Element elmTagTd : elmsTagTd2) {
                menü.get(0).add(elmTagTd.text());
            }

            hinweis.add(new ArrayList<String>(0));
            for (Element elmTagTd : elmsTagTd3) {
                hinweis.get(0).add(elmTagTd.text());
            }
        }catch(IOException e){}

        return params;
    }

    @Override
    protected void onPostExecute(Void[] result) {

        MensaCreator.setPreisliste(preisliste);
        MensaCreator.setMenü(menü);
        ArrayList<ArrayList<String>> a = MensaCreator.getMenü();
        MensaCreator.setMenü();
        a = MensaCreator.getMenü();
        MensaSeite.menüAnzeigen();
        MensaCreator.setHinweis(hinweis);


    }

}
