#include <iostream>
#include <string>
using namespace std;

string wochentag (int tag, int monat, int jahr, int jahrhundert)
{
	int wochentag = static_cast<int>((tag + (2.6 * monat - 0.2) + jahr + jahr / 4 + jahrhundert / 4 - 2 *jahrhundert)) % 7;
	wochentag = (wochentag + 1) % 7;

	switch(wochentag)
	{
	case 0: return "Montag"; 
		case 1: return "Di"; 
			case 2: return "Mi";
				case 3: return "Do"; 
					case 4: return "Frei"; 
						case 5: return "Sam"; 
							case 6: return "So"; 
	}

}


int main()
{	
	
	cout << endl << wochentag(17,10,16,20);
return 0;
}