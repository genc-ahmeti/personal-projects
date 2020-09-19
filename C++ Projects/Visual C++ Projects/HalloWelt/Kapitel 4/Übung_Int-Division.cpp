#include <iostream>
using namespace std;

int main()
{
	int x = 5, y = 2;
    double ergebnis = static_cast<double>(x) / y;

cout << " Das Ergebnis der Division " << x << " / " << y;
cout << " ergibt: " << ergebnis << endl;

	x = 5;
    double y2 = 2.0;
	double ergebnis2 = x / y2;

cout << " Das Ergebnis der Division " << x << " / " << y2;
cout << " ergibt: " << ergebnis2 << endl;

return 0;
}



