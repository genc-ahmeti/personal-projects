#include <iostream>
#include "head_Vektor.h"

Vektor::Vektor() : x1(0.0), x2(0.0) {}                            
Vektor::Vektor(double _x1, double _x2) : x1(_x1), x2(_x2) {}
Vektor::Vektor(int _x1, int _x2) : x1(static_cast <double> (_x1)), x2(static_cast <double> (_x2)) {}
	
	
	void Vektor::set_Koordinaten(double x1, double x2)
		{
		this->x1 = x1;
		this->x2 = x2;
		}
	void Vektor::get_Koordinaten()
		{
			std::cout << "Vektor: " << "\t" << "{ " << this->x1 << "\t}  \n"
				<< "        " << "\t" << "{ " << this->x2 << "\t}  \n\n";
		}
	Vektor* Vektor::addieren_mit(Vektor& obj)
	{
		this->x1 += obj.x1;
		this->x2 += obj.x2;

		return this;
	}
	Vektor* Vektor::subtrahieren_mit(Vektor& obj)
	{
		this->x1 -= obj.x1;
		this->x2 -= obj.x2;

		return this;
	}
	Vektor* Vektor::Vektoraddition(Vektor& vektor1, Vektor& vektor2)
	{
		Vektor* vektor_neu = new Vektor;
		vektor_neu->x1 = vektor1.x1+ vektor2.x1;
		vektor_neu->x2 = vektor1.x2+ vektor2.x2;

		return vektor_neu;
	}
	Vektor* Vektor::Vektorsubtraktion(Vektor& vektor1, Vektor& vektor2)
	{
		Vektor* vektor_neu = new Vektor;
		vektor_neu->x1 = vektor1.x1- vektor2.x1;
		vektor_neu->x2 = vektor1.x2- vektor2.x2;

		return vektor_neu;
	}