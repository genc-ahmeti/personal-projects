#include <iostream>
using namespace std;

bool lineareAbh(double array1[3], double array2[3])
{
	double erg[3];
	for(int n = 0; n < 3; n++)
	{
		erg[n] = array2[n] / array1[n];
	}

	if (erg[0] == erg[1] && erg[0] == erg[2])
		return true;
	else
		return false;
}
bool AufpunktDrauf(double testvektor[3],double stuetzvektor[3], double richtungsvektor[3])
{
	double erg[3];

	for(int n = 0; n < 3; n++)
	{
		erg[n] = testvektor[n] - stuetzvektor[n];
		erg[n] /= richtungsvektor[n];
}

	if (erg[0] == erg[1] && erg[0] == erg[2])
		return true;
	else
		return false;
}

/*bool Schnittpunkt(double stuetzvektor1[3], double richtungsvektor1[3], double stuetzvektor2[3], double richtungsvektor2[3])                  // hier weiter machen!!! Stichwort: Gaußsche 
                                                                                                                                               // Eliminationsverfahren
{
	 
}*/ 
int main()
{
	
	double stuetzvektor1[3] = {1.0, 0.0, 5.0};
	double richtungsvektor1[3] = {2.0, -2.0, 2.0};
	
	double stuetzvektor2[3] = {5.0, 0.0, 1.0};
	double richtungsvektor2[3] = {-3.0, 3.0, -3.0};
	
	if(lineareAbh(richtungsvektor1,richtungsvektor2))
	{
		if(AufpunktDrauf(stuetzvektor2, stuetzvektor1, richtungsvektor1))
			cout << "Geraden sind identisch.";
		else
			cout << "Geraden sind parallel zueinander.";
	}
	else
	{
		/*if(Schnittpunkt(stuetzvektor1, richtungsvektor1, stuetzvektor2, richtungsvektor2)                          
			cout << "Geraden schneiden sich am Punkt: " << Schnittpunkt;                             // Schnittpunkt-Variable machen
		else
			cout << "Geraden sind windschief."*/
	}
		
		cout << endl << endl;
return 0;
}