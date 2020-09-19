#include <iostream>
#include <string>
#include <vector>
using namespace std;

string LongestWord(string sen) { 

	vector<string> wort(20);
int n = 0;
int i = 0;
string temp;
for(;n < sen.length();)
{
while((n < sen.length() && isalpha(sen[n]) || n < sen.length() && isdigit(sen[n])))
	temp += sen[n++];
	wort[i++] = temp;
	++n;
	temp = "";
}

int laenge = 0;
int merk;
for(int n = 0; n < wort.size(); n++)
{
	if(laenge < wort[n].length())
	{
		laenge = wort[n].length();
		merk = n;
	}
}


  return wort[merk]; 
            
}
int main() 
{ 
	char c = toupper('c');
	string ergebnis = LongestWord("a confusing /:sentence:/[ this is not!!!!!!!~");
	cout << ergebnis;
	cout << endl << ergebnis<< endl << ergebnis<< endl << ergebnis;
	cout << endl << endl << c;
	return 0;
    
} 