#include <iostream>
#include <time.h>
using namespace std;

int main()
{
time_t t;
time(&t);
srand(static_cast<unsigned int>(t));
int Eingabe;
int Alter = 1+(rand() % 100);

cout << " Hallo, rate, wie alt ich bin! " << "        ";
cin >> Eingabe;
while (Eingabe != Alter)
{
  if (Eingabe > Alter)
	  cout << endl << " Tut mir leid, ihr eingegebenes Alter ist zu gross! " << endl;
  else
	  cout << endl << " Tut mir leid, ihr eingegebenes Alter ist zu klein! " << endl;
	  
  cout << " Verschen Sie es noch einmal. :D " << "      ";
  cin >> Eingabe;
}
cout << endl << endl <<" Herzlichen Glueckwunsch! DU hast mein Alter erraten! " << endl << endl;
return 0;
}