/*
 * Folgende Klasse dient zur Fertigung und Überprüfung
 * der Zimmerbelegung. Innerhalb dieser Klasse wird die
 * Aufgabe gelöst.
 */

/*
 * Hier werden Klassen importiert zur Erweiterung der
 * der Funktionalität meines Programms.
 */

// Dynamische Listen (Listen während der Laufzeit verändern)
import java.util.ArrayList;

// Auslesen von Textdateien
import java.io.FileReader;

// Reinschreiben in Textdateien
import java.io.FileWriter;

// Verbessert Effizienz von FileReader durch Buffer
import java.io.BufferedReader;

// Verbessert Effizienz von FileWriter durch Buffer
import java.io.BufferedWriter;

// Ausnahmebehandlung (Fehler beim Bearbeiten der Textdatei)
import java.io.IOException;

// Erweitert die Funktion von BufferedWriter (println()/print())
// Der Code wird dadurch besser lesbar.
import java.io.PrintWriter;

public class Zimmerbelegung
{
    // Informationen zur Fertigung einer Zimmerbelegung
    // Schülerliste, Zimmerliste (enthält nur die Namen der Schülerinnen),
    private Schüler[] schüler;
    private ArrayList<ArrayList<String>> zimmer;

    /**
     * Konstruktoren
     */

    public Zimmerbelegung(Schüler[] schülerListe)
    {
        // Instanzvariable initialisieren
        schüler = schülerListe;
        zimmer = new ArrayList<ArrayList<String>>();
    }

    public Zimmerbelegung()
    {
        // Instanzvariable initialisieren
        schüler = null;
        zimmer = new ArrayList<ArrayList<String>>();
    }

    // Gibt die Zimmernummer (beginnend ab 0) zurück, bei welcher
    // die Schülerin gefunden wurde bzw. -1, wenn die Schülerin 
    // noch nicht zugeordnet wurde.
    public int zimmernummerVonSchüler(Schüler s)
    {
        for(int indexZimmer = 0; indexZimmer < zimmer.size();indexZimmer++)
        // Taucht der Name der Schülerin in einem der Zimmer auf?
        // Wenn ja, wird die Stelle des Funds zurückgegeben
            if(zimmer.get(indexZimmer).indexOf(s.getName()) != -1)
                return indexZimmer;
        // Nur überprüfen, wenn die Plus-Liste nicht leer ist
        if(!s.istTeilenNull())
        {for(int indexSchülerAusPlusliste = 0; indexSchülerAusPlusliste < s.getTeilenGröße(); indexSchülerAusPlusliste++)
                for(int indexZimmer = 0; indexZimmer < this.zimmer.size(); indexZimmer++)
                // Taucht der Name einer Schülerin aus der Plus-Liste auf?    
                // Wenn ja, wird die Stelle des Funds zurückgegeben
                    if(this.zimmer.get(indexZimmer).indexOf(s.getTeilen(indexSchülerAusPlusliste)) != -1)
                        return indexZimmer;
        }
        // Keine Schülerin taucht auf
        return -1;
    }

    // Überprüft, ob die Schülerin oder eine Schülerin aus ihrer Plus-Liste
    // in einem beliebigen Zimmer vorhanden ist.
    public boolean istSchülerInEinemZimmer(Schüler s, ArrayList<String> schülerImZimmer)
    {
        // Taucht der Name der Schülerin in einem der Zimmer auf?
        // Wenn ja, wird die Stelle des Funds zurückgegeben.

        // Überprüft, ob die Schülerin vorhanden ist.
        if(schülerImZimmer.indexOf(s.getName()) != -1)
            return true;
        if(!s.istTeilenNull())
        {
            for(int indexSchülerAusPlusliste = 0; indexSchülerAusPlusliste < s.getTeilenGröße(); indexSchülerAusPlusliste++)
            // Überprüft, ob eine Schülerin aus der Plus-Liste vorhanden ist.    
                if(schülerImZimmer.indexOf(s.getTeilen(indexSchülerAusPlusliste)) != -1)
                    return true;
        }
        // Keine der vorherigen, überprüften Schülerinnen ist vorhanden.    
        return false; 
    }

    // Die Zimmerbelegung wird kreiert.
    public void zimmerBelegen()
    {
        // Ein Statusflag, der auf true gesetzt wird, wenn eine Schülerin oder
        // eine aus ihrer Plus-Liste in einem Zimmer gefunden wird.
        boolean schalter;
        for(int indexSchüler1 = 0; indexSchüler1 < schüler.length; indexSchüler1++)
        {
            // Die Schülerinnen, die leere Plus-Listen haben und noch keinem
            // Zimmer zugeordnet wurden, bekommen ein Einzelzimmer für sich.
            // Weil die Schülerliste vorher sortiert wurde, wird garantiert,
            // dass diese Schülerin nicht bereits in einem vorherigen Zimmer
            // vorkommt.
            if(!schüler[indexSchüler1].istZugeteilt() && schüler[indexSchüler1].istTeilenNull())
            {
                // Ein neues Zimmer wird hinzugefügt.
                zimmer.add(new ArrayList<String>());
                // Die Schülerin wird dem Einzelzimmer zugeordnet.
                zimmer.get(zimmer.size()-1).add(schüler[indexSchüler1].getName());
                continue;
            }
            // Wenn die Schülerin schon einem Zimmer zugeordnet wurde, wird
            // zu einer neuen Schülerin übergegangen.
            if(schüler[indexSchüler1].istZugeteilt())
                continue;
            // Falls die Schülerin nicht zugeordnet wurde, werden sie und
            // diejenigen aus ihrer Plus-Liste einem neuen Zimmer zugeordnet.
            zimmer.add(new ArrayList<String>());
            // Die Schülerin wird hinzugefügt.
            zimmer.get(zimmer.size()-1).add(schüler[indexSchüler1].getName());
            for(int indexSchülerAusPlusliste1 = 0; indexSchülerAusPlusliste1 < schüler[indexSchüler1].getTeilenGröße(); indexSchülerAusPlusliste1++)
            {
                if(!zimmer.get(zimmer.size()-1).contains(schüler[indexSchüler1].getTeilen(indexSchülerAusPlusliste1)))
                // Die Schülerinnen aus der Plus-Liste werden hinzugefügt.    
                    zimmer.get(zimmer.size()-1).add(schüler[indexSchüler1].getTeilen(indexSchülerAusPlusliste1));
            }
            // Die Schülerin kann jetzt in einem neuen Schleifendurchgang
            // nicht mehr hinzugefügt werden.
            schüler[indexSchüler1].wirdZugeteilt();
            do
            {   // Noch wurde keine Schülerin im Zimmer gefunden.
                schalter = false;
                for(int indexSchüler2 = 0; indexSchüler2 < schüler.length; indexSchüler2++)
                {
                    // Wurde eine Schülerin zugeteilt, soll sie nicht nochmals
                    // einem Zimmer hinzugefügt werden.
                    if(schüler[indexSchüler2].istZugeteilt())
                        continue;
                    // Wird Schülerin oder eine aus ihrer Plus-Liste gefunden:
                    if(istSchülerInEinemZimmer(schüler[indexSchüler2],zimmer.get(zimmer.size()-1)))
                    {
                        schüler[indexSchüler2].wirdZugeteilt();
                        if(!zimmer.get(zimmer.size()-1).contains(schüler[indexSchüler2].getName()))
                        // Nur wenn der Name der Schülerin nicht bereits im Zimmer vorkommt, soll er
                        // hinzugefügt werden.
                            zimmer.get(zimmer.size()-1).add(schüler[indexSchüler2].getName());
                        // Überspringt leere Plus-Listen
                        if(!schüler[indexSchüler2].istTeilenNull())
                        {for(int indexSchülerAusPlusliste2 = 0; indexSchülerAusPlusliste2 < schüler[indexSchüler2].getTeilenGröße(); indexSchülerAusPlusliste2++)
                                if(!zimmer.get(zimmer.size()-1).contains(schüler[indexSchüler2].getTeilen(indexSchülerAusPlusliste2)))
                                // Wenn der Name einer Schülerin aus der Plus-Liste nicht bereits im Zimmer vorkommt,
                                // wird er hinzugefügt.
                                    zimmer.get(zimmer.size()-1).add(schüler[indexSchüler2].getTeilen(indexSchülerAusPlusliste2));
                        }
                        // Eine Schülerin wurde gefunden. Daher muss die Schleife
                        // erneut durchlaufen werden, um die restlichen hinzuzufügen.
                        schalter = true;
                    }
                }
                // Solange eine Schülerin im Zimmer gefunden wird, wird die Schleife
                // wiederholt.
            }while(schalter);
        }
    }
    
    // Die Belegung wird überprüft.
    // Existieren Wunschkonflikte,
    // so werden die Namen der Schülerinnen zurückgegeben, deren 
    // Wünsche konfligieren
    public String zimmerbelegungÜberprüfen()
    {// Zimmerbelegung wird anhand der Minus-Liste überprüft für jede Schülerin
        for(int indexSchüler = 0; indexSchüler<schüler.length;indexSchüler++)
        {
            // Soll nur durchgeführt werden, wenn die Minus-Liste nicht leer ist
            if(!schüler[indexSchüler].istNichtTeilenNull())
            {
                // Zimmernummer, in welchem die Schülerin vorkommt
                int zimmernummer = zimmernummerVonSchüler(schüler[indexSchüler]);
                for(int indexSchülerAusMinusliste = 0; indexSchülerAusMinusliste < schüler[indexSchüler].getNichtTeilenGröße();indexSchülerAusMinusliste++)
                {
                    // Wenn eine Schülerin aus der Minus-Liste vorkommt, dann sollen die
                    // die Schülerin selbst und die Schülerin aus der Minus-Liste zurückgegeben werden
                    if(zimmer.get(zimmernummer).contains(schüler[indexSchüler].getNichtTeilen(indexSchülerAusMinusliste)))
                    {
                        return schüler[indexSchüler].getName() + " und " + schüler[indexSchüler].getNichtTeilen(indexSchülerAusMinusliste);
                    }
                }
            }
        }
        // Zimmerbelegung hat geklappt.
        return "";
    }

    // Extrahiert die Daten der Schülerinnen aus zimmerbelegung.txt
    // und speichert sie in das Schüler-Array ein
    public void setSchülerliste(String datei) throws IOException
    {
        // Eine temporäre Schülerliste wird erstellt
        // und eine Schülerin wird dieser Liste hinzugefügt
        // (Arrayliste kann dynamisch während der Laufzeit erweitert werden)
        ArrayList<Schüler> schülerListe = new ArrayList<Schüler>();
        schülerListe.add(new Schüler());
        // zimmerbelegung.txt wird geöffnet;
        // Textzeilen können eingelesen werden
        BufferedReader br = new BufferedReader(new FileReader(datei));
        // Inhalt der Textzeile aus der Datei
        String zeile;
        int indexSchüler = 0;
        // Der Schülername, der aus der Textzeile extrahiert wird
        String schülername;
        // Liste, die die Schülernamen aus jeweils einer Liste (Minus-/Plus-Liste)
        // enthält
        ArrayList<String> schülernameListe = new ArrayList<String>();
        // Solange das Zeilenende der Datei noch nicht erreicht wurde, soll eine Textzeile
        // eingelesen werden
        while( (zeile = br.readLine()) != null)
        {
            // Liste der Schülernamen wird gelöscht, damit die neue
            // Liste (Minus-/Plus-Liste) aufgenommen werden kann
            schülernameListe.clear();
            // Ein neuer Schülernamen soll aufgenommen werden
            schülername = "";
            //Schülerinnen werden durch einen Zeilenumbruch voneinander
            // getrennt
            if(zeile.equals(""))
            {
                // Fügt eine neue Schülerin hinzu und geht deswegen
                // zur nächsten Schülerin über
                schülerListe.add(new Schüler());
                indexSchüler++;
            }  
            // Das "+"-Zeichen markiert eine Zeile, in der die Namen
            // der Plus-Liste vorkommen; die Plus-Liste wird erstellt
            else if(zeile.charAt(0)== '+')
            {
                // Überspringt leere Plus-Listen
                if(zeile.length() > 2){
                    for(int indexBuchstabe = 2; indexBuchstabe < zeile.length(); indexBuchstabe++)
                    {
                        //Schülerinnen werden durch ein Leerzeichen voneinander
                        // getrennt
                        if(zeile.charAt(indexBuchstabe) == ' ')
                        {
                            // Die Schülerin aus der Plus-Liste wird der Schülerliste
                            // hinzugefügt und ein neuer Schülernamen soll aufgenommen werden
                            schülernameListe.add(schülername);
                            schülername = "";
                            // Es wird zur nächsten Schülerin übergegangen
                            continue;
                        }
                        // Der Schülername wird aus der Zeile extrahiert
                        schülername += zeile.charAt(indexBuchstabe);
                    }
                    // Dieser Extrabefehl versichert, dass auch die letzte Schülerin
                    // in die Schülerliste aufgenommen wird
                    schülernameListe.add(schülername);
                    // Die Plus-Liste wird der Schülerliste hinzugefügt
                    schülerListe.get(indexSchüler).setTeilen(schülernameListe.toArray(new String[0]));
                }
            }    
            // Das "-"-Zeichen markiert eine Zeile, in der die Namen
            // der Minus-Liste vorkommen; die Minus-Liste wird erstellt;
            // Derselbe Aufbau wie der vorherige Programmblock zur Erstellung
            // der Plus-Liste, nur das die Plus-Liste mit der Minus-Liste
            // ausgetauscht wurde
            else if(zeile.charAt(0) == '-')
            {
                if(zeile.length() > 2){
                    for(int indexBuchstabe = 2; indexBuchstabe < zeile.length(); indexBuchstabe++)
                    {
                        if(zeile.charAt(indexBuchstabe) == ' ')
                        {
                            schülernameListe.add(schülername);
                            schülername = "";
                            continue;
                        }
                        schülername += zeile.charAt(indexBuchstabe);
                    }
                    schülernameListe.add(schülername);
                    schülerListe.get(indexSchüler).setNichtTeilen(schülernameListe.toArray(new String[0]));
                }
            }
            // Fügt den Namen der Schülerin zur der Schülerliste hinzu
            else
            {
                schülerListe.get(indexSchüler).setName(zeile);
            }
        }
        // Die zimmerbelegung.txt - Datei wird geschlossen, da
        // sie nun nicht mehr benötigt wird
        br.close();
        // Das Schüler-Array wird hier erstellt;
        // Dazu wird der Inhalt der Schülerliste reinkopiert
        schüler = schülerListe.toArray(new Schüler[0]);  
    }
    // Das Schüler-Array wird so sortiert, dass zuerst die
    // Schülerinnen mit nicht-leeren Plus-Listen an erster Stelle
    // stehen
    public void schülerlisteSortieren()
    {
        // Eine Schülerin wird für den folgenden Tausch reinkopiert
        Schüler schülerkopie;
        for(int indexSchüler1=1; indexSchüler1<schüler.length; indexSchüler1++) {
            for(int indexSchüler2=0; indexSchüler2<schüler.length-indexSchüler1; indexSchüler2++) {
                // Wenn die nächste Schülerin eine nicht-leere Plus-Liste hat
                // und die vorherige eine leere, so tauschen sie
                // ihre Plätze im Array
                if(!schüler[indexSchüler2+1].istTeilenNull() && schüler[indexSchüler2].istTeilenNull()) {
                    schülerkopie=this.schüler[indexSchüler2];
                    this.schüler[indexSchüler2]=this.schüler[indexSchüler2+1];
                    this.schüler[indexSchüler2+1]=schülerkopie;
                }
            }
        }
    }
    
    // Die Zimmerbelegung wird ausgegeben
    public void zimmerbelegungAusgeben(String lösungsdatei)
    {
        // Vorbereitung des Writers, damit in die Lösungsdatei
        // geschrieben werden kann
        PrintWriter pWriter = null;
        try {
            // Die Lösungsdatei soll jetzt beschrieben werden
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(lösungsdatei)));
            for(int indexZimmer = 0; indexZimmer < zimmer.size(); indexZimmer++)
            {
                // Zimmernummer ausgeben
                pWriter.println("Zimmer " + Integer.toString(indexZimmer+1));
                for(int indexSchülerAusZimmer = 0; indexSchülerAusZimmer < zimmer.get(indexZimmer).size(); indexSchülerAusZimmer++)
                // Die Schülernamen werden durch ein Komma voneinander getrennt;
                {    
                    // Damit nicht ein zusätzliches Komma nach der letzten Schülerin steht
                    if(!(indexSchülerAusZimmer == zimmer.get(indexZimmer).size() - 1))
                        pWriter.print(zimmer.get(indexZimmer).get(indexSchülerAusZimmer) + ", ");
                    else
                        pWriter.print(zimmer.get(indexZimmer).get(indexSchülerAusZimmer));
                }
                // Die Zimmer werden durch ein Zeilenumbruch getrennt
                pWriter.println("");
            }
        } 
        // Falls das Schreiben in die Lösungsdatei nicht funktioniert,
        // wird eine Fehlermeldung ausgegeben
        catch (IOException ioe) {
            System.out.println("Die Zimmerbelegung kann nicht ausgegeben werden!");
        } 
        // Leeren des Streams und Schließen der Lösungsdatei
        finally {
            if (pWriter != null){
                pWriter.flush();
                pWriter.close();
            }
        } 
    }
    
    // Main-Methode
    public static void main(String[] args)
    {
        Zimmerbelegung belegung = new Zimmerbelegung();
        try{
            // Die Schülerliste wird erstellt
            belegung.setSchülerliste(args[0]);
        }
        // Falls die Datei nicht geöffnet werden kann, wird eine Fehlermeldung ausgegeben
        // und das Programm wird beendet
        catch(IOException e)
        {
            System.out.println("Datei konnte nicht geöffnet werden!");
            return;
        }
        // Die Schülerliste wird sortiert
        belegung.schülerlisteSortieren();
        // Die Zimmerbelegung wird erstellt und überprüft
        belegung.zimmerBelegen();
        String belegungNichtMöglich = belegung.zimmerbelegungÜberprüfen();    
        // Wenn die Zimmerbelegung nicht möglich ist, dann soll auch der
        // Grund der Unmöglichkeit ausgegeben werden
        if(!belegungNichtMöglich.equals(""))
            System.out.println("Es gibt einen Wunschkonflikt, weil die Wünsche von " + belegungNichtMöglich + " konfligieren!");
        else
        {
            // Ausgabe der Zimmerbelegung in der Lösungsdatei
            belegung.zimmerbelegungAusgeben(args[1]);
        }        
    }
}