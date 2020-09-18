import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.io.File;
import java.util.ArrayList; 
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.lang.Character;
import java.io.FileNotFoundException;
public class StundenpläneKursstufe
{
    private static ArrayList<String> stundenplanAusDatei;

    private static ArrayList<String> schülernamen;
    private static ArrayList<String[][]> stundenpläne;

    public static void parseHtml(String file) throws IOException
    {
        stundenplanAusDatei = new ArrayList<String>();

        File input = new File(file);
        int counter = 0;
        //   try
        //     {
        Document doc = Jsoup.parse(input, "UTF-8");

        String temp;
        //Suche nach dem body-BLock (dort stehen die Infos zu Vertretungen)
        Element body = doc.body();
        //Suche nach den Elementen mit dem Tag <td> im body-blick (in diesen stehen die eigentlichen Vertretungen)
        Elements elmsTagTd = body.getElementsByTag("td");
        for (Element elmTagTd : elmsTagTd)
        {
            temp = elmTagTd.text();
            if(counter < 2)
                stundenplanAusDatei.add(elmTagTd.text());
            else if(counter < 8)

            {
                counter++;
                continue;
            }
            else
            { 
                if(temp.length() != 0 && Character.isDigit(elmTagTd.text().charAt(0)))
                    continue;
                stundenplanAusDatei.add(elmTagTd.text());
            }
            counter++;
        }

        //        }catch(IOException e){e.printStackTrace();}
    }

    public static String getSchülername()
    {
        String[] arr = stundenplanAusDatei.get(0).split(" ");
        return arr[2] + " " + arr[1].substring(0, arr[1].length()-1);
    }

    public static String[][] getStundenplan()
    {
        String[][] stundenplan = new String[5][11];
        int counterc = 2;

        for(int stunde = 0; stunde < 11; stunde++)
        {
            if(stundenplanAusDatei.get(counterc).length() != 0)
                stundenplan[0][stunde] = stundenplanAusDatei.get(counterc).split(" ")[0] + " (" + stundenplanAusDatei.get(counterc++).split(" ")[2] + ")";
            else
                stundenplan[0][stunde] = stundenplanAusDatei.get(counterc++);            
            if(stundenplanAusDatei.get(counterc).length() != 0)
                stundenplan[1][stunde] = stundenplanAusDatei.get(counterc).split(" ")[0] + " (" + stundenplanAusDatei.get(counterc++).split(" ")[2] + ")";
            else
                stundenplan[1][stunde] = stundenplanAusDatei.get(counterc++);
            if(stundenplanAusDatei.get(counterc).length() != 0)
                stundenplan[2][stunde] = stundenplanAusDatei.get(counterc).split(" ")[0] + " (" + stundenplanAusDatei.get(counterc++).split(" ")[2] + ")";
            else
                stundenplan[2][stunde] = stundenplanAusDatei.get(counterc++);
            if(stundenplanAusDatei.get(counterc).length() != 0)
                stundenplan[3][stunde] = stundenplanAusDatei.get(counterc).split(" ")[0] + " (" + stundenplanAusDatei.get(counterc++).split(" ")[2] + ")";
            else
                stundenplan[3][stunde] = stundenplanAusDatei.get(counterc++);
            if(stundenplanAusDatei.get(counterc).length() != 0)
                stundenplan[4][stunde] = stundenplanAusDatei.get(counterc).split(" ")[0] + " (" + stundenplanAusDatei.get(counterc++).split(" ")[2] + ")";
            else
                stundenplan[4][stunde] = stundenplanAusDatei.get(counterc++);
        }

        return stundenplan;
    }

    public static void getSchülerpläne()
    {
        String schülernamenStr = "ArrayList<String> schülernamen = new ArrayList<String>(Arrays.asList(";
        for(int n = 0; n < schülernamen.size()-1; n++)
        {
            schülernamenStr += "\"" + schülernamen.get(n) + "\", ";
        }
        schülernamenStr += "\"" + schülernamen.get(schülernamen.size()-1) + "\"));";

        String stundenpläneStr = "String[][][] stundenpläne = {";
        for(int listeIndex = 0; listeIndex < stundenpläne.size(); listeIndex++)
        {
            stundenpläneStr += "{";
            for(int wochentag = 0; wochentag < 5; wochentag++)
            {
                stundenpläneStr += "{";
                for(int stunde = 0; stunde < 10; stunde++)
                {
                    stundenpläneStr += "\""+ stundenpläne.get(listeIndex)[wochentag][stunde] +"\", ";
                }
                if(wochentag != 4)
                    stundenpläneStr += "\""+ stundenpläne.get(listeIndex)[wochentag][10] +"\"}, ";
                else
                    stundenpläneStr += "\""+ stundenpläne.get(listeIndex)[4][10] +"\"}";
            }
            if(listeIndex != stundenpläne.size()-1)
                stundenpläneStr += "}, ";
            else
                stundenpläneStr += "}};";
        }

        System.out.println(schülernamenStr + "\n" + stundenpläneStr);
    }

    public static void main()
    {
        String dateiname = "C:/Users/Genc Ahmeti/Documents/Desktop/Kursstufe/";

        // DAS MUSS MAN JEDES MAL ÄNDERN; JE NACH DATEIEN UND SCHÜLER
        int schülerID = 2018002;
        int letzterSchüler = 2018108;
        // DAS MUSS MAN JEDES MAL ÄNDERN; JE NACH DATEIEN UND SCHÜLER

        String url;

        schülernamen = new ArrayList<String>();
        stundenpläne = new ArrayList<String[][]>();

        for(; schülerID < letzterSchüler+1 ; schülerID++)
        {
            url = dateiname + Integer.toString(schülerID) + ".htm";
            try
            {
                parseHtml(url);
                schülernamen.add(getSchülername());
                stundenpläne.add(getStundenplan());
            }catch(IOException e){}
        }
        System.out.println("Done.");
        getSchülerpläne();
    }
}
