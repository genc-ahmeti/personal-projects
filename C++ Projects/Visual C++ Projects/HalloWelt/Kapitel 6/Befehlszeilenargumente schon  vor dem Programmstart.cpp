#include <iostream>
#include <string>
using namespace std;

string Textezusammenf�hren(string a, string b);

int main(int argc, char* argv[])                 // argument count (WIE VIELE bEFEHLSZEILENARGUMENTE), argument vector (WELCHE BEFEHLSZEILENARGUMENTE) aus C
{
	cout << " Hallo,  dieses Programm zeigt dir an, welche Befehlszeilenargumente hast! " << endl << endl;

	for(int n = 0; n < argc; n++)
		cout << " " << n << ". Argument:   "
	         << argv[n] << endl;

	cout << endl << endl << endl;

    cout << Textezusammenf�hren(argv[4], argv[5]) << endl;
	
	/*  Du kannst das bruachen, wenn du ARgumente f�r FUnktionenn oder sonstiges brauchst, schon vor dem
	    Programm start.*/

	return 0;

}

string Textezusammenf�hren(string a, string b)
{
return a + " " + b;
}