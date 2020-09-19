package app.com.example.genc_ahmeti.aeg_app;

/**
 * Created by Genc_Ahmeti on 02.03.2018.
 */

import java.util.ArrayList;

public class MensaCreator {

    // System.out.println(hinweis.get(0).get(n));
    // System.out.println(arrList.get(n).get(i));

    private static ArrayList<ArrayList<String>> preisliste = new ArrayList<ArrayList<String>>();
    private static ArrayList<ArrayList<String>> menü = new ArrayList<ArrayList<String>>();
    private static ArrayList<ArrayList<String>> hinweis = new ArrayList<ArrayList<String>>();

    private static ArrayList<Integer> indexliste = new ArrayList<Integer>();
    private static ArrayList<ArrayList<String>> allergienliste = new ArrayList<ArrayList<String>>();

    private boolean keineAllergien = false;

    public static void setMenü() {

        ArrayList<ArrayList<String>> arrList = new ArrayList<ArrayList<String>>();
        boolean token = false;
        for(int n = 0; n < menü.get(0).size(); n++)
        //for(int i = 0; i < 2; i++)
        {
            String str = menü.get(0).get(n);

            if(str.length() < 3)
            {
                if(!token)
                    continue;
                else
                    arrList.get(arrList.size()-1).add(str);

            }

            else if(token || istWochentag(str.substring(0, 2)))
            {
                if(!token)
                    token = true;

                if(istWochentag(str.substring(0, 2)))
                {
                    arrList.add(new ArrayList<String>());
                    arrList.get(arrList.size()-1).add(str);
                }
                else
                    arrList.get(arrList.size()-1).add(str);
            }
        }
        menü = arrList;
    }

    public static void setIndexliste(String str) {
        indexliste = new ArrayList<Integer>();
        // erst die Indexe holen
        /*boolean token = true;
        for(int n = 0; n < str.length(); n++)
        {
            char temp = str.charAt(n);

            if(str.charAt(n) != '(' && str.charAt(n) != ')')
                continue;
            if(indexliste.size() % 2 == 1 && n>= 1 &&  (str.substring(n-1, n+1).equals("(R") || str.substring(n-2, n+1).equals("( R") || str.substring(n-1, n+1).equals("(S")))
            {
                token = true;
            }
            else if (indexliste.size() % 2  == 1 && n<=str.length()-2 && (str.substring(n, n+2).equals("(K") || str.substring(n).equals("K)")))
                continue;

            if(str.charAt(n) == '(')
            {
                indexliste.add(n);
            }

            if(str.charAt(n) == ')')
            {
                for(int stelle = n+1; stelle < str.length(); stelle++)
                {
                    if(str.charAt(stelle) == ')')
                    {
                        n = stelle;
                        indexliste.add(n);
                        break;
                    }
                    if(str.charAt(stelle) == '(')
                    {
                        indexliste.add(n);
                        break;
                    }
                }
                if(token)
                {
                    token = false;
                }
                else
            }
        }*/
        int stelle = str.indexOf('(');
        int stelleKlammer1;
        int stelleKlammer2;
        while (stelle != -1) {
            indexliste.add(stelle);
            stelleKlammer1 = str.indexOf(')', stelle + 1);
            if (stelleKlammer1 + 1 < str.length()) {
                stelleKlammer2 = str.indexOf(')', stelleKlammer1 + 1);
                if (stelleKlammer2 == -1) {
                    indexliste.add(stelleKlammer1);
                    break;
                }
                stelle = str.indexOf('(', stelle + 1);
                if (stelle == -1) {
                    indexliste.add(stelleKlammer2);
                    break;
                } else if (stelle > stelleKlammer2)
                    indexliste.add(stelleKlammer2);
                else
                    indexliste.add(stelleKlammer1);
            }
            else {
                indexliste.add(stelleKlammer1);
                break;
            }
        }
    }

    public static void setAllergienliste(String str) {

        allergienliste = new ArrayList<ArrayList<String>>();
        ArrayList<String> allergien = new ArrayList<String>();
        for(int n = 0; n < indexliste.size(); n+=2)
            allergien.add(str.substring(indexliste.get(n)+1, indexliste.get(n+1)));

        String temp = "";
        boolean schalter = false;
        for(int n = 0; n < allergien.size(); n++)
        {
            allergienliste.add(new ArrayList<String>());
            for(int i = 0; i < allergien.get(n).length(); i++)
            {
                if(i == allergien.get(n).length() -1)
                {
                    temp += allergien.get(n).charAt(i);
                    schalter = false;
                    allergienliste.get(n).add(temp);
                    temp = "";
                }
                else if(allergien.get(n).charAt(i) != ' ' && allergien.get(n).charAt(i) != ',' && allergien.get(n).charAt(i) != ')' && allergien.get(n).charAt(i) != '(')
                {
                    temp += allergien.get(n).charAt(i);
                    schalter = false;
                }
                else if(schalter)
                    continue;
                else
                {
                    allergienliste.get(n).add(temp);
                    schalter = true;
                    temp = "";
                }
            }
        }
    }

    public static boolean istWochentag(String str)
    {
        return str.equals("Mo") || str.equals("Di")
                || str.equals("Mi") || str.equals("Do")
                || str.equals("Fr");
    }

    public static String allergienAnzeigen()
    {
        String nachricht = "Hinweis: \n";
        String tempStr;

        // falls leer oder keine Allergien vorhanden
        if(indexliste.size() == 0)
            return "";

        // die Nachricht bilden
        for(int n = 0; n < allergienliste.size(); n++)
            for(int i = 0; i < allergienliste.get(n).size(); i++)
            {
                tempStr = allergienliste.get(n).get(i);
                ArrayList<ArrayList<String>> aaaaa = allergienliste;
                if(tempStr.equals("R") && !enthalten(nachricht, tempStr))
                {nachricht+="\t" + "- Rind" + " \n"; continue;}
                else if(tempStr.equals("S") && !enthalten(nachricht, tempStr))
                {nachricht+="\t" + "- Schwein" + " \n"; continue;}

                int das = tempStr.indexOf(' ');
                int index = hinweis.get(0).get(0).indexOf(" "+ tempStr +" ");
                String aaaa = " "+ tempStr +" ";

                if(index != -1)
                {
                    if(Integer.parseInt(tempStr) < 10 && !enthalten(nachricht, hinweis.get(0).get(0).substring(index+6, hinweis.get(0).get(0).indexOf("\"", index+6))))
                        nachricht += "\t" +"- " + hinweis.get(0).get(0).substring(index+6, hinweis.get(0).get(0).indexOf("\"", index+6)) + " \n";
                    else if (!enthalten(nachricht, hinweis.get(0).get(0).substring(index+7, hinweis.get(0).get(0).indexOf("\"", index+7))))
                        nachricht += "\t" +"- " + hinweis.get(0).get(0).substring(index+7, hinweis.get(0).get(0).indexOf("\"", index+7)) + " \n";
                    String b = hinweis.get(0).get(0).substring(index+5, hinweis.get(0).get(0).indexOf("\"", index+5));

                }
                else
                {
                    int index2 = hinweis.get(0).get(1).indexOf(" "+ tempStr +" ");
                    if(index2 != -1) {
                        for (int a = index2; a < hinweis.get(0).get(1).length(); a++) {
                            char bss = hinweis.get(0).get(1).charAt(a);
                            boolean asasw = hinweis.get(0).get(1).charAt(a) == ';';
                            if (hinweis.get(0).get(1).charAt(a) == ';') {
                                if (!enthalten(nachricht, hinweis.get(0).get(1).substring(index2 + 5, hinweis.get(0).get(1).indexOf(";", index2 + 5)))) {
                                    if (tempStr.length() == 2)
                                        nachricht += "\t" + "- " + hinweis.get(0).get(1).substring(index2 + 6, hinweis.get(0).get(1).indexOf(";", index2 + 6)) + " \n";
                                    else
                                        nachricht += "\t" + "- " + hinweis.get(0).get(1).substring(index2 + 5, hinweis.get(0).get(1).indexOf(";", index2 + 5)) + " \n";
                                }
                                break;
                            } else if (hinweis.get(0).get(1).charAt(a) == ',') {
                                if (!enthalten(nachricht, hinweis.get(0).get(1).substring(index2 + 6, hinweis.get(0).get(1).indexOf(",", index2 + 6)))) {
                                    String bb = nachricht;
                                    String bbb = hinweis.get(0).get(1).substring(index2 + 6, hinweis.get(0).get(1).indexOf(",", index2 + 6));
                                    boolean gt = !enthalten(nachricht, hinweis.get(0).get(1).substring(index2 + 6, hinweis.get(0).get(1).indexOf(",", index2 + 6)));
                                    String aaa = " " + hinweis.get(0).get(1).substring(index2 + 6, hinweis.get(0).get(1).indexOf(",", index2 + 6)) + " ";
                                    nachricht += "\t" + "- " + hinweis.get(0).get(1).substring(index2 + 6, hinweis.get(0).get(1).indexOf(",", index2 + 6)) + " \n";
                                }
                                break;
                            }
                        }
                    }
                }
            }
        return nachricht;
    }

    public static boolean enthalten(String str1, String str2)
    {
        int index = str1.indexOf(str2);
        int indexLeerzeichen;
        char c;
        while(index != -1)
        {
            indexLeerzeichen = index+str2.length();
            c = str1.charAt(indexLeerzeichen);
            if(c == ' ')
                return true;
            else
            {
                if(index +1 >= str1.length())
                    break;
                else
                    index = str1.indexOf(str2, index + 1);
            }
        }
        return false;
    }

    public static void setPreisliste(ArrayList<ArrayList<String>> arrList) {
        preisliste = arrList;
    }

    public static void setMenü(ArrayList<ArrayList<String>> arrList) {
        menü = arrList;
    }

    public static void setHinweis(ArrayList<ArrayList<String>> arrList) {
        hinweis = arrList;
    }

    public static ArrayList<ArrayList<String>> getHinweis() {
        return hinweis;
    }

    public static ArrayList<ArrayList<String>> getPreisliste() {
        return preisliste;
    }

    public static ArrayList<ArrayList<String>> getMenü() {
        return menü;
    }

    public static ArrayList<Integer> getIndexliste() {
        return indexliste;
    }

}
