#include <iostream>
using namespace std;

	class Zahl
	{
	public:
		
		int wert;	
		Zahl()
		{
			wert = 0;
		}
		Zahl(int a)
		{
			wert = a;
		}
		
	};

int ggT(Zahl a, Zahl b)
{
	int hilf = 1;
	if (a.wert < b.wert)
	{
		hilf = a.wert;
		a.wert = b.wert;
		b.wert = hilf;
	}
	
	while(hilf != 0)
	{
		hilf = a.wert % b.wert;
		a.wert = b.wert;
	 b.wert = hilf;
	
}
	return a.wert;
}




int main(int argc, char* argv[]) 
{
	Zahl Eins(50),Zwei(10);

	cout << ggT(Eins,Zwei);
	

	return 0;
}