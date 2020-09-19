/*
 * Beschreibung eines Punktes im Koordinatensystem 
 */

public class Punkt
{
    // Koordinaten eines Punktes, bestehend aus der Abszisse und
    // Ordinate.
    private double x;
    private double y;

    /*
     * Konstruktoren
     */

    public Punkt()
    {
        // Instanzvariable initialisieren
        this.x = 0.0;
        this.y = 0.0;
    }

    public Punkt(double abszisse, double ordinate)
    {
        // Instanzvariable initialisieren
        this.x = abszisse;
        this.y = ordinate;
    }

    // Vergleicht zwei Punkte und überprüft, ob diese
    // gleich sind.
    public boolean equals(Punkt p)
    {
        return (this.x == p.x && this.y == p.y);
    }

    /*
     * Setter und Getter
     */

    // Legt die Abszisse des Punktes fest.
    public void setX(double xWert)
    {
        // tragen Sie hier den Code ein
        this.x = xWert;
    }

    // Gibt die Abszisse des Punktes zurück.
    public double getX()
    {
        // tragen Sie hier den Code ein
        return this.x;
    }

    // Legt die Ordinate des Punktes fest.
    public void setY(double yWert)
    {
        // tragen Sie hier den Code ein
        this.y = yWert;
    }

    // Gibt die Ordinate des Punktes zurück.
    public double getY()
    {
        // tragen Sie hier den Code ein
        return this.y;
    }
}