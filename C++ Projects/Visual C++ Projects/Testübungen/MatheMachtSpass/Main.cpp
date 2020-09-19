#include <iostream>
#include <ctime>
using namespace std;

int main()
{
	time_t t, start, ende;
	int aufgabenzahl;
	int richtigeErg = 0;
	time(&t);
	srand(static_cast<unsigned int>(t));
	char eingabe;
	unsigned int a,b;
	unsigned int ergebnis;
	cout << "Willkommen bei \" Mathe macht Spass\" \n" << "Suchen Sie sich Ihre Herausforderung aus! \n"
         << "\n\t [a] Das Einmaleins bis 10\n"
		 << "\t [b] Das Einmaleins bis 20        ";
	cin >> eingabe;
	
	switch(eingabe)
	{
	case 'a':
	case 'A':	cout << "\nWie viele Aufgaben?  " << "\t\t\t  ";
				cin >> aufgabenzahl;
				cout << "\n Lass die Rechnerei beginnen! (Enter druecken)\n";
				cin.get();
				cin.get();
				time(&start);
				
				for(int n = 0; n < aufgabenzahl; n++)
				{
					a = rand() % 11;
				    b = rand() % 21;
					cout << "Was ergibt: " << a << " * " << b << " ?      " << "\t\t  ";
					cin >> ergebnis;
					if(ergebnis == a * b)
						{
							cout << "Richtig!\n";
							++richtigeErg;
					    }
					else
						cout << "\nFalsch, das richtige Ergebnis von " << a << " * " << b
						     << " ist " << a * b << ".\n";
				}
				
				time(&ende);
				cout << "\n\nGut gemacht, du hast alle Aufgaben geloest!\n\n"
					 << "Du hast " << richtigeErg << " von " << aufgabenzahl << " richtig geloest in "
					 << difftime(ende, start) << " s.\n ";
				break;

    case 'B':
	case 'b':   cout << "\nWie viele Aufgaben?  " << "\t\t\t  ";
				cin >> aufgabenzahl;
				cout << "\n Lass die Rechnerei beginnen! (Enter druecken)\n";
				cin.get();
				cin.get();
				time(&start);
				
				for(int n = 0; n < aufgabenzahl; n++)
				{
					a = rand() % 21;
					b = rand() % 21;
					cout << "Was ergibt: " << a << " * " << b << " ?      " << "\t\t  ";
					cin >> ergebnis;
					if(ergebnis == a * b)
						{
							cout << "Richtig!\n";
							++richtigeErg;
					    }
					else
						cout << "\nFalsch, das richtige Ergebnis von " << a << " * " << b
						     << " ist " << a * b;
				}
				
				time(&ende);
				cout << "\nGut gemacht, du hast alle Aufgaben geloest!\n\n"
					 << "Du hast " << richtigeErg << " von " << aufgabenzahl <<  " Aufgabe[n] richtig geloest in "
					 << difftime(ende, start) << " s.\n ";
				break;
	
	default:    cout << "Falsche Eingabe, starten Sie das Programm erneut!";
		        break;
			   }
	
	cout << "\n\n\nDruecken Sie Enter zum Beenden!";
		        cin.get();
				cin.get();
	
	return 0;
}