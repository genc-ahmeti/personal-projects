#include <iostream>
#include <cmath>
using namespace std;


	double a;
	double b;
	double c;
	
	
	cout << " Hallo! " << endl;
	cout << " Berechnen Sie hiermit die Gleichung einer quadratischen Funktion: " << endl;
	
	cout << "a = " ;
	cin >> a;
	cout << endl;
	cout << "b = ";
	cin >> b;
	cout << endl;
	cout << "c = ";
	cin >> c;
	cout << endl;
	
	double x1 = (-b + sqrt(b*b-4*a*c))/(2*a);
    double x2 = (-b - sqrt(b*b-4*a*c))/(2*a);
	
	cout << "x1 = " << x1 << endl;
	cout << "x2 = " << x2 << endl;
	cout << "Druecken Sie die Entertaste zum Beenden!" << endl;
	
	cin.get();
	
	return 0;
}