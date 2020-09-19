#include <iostream>
#include <string>
#include <vector>
using namespace std;
void StrZuweisen (string satz, vector <string> *container)
{
	int n = 0;
	string temp;
    for(int i = 0; i < 5 ; i++)
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

void StrReinigen (vector <string> & container)
{
	
	for(int n = 0; n < container.size(); n++)
	while(container[n].back() <= 64)
    container[n].pop_back();

}



int main()
{
	
	string satz = "Hallo, ich.. bin... ein.... Mann.....";
    vector <string> container(5);
	StrZuweisen(satz,&container);
	//StrReinigen(container);
	//cout << container[0] << endl << container[1]<< endl << container[2] << endl << container[3] << endl << container[4] << endl << endl;

	
	
	return 0;

}