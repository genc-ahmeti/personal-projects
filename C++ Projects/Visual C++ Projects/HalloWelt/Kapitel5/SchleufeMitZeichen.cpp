#include <iostream>
using namespace std;

int main()
{
	cout << endl;
	for(int n = 0, y = 1; y < 11; y++, cout << endl << endl, n = 0)
	for(; n < y; cout << "*", n++)
    if (n == 0)
		cout << " ";
        
	cout << endl << endl << endl << endl;
	
	return 0;
}