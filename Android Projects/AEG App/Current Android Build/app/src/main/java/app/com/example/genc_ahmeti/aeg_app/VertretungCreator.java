package app.com.example.genc_ahmeti.aeg_app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Genc_Ahmeti on 14.05.2017.
 */

public class VertretungCreator {

    //private Membervariablen

    public static String klasseName;
    public static String URL = "http://www.aeg-reutlingen.de/aktuelles/vertretungsplan/";

    //public Membervariablen
    public static ArrayList<String> vertretungenHeute;
    public static ArrayList<String> vertretungenMorgen;
    /*Index: allgemein: 0=Klasse, 1=Stunde, 2=Fach, 3=Lehrer, 4=Vertretung, 5=Raum, 6=Bemerkung
     * wenn entfällt: 0=Klasse, 1=Stunde, 2=Fach, 3="entfällt"
     * wenn Fach anders:
     * wenn Raum anders
     */
    public static ArrayList<ArrayList<String>> vertretungenHeuteGeteilt;
    public static ArrayList<ArrayList<String>> vertretungenMorgenGeteilt;

    // public Memberfunktionen
    public static void setKlasseName() {
        klasseName = StundenplanSeite.getKlasseName();
    }

    public static int getKlasseZahl() {
        int kommaStelle = klasseName.indexOf('.');
        if (kommaStelle != -1)
            return Integer.parseInt(klasseName.substring(0, kommaStelle));
        else
            return Integer.parseInt(klasseName.substring(0, klasseName.length() - 1));
    }

    private static boolean enthältJetzigeKlasse(String str) {
        String f = str;
        String e = klasseName;
        boolean a = str.contains(klasseName);
        boolean b = enthältEineKlasse(str) && str.contains("," + getKlasseKurs());
        return (str.contains(klasseName) || enthältEineKlasse(str) && str.contains("," + getKlasseKurs()));
    }

    private static boolean enthältEineKlasse(String str) {
        String a = str;
        String e = klasseName;
        String temp = klasseName.substring(0, klasseName.length() - 1);
        return str.contains(temp + "a") || str.contains(temp + "b") || str.contains(temp + "c") || str.contains(temp + "d") || str.contains(temp + "e");
    }

    private static String getKlasseKurs() {
        int kommaStelle = klasseName.indexOf('.');
        if (kommaStelle != -1)
            return klasseName.substring(kommaStelle + 1);
        else
            return klasseName.substring(klasseName.length() - 1);
    }

    public static String getKlasseURL() {

        URL = "http://www.aeg.rt.bw.schule.de/aktuelles/vertretungsplan/v-plan/";
        switch (getKlasseZahl()) {
            case 5:
                URL += "vertretungen-klasse-5/";
                break;
            case 6:
                URL += "klasse-6/";
                break;
            case 7:
                URL += "klasse-7/";
                break;
            case 8:
                URL += "klasse-8/";
                break;
            case 9:
                URL += "klasse-9/";
                break;
            case 10:
                URL += "klasse-10/";
                break;
         /* case 11:
                URL += "ks1/";
                break;*/
            case 12:
                URL += "ks2/";
                break;
        }
        return URL;
    }

    public static ArrayList<String> getVplanText() throws IOException {

        String url = getKlasseURL();

        Connection.Response loginForm = Jsoup
                .connect(url)
                .method(Connection.Method.GET)
                .execute();

        Connection.Response response = Jsoup.connect(url)
                .data("uName", "vplan")
                .data("uPassword", "v-planAEG1718")
                .data("rcID", "1177")
                .data("submit", "Anmelden >")
                .cookies(loginForm.cookies())
                .method(Connection.Method.POST)
                .execute();

        //Verbindung mit Website von AEG Vertretungsplan: Die Seite wird als Dokument (HTML Datei)
        //in doc gespeichert
        Document doc = response.parse();
        ArrayList<String> vplanText = new ArrayList<String>();
        //Suche nach dem body-BLock (dort stehen die Infos zu Vertretungen)
        Element body = doc.body();
        //Suche nach den Elementen mit dem Tag <td> im body-blick (in diesen stehen die eigentlichen Vertretungen)
        Elements elmsTagTd = body.getElementsByTag("td");
        for (Element elmTagTd : elmsTagTd)
            if (Character.isLetterOrDigit(elmTagTd.text().charAt(0)) || elmTagTd.text().charAt(0) == '.')
                // System.out.println(unterelement.text()); das zeigt den Text in dem block an (wichtig)
                vplanText.add(elmTagTd.text());
        ArrayList<String> a = vplanText;
        return vplanText;
    }

    public static void setVertretungen(ArrayList<String> arrList) //holt von einem Array von Strings die Informtionen über vErtretungen von einer
    // Klasse und packt sie jeweils in einen String
    {
        //vertretungenHeute = new ArrayList<String>(0);
        //vertretungenMorgen = new ArrayList<String>(0);
        vertretungenHeuteGeteilt = new ArrayList<ArrayList<String>>(0);
        vertretungenMorgenGeteilt = new ArrayList<ArrayList<String>>(0);
        boolean istVonEinerKlasse = false;
        boolean istVonEinerAnderenKlasse = false;
        boolean vertretungVonMorgen = false;
        //String vertretungKlasse = "";

        for (int n = 4; n < arrList.size(); n++) {

            // Bei bitte beachten kommen manchmal klassen schon vor, aso verhindert man,dass man die nict in die
            // vertretungliste mitnimmt
            if (n < arrList.size() - 1 && enthältEineKlasse(arrList.get(n)) && enthältEineKlasse(arrList.get(n + 1)))
                continue;

            if (enthältJetzigeKlasse(arrList.get(n)))
                istVonEinerAnderenKlasse = false;

            if (istVonEinerAnderenKlasse)
                continue;

            // Wenn eine andere Klasse mit den Vertretungen kommt, überspringen
            if (enthältEineKlasse(arrList.get(n)) && !enthältJetzigeKlasse(arrList.get(n))) {
                istVonEinerAnderenKlasse = true;
                continue;
            }

            //Wenn "Abwesende Klassen" gefunden wird, dann weiß man, dass es das zweite mal ist; das heißt, die nachfolgenden infos zeigen vertretungen
            //für den morgigen Tag
            if (arrList.get(n).length() > 1 && arrList.get(n).substring(0, 2).equals("Ab")) {
                n = n + 3;
                vertretungVonMorgen = true;
                continue;
            }
            // Infos unter dem Klassennamen: unterschediung von morgen und heute
            if (istVonEinerKlasse) {
                String a = arrList.get(n);
                if (vertretungVonMorgen)
                    vertretungenMorgenGeteilt.get(vertretungenMorgenGeteilt.size() - 1).add(arrList.get(n));
                else
                    vertretungenHeuteGeteilt.get(vertretungenHeuteGeteilt.size() - 1).add(arrList.get(n));
                //vertretungKlasse += " " + arrList.get(n);
                if (vertretungVonMorgen && (n + 1 == arrList.size() || enthältEineKlasse(arrList.get(n + 1)))) {
                    //vertretungenMorgen.add(vertretungKlasse);
                    //vertretungKlasse = "";
                    istVonEinerKlasse = false;
                }
                // arrList.get(n + 1).substring(0, Integer.toString(getKlasseZahl()).length()).equals(Integer.toString(getKlasseZahl()))
                // arrList.get(n + 1).substring(0, Integer.toString(getKlasseZahl()).length()).equals(Integer.toString(getKlasseZahl()))

                else if (n + 1 == arrList.size() || enthältEineKlasse(arrList.get(n + 1)) || (arrList.get(n + 1).length() > 1 && arrList.get(n + 1).substring(0, 2).equals("Ab"))) {
                    //vertretungenHeute.add(vertretungKlasse);
                    //vertretungKlasse = "";
                    istVonEinerKlasse = false;
                }
            }
            //Wird ausgeführt, sobald im Text des Vplans der Klassename gefunden wird -> markiert, dass die folgenden Zeilen dazugehören durch
            //istVonEinerKlasse = true;
            ArrayList<String> a = arrList;
            if (!istVonEinerKlasse && enthältJetzigeKlasse(arrList.get(n))) {
                if (vertretungVonMorgen) {
                    vertretungenMorgenGeteilt.add(new ArrayList<String>());
                    vertretungenMorgenGeteilt.get(vertretungenMorgenGeteilt.size() - 1).add(arrList.get(n));
                } else {
                    vertretungenHeuteGeteilt.add(new ArrayList<String>());
                    vertretungenHeuteGeteilt.get(vertretungenHeuteGeteilt.size() - 1).add(arrList.get(n));
                }
                //vertretungKlasse += " " + arrList.get(n);
                istVonEinerKlasse = true;
            }

            ArrayList<ArrayList<String>> aaaa = vertretungenHeuteGeteilt;
            ArrayList<ArrayList<String>> bbbb = vertretungenMorgenGeteilt;
            a = a;
        }


    }

    public static void setVertretungenOS(ArrayList<String> arrList) {

        ArrayList<String> aba = arrList;
        vertretungenHeuteGeteilt = new ArrayList<ArrayList<String>>(0);
        vertretungenMorgenGeteilt = new ArrayList<ArrayList<String>>(0);
        boolean istVonEinerKlasse = false;
        boolean vertretungVonMorgen = false;
        boolean vertretungenFangenAn = false;
        for (int n = 4; n < arrList.size(); n++) {
            if (!vertretungenFangenAn && !(n < arrList.size() - 1 && arrList.get(n + 1).equals(Integer.toString(getKlasseZahl())))) {
                vertretungenFangenAn = true;
                continue;
            }

            //Wenn "Abwesende Klassen" gefunden wird, dann weiß man, dass es das zweite mal ist; das heißt, die nachfolgenden infos zeigen vertretungen
            //für den morgigen Tag
            if (arrList.get(n).length() > 1 && arrList.get(n).substring(0, 2).equals("Ab")) {
                n = n + 3;
                vertretungVonMorgen = true;
                continue;
            }

            // Infos unter dem Klassennamen: unterschediung von morgen und heute
            if (istVonEinerKlasse) {
                String a = arrList.get(n);
                if (vertretungVonMorgen)
                    vertretungenMorgenGeteilt.get(vertretungenMorgenGeteilt.size() - 1).add(arrList.get(n));
                else
                    vertretungenHeuteGeteilt.get(vertretungenHeuteGeteilt.size() - 1).add(arrList.get(n));
                //vertretungKlasse += " " + arrList.get(n);
                if (vertretungVonMorgen && (n + 1 == arrList.size() || arrList.get(n + 1).equals(Integer.toString(getKlasseZahl())))) {
                    //vertretungenMorgen.add(vertretungKlasse);
                    //vertretungKlasse = "";
                    istVonEinerKlasse = false;
                }
                // arrList.get(n + 1).substring(0, Integer.toString(getKlasseZahl()).length()).equals(Integer.toString(getKlasseZahl()))
                // arrList.get(n + 1).substring(0, Integer.toString(getKlasseZahl()).length()).equals(Integer.toString(getKlasseZahl()))

                else if (n + 1 == arrList.size() || arrList.get(n + 1).equals(Integer.toString(getKlasseZahl())) || (arrList.get(n + 1).length() > 1 && arrList.get(n + 1).substring(0, 2).equals("Ab"))) {
                    //vertretungenHeute.add(vertretungKlasse);
                    //vertretungKlasse = "";
                    istVonEinerKlasse = false;
                }
            }

            //Wird ausgeführt, sobald im Text des Vplans der Klassename gefunden wird -> markiert, dass die folgenden Zeilen dazugehören durch
            //istVonEinerKlasse = true;
            if (!istVonEinerKlasse && arrList.get(n).equals(Integer.toString(getKlasseZahl()))) {

                if (vertretungVonMorgen) {
                    vertretungenMorgenGeteilt.add(new ArrayList<String>());
                    vertretungenMorgenGeteilt.get(vertretungenMorgenGeteilt.size() - 1).add(arrList.get(n));
                } else {
                    vertretungenHeuteGeteilt.add(new ArrayList<String>());
                    vertretungenHeuteGeteilt.get(vertretungenHeuteGeteilt.size() - 1).add(arrList.get(n));
                }
                istVonEinerKlasse = true;
            }
        }
    }

    public static void getVertretungen(ArrayList<String> arrList) {
        for (int n = 0; n < arrList.size(); n++)
            System.out.print("\n\n\n" + arrList.get(n));
    }

    public static ArrayList<ArrayList<String>> getVertretungenGeteilt(int heuteOderMorgen) {
        if (heuteOderMorgen == 0)
            return vertretungenHeuteGeteilt;
        else
            return vertretungenMorgenGeteilt;
    }


    public static void getVertretungenGeteilt(ArrayList<ArrayList<String>> doppelArrList) {
        for (int n = 0; n < doppelArrList.size(); n++)
            for (int i = 0; i < doppelArrList.get(n).size(); i++)
                System.out.println("\n" + doppelArrList.get(n).get(i));
    }

    public static int AnzahlVertretungen(ArrayList<ArrayList<String>> doppelArrList) {
        return doppelArrList.size();
    }

    public static boolean internetverbindungIstAktiv(Context context) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI") && ni.isConnected())
                haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE") && ni.isConnected())
                haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    /*
     *
     * VERTRETUNGEN ANZEIGEN
     *
     */

    // ENTFÄLLT

    public static boolean stundeEntfällt(int vertretungNr, int heuteOderMorgen) {
        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(3).equals("entfällt"))
            return true;
        else
            return false;
    }

    public static void stundeEntfälltVertretung(int vertretungNr, int heuteOderMorgen) {
        String stunde = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).substring(0, getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).length() - 1);
        VertretungSeite.getTvVertretung(heuteOderMorgen, Integer.parseInt(stunde) - 1).setText("[entfällt]");
    }

    // VERLEGT

    public static boolean stundeVerlegt(int vertretungNr, int heuteOderMorgen) {
        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).contains("statt"))
            return true;
        else
            return false;
    }

    public static void stundeVerlegtVertretung(int vertretungNr, int heuteOderMorgen) {
        String stunde = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).substring(0, getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).length() - 1);
        String fach = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(4);
        String raum = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(5);
        String zusatz = "";

        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).contains(", "))
            zusatz += getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).substring(getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).indexOf(", ")+2);

        VertretungSeite.getTvVertretung(heuteOderMorgen, Integer.parseInt(stunde) - 1).setText(fach + ", " + raum + "\n" + "[" + zusatz + "]");
    }

    // ZUSATZSTUNDE

    public static boolean stundeZusatzstunde(int vertretungNr, int heuteOderMorgen) {
        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).contains("Zusatzstunde"))
            return true;
        else
            return false;
    }

    public static void stundeZusatzstundeVertretung(int vertretungNr, int heuteOderMorgen) {
        String stunde = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).substring(0, getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).length() - 1);
        String fach = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(4);
        String raum = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(5);
        String zusatz = "";

        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).contains(", "))
            zusatz += getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).substring(getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).indexOf(", ")+2);

        VertretungSeite.getTvVertretung(heuteOderMorgen, Integer.parseInt(stunde) - 1).setText(fach + ", " + raum + "\n[" + zusatz + "]");
    }

    // ---------------------------------------------------- VERTRETUNG --------------------------------------------------------

    public static boolean stundeVertretung(int vertretungNr, int heuteOderMorgen) {
        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(4).equals("Vertr."))
            return true;
        else
            return false;
    }

    public static void stundeVertretungVertretung(int vertretungNr, int heuteOderMorgen) {
        String stunde = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).substring(0, getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).length() - 1);
        String fach = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(2);
        String raum = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(5);
        String zusatz = "";

        if (!getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).equals("."))
            zusatz += getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6);

        VertretungSeite.getTvVertretung(heuteOderMorgen, Integer.parseInt(stunde) - 1).setText(fach + ", " + raum + "\n[" + zusatz + "]");
    }

    // RAUMÄNDERUNG (das taucht in allen anderen Vertretungnen auf, deswegen muss es als letztes abgefragt werden)

    public static boolean stundeRaumänderung(int vertretungNr, int heuteOderMorgen) {
        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).contains("Raumänderung"))
            return true;
        else
            return false;
    }

    public static void stundeRaumänderungVertretung(int vertretungNr, int heuteOderMorgen) {
        String stunde = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).substring(0, getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(1).length() - 1);
        String fach = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(4);
        String raum = getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(5);
        String zusatz = "";

        if (getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).contains(", "))
            zusatz += getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).substring(getVertretungenGeteilt(heuteOderMorgen).get(vertretungNr).get(6).indexOf(", ")+2);

        VertretungSeite.getTvVertretung(heuteOderMorgen, Integer.parseInt(stunde) - 1).setText(fach + ", " + raum + "\n[" + zusatz + "]");
    }
}
