#include <iostream>
#include <vector>
#include <locale>
using namespace std;

int main()
{
	locale loc("");
	locale::global(loc);
	
	const int CONTAINERGRÖßE = 33;
	vector <long long> container(CONTAINERGRÖßE);
	
	/*for(int i = 0; i < CONTAINERGRÖßE; ++i)
	{
	  cout << endl;
	  container[i] = pow(2.0,i);
	  cout << " Die " << i << ". Potenz von 2 lautet: "
		   << container[i];
	}*/

	//typedef vector<int>::iterator iterTyp
	int zähler = 0;
	for(vector<long long>::iterator it = container.begin(); it < container.end(); ++it)
	{
	  cout << endl;
	  *it = pow(2.0, zähler);
	  cout << " Die " << zähler++ << ". Potenz von 2 lautet: "
		   << *it;
	}
	cout << "\n";
	return 0;
}
