#include <iostream>
#include <sstream>
using namespace std;

int main()
{
	enum Ad {a = 0, b, c, d};
	int erg = 0;
	Ad b = a;
	


	cout << erg << endl << b;
	return 0;
}