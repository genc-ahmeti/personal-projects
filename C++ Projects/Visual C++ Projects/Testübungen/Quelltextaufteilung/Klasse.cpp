#include <iostream>
#include "Punkt.h"

	Punkt::Punkt()
	{
		x = 0;
		y = 0;
	}

	Punkt::Punkt(int n, int m)
	{
		x = n;
		y = m;
	}

	void Punkt::verschieben(int dx, int dy)
	{
		x += dx;
		y += dy;
	}

	void Punkt::ausgeben()
	{
		std::cout << "(" << x << "," << y << ")";
	}
