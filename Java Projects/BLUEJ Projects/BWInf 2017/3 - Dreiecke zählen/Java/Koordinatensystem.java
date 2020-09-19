/*
 * Folgende Klasse dient zur Erstellung des Koordinatensystems.
 * Hier werden die Dreiecke gezählt und ausgegeben.
 */

/*
 * Klassen werden importiert zur Erweiterung der
 * Funktionalität meines Programms
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

// Rundungsfunktionen für die Berechnung von Koordinaten
import java.math.*;

public class Koordinatensystem 
{
    // Unterteilung des Koordinatensystems:
    // - Punkte; Schnittpunkte der Strecken; 
    //   Punkte, die auf Strecken liegen
    // - Strecken
    // - Dreiecke
    private ArrayList<Punkt> punkte;
    private ArrayList<Punkt> schnittpunkte;
    private ArrayList<Strecke> strecken;
    private ArrayList<ArrayList<Punkt>> dreiecke;
    private ArrayList<ArrayList<Punkt>> punkteAufStrecken;

    /*
     * Konstruktor
     */

    public Koordinatensystem()
    {
        // Instanzvariablen initialisieren
        this.punkte = new ArrayList<Punkt>();
        this.schnittpunkte = new ArrayList<Punkt>();
        this.strecken = new ArrayList<Strecke>();
        this.dreiecke = new ArrayList<ArrayList<Punkt>>();
        this.punkteAufStrecken = new ArrayList<ArrayList<Punkt>>();
    }

    // Gibt die Anzahl der Dreiecke im Koordinatensystem zurück.
    // Zusätzlich werden existierende Dreiecke in die Liste der
    // bestehenden Dreiecke im Koordinatensystem mitaufgenommen.
    public int anzahlDreiecke()
    {
        // Die Anzahl der Dreiecke wird in dieser Variable gespeichert.
        int anzahlDreiecke = 0;

        // Man vergleicht immer drei Punkte miteinander und schaut jeweils,
        // ob es von einem Punkt zum anderen eine Strecke gibt.
        // Falls ja, dann muss ein Dreieck existieren.
        for(int punkt1 = 0; punkt1 < punkte.size(); punkt1++)
            for(int punkt2 = 0; punkt2 < punkte.size(); punkt2++)
            {
                // Wenn zwei Punkte gleich sind, kann es sich nicht um ein
                // Dreieck handeln. Man überspringt diese Konstellation.
                if(punkte.get(punkt1).equals(punkte.get(punkt2)))
                    continue;
                for(int punkt3 = 0; punkt3 < punkte.size(); punkt3++)
                {
                    // Wenn jeweils zwei Punkte gleich sind, kann es sich nicht um ein
                    // Dreieck handeln. Man überspringt diese Konstellation.
                    if(punkte.get(punkt1).equals(punkte.get(punkt3)) || punkte.get(punkt2).equals(punkte.get(punkt3)))
                        continue;

                    // Wenn von jedem Punkt zum anderen eine Strecke existiert,
                    // dann existiert auch ein Dreieck, dass von diesen Punkten
                    // gebildet wird. Das Dreieck soll nur hinzugefügt werden,
                    // wenn es noch nicht bereits hinzugefügt wurde.
                    if(dreieckExistiert(punkte.get(punkt1),punkte.get(punkt2),punkte.get(punkt3)) && !dreieckBereitsHinzugefügt(punkte.get(punkt1),punkte.get(punkt2),punkte.get(punkt3)))
                    {
                        // Füge das Dreieck hinzu mit den drei Punkten.
                        dreiecke.add(new ArrayList<Punkt>());
                        dreiecke.get(anzahlDreiecke).add(punkte.get(punkt1));
                        dreiecke.get(anzahlDreiecke).add(punkte.get(punkt2));
                        dreiecke.get(anzahlDreiecke).add(punkte.get(punkt3));

                        // Da ein Dreieck existiert, wird die Anzahl um 1 erhöht.
                        anzahlDreiecke++;
                    }
                }
            }
        // Alle Punkt-Konstellationen wurden untersucht.
        // Jetzt wird die Anzahl der dabei gefundenen Dreiecke
        // zurückgegeben werden.
        return anzahlDreiecke;
    }

    // Gibt zurück, ob ein Dreieck bereits hinzugefügt wurde.
    public boolean dreieckBereitsHinzugefügt(Punkt p1, Punkt p2, Punkt p3)
    {
        for(int indexDreieck = 0; indexDreieck < dreiecke.size(); indexDreieck++)
            if(punktExistiertBereits(p1,dreiecke.get(indexDreieck)) && punktExistiertBereits(p2,dreiecke.get(indexDreieck)) && punktExistiertBereits(p3,dreiecke.get(indexDreieck)))
                return true;
        return false;
    }

    // Gibt zurück, ob ein Dreieck aus einer angegebenen
    // Punkte-Konstellation existiert. Es prüft also,
    // ob von jedem Punkt zum anderen eine Strecke existiert und
    // ob die Punkte kollinear sind.
    public boolean dreieckExistiert(Punkt p1, Punkt p2, Punkt p3)
    {
        return (streckeExistiertBereits(new Strecke(p1,p2)) && streckeExistiertBereits(new Strecke(p1,p3)) && streckeExistiertBereits(new Strecke(p2,p3)) && !sindPunkteKollinear(p1,p2,p3));
    }

    // Diese Methode erstellt eine Liste zu den Punkten, die
    // auf einer der gegebenen Strecken liegen.
    public void setPunkteAufStrecken()
    {
        // Die Koordinaten des Ortsvektor des ersten Punkts.
        double p1, p2;
        // Die Koordinaten des Richtungsvektors.
        double u1, u2;
        // Die Koordinaten des Ortsvektor des zweiten, zu
        // untersuchenden Punkts.
        double q1, q2;
        // Die Werte des Parameters der Geraden.
        // Außerdem die Differenz der beiden Werte.
        double t1, t2, diff;
        for(int indexStrecke = 0; indexStrecke < strecken.size(); indexStrecke++)
        {   
            // Eine Liste zu einer der gegebenen Strecken wird erstellt.
            // Die Liste ist nie leer, weil immer der Start- und Endpunkt
            // auf der Strecke liegt.
            punkteAufStrecken.add(new ArrayList<Punkt>());
            // Die Koordinaten des Ortsvektors werden zugewiesen.
            p1 = strecken.get(indexStrecke).getPunktVonStrecke(0).getX();
            p2 = strecken.get(indexStrecke).getPunktVonStrecke(0).getY();
            // Die Koordinaten des Richtungsvektors der Geraden mit
            // dem Aufpunkt (p1, p2) wird gebildet.
            u1 = strecken.get(indexStrecke).getPunktVonStrecke(1).getX() - p1;
            u2 = strecken.get(indexStrecke).getPunktVonStrecke(1).getY() - p2;
            for(int indexPunkt = 0; indexPunkt < punkte.size(); indexPunkt++)
            {
                // Die Koordinaten des Ortsvektors des zu untersuchenden Punkts
                // werden zugewiesen.
                q1 = punkte.get(indexPunkt).getX();
                q2 = punkte.get(indexPunkt).getY();
                // Spezialfall 1: Der erste Wert des Richtungsvektors ist 0.
                if(u1 == 0)
                {
                    t2 = (q2-p2)/u2;
                    if(p1==q1 && t2 >=-0.001 && t2<=1.001)
                    // Nur wenn die ersten beiden Werte der Ortsvektoren
                    // übereinstimmen und der zweite Wert des Parameters
                    // zwischen 0 und 1 mit einer maximalen Abweichung von 0,001 liegt
                    // (d.h. er liegt auf der Strecke), dann wird der Punkt, bestehend
                    // aus den Koordinaten des ersten Ortsvektors, hinzugefügt.
                        punkteAufStrecken.get(indexStrecke).add(new Punkt(q1,q2));
                }
                // Spezialfall 2: Der zweite Wert des Richtungsvektors ist 0.
                // (dasselbe wie bei Spezialfall 1, nur dass jeweils die zweiten
                // Werte der Vektoren zählen)
                else if(u2 == 0)
                {
                    t1 = (q1-p1)/u1;
                    if(p2==q2 && t1 >=-0.001 && t1<=1.001)
                        punkteAufStrecken.get(indexStrecke).add(new Punkt(q1,q2));
                }
                // Spezialfall 3: Der Richtungsvektor besitzt Koordinaten ungleich 0.
                else
                {
                    t1 = (q1-p1)/u1;
                    t2 = (q2-p2)/u2;
                    diff = t1 - t2;
                    if((diff <= 0.001 && diff >=0 && t1 >=0 || diff >= -0.001 && diff <=0) && t1 >=0 && t1<=1)
                    // Nur wenn die beiden Werte des Parameters gleich sind
                    // bzw. die Differenz beträgt null
                    // (hier: maximale Abweichung von 0,001), dann wird der
                    // Punkt hinzugefügt.
                        punkteAufStrecken.get(indexStrecke).add(new Punkt(q1,q2));
                }
            }
        }
    }

    // Die Strecken werden aus der Textdatei eingelesen.
    public void setStrecken(String datei) throws IOException
    {
        // dreiecke.txt wird geöffnet;
        // Textzeilen können eingelesen werden
        BufferedReader br = new BufferedReader(new FileReader(datei));
        // Inhalt der Textzeile aus der Datei
        String zeile;
        // Beschreibt die aktuelle Strecke, die hinzugefügt wird.
        int indexStrecke = 0;
        // Ein Schalter. Mit ihm wird entschieden, ob eine Abszisse
        // oder Ordinate hinzugefügt werden soll.
        int wertPunkt = 1;
        // Wird nachher immer um ein Zeichen erweitert, bis in der
        // Variablen eine Gleitkommazahl steht.
        String temp = "";
        // Die erste Zeile der Textdatei wird übersprungen. Dort
        // steht nur die Anzahl der Strecken, die jetzt nicht relevant
        // ist.
        br.readLine();
        // Die Abszisse und Ordinate des Punktes, der einer Strecke
        // zugeordnet wird.
        double x = 0,y = 0;
        // Solange das Zeilenende der Datei noch nicht erreicht wurde, soll eine Textzeile
        // eingelesen werden
        while( (zeile = br.readLine()) != null)
        {
            // Alle Zeichen der Textzeile werden durchlaufen.
            for(int indexZeichen = 0; indexZeichen < zeile.length(); indexZeichen++)
            {
                // Die Koordinaten werden durch ein Leerzeichen voneinander 
                // getrennt. Solange also das Leerzeichen nicht vorkommmt,
                // wird temp um das Zeichen erweitert.
                if(zeile.charAt(indexZeichen) != ' ')
                    temp += zeile.charAt(indexZeichen);
                // Alternierend soll die Zahl in temp als Abszisse oder Ordinate
                // gesehen werden (so sind die Zahlen in der Datei angeordnet).
                else
                {
                    if(wertPunkt == 1)
                    {
                        // Zuerst wird die Abszisse gebildet.
                        x= Double.parseDouble(temp);
                        // Der nächste Wert soll als Ordinate gesehen werden.
                        wertPunkt *= -1;
                    }

                    else if (wertPunkt != 1)
                    {
                        // Jetzt wird die Ordinate gebildet.
                        y=Double.parseDouble(temp);
                        // Der nächste Wert soll als Abszisse gesehen werden.
                        wertPunkt *= -1;
                        // Der Punkt wird zur Punkteliste nur dann hinzugefügt,
                        // wenn dieser nicht schon bereits in der Liste existiert.
                        if(!punktExistiertBereits(new Punkt(x,y)))
                            punkte.add(new Punkt(x,y));
                        // Der Startpunkt der Strecke wird hinzugefügt.
                        strecken.add(new Strecke());
                        strecken.get(indexStrecke).setStrecke(new Punkt(x,y),0);

                    }
                    // Neue Abszissen und Ordinaten können eingelesen werden.
                    temp = "";
                }
                // Wenn man das Ende der Textzeile erreicht hat,
                // dann stellt der neue Punkt den Endpunkt der Strecke
                // dar (aufgrund der Struktur der Datei).
                if(indexZeichen == zeile.length()-1)
                {
                    y=Double.parseDouble(temp);
                    wertPunkt *= -1;
                    if(!punktExistiertBereits(new Punkt(x,y)))
                        punkte.add(new Punkt(x,y));
                    // Der Endpunkt der Strecke wird hinzugefügt.
                    // Gleichzeitig geht man zur nächsten, zu erstellenden
                    // Strecke über.
                    strecken.get(indexStrecke++).setStrecke(new Punkt(x,y),1);
                    temp = "";
                }
            }
        }
    }

    // Überprüft, ob ein Punkt bereits in der Punkteliste existiert.
    public boolean punktExistiertBereits(Punkt p)
    {
        for(int indexPunkt = 0; indexPunkt < punkte.size(); indexPunkt++)
            if(punkte.get(indexPunkt).equals(p))
                return true;
        return false;
    }

    // Überprüft, ob ein Punkt bereits in einer beliebigen Punkteliste existiert.
    // Diese Methode wird benutzt, um die Überprüfung auf die Menge der Schnittpunkte
    // und auf die Menge der Punkte auf Strecken zu übertragen.
    public boolean punktExistiertBereits(Punkt p, ArrayList<Punkt> punkteMenge)
    {
        for(int indexPunkt = 0; indexPunkt < punkteMenge.size(); indexPunkt++)
            if(punkteMenge.get(indexPunkt).equals(p))
                return true;
        return false;
    }

    // Überprüft, ob eine Strecke bereits in der Streckenliste existiert.
    public boolean streckeExistiertBereits(Strecke s)
    {
        for(int indexStrecke = 0; indexStrecke < strecken.size(); indexStrecke++)
            if(strecken.get(indexStrecke).equals(s))
                return true;
        return false;
    }

    // Überprüft, ob drei beliebige Punkte kollinear sind.
    // Dafür wird überprüft, ob die Punkte alle in einer der Listen
    // der Punkte auf einer Strecke zu finden sind.
    public boolean sindPunkteKollinear(Punkt p1, Punkt p2,Punkt p3)
    {
        for(int indexPunktAufStrecke = 0; indexPunktAufStrecke < punkteAufStrecken.size(); indexPunktAufStrecke++)
            if(punktExistiertBereits(p1, punkteAufStrecken.get(indexPunktAufStrecke)) && punktExistiertBereits(p2, punkteAufStrecken.get(indexPunktAufStrecke)) && punktExistiertBereits(p3, punkteAufStrecken.get(indexPunktAufStrecke)))
                return true;
        return false;
    }

    // Diese Methode dient dazu, Strecken der Streckenliste hinzuzufügen,
    // wobei der Startpunkt der Schnittpunkt selber und der Endpunkt jeweils ein Punkt auf der Strecke ist.
    // Der Schnittpunkte muss auf mindestens zwei Strecken liegen, sonst wäre
    // er kein Schnittpunkt zweier oder mehrerer Strecken.
    public void schnittpunkteStreckenHinzufügen()
    {
        // Ein Schalter. Zeigt an, ob ein Schnittpunkt auf einer Strecke liegt.
        boolean punktIstDa;
        for(int indexSchnittpunkt = 0; indexSchnittpunkt < schnittpunkte.size(); indexSchnittpunkt++)
        {
            for(int indexPunktAufStrecke = 0; indexPunktAufStrecke < punkteAufStrecken.size(); indexPunktAufStrecke++)
            {
                // Der Schnittpunkt wurde noch nicht auf einer Strecke gefunden.
                punktIstDa = false;
                for(int indexPunkt1 = 0; indexPunkt1 < punkteAufStrecken.get(indexPunktAufStrecke).size(); indexPunkt1++)
                {
                    // Es soll nur dann die Überprüfung gestartet werden, wenn der 
                    // Schnittpunkt noch nicht auf einer Strecke gefunden wurde.
                    if(!punktIstDa)
                        for(int indexPunkt2 = 0; indexPunkt2 < punkteAufStrecken.get(indexPunktAufStrecke).size(); indexPunkt2++)
                        // Überpüfung, ob der Schnittpunkt auf der aktuell benutzten Strecke
                        // liegt.
                            if(punkteAufStrecken.get(indexPunktAufStrecke).get(indexPunkt2).equals(schnittpunkte.get(indexSchnittpunkt)))
                            {
                                // Liegt der Schnittpunkt auf der aktuellen Strecke,
                                // so kann die Untersuchung gestoppt werden.
                                punktIstDa = true;
                                break;
                            }
                    // Wenn Start- und Endpunkt gleich sind, dann ergibt sich so
                    // keine Strecke. Diese Konstellation wird übersprungen.
                    if(punkteAufStrecken.get(indexPunktAufStrecke).get(indexPunkt1).equals(schnittpunkte.get(indexSchnittpunkt)))
                        continue;
                    // Wenn punktIstDa true ist, dann muss jede Strecke von dem Schnittpunkt aus
                    // zu einem anderen Punkt auf der Strecke gebildet werden, falls sie nicht
                    // bereits schon existiert.
                    if(punktIstDa)
                    {
                        if(!streckeExistiertBereits(new Strecke(schnittpunkte.get(indexSchnittpunkt), punkteAufStrecken.get(indexPunktAufStrecke).get(indexPunkt1))))
                            strecken.add(new Strecke(schnittpunkte.get(indexSchnittpunkt), punkteAufStrecken.get(indexPunktAufStrecke).get(indexPunkt1)));
                    }
                    // Wenn der Schnittpunkt nicht auf der aktuellen Strecke liegt, geht man zur
                    // nächsten Strecke über.
                    else
                        break;
                }
            }
        }
    }

    // Man fügt der Punkte- und Schnittpunkteliste alle Schnittpunkte der
    // Strecken hinzu.
    public void schnittpunkteHinzufügen()
    {
        // Gerade 1:
        // Ortsvektor
        double p1,p2;
        // Richtungsvektor
        double u1,u2;

        // Gerade 2:
        // Ortsvektor
        double r1,r2;
        // Richtungsvektor
        double v1,v2;

        // Zwei Statusflags: Sie zeigen an, ob die ersten/zweiten Werte
        // der beiden Richtungsvektoren null sind.
        boolean u1Nv1N, u2Nv2N;
        // Abszisse und Ordinate.
        double x,y;
        // Die Werte der Parameter der Geraden, an welchen sich der Schnittpunkt
        // der beiden Geraden befindet.
        double schnittstelle1, schnittstelle2;
        for(int indexStrecke1 = 0; indexStrecke1 < strecken.size(); indexStrecke1++)
        {
            // Abszisse und Ordinate des Aufpunkts der 1. Gerade
            p1 = strecken.get(indexStrecke1).getPunktVonStrecke(0).getX();
            p2 = strecken.get(indexStrecke1).getPunktVonStrecke(0).getY();
            // Der erste und zweite Wert des Richtungsvektors der 1. Gerade
            u1 = strecken.get(indexStrecke1).getPunktVonStrecke(1).getX() - p1;
            u2 = strecken.get(indexStrecke1).getPunktVonStrecke(1).getY() - p2;
            for(int indexStrecke2 = 0; indexStrecke2 < strecken.size(); indexStrecke2++)
            {
                // Wenn die Geraden identisch sind, soll diese Konstellation 
                // übersprungen werden.
                if(indexStrecke1 == indexStrecke2)
                    continue;
                // Abszisse und Ordinate des Aufpunkts der 2. Gerade
                r1 = strecken.get(indexStrecke2).getPunktVonStrecke(0).getX();
                r2 = strecken.get(indexStrecke2).getPunktVonStrecke(0).getY();
                // Der erste und zweite Wert des Richtungsvektors der 2. Gerade
                v1 = strecken.get(indexStrecke2).getPunktVonStrecke(1).getX() - r1;
                v2 = strecken.get(indexStrecke2).getPunktVonStrecke(1).getY() - r2;
                // Überprüfen, ob die ersten/zweiten Werte der beiden 
                // Richtungsvektoren null sind.
                u1Nv1N = ((u1 == 0) && (v1 == 0));
                u2Nv2N = ((u2 == 0) && (v2 == 0));
                // Überprüfung der Parallelität: (lineare Abhängigkeit der
                // Richtungsvektoren)
                // 1. Fall: Ist jeweils der erste/zweite Wert der beiden Richtungsvektoren
                // null, so müssen die beiden Geraden parallel sein.
                if(u1Nv1N || u2Nv2N)
                    continue;
                // 2. Fall (generelle Parallelität): Die Division der entsprechenden Werte
                // der Richtungsvektoren ergibt einen konstanten Wert.
                // Wenn der erste oder zweite Wert des zweiten Richtungsvektoren null ist,
                // sind die Geraden aufgrund der vorherigen if-Abfrage nie parallel.
                if(v1 != 0 && v2 != 0)
                {
                    if(u1/v1 == u2/v2)
                        continue;
                }
                // Die Werte der Parameter werden entsprechend berechnet.
                schnittstelle1 = (u2*(r1-p1)-u1*(r2-p2))/(u1*v2-v1*u2);
                schnittstelle2 = (v2*(r1-p1)-v1*(r2-p2))/(u1*v2-v1*u2);
                // Die Abszisse und Ordinate werden berechnet. Sie werden der
                // Genauigkeit halber erst nach sechs Nachkommastellen gerundet.
                x = BigDecimal.valueOf(r1+schnittstelle1*v1).setScale(6, RoundingMode.HALF_UP).doubleValue();
                y = BigDecimal.valueOf(r2+schnittstelle1*v2).setScale(6, RoundingMode.HALF_UP).doubleValue();
                // Der Schnittpunkt muss auf beiden Strecken liegen.
                if((!(schnittstelle1 >= 0 && schnittstelle1 <= 1) || (!(schnittstelle2 >= 0 && schnittstelle2 <= 1))))
                    continue;
                // Der Schnittpunkt wird, falls noch nicht vorhanden, der Punkte- und
                // Schnittpunkteliste hinzugefügt.
                if(!punktExistiertBereits(new Punkt(x,y)))
                    punkte.add(new Punkt(x,y)); 
                if(!punktExistiertBereits(new Punkt(x,y), schnittpunkte))
                    schnittpunkte.add(new Punkt(x,y));
            }
        }
    }

    // Die Dreiecke werden in die Lösungsdatei reingeschrieben.
    public void dreieckeAusgeben(String lösungsdatei)
    {
        // Vorbereitung des Writers, damit in die Lösungsdatei
        // geschrieben werden kann
        PrintWriter pWriter = null;
        try 
        {
            // Die Lösungsdatei soll jetzt beschrieben werden
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter(lösungsdatei)));
            // Die Antwort auf das Problem.
            pWriter.println("Es gibt " + Integer.toString(anzahlDreiecke()) + " Dreiecke.");
            for(int indexDreieck = 0; indexDreieck < dreiecke.size(); indexDreieck++)
            // Für jedes Dreieck werden die drei jeweiligen Eckpunkte ausgegeben.
            // Gerundet wird bei der Ausgabe auf zwei Nachkommastellen.
                pWriter.println(Integer.toString(indexDreieck + 1)+ ". Dreieck: (" 
                    + Double.toString(BigDecimal.valueOf(dreiecke.get(indexDreieck).get(0).getX()).setScale(2, RoundingMode.HALF_UP).doubleValue()) 
                    + "|"    + Double.toString(BigDecimal.valueOf(dreiecke.get(indexDreieck).get(0).getY()).setScale(2, RoundingMode.HALF_UP).doubleValue())
                    + "), (" + Double.toString(BigDecimal.valueOf(dreiecke.get(indexDreieck).get(1).getX()).setScale(2, RoundingMode.HALF_UP).doubleValue())
                    + "|"    + Double.toString(BigDecimal.valueOf(dreiecke.get(indexDreieck).get(1).getY()).setScale(2, RoundingMode.HALF_UP).doubleValue())
                    + "), (" + Double.toString(BigDecimal.valueOf(dreiecke.get(indexDreieck).get(2).getX()).setScale(2, RoundingMode.HALF_UP).doubleValue())
                    + "|"    + Double.toString(BigDecimal.valueOf(dreiecke.get(indexDreieck).get(2).getY()).setScale(2, RoundingMode.HALF_UP).doubleValue())
                    +")");
        } 
        // Falls das Schreiben in die Lösungsdatei nicht funktioniert,
        // wird eine Fehlermeldung ausgegeben
        catch (IOException e) {
            System.out.println("Die Dreiecke können nicht ausgegeben werden!");
        } 
        // Leeren des Streams und Schließen der Lösungsdatei
        finally {
            if (pWriter != null){
                pWriter.flush();
                pWriter.close();
            }
        } 
    }

    public static void main(String[] args)
    {
        try
        {
            Koordinatensystem koordinatensystem = new Koordinatensystem();
            // Die Strecken aus der Datei werden der Streckenliste hinzugefügt.
            koordinatensystem.setStrecken("C:/Users/Genc Ahmeti/Documents/Desktop/BWInf 2017/3 - Dreiecke zählen/Textdateien + Lösung/dreiecke1.txt");
            // Die Schnittpunkte werden berechnet.
            koordinatensystem.schnittpunkteHinzufügen();
            // Die Punkte auf jeweils einer Strecken werden in einer Liste
            // zusammengefasst.
            koordinatensystem.setPunkteAufStrecken();
            // Die Strecken, die von den Schnittpunkten ausgehen, werden hinzugefügt.
            koordinatensystem.schnittpunkteStreckenHinzufügen();
            // Die Lösung der Aufgabe wird ausgegeben.
            koordinatensystem.dreieckeAusgeben("C:/Users/Genc Ahmeti/Documents/Desktop/BWInf 2017/3 - Dreiecke zählen/Textdateien + Lösung/dreieckeLösung.txt");
        }
        catch(IOException e)
        {
            System.out.println("Es ist ein Fehler beim Öffnen der Datei aufgetreten!");
        }
    }
}