#include <iostream>
#include <fstream>
#include <vector>
#include <locale>
#include <string>
#include <cctype>    //isupper()
#include <iterator>  //next(), eigentlich auch für it.begin()/end()
using namespace std;


void WortFilter(vector <string> & container, vector <string> &neu_container)    // Entnimmt die einzelnen Wörter aus einem string vom container, speichert es in ein neuen container; behandelt auch zahlen als wörter
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
		  wort += (*it)[n++];                                           //tempöräres string wird erweitert
		
		  //*************** Wichtig für das letzte Wort eines Satzes  
		  if (n == (*it).length())
		  {
			  neu_container.push_back(wort);
			  wort = "";  
		  }
		  //********************
	  }
	    
		  else
			   {
				neu_container.push_back(wort);                           // string hinzugefügt
				n++;                                                      // neuer buchstabe im index, damit der whitespace von vorhin weg ist
			    wort = "";                                                // wort leeren, damit nicht das vorherige zusätzlich genommen wird
	  }
	  }
else if (it != --container.end())                                   // wenn das ende erreicht, neue container_index und man fängt bei buchstabe von vorne an
{
	it++;
	n = 0;
	  }
else
	break;                                                          // maximale container_index erreicht, also raus aus der schleife
		
  }
}

bool istAmSatzanfang (string s, vector<string> sätze)       //noch werden auch Nomen am Anfang nicht aufgenommen
{
  vector<string>::iterator it = sätze.begin();
  while(it != sätze.end())
  {
	  if((*it).find(s) == 0)
		  return true;
	 it++;
  }
return false;
}

bool istSchonVorhanden (string wort, vector<string>& nomen)
{
	const int MAX_LÄNGE = 5;
	
	if(nomen.empty())
		return false;
	
	for(vector<string>::iterator it = nomen.begin(); it != nomen.end(); it++)
	{
		if(wort.length() < MAX_LÄNGE)                                      //Bei wörtern mit 4, 3 oder 2 Buchstaben
		{
			if((*it).find(wort) != string::npos)                   
				return true;
		}
		else 
		{
			if((*it).find(wort.substr(0,MAX_LÄNGE)) != string::npos)        //sonst alle wörter so machen, also 5 buchstaben von anfang nehmen, das reicht zur identifikation
			return true;
		}
	}
	
return false;
}

string reinigen(string& wort)             //wenn letzter char kein buchstabe ist, dann soll er gelöscht werden
{
	if(!isalpha(wort.back()))
	  wort = wort.substr(0, wort.length()-1);
	 
	return wort;
}

void NomenFilter (vector<string>& sätze, vector <string>& wörter, vector <string>& nomen)
{
	unsigned int n = 0;
	vector<string>::iterator it = wörter.begin();

	while (n++ < wörter.size())
	{
		if((*it)[0] > 64 && isupper((*it)[0]) && !istAmSatzanfang(*it, sätze) && !istSchonVorhanden(reinigen(*it), nomen))
			nomen.push_back(*it);
		it++;
	}
}



int main()
{
	locale loc("");
	locale::global(loc);
	
	vector <string> sätze;
	vector <string> wörter;
	vector <string> nomen;

	char eingelesen[1000];
	ifstream datei_lesen;
	datei_lesen.open("C:/Users/Genc Ahmeti/Documents/Desktop/HalloWelt.txt", ios_base::in);

	while(!datei_lesen.eof())
	{
		datei_lesen.getline(eingelesen, 1000);
		sätze.push_back(eingelesen);
	}
	datei_lesen.close();
    WortFilter(sätze, wörter);
	NomenFilter(sätze, wörter, nomen);
	cout << "hi";
	return 0;

}