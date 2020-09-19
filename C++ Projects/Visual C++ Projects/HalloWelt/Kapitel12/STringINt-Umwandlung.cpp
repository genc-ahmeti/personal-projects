#include <iostream>
#include <string>
#include <sstream> // für die Umwandlung benötigt, also für Verwendung von sstream << / >> / .clear()
using namespace std;

int main()
{
	stringstream sstream; // das ist ein objekt der klasse <sstream>, die einlesen/umwandeln kann
	int zahl;
	string text1 = "Hi";
	string text;
	cout << " Geben Sie die Zahl ein: ";
	cin >> zahl;

	sstream << zahl;         // zahl eingelesen
	sstream >> text;         // hier als string in variable text eingespeichert
	text1 += text;

	cout << text1;

	sstream << text;          // Umwandlung rückgangi gemacht
	sstream >> zahl;
	zahl *= -1;

	cout << zahl;

		return 0;
}