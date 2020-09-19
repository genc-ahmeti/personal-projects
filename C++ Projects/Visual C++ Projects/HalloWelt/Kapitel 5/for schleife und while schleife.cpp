#include <iostream>
using namespace std;

int main()
{
	
	int x = 1, y = 10;
	
	while(x <= 10 && y >= 1)                                       //while-schleife
	cout << " " << x++ * y-- << endl;
	
	double ergebnis; 
    
	for(int z=0, a = 10; z<11; ++z)                               // for-schleife; man kann auch einzelne dinge weglassen wie for(;;) für endlos
	{
		if (z==5)
			continue;
		if (z==9)
			break;
		
		ergebnis = pow(2.0,z);
		cout << " Die Potenz von 2 hoch " << z << " ergibt: " << ergebnis << endl;
	}
		Punkt1: cout << 1 << endl;                                   // Den Sprngpunkt markieren
	    Punkt2: cout << 13 << endl;
Punkt3: cout << 231 << endl;

//goto Punkt2;                                                         // Zum Sprungpunkt gehen
//goto Punkt1;
//goto Punkt3;

	for(int n = 1; n < 11; cout << " Das ist der " << n << ". Durchlauf. " << endl, n++);
		
	
		
	return 0;
	
	}
