#include <iostream>
#include "Punkt.h"
using namespace std;


int main()
{
	Punkt p;

	p.verschieben(-10, 5);

	cout << endl;
	cout << " Punkt p; ";
	p.ausgeben();
	cout << endl << endl;

	return 0;
}