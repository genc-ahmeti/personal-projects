#include <iostream>
#include <string>
#include <fstream>
#include <vector>
#include <locale>
using namespace std;

void StrZuweisenExtra (string satz, vector <string> *container)
{
	int n = 0;
	string temp;
    for(int i = 0; i < (*container).size() ; i++)
	{
		for(; n < satz.length(); n++)
	 {
		 if (satz[n] == ' ')
			 break;
		 else
		 temp += satz[n];
	 }
		(*container)[i] = temp;
		cout << (*container)[i] << endl;
		n++;
		temp ="";
	}
}
	

void StrZuweisen (string satz, vector <string> *container, int *i)
{
	int n = 0;
	string temp;
    for(;;)
	{
		for(;n < satz.length(); n++)
	 {
		 if (satz[n] == ' ')
			 break;
		 else
		 temp += satz[n];
	 }
		
		(*container)[(*i)++] = temp;
		if(n == satz.length())
			break;
		++n;
		temp ="";
		
	}
}

string StrReinigen (string wort)              // Reinigen hier: von allen anderen Zeichen befreien
{
	while(wort.back() <= 64)
	wort.pop_back();

	return wort;
}

int Haeufigkeit (vector <string> &container, int stelle)
	{
		int zaehler = 0;
		if (container[stelle] != "" && container[stelle].front() <= 91)
		{
			++zaehler;
			for(int n = 0; n < (container).size(); n++)
			{
				if(n != stelle && container[n].find(container[stelle]) != -1)
				++zaehler;
			}
		}
	 
	return zaehler;
}



int main()
{
	locale loc("");               // Erzeugt eine Lokale mit Information aus meiner Region (Deutsche Lokale) (v.a. Umlaute)
	locale::global(loc);          // Genau diese Lokale soll verwendet werden
	
	const int ANZAHL_WOERTER = 1000;
	const int MAX_CHARS = ANZAHL_WOERTER;
	vector <string> textspeicher(ANZAHL_WOERTER);
	vector <int> haeufigkeit(ANZAHL_WOERTER);
	vector <string> Nomen(ANZAHL_WOERTER);
	
	char text[MAX_CHARS];
	string dateiname = "C:/Users/Genc Ahmeti/Documents/Desktop/HalloWelt.txt";
	ifstream datei;
	datei.open(dateiname.c_str(), ios_base::in);
	
	

	int n = 0;
	int i = 0;
	int temp_int = 0;
	string temp_string;
	
	while(!datei.eof())
	{
		datei.getline(text, MAX_CHARS);
		
		StrZuweisen(text, &textspeicher, &i);
	}
	
	datei.close();

	for(int n = 0; n < textspeicher.size(); n++)
		if(textspeicher[n] != "")
		haeufigkeit[n] = Haeufigkeit(textspeicher,n);
	
	for(int n = 0; n < textspeicher.size(); n++)
		if(textspeicher[n] != "" && haeufigkeit[n] != 0)
		{
			cout << n + 1 << ". Wort: " << textspeicher[n] << " " << haeufigkeit[n]
	         << " mal im Text vorhanden. \n\n" ;
			Nomen[temp_int++] = StrReinigen(textspeicher[n]);
		}

for(int n = 0; n < Nomen.size(); n++)
{
	if(Nomen[n] == "")
	{
		temp_int = n;
		break;
    }

}
Nomen.resize(temp_int);


for(int n = 0; n < Nomen.size(); n++)
		haeufigkeit[n] = Haeufigkeit(Nomen,n);

vector <string> haeufigeNomen (temp_int);

temp_int = 0;
int marker = 0;
for(int n = 0; n < Nomen.size(); n++)
{
	if(haeufigkeit[n] >= 7)
		{
				for(int i = 0; i < haeufigeNomen.size(); i++)
						if(marker == 0 && Nomen[n] == haeufigeNomen[i])
								marker = 1;
				if(marker == 0)
				haeufigeNomen[temp_int++] = Nomen[n];
		}
	marker = 0;
}

for(int n = 0; n < haeufigeNomen.size(); n++)
		if(haeufigeNomen[n] == "")
			temp_int == n;
haeufigeNomen.resize(temp_int);



ofstream datei_2;
string dateiname_2 = "C:/Users/Genc Ahmeti/Documents/Desktop/HalloWelt_kurz.txt";
marker = 0;
vector <string> textspeicher2 (ANZAHL_WOERTER);
datei.open(dateiname.c_str(), ios_base::in);
vector <string> zusammenfassung (ANZAHL_WOERTER);
int o = 0;
int p = 0;

while(!datei.eof())
	{
		datei.getline(text, MAX_CHARS);
		StrZuweisenExtra(text, &textspeicher2);
		for(int n = 0; n < haeufigeNomen.size(); n++)
		{
			for(int i = 0; i < textspeicher2.size(); i++)
					if(textspeicher2[i].find(haeufigeNomen[n]) != -1)
					{
						marker = 1;
						break;
					}
			
		
			if (marker == 1)
				break;
				
		}

		if(marker == 1)
			StrZuweisen(text, &zusammenfassung, &p);
		marker = 0;
    }
	
	datei.close();
datei_2.open(dateiname_2.c_str(),ios_base:: out);
for(int n = 0; n < zusammenfassung.size(); n++)
	datei_2 << zusammenfassung[n] << " ";
datei_2.close();

return 0;
}



