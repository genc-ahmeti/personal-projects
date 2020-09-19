import java.util.*;
import java.io.*;

public class AutoScrabble
{
    private ArrayList<Kennzeichen> kennzeichen;
    private ArrayList<String> kürzel;

    private String kürzeldatei;
    public AutoScrabble()
    {
        kennzeichen = new ArrayList<Kennzeichen>();
        kürzel = new ArrayList<String>();
        kürzeldatei = "C:/Users/Genc Ahmeti/Documents/Desktop/BWInf 2017/4 - Auto-Scrabble/Textdateien/kuerzelliste.txt";
    }

    public void setKürzelliste() throws IOException
    {
        FileReader fr = new FileReader(kürzeldatei);
        BufferedReader br = new BufferedReader(fr);
        String zeile = "";

        while( (zeile = br.readLine()) != null)
            kürzel.add(new String(zeile));
    }

    public boolean existiertAlsKürzel(String wort)
    {
        for(int n = 0; n< kürzel.size(); n++)   
            if(kürzel.get(n).equals(wort))
                return true;
        return false;
    }

    public boolean istWortAusKennzeichenMöglich(String wort, int stelle)
    {
        if(wort.length() == 0 || wort.length() == 1)
            return false;
        else if(wort.length() == 2)
        {
            if(existiertAlsKürzel(String.valueOf(wort.charAt(0))))
            {
                kennzeichen.add(new Kennzeichen(String.valueOf(wort.charAt(0)), String.valueOf(wort.charAt(1))));
                return true;
            }
            else
                return false;
        }
        else if (wort.length() == 3)
        {
            if(existiertAlsKürzel(wort.substring(0,2)))
            { kennzeichen.add(new Kennzeichen(wort.substring(0,2), String.valueOf(wort.charAt(2))));
                return true;
            }
            else if(existiertAlsKürzel(String.valueOf(wort.charAt(0))))
            { kennzeichen.add(new Kennzeichen(String.valueOf(wort.charAt(0)), wort.substring(1)));
                return true;
            }
            else
                return false;
        }
        else if (wort.length() == 4)
        {
            if(existiertAlsKürzel(wort.substring(0,3)))
            { kennzeichen.add(new Kennzeichen(wort.substring(0,3), String.valueOf(wort.charAt(3))));
                return true;
            }
            else if(existiertAlsKürzel(wort.substring(0,2)))
            { kennzeichen.add(new Kennzeichen(wort.substring(0,2), wort.substring(2)));
                return true;
            }
            else
                return false;
        }
        else
        {
            String kürzelTemp;
            String mittelteilTemp;
            for(int i = 2; i >= 0; i--)
            {
                if(stelle+i >= wort.length())
                    continue;
                kürzelTemp = wort.substring(stelle,stelle+i+1); 
                if(existiertAlsKürzel(kürzelTemp))
                {
                    for(int n = 1; n >= 0;n--)
                    {
                        if(stelle+i+n+1 >= wort.length())
                            continue;
                       mittelteilTemp = wort.substring(stelle+i+1,stelle+i+n+2);
                      // "\u00c4": Ä, "\u00d6": Ö, "\u00d6": Ü
                       if(mittelteilTemp.contains("\u00c4")|| mittelteilTemp.contains("\u00d6") || mittelteilTemp.contains("\u00dc"))
                         continue;
                         if(stelle+i+n+2 == wort.length() || istWortAusKennzeichenMöglich(wort, stelle+i+n+2))
                           {
                           kennzeichen.add(new Kennzeichen(kürzelTemp, mittelteilTemp));
                           return true;
                           }
                    }
                    }
                }
            return false;
        }
            }
        
        public static void main(String[] args)
        {
        AutoScrabble a = new AutoScrabble();
        try
        {
            a.setKürzelliste();
        } 
        catch(IOException e)
        {
            System.out.println("kürzelliste.txt konnte nicht geöffnet werden!");
            return;
        }
        if(a.istWortAusKennzeichenMöglich("DONAUDAMPFSCHIFFFAHRTSKAPITÄNSMÜTZE",0))
        //ACHTUNG!!! KENNZEICHEN FALSCH HERUM EINGETRAGEN! SIEHE OBEN    
        for(int kennzeichenIndex = a.kennzeichen.size()-1; kennzeichenIndex >= 0; kennzeichenIndex--)
                {
                    a.kennzeichen.get(kennzeichenIndex).setErkennungsnummer();
                    a.kennzeichen.get(kennzeichenIndex).kennzeichenAusgeben();
                }
        else
        {
            System.out.println("Es konnte keine Kennzeichenkombination gefunden werden!");
        }

    }
}
