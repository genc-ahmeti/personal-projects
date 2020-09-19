import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Test
{
    
         
            private static ArrayList<ArrayList<String>> hinweis = new ArrayList<ArrayList<String>>();
    public static void main()
    {
        try{
            String url1 = "http://www.aeg.rt.bw.schule.de/aktuelles/ansprechpartner/";
            String url2 = "https://www.insiva-gmbh.de/catering/speiseplaene/schulen/albert-einstein-gymnasium-reutlingen/";
   ArrayList<ArrayList<String>> preisliste = new ArrayList<ArrayList<String>>();
            ArrayList<ArrayList<String>> menü = new ArrayList<ArrayList<String>>();
            //Verbindung mit Website von AEG Vertretungsplan: Die Seite wird als Dokument (HTML Datei)
            //in doc gespeichert
            Document doc = Jsoup.connect(url1).userAgent("Mozilla").get();
            Document doc2 = Jsoup.connect(url2).userAgent("Mozilla").get();
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
                switch(counter)
                {
                    case 0:
                    preisliste.add(new ArrayList<String>(0));
                    preisliste.get(preisliste.size()-1).add(elmTagTd.text());
                    counter++;
                    break;
                    case 1:
                    preisliste.get(preisliste.size()-1).add(elmTagTd.text());
                    counter--;
                    break;          
                }

            menü.add(new ArrayList<String>(0));
            for (Element elmTagTd : elmsTagTd2)
            {
                menü.get(0).add(elmTagTd.text());
            }

            hinweis.add(new ArrayList<String>(0));
            for (Element elmTagTd : elmsTagTd3)
            {
                hinweis.get(0).add(elmTagTd.text());
            }

            for(int n = 0; n < preisliste.size(); n++)
            for(int i = 0; i < 2; i++)
            System.out.println(preisliste.get(n).get(i));
            ArrayList<ArrayList<String>> arrList = new ArrayList<ArrayList<String>>();
            boolean token = false;
            for(int n = 0; n < menü.get(0).size(); n++)
            //for(int i = 0; i < 2; i++)
            {    
                String str = menü.get(0).get(n);
                if(str.length() == 0)
                continue;
                if(!token && !istWochentag(str.substring(0, 2)))
                    continue;
                else
                {
                    if(!token)
                    {
                        token = true;
                    }
                    if(istWochentag(str.substring(0, 2)))
                    {
                        arrList.add(new ArrayList<String>());
                        arrList.get(arrList.size()-1).add(str);
                    }
                    else
                        arrList.get(arrList.size()-1).add(str);

                }

                
            }
                    
            for(int n = 0; n < hinweis.get(0).size(); n++){
            //for(int i = 0; i < 2; i++)
                System.out.println(hinweis.get(0).get(n));
      //          System.out.println(menü.get(0).get(n));
            }
            
            for(int n = 0; n< arrList.size(); n++)
            for(int i = 0; i < arrList.get(n).size(); i++)
            System.out.println(arrList.get(n).get(i));

     System.out.println(allergienAnzeigen("Rindergulasch (R ) Ca ,Cc) mit Paprikawürfel und Nudeln (15, A, Ca) dazu kleiner Salat"));
        }
        catch(IOException e)
        {
            String a = "";
            e.printStackTrace();
        }
    }

    public static boolean istWochentag(String str)
    {
        return str.equals("Mo") || str.equals("Di") 
        || str.equals("Mi") || str.equals("Do") 
        || str.equals("Fr");
    }
    
    public static String allergienAnzeigen(String str)
    {
        ArrayList<Integer> index = new ArrayList<Integer>();
        
        // erst die Indexe holen
        boolean token = false;
        for(int n = 0; n < str.length(); n++)
        {
            if(str.charAt(n) != '(' && str.charAt(n) != ')')
                continue;
            if(str.charAt(n) == '(' && (str.substring(n, n+2).equals("(R") || str.substring(n, n+2).equals("(S")))
            {
                index.add(n);
                token = true;
            }
            else if(str.charAt(n) == '(')
                index.add(n);
            if(str.charAt(n) == ')')
            {
                if(token)
                {
                    token = false;
                }
                else
                index.add(n);
            }
        }
        
        ArrayList<String> allergien = new ArrayList<String>();
        for(int n = 0; n < index.size(); n+=2)
        {    
        String a = str.substring(index.get(n), index.get(n+1));
            allergien.add(str.substring(index.get(n)+1, index.get(n+1)));
        
    }
        ArrayList<ArrayList<String>> allergienListe = new ArrayList<ArrayList<String>>();
            String temp = "";
            boolean schalter = false;
        for(int n = 0; n < allergien.size(); n++)
        {
        allergienListe.add(new ArrayList<String>());
            for(int i = 0; i < allergien.get(n).length(); i++)
        {
            if(i == allergien.get(n).length() -1)
            {
                temp += allergien.get(n).charAt(i);
            schalter = false;
            allergienListe.get(n).add(temp);
                temp = "";
            }
            else if(allergien.get(n).charAt(i) != ' ' && allergien.get(n).charAt(i) != ',' && allergien.get(n).charAt(i) != ')')
            {    
            temp += allergien.get(n).charAt(i);
            schalter = false;
        }
            else if(schalter)
                continue;
            else 
            {
                allergienListe.get(n).add(temp);
                schalter = true;
                temp = "";
            }
        }
    }
            String nachricht = "enthält: \n";
            String tempStr;
            
    // die Nachricht bilden
    for(int n = 0; n < allergienListe.size(); n++)
    for(int i = 0; i < allergienListe.get(n).size(); i++)
    {
        tempStr = allergienListe.get(n).get(i);
        if(tempStr.equals("R") && !enthalten(nachricht, tempStr))
        {nachricht+="\t" + "- Rind" + "\n";
            continue;}
        else if(tempStr.equals("S") && !enthalten(nachricht, tempStr))
        {nachricht+="\t" + "- Schwein" + "\n";continue;}
        int das = tempStr.indexOf(' ');
        int index2 = hinweis.get(0).get(0).indexOf(" "+ tempStr +" ");
        String aaaa = " "+ tempStr +" ";
        
        if(index2 != -1)
        {
            if(Integer.parseInt(tempStr) < 10 && !enthalten(nachricht, hinweis.get(0).get(0).substring(index2+6, hinweis.get(0).get(0).indexOf("\"", index2+6))))
            nachricht += "\t" +"- " + hinweis.get(0).get(0).substring(index2+6, hinweis.get(0).get(0).indexOf("\"", index2+6)) + " \n";
            else if (!enthalten(nachricht, hinweis.get(0).get(0).substring(index2+7, hinweis.get(0).get(0).indexOf("\"", index2+7))))
            nachricht += "\t" +"- " + hinweis.get(0).get(0).substring(index2+7, hinweis.get(0).get(0).indexOf("\"", index2+7)) + " \n";
            String b = hinweis.get(0).get(0).substring(index2+5, hinweis.get(0).get(0).indexOf("\"", index2+5));
            
        }
        else
        {
            
            
            index2 = hinweis.get(0).get(1).indexOf(" "+ tempStr +" ");
            for(int a = index2; a < hinweis.get(0).get(1).length();a++)
            {
                char bss = hinweis.get(0).get(1).charAt(a);
                boolean asasw = hinweis.get(0).get(1).charAt(a) == ';';
                if(hinweis.get(0).get(1).charAt(a) == ';')
                {    
              if( !enthalten(nachricht, hinweis.get(0).get(1).substring(index2+5, hinweis.get(0).get(1).indexOf(";", index2+5))))
              {
                    if(tempStr.length() == 2)
               nachricht += "\t" + "- " +  hinweis.get(0).get(1).substring(index2+6, hinweis.get(0).get(1).indexOf(";", index2+6)) + " \n";
               else
                    nachricht += "\t" + "- " +  hinweis.get(0).get(1).substring(index2+5, hinweis.get(0).get(1).indexOf(";", index2+5)) + " \n";
                    }
               break;
            }
                else if(hinweis.get(0).get(1).charAt(a) == ',')
                {
                    if(!enthalten(nachricht, hinweis.get(0).get(1).substring(index2+6, hinweis.get(0).get(1).indexOf(",", index2+6))))
                    {
                    String bb = nachricht;
                    String bbb = hinweis.get(0).get(1).substring(index2+6, hinweis.get(0).get(1).indexOf(",", index2+6));
       boolean gt = !enthalten(nachricht, hinweis.get(0).get(1).substring(index2+6, hinweis.get(0).get(1).indexOf(",", index2+6)));
                    String aaa = " "+ hinweis.get(0).get(1).substring(index2+6, hinweis.get(0).get(1).indexOf(",", index2+6)) +" ";
                    nachricht += "\t" + "- " + hinweis.get(0).get(1).substring(index2+6, hinweis.get(0).get(1).indexOf(",", index2+6))+ " \n";
                }
                    break;
                }
        }
    }
}
    
    
        String a = "";
        return nachricht;
    }
    public static boolean enthalten(String str1, String str2)
    {return str1.indexOf(str2) != -1;}
}
