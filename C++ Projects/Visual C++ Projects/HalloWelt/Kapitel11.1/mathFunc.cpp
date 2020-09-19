#include <iostream>
#define _USE_MATH_DEFINES
#include <cmath>
#include <cstdlib>
using namespace std;

int main()
{
	int zahl;
	cout << endl;
	cout << " Hallo, eine Integerzahl kleiner 100 eingeben: ";
	cin >> zahl;

	if (zahl > 100)
		return 0;
	int potenz = pow(10.0,zahl);
	double log_potenz = log(static_cast<double>(potenz));
	cout << " Potenz: 10^" << zahl << " betraegt: ";
	cout << potenz << endl;
	cout << " Log von: " << zahl << ": ";
	cout << log_potenz;
	cout << endl;

	double array[4];
	for(int n = 0; n < 4; n++)
	{
		array[n] = (rand() % 11) - 5;
		cout << endl << " " << array[n];
	}

    return 0;
}
