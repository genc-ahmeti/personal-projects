#include <iostream>
using namespace std;

int main()
{
	int anzahl = 0;
	double *summe = new double;
	*summe = 0;

	cout << " Wie viele Messwerte? ";
	cin >> anzahl;
	const int ANZAHL = anzahl;
	if (ANZAHL <= 0 || ANZAHL > 100)
		return 0;
	double *messwerte = new double[ANZAHL];
	for(int n = 0; n < ANZAHL; n++)
	{
	  cout << " " << n + 1 << ". Messwert eingeben: ";
	  cin >> messwerte[n];
	  *summe += messwerte[n];
	}
	double mittelwert = *summe / ANZAHL;

	cout << endl << endl << " Mittelwert: " << mittelwert;
	cout << endl << endl;
	delete[] messwerte;
	delete(summe);
	return 0;
}