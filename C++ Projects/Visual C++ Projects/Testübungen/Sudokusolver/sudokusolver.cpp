#include<iostream>
using namespace std;

/*
* Version 1: Sudoku-Löser           Made by Genc Ahmeti
*
* Dieses Programm basiert auf den Algorithmus "Backtracking" bzw. iteratives + rekursives "Backtracking
*
*
*/

// drei einzelne Kontrollfunktionen in einer großen Überprüfungsfunktion
// bool aufgrund Wahrheitswerten (besetzt oder nicht besetztt bzw. true/false)

bool gleichInReihe(int feld[9][9], int reihe, int spalte, int zahl)				// Schauen, ob Zahl in der Reihe(horizaontal) schon vorhanden
{
	for(int n = 0; n < 9; n++)
	{
		if(zahl == feld[spalte][n])
			return true;
	}
return false;
}


bool gleichInSpalte(int feld[9][9], int reihe, int spalte, int zahl)			// Schauen, ob Zahl in der Spalte(vertikal) schon vorhanden
{
	for(int n = 0; n < 9; n++)
	{
		if(zahl == feld[n][reihe])
			return true;
	}
return false;
}

bool gleichInBox(int feld[9][9], int reihe, int spalte, int zahl)				//Schauen, ob Zahl in derselben "Box" (3*3-Einheit) schon vorhanden
{
	for(int n = 0; n < 3; n++)
	for(int i = 0; i < 3; i++)
	{
        if(zahl == feld[(n % 3) + (((spalte)/3) * 3)][(i % 3) + (((reihe) / 3) * 3)])   // Indikator einer Einheit(wo fange ich an) 
																						// anhand einer Reihe gekennzeichnet, sodass nur drei Werte
																						// in drei Spalten in Frage kommen (3*3; zuerst kommen
																						// die Werte einer Reihe innerhalb einer Box, dann geht man 
																						// eine Spalte runter, macht das gleiche
				{
		         return true;
				}
	}                      // Ende 2. for-schleife
return false;
}                         // Ende Funktion

bool ZahlEinsetzbar (int feld[9][9], int reihe, int spalte, int zahl)                      
{
	return !gleichInReihe(feld, reihe, spalte, zahl) && !gleichInSpalte(feld, reihe, spalte, zahl) && !gleichInBox(feld, reihe, spalte, zahl);
	// Das ist das Geniale, welches ich asu dem Internet abgeschaut habe :D
	// das "!" negativiert den bool-wert; aus false wird true und umgekehrt
	//d.h. die werte müssen "false" sein, um "true" zu sein
	//nur wenn heir alle true sind, wird true wiedergegeben, sonst false (kürzer als if-abfragen)
}

bool SucheNeueZahl(int feld[9][9], int* reihe, int* spalte)        // sucht nach neuer stelle; pointer sind wichtig, da die aktuelle stelle des sudokus
																   // in der Funktion selber beibehalten werdeen sollte über den gesamten verlauf,
																   // außer er wird geändert und wird dann folglich beibehalten 
{
	for(*spalte = 0; *spalte < 9; (*spalte)++)
		for(*reihe = 0; *reihe < 9; (*reihe)++)
		{
			if(feld[*spalte][*reihe] == 0)
			{
                return true;
			}			
		}
return false;
}


bool SudokuLösen (int feld[9][9], int reihe, int spalte)                           // hier beginnt das Lösen mit allen anderen Programmen
{


if(!SucheNeueZahl(feld, &reihe, &spalte))          // Abbruchbedingung: Sudoku gelöst, wenn keine neue Zahl gefunden werden kann
     return true;							     // , da alle anderen Zahlen gültig eingesetzt

for(int zahl = 1; zahl < 10; zahl++) 
{                                                           /*Erklärung des Verlaufs (mit fokus "rekursion")
														    *
															*Zuerst wird geschaut, ob die Zahl gültig ist, also den Sudokuregeln entsprechend
															*eingesetzt werden kann. gilt das, wird true abgebebn, der ablauf wird fortgesetzt.
															*die gültige Zahl wird dem feld zugewiesen.
															* jetzt wird die Funktion selber nochmal aufgerufen 
															*(es entsteht somit eine kette voller unvollständig gelöster "SudokuLösen"-Funktionen)
															* diese verlängert sich, bis etwas nicht klappt und false gesendet wird
															*diesen false-wert bekommt die letzte Kette, wert auf null gesetzt und
															* false auf die letzt kette gesendet usw. (dabei werden neue zahlen von alten Stellen
															* ersetzt und mit neuen ergänzt, da die funktion zurückspringt, wo es davor in der 
															* Kette war (hier: Stacks "weggenommen" oder "daraufgelegt"
															*
															*/
	if(ZahlEinsetzbar(feld, reihe, spalte, zahl))          
	{
		feld[spalte][reihe] = zahl;
		 if(SudokuLösen(feld, reihe, spalte))
			 return true;
		 else
			 feld[spalte][reihe] = 0;
     }
}
return false;
}






int main()
{
	int feld[][9] = {{0, 0, 2, 0, 8, 0, 4, 0, 0},
                      {0, 5, 0, 4, 0, 3, 0, 6, 0},
                      {8, 0, 0, 6, 0, 9, 0, 0, 5},
                      {4, 0, 0, 2, 0, 8, 0, 0, 6},
                      {0, 0, 0, 0, 0, 0, 0, 0, 0},
                      {9, 7, 0, 0, 0, 0, 0, 2, 1},
                      {7, 0, 0, 0, 0, 0, 0, 0, 4},
                      {0, 9, 0, 0, 0, 0, 0, 3, 0},
                      {0, 0, 8, 0, 1, 0, 7, 0, 0}};
int reihe = 0;

int spalte = 0;

	if(SudokuLösen(feld, reihe, spalte))
{
for(int i = 0; i < 9; i++)	
{
	cout << " ";
	for(int n = 0; n < 9; n++)                                                     // Ausgabe des Ergbenisses
	{
		cout << "[" << feld[i][n] << "]" << " ";
		if (n == 2 ||n == 5 || n == 8)
			cout << "|" << " ";
	}
		cout << endl << endl;
		if (i == 2 ||i == 5 || i == 8)
			cout << "__________________________________________" << endl << endl;
}
}
else
	cout << " Sudoku unloesbar, bro!" << endl;
cout << endl << endl << endl;

return 0;
}
	