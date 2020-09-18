import java.util.Scanner;

/*
 * import java.util.Scanner;
 * Mit Scanner Eingabe machen:
 * int a;
 * Scanner scanner = new Scanner (System.in);
 * a = Scanner.nextInt();
 * System.out.println(a);
 * 
 */

public class Übungen
{
    public static int A1(int zahl)
    {
        return zahl >= 0 ? zahl : -zahl;
    }
    
    public static String A2(int zahl)
    {
        return zahl % 2 == 0 ? "Zahl ist gerade." : "Zahl ist ungerade.";
    }
    
    public static String A3(int geburtsjahr)
    {
        return (geburtsjahr < 2000 && geburtsjahr > 1989) ? "90er-Kind." : "Kein 90er-Kind.";
    }
    
    public static String A4(int ziffer)
    {    
        return ziffer+"";
    }
    
    
    public static void lol()
    {
        String str = (String) 123;
        System.out.println(str);
    }
    
  /*   public static String A5(int jahreszahl)
    {
    }*/
    
    public static double A6()
    {
        int mittelwert = 0;
        for(int i = 1; i <= 100; i++)
        {
            mittelwert += i/100;
        }
        return mittelwert;
    }
    
     public static double A7()
    {
        int summe = 0;
        for(int i = 1; i <= 100; i++)
        {
            if (i%2==0)
              summe += i;
        }
        return summe;
    }
    
    public static int A8(int i, int n)
    {
        int summe = 0;
        for(; i <= n; i++)
        {
              summe += i;
        }
        return summe;
    }
    
     public static int A9()
    {
        int maximum = 0;
        Scanner scanner = new Scanner (System.in);
        int nächsteZahl = scanner.nextInt();
        while(nächsteZahl >= 0)
        {
            maximum = nächsteZahl > maximum ? nächsteZahl : maximum;
            nächsteZahl = scanner.nextInt();
        }
        return maximum;
    }
    
  /*  public static int A11(int a, int b)
    {
        int produkt = 0;
        for(int i = 0; i < b; i++)
        {
            maximum = nächsteZahl > maximum ? nächsteZahl : maximum;
            nächsteZahl = scanner.nextInt();
        }
        return maximum;
    }*/
    
    // Quersumme einer Zahl, Produkt zweier Int, indem b-mal auf a addiert wird
    
    public static void main(String[] args)
    {
        System.out.println(A1(3));
    }
}
