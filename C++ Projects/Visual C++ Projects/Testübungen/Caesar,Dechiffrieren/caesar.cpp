#include <iostream>
#include <string>
using namespace std;

int main()
{
string Text = "bb";
string Ergebnis;
string abc = "abcdefghijklmnopqrstuvwxyz";
int abclang = abc.length();
int zahl;
char b;

for(int i = -26 ; i < 27; ++i)   
{
	Ergebnis = "";
	for(int n = 0; n < Text.length(); n++)
		{
			b = Text[n];
			zahl = abc.find_first_of(b);
		    b = abc [(((zahl + i) % abclang)+ abclang) % 26];
			Ergebnis += b;
		}
	cout << endl << Ergebnis << "\t Schieber: " << i;
}
return 0;
}