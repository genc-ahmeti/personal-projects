//////////////// Klasse Vektor
/*
* Zweidimensionale Vektoren
* Addition
* Subtraktion
*/
#ifndef head_Vektor
#define head_Vektor

class Vektor
{
private:
	double x1;   // x1- Koordinate(Abszisse)
	double x2;	// x2- Koordinate(Ordinate)

public:
	// Konstruktoren
	Vektor();                            
	Vektor(double _x1, double _x2);
	Vektor(int _x1, int _x2);
    
	//Get-/Set-Memberfunktionen
	void set_Koordinaten(double x1, double x2);
	void get_Koordinaten();

	// restlichen Memberfunktionen
	Vektor* addieren_mit(Vektor& obj);
	Vektor* subtrahieren_mit(Vektor& obj);
	static Vektor* Vektoraddition(Vektor& vektor1, Vektor& vektor2);
	static Vektor* Vektorsubtraktion(Vektor& vektor1, Vektor& vektor2);
};
#endif