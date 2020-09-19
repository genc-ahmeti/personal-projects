/*
 * Beschreibung einer Strecke, die aus zwei Punkten besteht, im Koordinatensystem. 
 */

public class Strecke
{
    // Zwei Punkte definieren eine Strecke
    private Punkt[] punkte;

    /*
     * Konstruktoren
     */

    public Strecke()
    {
        // Instanzvariable initialisieren
        this.punkte = new Punkt[2];
    }

    public Strecke(Punkt start, Punkt ende)
    {
        // Instanzvariable initialisieren
        this.punkte = new Punkt[2];
        this.punkte[0] = start;
        this.punkte[1] = ende;
    }

    // Vergleicht zwei Strecken und gibt zurück, ob sie gleich sind.
    public boolean equals(Strecke s)
    {
        // Zwei Strecken sind dann gleich, wenn der Startpunkt/Endpunkt der 
        // einen Strecke mit dem Startpunkt/Endpunkt der anderen oder
        // wenn der Startpunkt mit dem Endpunkt/der Endpunkt mit dem Startpunkt
        // der anderen Strecke übereinstimmt.
        return ((this.punkte[0].equals(s.punkte[0]) 
                && this.punkte[1].equals(s.punkte[1])) 
            || (this.punkte[0].equals(s.punkte[1]) 
                && this.punkte[1].equals(s.punkte[0])));
    }

    /*
     * Setter und Getter
     */

    // Gibt die beiden Punkte als Array zurück.
    public Punkt[] getStrecke()
    {
        return this.punkte;
    }

    // Legt die Punkte der Strecke fest
    public void setStrecke(Punkt start, Punkt ende)
    {
        punkte[0] = start;
        punkte[1] = ende;
    }

    // Legt einen Punkt der Strecke fest.
    // Entweder der erste (pos = 0) oder der zweite (pos = 1)
    public void setStrecke(Punkt p, int pos)
    {
        this.punkte[pos] = p;
    }

    // Gibt einen Punkt von der Strecke zurück.
    // Entweder der erste (pos = 0) oder der zweite (pos = 1)
    public Punkt getPunktVonStrecke(int pos)
    {
        return this.punkte[pos];
    }
}