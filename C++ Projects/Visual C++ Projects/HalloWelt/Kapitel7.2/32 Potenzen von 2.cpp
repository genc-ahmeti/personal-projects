#include <iostream>
#include <vector>
#include <locale>
using namespace std;

int main()
{
	locale loc("");
	locale::global(loc);
	
	const int CONTAINERGR��E = 33;
	vector <long long> container(CONTAINERGR��E);
	
	/*for(int i = 0; i < CONTAINERGR��E; ++i)
	{
	  cout << endl;
	  container[i] = pow(2.0,i);
	  cout << " Die " << i << ". Potenz von 2 lautet: "
		   << container[i];
	}*/

	//typedef vector<int>::iterator iterTyp
	int z�hler = 0;
	for(vector<long long>::iterator it = container.begin(); it < container.end(); ++it)
	{
	  cout << endl;
	  *it = pow(2.0, z�hler);
	  cout << " Die " << z�hler++ << ". Potenz von 2 lautet: "
		   << *it;
	}
	cout << "\n";
	return 0;
}
