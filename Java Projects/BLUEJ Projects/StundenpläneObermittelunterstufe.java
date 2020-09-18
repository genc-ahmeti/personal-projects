import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.lang.Character;
import java.lang.Math;

public class StundenpläneObermittelunterstufe
{
    public  String[] besondereRäume = {"klet", "tech"};
    public  int[][] stundenverteilung = new int[11][5];

    public  ArrayList<Integer> indezesFürAussortieren2;
    public  ArrayList<Boolean> istAoderBfürAussortieren1;

    public  String[][] stundenplan = new String[5][11];

    public  String[][][] stundenplan5 = new String[5][5][11];
    public  String[][][] stundenplan6 = new String[5][5][11];
    public  String[][][] stundenplan7 = new String[4][5][11];
    public  String[][][] stundenplan8 = new String[5][5][11];
    public  String[][][] stundenplan9 = new String[5][5][11];
    public  String[][][] stundenplan10 = new String[5][5][11];

    
    // ------------------------------------------------ Alle notwendigen Schritte, um aus dem Stundenplan ein Array zu erstellen ------------------------------------------------
    
    //Schritt 1: Text aus PDF extrahieren
    public  ArrayList<String> textExtrahieren(String pfad)
    {
        String text;
        try
        {
            //Loading an existing document
            File file = new File(pfad);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper;
            //Instantiate PDFTextStripper class
            pdfStripper = new PDFTextStripper();
            //Retrieving text from PDF document
            text = pdfStripper.getText(document);

            //Closing the document
            document.close();

        }
        catch(IOException e)
        {
            e.printStackTrace();
            return null;
        }

        PrintWriter pWriter = null;
        try {
            pWriter = new PrintWriter(new BufferedWriter(new FileWriter("test.txt")));
            pWriter.println(text);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (pWriter != null){
                pWriter.flush();
                pWriter.close();
            }
        }

        ArrayList<String> ergebnis = new ArrayList<String>(); 
        FileReader fr = null;
        try{
            fr = new FileReader("test.txt");

            BufferedReader br = new BufferedReader(fr);
            String zeile = "";
            while( (zeile = br.readLine()) != null )
            {
                ergebnis.add(zeile);
            }
            br.close();
        }catch(IOException e)
        {e.printStackTrace();}

        return ergebnis;
    }

    // Schritt 2: nur fächer sollen stehen
    public  ArrayList<String> fächerAussortieren(ArrayList<String> arrList)
    {
        boolean schalter = false;
        ArrayList<String> temp = new ArrayList<String>();
        for(int n = 1; n < arrList.size()-2; n++)
        {
            if(!schalter && arrList.get(n).contains("-"))
            {
                schalter = true;
            }
            if(schalter)
            {
                String currentItem = arrList.get(n);
                if(!currentItem.contains(":") && !currentItem.contains("-"))
                    temp.add(currentItem);
            }
        }
        return temp;
    }

    // Schritt 3: in 5, 6 klasse steht manchmal zweimal fach mit zwei unterschiedlichen lehrern
    // aber gleichem raum
    public  ArrayList<String> fächerdopplungenAussortieren2(ArrayList<String> arrList)
    {
        setIndezesFürAussortieren2(arrList);
        ArrayList<String> temp = new ArrayList<String>();
        String aa, bb, cc, dd, ee;
        for(int n = 0; n < arrList.size(); n++)
        {
            String currentItem = arrList.get(n);
            boolean a, b;

            if(indezesFürAussortieren2Vorhanden(n))
            {
                a = raumVorhanden(arrList.get(n+2)) && !raumVorhanden(arrList.get(n+5));
                b = !raumVorhanden(arrList.get(n+2)) && raumVorhanden(arrList.get(n+4));
                if(a)
                {
                    temp.add(arrList.get(n));
                    temp.add(arrList.get(++n));
                    temp.add(arrList.get(++n));
                    n = n + 2;
                }
                else if(b)
                {
                    temp.add(arrList.get(n));
                    temp.add(arrList.get(++n));
                    n = n + 3;  
                    temp.add(arrList.get(n));
                }
                else
                {
                    temp.add(arrList.get(n) + " " + arrList.get(n+3));
                    temp.add(arrList.get(n+1) + " " + arrList.get(n+4));
                    temp.add(arrList.get(n+2) + " " + arrList.get(n+5));
                    n = n +5;
                }
            }
            else
            {
                temp.add(arrList.get(n));
                temp.add(arrList.get(++n));
                temp.add(arrList.get(++n));
            }
        }
        return temp;
    }

    
    // schritt 4: bei manchen fächerkombis (ethik, religion) steht zuerst ethik,
    // dann religion, das soll man zusammenführen
    public  ArrayList<String> fächerdopplungenAussortieren1(ArrayList<String> arrList)
    {
        setIndezesFürAussortieren1(arrList);
        ArrayList<String> temp = new ArrayList<String>();
        int index;
        for(int n = 0; n < arrList.size(); n++)
        {
            if(n+3 >= arrList.size())
            {
                temp.add(arrList.get(n));
                temp.add(arrList.get(n+1));
                temp.add(arrList.get(n+2));
                break;
            }
            String current = arrList.get(n);
            String item = arrList.get(n+3);

            index = getIndezesFürAussortieren2Index(n+3);
            if(index != -1 && !istAoderBfürAussortieren1.get(index))
            {
                temp.add(arrList.get(n));
                temp.add(arrList.get(++n));
                temp.add(arrList.get(++n));
                continue;
            }

            if(item.contains(" "))
            {
                temp.add(current + " " + item);
                temp.add(arrList.get(n+1) + " " + arrList.get(n+4));
                temp.add(arrList.get(n+2) + " " + arrList.get(n+5));
                n = n + 5;
            }
            else
            {
                temp.add(arrList.get(n));
                temp.add(arrList.get(++n));
                temp.add(arrList.get(++n));
            }
        }
        return temp;
    }
    
    // Schritt 5: Lehrer aussortieren
    public  ArrayList<String> lehrerAussortieren(ArrayList<String> arrList)
    {
        ArrayList<String> temp = new ArrayList<String>();
        for(int n = 0; n < arrList.size(); n++)
            if(Math.pow(-1, n%3) == 1)
                temp.add(arrList.get(n));
        return temp;
    }

    // Schritt 6: Letzte Formatierungen
    public  ArrayList<String> formatieren(ArrayList<String> arrList)
    {
        ArrayList<String> temp = new ArrayList<String>();
        String[] fächer, räume;
        String str = "";
        for(int n = 0; n < arrList.size(); n = n+2)
        {
            if(!arrList.get(n).contains(" "))
                temp.add(arrList.get(n) + " (" + arrList.get(n+1) + ")");
            else
            {
                fächer = arrList.get(n).split(" ");
                räume = arrList.get(n+1).split(" ");
                for(int i = 0; i < fächer.length-1; i++)
                    str += fächer[i] + " (" + räume[i] + "), ";
                str += fächer[fächer.length-1] + " (" + räume[fächer.length-1] + ")";
                temp.add(str);
                str = "";
            }
        }   
        return temp;
    }

    // Letzter Schritt: Stundenplan erstellen
    public  void setStundenplan(ArrayList<String> arrList)
    {
        ArrayList<String> temp = new ArrayList<String>();
        int counter = 0;
        for(int n = 0; n < 11; n++)
        {
            if(stundenverteilung[n][0] != 0)
                stundenplan[0][n] = arrList.get(counter++);
            else
                stundenplan[0][n] = "";
            if(stundenverteilung[n][1] != 0)
                stundenplan[1][n] = arrList.get(counter++);
            else
                stundenplan[1][n] = "";
            if(stundenverteilung[n][2] != 0)
                stundenplan[2][n] = arrList.get(counter++);
            else
                stundenplan[2][n] = "";
            if(stundenverteilung[n][3] != 0)
                stundenplan[3][n] = arrList.get(counter++);
            else
                stundenplan[3][n] = "";
            if(stundenverteilung[n][4] != 0)
                stundenplan[4][n] = arrList.get(counter++);
            else
                stundenplan[4][n] = "";
        }
    }

    // -------------------------------------------------------------- Hilfsfunktionen für die Array-Erstellung ---------------------------------------------
    
    // schaut, ob bei der stelle 'index' im Stundenplan tatsächlich zwei Fächer stehen
    public  boolean indezesFürAussortieren2Vorhanden(int index)
    {
        for(int n = 0; n < indezesFürAussortieren2.size(); n++)
            if(indezesFürAussortieren2.get(n) == index)
                return true;
        return false;
    }

    // füllt die liste auf mit stellen, an denen im stundenplan zwei fächer stehen;
    // markiert auch, an welchen stellen zwei fächer mit zwei räumen und lehrern stehen oder zwei fächer mit
    // zwei lehreren, aber mit einem raum
    // Hinweis: da sich die liste immer ändert (dopplungen werden rausgeschmissen), müssen
    // die indezes immer neu berechnet werden
    public  void setIndezesFürAussortieren2(ArrayList<String> arrList)
    {
        indezesFürAussortieren2 = new ArrayList<Integer>();
        istAoderBfürAussortieren1 = new ArrayList<Boolean>();
        int counter = 0;
        boolean a, b;
        for(int n = 0; n < 11; n++)
            for(int i = 0; i < 5; i++)
            {
                if(stundenverteilung[n][i] == 0)
                    continue;
                else if(stundenverteilung[n][i] == 1)
                    counter = counter + 3;
                else if(stundenverteilung[n][i] == 2)
                {
                    indezesFürAussortieren2.add(counter);
                    a = raumVorhanden(arrList.get(counter+2)) && !raumVorhanden(arrList.get(counter+5));
                    b = !raumVorhanden(arrList.get(counter+2)) && raumVorhanden(arrList.get(counter+4));
                    if(a || b)
                    {
                        counter = counter + 5;
                        istAoderBfürAussortieren1.add(true);
                    }
                    else 
                    {
                        counter = counter + 6;
                        istAoderBfürAussortieren1.add(false);
                    }
                }
                else
                    counter = counter + 6;
            }
    }
    
    // schaut, ob und wann eine stelle in der liste der vorherigen indezes vorkommt
    public  int getIndezesFürAussortieren2Index(int stelle)
    {
        for(int n = 0; n < indezesFürAussortieren2.size(); n++)
            if(indezesFürAussortieren2.get(n) == stelle)
                return n;
        return -1;
    }

    // sucht die stellen, an denen im stundenplan zwei fächer vorkommen
    // Hinweis: da sich die liste immer ändert (dopplungen werden rausgeschmissen), müssen
    // die indezes immer neu berechnet werden
    public  void setIndezesFürAussortieren1(ArrayList<String> arrList)
    {
        indezesFürAussortieren2 = new ArrayList<Integer>();
        int counter = 0;
        boolean a, b;
        for(int n = 0; n < 11; n++)
            for(int i = 0; i < 5; i++)
            {
                if(stundenverteilung[n][i] == 0)
                    continue;
                else if(stundenverteilung[n][i] == 1)
                    counter = counter + 3;
                else if(stundenverteilung[n][i] == 2)
                {
                    indezesFürAussortieren2.add(counter);
                    counter = counter + 3;
                }
                else
                    counter = counter + 6;
            }
    }
    
    // schaut, ob der angegebene raum zu den besonderen Räumen gehört
    public  boolean istEinBesondererRaum(String raum)
    {
        int zähler = 0;
        while(zähler < besondereRäume.length)
        {
            if(besondereRäume[zähler++].equals(raum))
                return true;
        }
        return false;
    }

    // schaut, ob der string ein raum ist oder ein fach oder ein lehrer
    public  boolean raumVorhanden(String raum)
    {
        int stelle = 0;

        if(istEinBesondererRaum(raum))
            return true;

        while(stelle < raum.length())
        {
            if(Character.isDigit(raum.charAt(stelle++)))
                return true;
        }

        return false;
    }

    // damit stellt man fest, in welchen stunden ein schüler wie viele fcäher unterricht hat
    // ist wichtig, weil man sonst nicht herausfindet kann, welches fach man
    // zu einer bestimmten zeit nur mit pdfbox und dem stundenplan
    // hier benutze ich PDFLayoutTextStripper, nicht PDFTextStripper
    public  void setStundenverteilung(String pfad)
    {
        String text = textMitLayoutExtrahieren(pfad);

        int[] index = new int [11];
        for(int n = 0; n < 11; n++)
        {
            if(n == 0)
                index[n] = text.indexOf("-", 300);
            else
                index[n] = text.indexOf("-", index[n-1]+1);
        }

        String[] str = new String[11];
        str[0] = text.substring(text.indexOf("07:40"), index[0]);
        str[1] = text.substring(text.indexOf("08:30"), index[1]);
        str[2] = text.substring(text.indexOf("09:35"), index[2]);
        str[3] = text.substring(text.indexOf("10:25"), index[3]);
        str[4] = text.substring(text.indexOf("11:20"), index[4]);
        str[5] = text.substring(text.indexOf("12:10"), index[5]);
        str[6] = text.substring(text.indexOf("13:05"), index[6]);
        str[7] = text.substring(text.indexOf("13:55"), index[7]);
        str[8] = text.substring(text.indexOf("14:45"), index[8]);
        str[9] = text.substring(text.indexOf("15:35"), index[9]);
        str[10] = text.substring(text.indexOf("16:25"), index[10]);

        String aa, bb, cc, dd, ee;
        for(int n = 0; n < 11; n++)
        {
            aa = str[n].substring(7, 32);
            bb = str[n].substring(32, 57);
            cc = str[n].substring(57, 82);
            dd = str[n].substring(82, 106);
            ee = str[n].substring(106, 131);
            
            stundenverteilung[n][0] = getAnzahlFächer(aa);
            stundenverteilung[n][1] = getAnzahlFächer(bb);
            stundenverteilung[n][2] = getAnzahlFächer(cc);
            stundenverteilung[n][3] = getAnzahlFächer(dd);
            stundenverteilung[n][4] = getAnzahlFächer(ee);
        }
    }

    // schaut, wie viele fächer in einer beliebigen stunde an verschieden wochentagen vorkommen bei der vorherigen funktion
    public  int getAnzahlFächer(String text)
    {
        int zähler = 0;
        if(text.charAt(0) != ' ')
            zähler++;
        boolean a;
        for(int n = 1; n < text.length(); n++)
        {
            a = text.charAt(n) != ' ' && text.charAt(n-1) == ' ';
            if(a)
                zähler++;
        }

        return zähler;
    }

    // das extrahihert den text mit layout, damit kann man herausfinden, wie viele fächer in einer stunde
    // vorkommen
    public  String textMitLayoutExtrahieren(String pfad)
    {
        try
        {
            //Loading an existing document
            File file = new File(pfad);
            PDDocument document = PDDocument.load(file);
            PDFTextStripper pdfStripper;
            //Instantiate PDFLayoutTextStripper class
            pdfStripper = new PDFLayoutTextStripper(); 

            //Retrieving text from PDF document
            String text = pdfStripper.getText(document);

            //Closing the document
            document.close();

            return text;
        }
        catch(IOException e)
        {
            e.printStackTrace();

            return null;
        }
    }

    // ----------------------------------------------------------- Quellcode-Erstellung für die App -------------------------------------------------------------------------------
    
    // die stundenpläne für alle klassen werden kreiert
    public  void alleStundenpläneInitialisieren()
    {
        String anfangVomDateinamen = "C:/Users/Genc Ahmeti/Documents/Desktop/Neuer Ordner/";
        String mitteVomDateinamen;
        String endeVomDateinamen = "_2017_2018.pdf";
        String dateipfad;

        for(int klasse = 5; klasse < 11; klasse++)
        {
            
            for(int stufe = 0; stufe < 5; stufe++)
            {
                mitteVomDateinamen = Integer.toString(klasse);
                
                // bei der siebten Klasse gibts kein 7e
                if(klasse == 7 && stufe == 4)
                    break;

                switch(stufe)
                {
                    case 0:
                    mitteVomDateinamen += "a";
                    break;
                    case 1:
                    mitteVomDateinamen += "b";
                    break;
                    case 2:
                    mitteVomDateinamen += "c";
                    break;
                    case 3:
                    mitteVomDateinamen += "d";
                    break;
                    case 4:
                    mitteVomDateinamen += "e";
                    break;
                }
                dateipfad = anfangVomDateinamen + mitteVomDateinamen + endeVomDateinamen; 
                setStundenverteilung(dateipfad);
                setStundenplan(formatieren(lehrerAussortieren(fächerdopplungenAussortieren1(fächerdopplungenAussortieren2(fächerAussortieren(textExtrahieren(dateipfad)))))));
                for(int wochentag = 0; wochentag < 5; wochentag++)
                    for(int stunde = 0; stunde < 11; stunde++)
                        {
                            switch(klasse)
                            {
                                case 5:
                                    stundenplan5[stufe][wochentag][stunde] = stundenplan[wochentag][stunde];
                                    break;
                                case 6:
                                    stundenplan6[stufe][wochentag][stunde] = stundenplan[wochentag][stunde];
                                    break;
                                case 7:
                                    stundenplan7[stufe][wochentag][stunde] = stundenplan[wochentag][stunde];
                                    break;
                                case 8:
                                    stundenplan8[stufe][wochentag][stunde] = stundenplan[wochentag][stunde];
                                    break;
                                case 9:
                                    stundenplan9[stufe][wochentag][stunde] = stundenplan[wochentag][stunde];
                                    break;
                                case 10:
                                    stundenplan10[stufe][wochentag][stunde] = stundenplan[wochentag][stunde];
                                    break;
                        }
                        }
            }
        }
    }

    // der quellcode für die App wird kreiert, das heißt die arrays mit ihrern inhalten werden als 
    // quellcode aufgeschrieben, lesbar für den compiler!
    public void codeFürAppKreieren()
    {
        // Klasse 5
        String klasse5 = "String[][][] stundenplan5 = {";
        for(int stufe = 0; stufe < 5; stufe++)
        {
            klasse5 += " {";
            for(int wochentag = 0; wochentag < 5; wochentag++)
            {
                klasse5 += " {";
                for(int stunde = 0; stunde < 10; stunde++)
                {
                    klasse5 += "\"" + stundenplan5[stufe][wochentag][stunde] + "\"" + ", ";
                }
                klasse5 += "\""+ stundenplan5[stufe][wochentag][10] + "\"";
                if(wochentag != 4)
                    klasse5 += "},";
                else
                    klasse5 += "}";
            }
            if(stufe != 4)
                    klasse5 += "},";
                else
                    klasse5 += "}";
        }
        klasse5 += "};";

        // Klasse 6
        String klasse6 = "String[][][] stundenplan6 = {";
        for(int stufe = 0; stufe < 5; stufe++)
        {
            klasse6 += " {";
            for(int wochentag = 0; wochentag < 5; wochentag++)
            {
                klasse6 += " {";
                for(int stunde = 0; stunde < 10; stunde++)
                {
                    klasse6 += "\""+ stundenplan6[stufe][wochentag][stunde] + "\"" + ", ";
                }
                klasse6 += "\"" + stundenplan6[stufe][wochentag][10]+ "\"";
                if(wochentag != 4)
                    klasse6 += "},";
                else
                    klasse6 += "}";
            }
            if(stufe != 4)
                    klasse6 += "},";
                else
                    klasse6 += "}";
        }
        klasse6 += "};";

        // Klasse 7
        String klasse7 = "String[][][] stundenplan7 = {";
        for(int stufe = 0; stufe < 5; stufe++)
        {
            // es gibt kein 7e, also muss man das überspringen
            if(stufe == 4)
                break;
                
            klasse7 += " {";
            for(int wochentag = 0; wochentag < 5; wochentag++)
            {
                klasse7 += " {";
                for(int stunde = 0; stunde < 10; stunde++)
                {
                    klasse7 += "\"" + stundenplan7[stufe][wochentag][stunde] + "\"" + ", ";
                }
                klasse7 += "\"" + stundenplan7[stufe][wochentag][10] + "\"";
                if(wochentag != 4)
                    klasse7 += "},";
                else
                    klasse7 += "}";
            }
            if(stufe != 4)
                    klasse7 += "},";
                else
                    klasse7 += "}";
        }
        klasse7 += "};";

        // Klasse 8
        String klasse8 = "String[][][] stundenplan8 = {";
        for(int stufe = 0; stufe < 5; stufe++)
        {
            klasse8 += " {";
            for(int wochentag = 0; wochentag < 5; wochentag++)
            {
                klasse8 += " {";
                for(int stunde = 0; stunde < 10; stunde++)
                {
                    klasse8 += "\"" + stundenplan8[stufe][wochentag][stunde] + "\"" + ", ";
                }
                klasse8 += "\"" + stundenplan8[stufe][wochentag][10] + "\"";
                if(wochentag != 4)
                    klasse8 += "},";
                else
                    klasse8 += "}";
            }
            if(stufe != 4)
                    klasse8 += "},";
                else
                    klasse8 += "}";
        }
        klasse8 += "};";

        // Klasse 9
        String klasse9 = "String[][][] stundenplan9 = {";
        for(int stufe = 0; stufe < 5; stufe++)
        {
            klasse9 += " {";
            for(int wochentag = 0; wochentag < 5; wochentag++)
            {
                klasse9 += " {";
                for(int stunde = 0; stunde < 10; stunde++)
                {
                    klasse9 += "\"" + stundenplan9[stufe][wochentag][stunde] + "\"" + ", ";
                }
                klasse9 += "\"" + stundenplan9[stufe][wochentag][10] + "\"";
                if(wochentag != 4)
                    klasse9 += "},";
                else
                    klasse9 += "}";
            }
            if(stufe != 4)
                    klasse9 += "},";
                else
                    klasse9 += "}";
        }
        klasse9 += "};";

        // Klasse 10
        String klasse10 = "String[][][] stundenplan10 = {";
        for(int stufe = 0; stufe < 5; stufe++)
        {
            klasse10 += " {";
            for(int wochentag = 0; wochentag < 5; wochentag++)
            {
                klasse10 += " {";
                for(int stunde = 0; stunde < 10; stunde++)
                {
                    klasse10 +="\"" + stundenplan10[stufe][wochentag][stunde] + "\"" + ", ";
                }
                klasse10 += "\"" + stundenplan10[stufe][wochentag][10] + "\"";
                if(wochentag != 4)
                    klasse10 += "},";
                else
                    klasse10 += "}";
            }
            if(stufe != 4)
                    klasse10 += "},";
                else
                    klasse10 += "}";
        }
        klasse10 += "};";

        System.out.println(klasse5);
        System.out.println(klasse6);
        System.out.println(klasse7);
        System.out.println(klasse8);
        System.out.println(klasse9);
        System.out.println(klasse10);
    }

    public static void main()
    {
        /*String anfangVomDateinamen = "C:/Users/Genc Ahmeti/Documents/Desktop/Neuer Ordner/";
        String mitteVomDateinamen = "5a";
        String endeVomDateinamen = "_2017_2018.pdf";
        String dateipfad;
        dateipfad = anfangVomDateinamen + mitteVomDateinamen + endeVomDateinamen; 
        
        setStundenverteilung(dateipfad);
        
        ArrayList<String> a = textExtrahieren(dateipfad);
        ArrayList<String> b = fächerAussortieren(a);
        ArrayList<String> c = fächerdopplungenAussortieren2(b);
        ArrayList<String> d =fächerdopplungenAussortieren1(c);
        ArrayList<String> e = lehrerAussortieren(d);
        ArrayList<String> f = formatieren(e);
        setStundenplan(f);*/
        
        StundenpläneObermittelunterstufe aeg = new StundenpläneObermittelunterstufe(); 
        
        aeg.alleStundenpläneInitialisieren();
        aeg.codeFürAppKreieren();
        
        /*for(int n = 0; n < a.size(); n++)
            System.out.println(a.get(n));
        System.out.println("--------------------------------------");
        for(int n = 0; n < b.size(); n++)
            System.out.println(b.get(n));
        System.out.println("--------------------------------------");
        for(int n = 0; n < c.size(); n++)
            System.out.println(c.get(n));
        System.out.println("--------------------------------------");
        for(int n = 0; n < d.size(); n++)
            System.out.println(d.get(n));    
        System.out.println("--------------------------------------");
        for(int n = 0; n < f.size(); n++)
            System.out.println(f.get(n));
        System.out.println("--------------------------------------");
        for(int n = 0; n < 11; n++)
        {
            for(int i = 0; i < 4; i++)
                System.out.print(stundenplan[i][n] + " <---> ");    
            System.out.println(stundenplan[4][n] + " >---< ");
        }*/
    }
}