/*
* Chatten-Simulation
* Am Anfang Chatpersonen nennen
  - zuerst anstatt Person1, Person2,... den Namen hinschreiben
  - den Namen dann nochmal in den Klammern dazwischen in Anführungszeichen
* Man bringt eine Person zum Schreiben mit: 
  - Name.schreiben(Was soll die Person sagen?);
  - !!! Was gesagt werden soll in Anführungszeichen !!!
* mit "Chatten::" verschiedene Funktionen verfügbar:
  - chatPunkte(Wie viele Durchgänge?); 
  - warten(Wie viele Sekunden?);
*/

#include <iostream>
#include <string>
#include <ctime>
#include "Chatten.h"

int main(int argc, char* argv[])
{
//--------------------------------------- AB HIER ANFANGEN MIT PROGRAMMIEREN -------------------------------------------------------

//HIER DIE PERSONEN NENNEN:

Chatten Person1("Name");
Chatten Person2("Name");
Chatten Person3("Name");

// AB HIER BEFEHLE SCHREIBEN!!!!

Person1.schreiben("Hallo");
Chatten::chatPunkte(2);
Person2.schreiben("Wie geht's dir?");

// -------------------------------------- ENDE DES PROGRAMMS ------------------------------------------------------------------------

std::cin.get();
return 0;
}