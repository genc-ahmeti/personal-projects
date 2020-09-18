#ifndef _ChattenH_
#define _ChattenH_

#include <string>

class Chatten
{
private:
// Membervariablen
std::string name;

public:
// Konstruktoren
Chatten(std::string personName): name(personName){}
// Memberfunktion zum Simulieren vom Chat
static void chatPunkte(int anzahlDurchgaenge);
static void warten(int sekunden);
void schreiben(std::string sagen);
};
#endif