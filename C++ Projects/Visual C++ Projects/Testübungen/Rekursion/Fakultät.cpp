#include<iostream>
using namespace std;

int fac(int zahl)
{
	if (zahl < 0)
		return -1;
	if (zahl == 0)
		return 1;
	return zahl * fac(zahl-1);  // zahl * zahl-1 * fac(zahl-2), zahl * zahl-1 * zahl-2; 

}

int main()
{
int zahlfac;
cout << "Gib eine Zahl ein: ";
cin >> zahlfac;
for(int n = 0; n <= zahlfac; n++)
	cout << " \n Die Fakultaet von " << n << " ist: " << fac(n) << endl;
    cout << endl << endl;

	return 0;

}
