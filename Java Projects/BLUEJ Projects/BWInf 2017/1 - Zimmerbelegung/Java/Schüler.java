/*
 * Die Klasse "Schüler" dient zur Beschreibung der Schülerinnen.
 * Daher sind nur Setter/Getter existent.
 */
public class Schüler
{
    // Eigenschaften einer Schülerin:
    // (Name, Plus-Liste, Minus-Liste, Zugeteilt-Sein Flag)
    private String name;
    private String[] teilen;
    private String[] nichtTeilen;
    private boolean zugeteilt;

    /*
     * Konstruktoren
     */

    public Schüler(String schülerName, String[] mitSchülerTeilen, String[] nichtMitSchülerTeilen)
    {
        // Instanzvariable initialisieren
        this.name = schülerName;
        this.teilen = mitSchülerTeilen;
        this.nichtTeilen = nichtMitSchülerTeilen;
        this.zugeteilt = false;
    }

    public Schüler()
    {
        // Instanzvariable initialisieren
        this.name = "";
        this.teilen = null;
        this.nichtTeilen = null;
        this.zugeteilt = false;
    }

    /*
     * Setter- und Getter-Methoden
     */

    // Gibt den Namen der Schülerin zurück
    public String getName()
    {
        return this.name;
    }

    // Legt den Namen der Schülerin fest
    public void setName(String name)
    {
        this.name = name;
    }

    // Gibt den Namen einer Schülerin der Plus-Liste zurück
    public String getTeilen(int stelle)
    {
        return this.teilen[stelle];
    }

    // Legt die Plus-Liste fest
    public void setTeilen(String[] teilen)
    {
        this.teilen = teilen;
    }

    // Gibt die Anzahl der Schülerinnen in der Plus-Liste zurück
    public int getTeilenGröße()
    {
        return this.teilen.length;
    }

    // Überprüft, ob die Plus-Liste leer ist
    public boolean istTeilenNull()
    {
        return this.teilen == null;
    }

    /*
     * Jetzt folgen dieselben Methoden, nur dass die Plus-Liste
     * mit der Minus-Liste ausgetauscht wurde
     */

    public String getNichtTeilen(int stelle)
    {
        return this.nichtTeilen[stelle];
    }

    public void setNichtTeilen(String[] nichtTeilen)
    {
        this.nichtTeilen = nichtTeilen;
    }

    public int getNichtTeilenGröße()
    {
        return this.nichtTeilen.length;
    }

    public boolean istNichtTeilenNull()
    {
        return this.nichtTeilen == null;
    }

    /*
     * Nun folgen Methoden, um den Zugeteilt-Sein-Flag
     * benutzen zu können.
     */

    // Die Schülerin wird einem Zimmer zugeteilt.
    void wirdZugeteilt()
    {
        this.zugeteilt = true;
    }
    // Überprüft, ob die Schülerin bereits einem Zimmer
    // zugeteilt wurde.
    public boolean istZugeteilt()
    {
        return this.zugeteilt;
    }
}
