#include <iostream>
using namespace std;

int main()
{
	int *zeiger;
	zeiger = NULL;                                         // wenn du nicht weiﬂt, welche adresse es bekommen soll
	int a[3] = {1, 2, 3};
	int n = 1;
	int *n_ptr = &n;                                        // bei definition wie variable, nur mit "*" wo du willst :D
	int* ptr = &n;                                           // Das sieht man hier
	// *ptr gibt den wert der adresse, ptr als variable die adresse
	cout << endl << endl << *n_ptr << endl;
	cout << n_ptr << endl;
	cout << *ptr << endl;

	*ptr = 3;                                               // zuweisung eies werts in die variable der adresse
	cout << n << endl;
	ptr = a;                                             // Man braucht bei arrays mit zeigern keinen adressoperator "&"
	cout << ptr[1] << endl;                              // geht auch: *(ptr + 1)
	
	// Referenz
	int x = 4;                                   
	int &referenz = x;                                   // f¸r literale varaiblen, bei initialiesieurng adresse ohne " & ", wobei das auf der linken seite steht

	cout << endl << endl << referenz;
	referenz = 20;                                       // kann nun wie variable verwendet werden, das auf die var x zeigt
	cout << endl << x;

	double var = 665;
	double *var_ptr = &var;
    ++*var_ptr;
    cout << endl << *var_ptr;
	double &var_ref = var;
	var_ref = 0;
	var_ref = n;
	cout << endl << var;
	cout << endl << endl;
	
	int *pointer = NULL;
	pointer = new int;                 // Man reserviert Speicher f¸r eine integer-zhal
    int bild = 12;
	*pointer = bild;
	cout << *pointer << endl << bild << endl << pointer << endl << &bild << endl;
	delete pointer;                    // Speicher wird freigegeben, aber Pointer kann weiter benutzt werden
	pointer = NULL;
	
    
	
	return 0;
}