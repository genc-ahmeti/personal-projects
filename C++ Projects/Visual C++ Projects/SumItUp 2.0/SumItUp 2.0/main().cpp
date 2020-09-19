#include <iostream>
#include <fstream>
#include <vector>
#include <locale>
#include <string>
#include <cctype>    //isupper()
#include <iterator>  //next(), eigentlich auch f�r it.begin()/end()
using namespace std;


void WortFilter(vector <string> & container, vector <string> &neu_container)    // Entnimmt die einzelnen W�rter aus einem string vom container, speichert es in ein neuen container; behandelt auch zahlen als w�rter
{
  string wort;
  vector<string>::iterator it = container.begin();
  unsigned int n = 0;
  
  while(1)
  {
	  if(n < (*it).length())                     //keine undefinierten Platz im string benutzen
	  {
	  if((*it)[n] != ' ')                                              //whitespace zeigt neues wort kommt
	  {	
		  wort += (*it)[n++];                                           //temp�r�res string wird erweitert
		
		  //*************** Wichtig f�r das letzte Wort eines Satzes  
		  if (n == (*it).length())
		  {
			  neu_container.push_back(wort);
			  wort = "";  
		  }
		  //********************
	  }
	    
		  else
			   {
				neu_container.push_back(wort);                           // string hinzugef�gt
				n++;                                                      // neuer buchstabe im index, damit der whitespace von vorhin weg ist
			    wort = "";                                                // wort leeren, damit nicht das vorherige zus�tzlich genommen wird
	  }
	  }
else if (it != --container.end())                                   // wenn das ende erreicht, neue container_index und man f�ngt bei buchstabe von vorne an
{
	it++;
	n = 0;
	  }
else
	break;                                                          // maximale container_index erreicht, also raus aus der schleife
		
  }
}

bool istAmSatzanfang (string s, vector<string> s�tze)       //noch werden auch Nomen am Anfang nicht aufgenommen
{
  vector<string>::iterator it = s�tze.begin();
  while(it != s�tze.end())
  {
	  if((*it).find(s) == 0)
		  return true;
	 it++;
  }
return false;
}

bool istSchonVorhanden (string wort, vector<string>& nomen)
{
	const int MAX_L�NGE = 5;
	
	if(nomen.empty())
		return false;
	
	for(vector<string>::iterator it = nomen.begin(); it != nomen.end(); it++)
	{
		if(wort.length() < MAX_L�NGE)                                      //Bei w�rtern mit 4, 3 oder 2 Buchstaben
		{
			if((*it).find(wort) != string::npos)                   
				return true;
		}
		else 
		{
			if((*it).find(wort.substr(0,MAX_L�NGE)) != string::npos)        //sonst alle w�rter so machen, also 5 buchstaben von anfang nehmen, das reicht zur identifikation
			return true;
		}
	}
	
return false;
}

string reinigen(string& wort)             //wenn letzter char kein buchstabe ist, dann soll er gel�scht werden
{
	if(!isalpha(wort.back()))
	  wort = wort.substr(0, wort.length()-1);
	 
	return wort;
}

void NomenFilter (vector<string>& s�tze, vector <string>& w�rter, vector <string>& nomen)
{
	unsigned int n = 0;
	vector<string>::iterator it = w�rter.begin();

	while (n++ < w�rter.size())
	{
		if((*it)[0] > 64 && isupper((*it)[0]) && !istAmSatzanfang(*it, s�tze) && !istSchonVorhanden(reinigen(*it), nomen))
			nomen.push_back(*it);
		it++;
	}
}



int main()
{
	locale loc("");
	locale::global(loc);
	
	vector <string> s�tze;
	vector <string> w�rter;
	vector <string> nomen;

	char eingelesen[1000];
	ifstream datei_lesen;
	datei_lesen.open("C:/Users/Genc Ahmeti/Documents/Desktop/HalloWelt.txt", ios_base::in);

	while(!datei_lesen.eof())
	{
		datei_lesen.getline(eingelesen, 1000);
		s�tze.push_back(eingelesen);
	}
	datei_lesen.close();
    WortFilter(s�tze, w�rter);
	NomenFilter(s�tze, w�rter, nomen);
	cout << "hi";
	return 0;

}