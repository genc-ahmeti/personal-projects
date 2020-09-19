#include <iostream>
using namespace std;

int main()
{
	const int ARRAYGROESSE = 3;
	const int ARRAYDIMENSION = 2;
	long long Array[ARRAYDIMENSION][ARRAYGROESSE];

	for(int Dim = 0; Dim < ARRAYDIMENSION; Dim++)
	for(int Groe = 0; Groe < ARRAYGROESSE; Groe++)
	{
		Array[Dim][Groe] = rand();
	    cout <<" Dimension Nr."<< Dim + 1 << " Platz Nr." << Groe + 1 << ": " << Array[Dim][Groe] << endl;
	}

	cout << endl << endl << endl;

	return 0;
}