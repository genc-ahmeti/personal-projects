#include<iostream>
#include <ctime>
using namespace std;

void warten(int sekunden)
{
	clock_t zeit1 , zeit2;
 
zeit1 = clock();
while(1)
{
	zeit2= clock();
	if((zeit2-zeit1)/CLOCKS_PER_SEC == sekunden)
	break;
}
}

int main()
{
cin.get();
cout << "Hallo Dielle,\n";
warten(3);
cout << "- Ja?\n";
warten(2);
cout <<"Ich muss DIR was sagen!\n";
warten(2);
cout << "- Was denn? :D\n";
warten(3);
cout <<"Ich ";
warten(5);
cout <<" ...";
warten(5);
cout << " Informatik ist cool, :D\n";
warten(3);
cout << "- Ok.\n";
warten(3);
cout << "Haha, voll lustig, oder?\n";
warten(3);
cout << "- Ne.\n";
cin.get();
cin.get();
return 0;
}