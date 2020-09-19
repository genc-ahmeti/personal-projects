#include <iostream>
using namespace std;



struct Punkt                                                          // auch nun ein Datentyp, den wir in der class Rechteck haben
{
  double x;
  double y;
};

class Rechteck                                                             // Vergiss nicht, class Rechteck, also "Reckteck" ist nun auc ein Datentyp
{
public:

	Punkt Koordinate;
	double laenge,breite;
	double Flaeche()                                           // Als Memberfunktion kann es auf Membervariablen zugreifen ohne Parameter, sonst wäre es "Rechteck rechteck" als Parameter               
{ 
	return laenge * breite;
}
} Rechteck1;

double Flaeche2(const Rechteck *r);
double Flaeche3(const Rechteck &r);

int main()
{ 
    cout << endl << endl;
	cout << " Hallo, waehlen Sie Ihr perfektes Rechteck! " 
		 << endl << " Koordinate x: ";
	cin >> Rechteck1.Koordinate.x;                                                   // Zugriff auf Membervariablen mittels ".",; hier sogar Doppelt
	cout << " Koordinate y: ";
	cin >> Rechteck1.Koordinate.y;
	cout << " Laenge: ";
	cin >> Rechteck1.laenge;
	cout << " Breite: ";
	cin >> Rechteck1.breite;
	cout << endl << endl << " Ihr gewuenschtes Rechteck besitzt die Flaeche: "
		<< Rechteck1.Flaeche() << endl << endl;
    cout << Flaeche2(&Rechteck1) << endl << Flaeche3(Rechteck1) << endl << endl;


	return 0;
}



double Flaeche2(const Rechteck *r)                                     // "const", damit die Werte des Rechtecks nicht versehenlich verändett werden
  {
	  return ((*r).laenge * (*r).breite);
  }


double Flaeche3(const Rechteck &r)
  {
	  return (r.laenge * r.breite);
  }


