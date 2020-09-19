#include<iostream>
#include <string>
using namespace std;

enum Antwort {JA = 1, NEIN, IGNORIEREN};

struct Kraft
{
  int Staerke;
  int Schnelligkeit;
  int Weisheit;
};


class Spieler
{
public:
	
	string name;
	Kraft Faehigkeit;
	
	Spieler()
	{
		Faehigkeit.Staerke = 0;
		Faehigkeit.Schnelligkeit = 0;
		Faehigkeit.Weisheit = 0;
	}

} spieler;


int main()
{
	char fuss = '"';
    cout << " Hallo, willkommen zu " << fuss << "Consequences" << fuss << ". " << endl;
	cout << " Gib nun deinen Namen ein: "; 
	cin >> spieler.name;
	cout << endl << fuss << "Du siehst einen Baum. Moechtest du "
		 << "mit ihm reden?" << fuss << " <Ja> / <Nein> / <Ignorieren> " << endl << endl;
	cout << " Deine Antwort (<1> / <2> / <3>): ";
	int eingabe;
	Antwort antwort;
	cin >> eingabe;
	antwort = static_cast<Antwort>(eingabe);
	cout << endl << endl;
	
	switch(antwort)
	{
	case JA: cout << " Der Baum zeigt dir wertvolle Kampftipps, jedoch verletzt du dich bei der Ausfuehrung dieser. " << endl
				 << "           -1 Schnelligkeit " << endl
				 << "           +1 Staerke " << endl;
		--spieler.Faehigkeit.Schnelligkeit;
		++spieler.Faehigkeit.Staerke;
		break;

	case NEIN: cout << " Der Baum greift dich an, wodurch du nahe verblutest" << endl
				 << "           -1 Schnelligkeit " << endl
				 << "           -1 Staerke " << endl
		         << "           -3 Weisheit " << endl;
		--spieler.Faehigkeit.Schnelligkeit;
		--spieler.Faehigkeit.Staerke;
		--spieler.Faehigkeit.Weisheit;
		break;

	case IGNORIEREN:  cout << " Der Baum verfolgt dich, doch dabei findest du wertvolle Schriftrollen." << endl
				 << "           +2 Schnelligkeit " << endl
				 << "           +2 Weisheit " << endl;
		spieler.Faehigkeit.Schnelligkeit += 2;
		spieler.Faehigkeit.Weisheit += 2;
		break;

	default: cout << "Das Programm wird beendet" << endl << endl;
		     cout << " Druecken Sie die Enteraste zum Beenden! ";
		     cin.get();
			 cin.get();
			 return 0;

	}
	cout << endl << endl;
	cout << " Ihre bisherigen Charakter-Werte: " << spieler.name << endl
		 << " Schnelligkeit:   " << spieler.Faehigkeit.Schnelligkeit << endl
		 << " Staerke:         " << spieler.Faehigkeit.Staerke << endl
		 << " Weisheit:        " << spieler.Faehigkeit.Weisheit << endl << endl << endl;
	
	return 0;


}