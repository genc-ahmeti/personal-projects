#include <iostream>
#include <string>
#include <ctime>
#include "Chatten.h"

void Chatten::chatPunkte(int anzahlDurchgaenge)
{
int temp = 1;
for(int n = 0; n < 2*anzahlDurchgaenge; n++)
{
for(int i = 0; i < 3; i++)
{
if(temp == 1)
{
	warten(1);
	std::cout << ". ";
}
else
{
	warten(1);
	std::cout << "\b\b \b";
}
	
}
	temp *= -1;
}
warten(1);
}

void Chatten::warten(int sekunden)
{
clock_t start, end;
start = clock();
while(1)
{
end = clock();
if((end-start)/CLOCKS_PER_SEC == sekunden)
	break;
}
}

void Chatten::schreiben(std::string sagen)
{
	std::cout << Chatten::name + ": " + sagen + "\n";
}