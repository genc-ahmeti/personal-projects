#include <iostream>
using namespace std;

enum Ampelzustand{Rot = 1, RotGelb, Gruen, Gelb};
 
int main()
{
    cout << endl;
	cout << " Hallo, willkommen bei der Ampelsimulation! " << endl;
	
	for(Ampelzustand Ampel = Rot;Ampel <= Gelb; Ampel = static_cast<Ampelzustand>(Ampel + 1))         //Im Datentyp AMpelzustand k�nnen literale Variablen wie Rot f�r Zahlen stehen :D, aber Int + Ampelzustand geht nicht, man bruacht also eine Typ�nderung
	{
		switch(Ampel)
		{
		case Rot: cout << " Rot! Halte an! " << endl;
			      cin.get();
				  break;
		case RotGelb: cout << " Kannsch noch durch :D " << endl;
			          cin.get();
				      break;
			       
		case Gruen: cout << " Durchfahrt genehmigt! " << endl;
			        cin.get();
				    break;
		case Gelb: cout << " ENde :D " << endl << endl;
			       break;
			}
	}

return 0;

}