#include <iostream>
#include <vector>
#include <string>
#include <sstream>
using namespace std;

string ArithGeo(vector <double> container)
{
	double konstant;
	stringstream sstream;
	konstant = container[1] - container[0];

	for(int n = 2; n < container.size(); n++)
	{
		if( container[n] != container[n - 1] + konstant)
			break;
		
		if(n == container.size() - 1)
			return "Arith"; 
	}

	konstant = container[1] / container[0];

	for(int n = 2; n < container.size(); n++)
	{
		if( container[n] != container[--n] * konstant)
				break;
		
		if(n == container.size() - 1)
				return "Geo"; 
	}

	return "Nichts";
}


int main()
{

	double arr [] = {1,2,3,4,5,6,7,7};
	vector <double> zahlen (sizeof(arr)/sizeof(double));

	for(int n = 0; n < zahlen.size(); n++)
		zahlen[n] = arr[n];
	
	cout << ArithGeo(zahlen);
}