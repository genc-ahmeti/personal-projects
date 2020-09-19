/* Klasse Punkt Header
 *
 *
 */

#ifndef Schalter_Punkt
#define Schalter_Punkt

class Punkt
{
private: 
	int x, y;

public:
	Punkt();
	Punkt(int n, int m);
	
	void verschieben(int dx, int dy);
	void ausgeben();
};

#endif


