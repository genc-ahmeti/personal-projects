#include <iostream>
#include <cstdlib>
#include <ctime>
using namespace std;

bool Test(int Zahlen[], int* zeiger)
{
  for (; *zeiger < 49; (*zeiger)++)
  for (int i = 0; i < 49; i++)
  {
	 if (i == *zeiger)
	  continue;
  else if (Zahlen[*zeiger] == Zahlen[i])
    {
	  return true;
    }
  
  }
}

int main()
{
	int* merk_n = new int;
	*merk_n = 0;
	time_t t;
	srand((unsigned) time(&t));

	const int LOTTOZAHLEN = 49;
	int zahlen[LOTTOZAHLEN];


	for (int i = 0; i < LOTTOZAHLEN; i++)
	zahlen[i] = (rand() % 49) + 1;
	 
do
{
if (Test(zahlen, merk_n))
if (*merk_n != 49)
zahlen[*merk_n] = (rand() % 49) + 1;
}
while(*merk_n < 49);
	
	

	cout << endl;
	cout << " Ihre persoenlichen Lottozahlen lauten: " << endl << endl << endl;

for (int i = 0; i < LOTTOZAHLEN; ++i)
	{
		if (zahlen[i] < 10)
		cout << " [" << zahlen[i] << " ] ";
		else
		cout << " [" << zahlen[i] << "] ";
	    if (i == 6 ||i == 13 ||i == 20 ||i == 27 ||i == 34 ||i == 41)
			cout << endl << endl;
	}

	int summe = 0;
	for (int i = 0; i < LOTTOZAHLEN; i++)
		summe += zahlen[i];
	cout << endl << endl << endl << summe << endl << endl << endl;
	cout << endl << endl;
	delete (merk_n);
	return 0;

}