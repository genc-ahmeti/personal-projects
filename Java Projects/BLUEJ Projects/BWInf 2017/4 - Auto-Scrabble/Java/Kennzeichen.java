
/**
 * Beschreiben Sie hier die Klasse Kennzeichen.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Kennzeichen
{
    private String kürzel;
    private String mittelteil;
    private int erkennungsnummer;
    public Kennzeichen()
    {
       this.kürzel = "";
       this.mittelteil = "";
       this.erkennungsnummer = 0;
    }

    public Kennzeichen(String kürzel, String mittelteil)
    {
       this.kürzel = kürzel;
       this.mittelteil = mittelteil;
this.erkennungsnummer = 0;
    }
    
    public void setKürzel(String kürzel)
    {
        this.kürzel = kürzel;
    }
    
    public String getKürzel()
    {
        return this.kürzel;
    }
    
    public void setMittelteil(String mittelteil)
    {
        this.mittelteil = mittelteil;
    }
    
    public String getMittelteil()
    {
        return this.mittelteil;
    }
    
    public void setErkennungsnummer()
    {
        if((getKürzel()+getMittelteil()).length() == 5)
        this.erkennungsnummer = (int) (Math.random()*999);
        else
        this.erkennungsnummer = (int) (Math.random()*9999);
    }
    
    public int getErkennungsnummer()
    {
        return this.erkennungsnummer;
    }
    
    public void kennzeichenAusgeben()
    {
        System.out.println(kürzel + "-" + mittelteil + " " + Integer.toString(erkennungsnummer));
    }
}
