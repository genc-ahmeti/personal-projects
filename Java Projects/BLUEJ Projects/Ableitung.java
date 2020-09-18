public class Ableitung
{
    private static double h = Math.pow(10, -5);
    
    public  static double f(double x)
    {
        return 5*Math.cos(3*x);
    }
    
    // n-te Ableitung an der Stelle x
    public static double ableiten (int n, double x)
    {
        if(n == 1)
            {
                double a = f(x + h);
                double b = f(x);
                double c = h;
                double d = a-b;
                
                return (a - b)/c;
            }
        
        return (ableiten(n-1, x+h)-ableiten(n-1, x))/h;
    }
    
    public static void main()
    {
       System.out.println(Double.toString(ableiten(2,0)));
    }
}
