/********************************************************************
* Hallo-Welt-Programm
*
* gibt einen Gruß auf dem Bildschirm aus und erfüllt allerlei Dinge
*
*/

#include <iostream>                                           // Headerdatei für "Ein- oder Ausgaben" in der Konsole
#include <cmath>                                              // Headerdatei für Mathematische Funktionen
#include <string>                                             // Headerdatei für die Benutzung von "strings"
using namespace std;                                                    // Man muss kein std:: vor den Funktionen und Variablen schreiben :D
                                   
double Runden(int Nachkommastellen,double ergebnis)                            // Eigene Funktion: Rundet teilweise :D; name + parameter = signatur
	{
	  double verschiebung = pow(10.0,Nachkommastellen);                        // links: Basis, rechts: Exponent (Exponentialrechnung mit pow())
	  double runden1 = ergebnis * verschiebung;
	 
	  if (ergebnis > 0)
	  {
		  runden1 += 0.5;                                                       // Variable erhöhen um 0,5
	  } 
	  else
	  {
		  runden1 -= 0.5;
	  }
	  
	  int runden1a = runden1;
	  ergebnis = runden1a / verschiebung;
	  
	  return ergebnis;
	}

void Zeilensprung()                                                // Void, denn die Funktion soll keinen Rückgabewert liefern
{
    cout << endl << endl << endl;  
}


double UmrechnungFahr(double GradFahrenheit)
{
  double celsius = (GradFahrenheit - 32) * (5.0 / 9);
  
  return celsius;
}
	

int main()                                                                                // Eintrittsfunktion
{
	cout << "Made by Genc" << endl;
	
	Zeilensprung();                                                       // Zeile frei lassen 
	
	string passwort;

	cout << " Hallo! Geben Sie Ihr Passwort ein: ";
	cin >> passwort;

	while (passwort != "genc")
	{
		cout << endl << " Haha, falsch! " << endl << endl;
		cout << " Geben Sie erneut Ihr Passwort ein: ";
		cin >> passwort;
	}
		
	cout << endl << endl << endl;
	
	int eingabe;
	
	cout << " Waehlen Sie eines dieser Programme aus!"
		 << endl << " Viel Spass! " << endl << endl << endl;
	cout << " Mitternachtsformel-Berechner:                <1> "
		 << endl << endl
		 << " Fahrenheit-Celsius-Umrechner:                <2> "
		 << endl << endl << endl << endl
		 << " Geben Sie nun Ihre Auswahl ein:               ";
    cin >> eingabe;


	switch (eingabe)
	{
case 1:
	{
	double a, b, c;
	
	cout << " Hallo! " << endl
	     << " Berechnen Sie hiermit die Gleichung einer quadratischen Funktion: "
	     << endl;
	
	Zeilensprung();
	
	cout << "      a = " ;
	cin >> a;
	cout << endl
	     << "      b = ";
	cin >> b;
	cout << endl
	     << "      c = ";
	cin >> c;
	cout << endl;
	
	
	long double x1 = (-b + sqrt(b*b-4*a*c))/(2*a);
    long double x2 = (-b - sqrt(b*b-4*a*c))/(2*a);
	
	int stellen, stellenmerk;
	
	cout << " Rundung mit wie vielen Nachkommastellen? (< 10) " << "       --> ";
    cin >> stellen;
	stellenmerk = stellen;
	cout << endl << endl;
	
	if (x1 < 0)
	      cout.precision(stellenmerk);
	else
          cout.precision(++stellenmerk);

	
	int n = 10, m = 2;
	bool abbruch = true;
	
	
	do
	{
		if (x1 >= n || x1 <= -n)
		{
			cout.precision(stellenmerk + m);
			n *= 10;
	        ++m;
		}
		else
			abbruch = false;
	}
	while (abbruch);
	
	cout << "                     x1 = " << showpoint <<  Runden(stellen,x1) << endl;
	
	stellenmerk = stellen;
	
	if (x2 < 0)
	      cout.precision(stellenmerk);
	else
          cout.precision(++stellenmerk);
	
	n = 10, m = 2;
	abbruch = true;
	
	do
	{
		if (x2 >= n || x2 <= -n)
		{
			cout.precision(stellenmerk + m);
			n *= 10;
			++m;
	    }
		else
			abbruch = false;
	}
	while (abbruch);
	
	cout << "                     x2 = " << showpoint << Runden(stellen,x2)  << endl;
	
	Zeilensprung();
	
	cout << " Druecken Sie die Entertaste zum Beenden!" << endl;
	
	cin.get();                                                    // Das Programmende wartet, bis der Anwender die "Entertaste drückt
	cin.get();  

                                                     
	break;
	}

case 2: 
	
	double fahrenheit;
    
	cout << endl << endl << endl;
	cout << " Hiermit koennen Sie von Fahrenheit in Celsius umrechnen." << endl << endl;
	cout << " Geben Sie nun die von Ihnen umzurechnende Temperatur ein!";
	cout << "     ---> ";
	cin >> fahrenheit;
	cout << endl << endl << endl;
	cout << " " << fahrenheit << " F sind umgerechnet: " << UmrechnungFahr(fahrenheit);
	cout << " C" << endl << endl;
	cout << "                Druecken Sie Enter zum Beenden! " << endl << endl;
	
    cin.get();
	cin.get();
	
	break;

default:
	cout << endl << endl << endl;
	cout << "                              Falsche Eingabe! Sie, Sir, sind ein Loser :D " << endl;
	
	cin.get();
	cin.get();
	
	break;
	
}
	cout << endl << endl << endl << endl;
	return 0;                                                        // Es soll kein Rückgabewert ankommen/Ende 
}

  

  

