#include <iostream>
#include <cmath>
using namespace std;

int wert = 1;  // Hier wird eine globale Variable "wert" definiert


void Ausgabe(double a)
{
	cout << a << " cool " << endl;
}

double endgeschwindigkeit(int hoehe)
{
	return sqrt(hoehe * 2.0 * 9.81);
}

double Kreisfläche(double Radius)
{
	const double PI = 3.14159265359;                 // Literale oder hier Literale VAriablen nützlich, um anstatt Zahlen Variablennamen, wobei sich der Wert nie ändert
	return PI * pow(Radius, 2);
}



int main(int argc, char* argv[])
{
	int wert = 2;                               // Hier ist die lokale Varibale, die die globale Variable verdeckt
	
	cout << argv[0] << endl << endl;            // der name der Datei
	cout << wert << endl
		<< ::wert << endl << endl;               // durch ::wert wird der Wert der globalen, nicht der der lokalen Variable angezeigt
	 Ausgabe(endgeschwindigkeit(1));
	Ausgabe(Kreisfläche(1));
	 return 0;
}