
import java.util.Random;

public class Test
{
    private static String abc = "abcdefghijklmnopqrstuvwxyzöäü"; 
    
    public static int[] StringToIndex(String tasten)
    {
        int länge = tasten.length();
        int[] erg = new int[länge];
        for(int i = 0; i <länge; i++)
            erg[i] = abc.indexOf(tasten.charAt(i));
            
        return erg;
    }
    
    public static String[] splitString(int anzahlBuchstaben, String tasten)
    {
        String temp = "";
        String tasten2 = tasten.substring(0,tasten.length() - (tasten.length() % anzahlBuchstaben));
        
        for(int i = 0; i < tasten2.length(); i++)
        {
            temp += tasten2.charAt(i);
            if(i % anzahlBuchstaben == (anzahlBuchstaben-1))
                temp += "-";
        }
        return temp.split("-",0);
    }
    
    public static void messungStarten(String tasten, int anzahlBuchstaben)
    {
        String[] container = splitString(anzahlBuchstaben,tasten);
        int limit = container.length;
        int[][] array = new int[limit][anzahlBuchstaben];
        Random rando = new Random();
        
        for(int i = 0; i < limit; i++)
            array[i] = StringToIndex(container[i]);
            
        int [][] array2 = zufälligeMesswerte(limit, anzahlBuchstaben);
        
        System.out.println(Integer.toString(limit));
        
        for(int i = 0; i < limit; i++)
        {
            if(rando.nextInt(2) == 1)
            {
                for(int n = 0; n < anzahlBuchstaben; n++)
                    System.out.print(Integer.toString(array[i][n]) + " ");
                System.out.println("0");
            }
            else
            {
                for(int n = 0; n < anzahlBuchstaben; n++)
                    System.out.print(array2[i][n] + " ");
                System.out.println("1");
            }
        }
}
    
    public static int[][] zufälligeMesswerte(int limit, int anzahlProArray)
    {
        int[][] erg = new int[limit][anzahlProArray];
        Random rando = new Random();
        
        for(int i = 0; i < limit; i++)
        for(int n = 0; n < anzahlProArray; n++)
            erg[i][n] =  rando.nextInt(29);
        
        return erg;
    }
    
    public static void main()
    {
        messungStarten("egcöjfdssl",10);
    }
}
