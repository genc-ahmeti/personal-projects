#include <iostream>
#include "head_Vektor.h"
int main()
{
	Vektor Eins(3.0, 4.0);
	Vektor Zwei(2.0, 1.5);

	Eins.addieren_mit(Zwei)->get_Koordinaten();
	Zwei.subtrahieren_mit(Eins)->get_Koordinaten();
	Vektor::Vektoraddition(Eins,Zwei)->get_Koordinaten();
	Vektor::Vektorsubtraktion(Eins,Zwei)->get_Koordinaten();
	
return 0;
}