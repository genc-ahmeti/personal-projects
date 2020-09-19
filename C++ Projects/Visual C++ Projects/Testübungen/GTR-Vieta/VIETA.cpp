#include <iostream>
#include <cmath> 
using namespace std;

double x, y;

char SchauObPosNeg (double n)
{
	if (n > 0)
		return '+';
	else
	    return '-';
}

int main()
{
double b,c;
cout << " Geben Sie die letzten beiden Koeffizienten der quadratischen Funktionsgleichung an! " << endl << endl;
   cout << " b = ";
		cin >> b;
   cout << " c = ";
		cin >> c;

double abc = sqrt(b*b - 4*c);

if (!(abc >= 0) && !(abc <=0))
{
	cout << "Ungueltige Koeffizienten! Funktionsgleichung besitzt keine Nullstellen! ";
	cin.get();
	cin.get();
	return 0;
}
else
{
if (c > 0)
{
	for(x = c; x > -c; x--)
		{
		  y = c / x;
          if (y + x == b)
	      break;
		}

}
else
{
	for(x = c; x < -c; x++)
		{
	       y = c / x;
           if (y + x == b)
	       break;
		}
}

cout << " Der aequivalente Funktionsterm lautet: (x " << SchauObPosNeg(y) << " " << fabs(y) << ") * (x " << SchauObPosNeg(x) << " " << fabs(x) << ")";
cin.get();
cin.get();

return 0;
}
}

