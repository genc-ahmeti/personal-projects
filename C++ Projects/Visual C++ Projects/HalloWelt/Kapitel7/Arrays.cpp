#include<iostream>
using namespace std;

enum Note {SehrGut = 1, Gut, Befriedigend, Ausreichend, Mangelhaft, Ungenuegend};                    // Datentyp auch, wobei erzeugt nur Variablen, die diese hier stehenden Werte annehmen können

struct Adresse                                // Datentyp eigentlich, mit dem man Objekte erzeugt, die dann die Membervariablen(Elemente) beinhalten
{
	int Hausnummer;
	int Postleitzahl;
};                                            //VAriablendefinition geht auch danach: } Genc,Adu;

Adresse Genc = {309,65}, Adu;                 // DIrekte Initialisierung der werte zu Memebrvariablen


int main()
{
    Note Schulnote;
	int Eingabe;
    Schulnote = static_cast<Note>(1);
	cout << " Welche Note hast du? " << endl;
	cout << Schulnote << endl;
	cin >> Eingabe;
	
switch(Eingabe)
{
   case SehrGut: cout << " 1 " << endl;
	             break;
   case Gut: cout << " 2 " << endl;
	             break;
   case Befriedigend: cout << " 3 " << endl;
	             break;
   case Ausreichend: cout << " 4 " << endl;
	             break;
   
   case Mangelhaft: cout << " 5 " << endl;
	             break;
   case Ungenuegend: cout << " 6 " << endl;
	             break;
}
    

cout << Genc.Hausnummer << endl;                     // Aufruf des Wertes einer Membervariabe

	
	int array1[3] = {1,2,3};    // Array-Werte werden direkt nach der Definition zugewiesen mit diesr Schleife
	int array2[]  = {4,5,6};    // Array-Werte werden zugewiesen ohne Angabe, wie viele Werte gespeichert werden sollten (Das errechnet der compiler)

	int ElementeImArray = sizeof(array1)/sizeof(int);    // Anzahl der Elmente im Array herausfinden weil Speicher für Zahlen und Datentyp benötigt
	cout << array1[0] << endl << array2[2] << endl << ElementeImArray << endl << endl << endl;  // 1.Wert ist über [0] der letze über [Gesamtzahl - 1]


	

	return 0;
}