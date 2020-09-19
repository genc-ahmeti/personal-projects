#define _USE_MATH_DEFINES          // f�r mathematische literalen, M_PI, M_E...
#include <cmath>                   // Mathematisch funktionen pow(),sqrt(),...

#include <cerrno>                  // um schauen, ob argumente in mathemat. funktion g�ltig sind (errno == EDOM); var==HUGE_VAL schaut, ob der wert von val dem maximalwert seines datentyps uberschreitet

#include <iostream>
#include <cstdlib>                 // f�r rand(), integer zahlen gr��er 0
#include <ctime>                   // f�r datentyp time_t, funktion time(unsigned adresse einer variable vom typ time_t); Zeit gemessen von einem Zeitpunkt 0:00 1970
using namespace std;

int main()
{
	time_t t;                      // variable vom datentyp time_t f�r werte der zeit
	srand((unsigned)time(&t));     // jede zuf�llige folge von zufallszahlen einen indexwert; da jede sekunde sich zeit �ndert, auch der seed(index) pro sekunde, also immer eine neue folge von zufallszahlen
	cout << endl;
	for(int i = 0; i < 10; i++)
	{
		cout << " " << M_PI << " ";
		cout << rand();
	    cout << endl;
	}
}