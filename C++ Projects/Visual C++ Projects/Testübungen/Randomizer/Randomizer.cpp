#include <iostream>
#include <ctime>
#include <vector>
#include <string>                          // VERGISS STRING-HEADER NICHT!!!!!!!
using namespace std;

int main()
{
	
int anzahlSpieler;
cout << "Wie viele Spieler?: ";
cin >> anzahlSpieler;
vector <string> namen(anzahlSpieler);

for(int n = 0; n < anzahlSpieler; n++)
{
	cout << "Gib den " << n + 1 << ". Namen ein: ";
	cin >> namen[n];
}
	
	time_t t;
	
	srand((unsigned int) time(&t));
	
	int random = rand() % 3;
	 cout << endl << endl << namen[random];
	
	 cin.get();
	 cin.get();
	 
	 return 0;
}