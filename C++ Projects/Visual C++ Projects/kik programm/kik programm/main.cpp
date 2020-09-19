#include <iostream>
#include <string>
#include <vector>
#include <sstream>
#include <ctime>
#include <cmath>
/*

*Write a program for finding an equation of form
*N ? N ? N ? N ? N = x
* where:
* N - int from 0...9
* ? - operator (+-x/)
* x - int from 0...90

* The x value will be passed to function. 
* Each operator must be used once. No repetition rules on n.

*/

char getRandomOperator(std::string text)
{
	char operators[4] = {'+','-', '*', '/'};
	char chosenOperator;
	bool mark = true;

	while(1)
	{
		chosenOperator = operators[rand() % 4];
		for(int n = 1; n < text.length() && mark == true; n += 2)
			if(text[n] == chosenOperator)
				mark = false;
		if(mark == true)
			return chosenOperator;
		mark = true;
	}
}

std::string fromNumberToString(int number)
{
std::stringstream sstream;
std::string numberStr;

sstream << number;
sstream >> numberStr;

return numberStr;
}

double getValueOfEquation(std::string equation)
{	
	double valueOfEquation;
	std::string equationCopy = equation;
	std::stringstream sstream;

int posMultiplication = equation.find('*');
int posDivision = equation.find('/');

int posOperatorFirst, posOperatorSecond;

if(posMultiplication < posDivision)
 	{posOperatorFirst = posMultiplication;
     posOperatorSecond = posDivision;
}
else
	{ posOperatorSecond = posMultiplication;
     posOperatorFirst = posDivision;
}

double temp1 = 0;
double temp2 = 0;
std::string tempStr("");
// calculate first term
sstream << equation[posOperatorFirst-1];
sstream >> temp1;
sstream.clear();

sstream << equation[posOperatorFirst+1];
sstream >> temp2;
sstream.clear();
switch(equation[posOperatorFirst])
{
case '+': temp1 = temp1 + temp2;
		  break;
case '-': temp1 = temp1 - temp2;
		  break;
case '*': temp1 = temp1 * temp2;
		  break;
case '/': temp1 = temp1 / temp2;
		  break;
}
sstream << temp1;
sstream >> tempStr;
sstream.clear();
std::string a1 = equation;
equation.replace(posOperatorFirst-1, 3, tempStr);
std::string aa1 = equation;
posOperatorSecond += equation.length()-9;
//calculate second term
if(equation.find('*') == -1)
posOperatorSecond = equation.find('/');
else
posOperatorSecond = equation.find('*');
temp1 = 0;
temp2 = 0;
tempStr = "";
int fall = 0;
if(equationCopy.find('*') - equationCopy.find('/') == 2 || equationCopy.find('*') - equationCopy.find('/') == -2)
{
if(posOperatorSecond < equation.find('+') && posOperatorSecond < equation.find('-'))
{
	if(equation.find('+') < equation.find('-'))
	{
		sstream << equation.substr(0, posOperatorSecond);
		fall = 1;
	}
	else
	{
		sstream << equation.substr(0, posOperatorSecond);
		fall = 2;
	}
	
}
else if(posOperatorSecond > equation.find('+') && posOperatorSecond > equation.find('-'))
{
	if(equation.find('+') < equation.find('-'))
	{
		sstream << equation.substr(equation.find('-') + 1, posOperatorSecond - equation.find('-') - 1);
		fall = 3;
	}
	else
	{
		sstream << equation.substr(equation.find('+') + 1, posOperatorSecond - equation.find('+') - 1);
		fall = 4;
	}
}
else
{
	if(equation.find('+') < equation.find('-'))
	{
		sstream << equation.substr(equation.find('+') + 1, posOperatorSecond - equation.find('+') - 1);
		fall = 5;
	}
	else
	{
		sstream << equation.substr(equation.find('-') + 1, posOperatorSecond - equation.find('-') - 1);
		fall = 6;
	}
}
}
else
{
	sstream << equation[posOperatorSecond-1];
}
sstream >> temp1;
sstream.clear();

sstream << equation[posOperatorSecond+1];
sstream >> temp2;
sstream.clear();
switch(equation[posOperatorSecond])
{
case '+': temp1 = temp1 + temp2;
		  break;
case '-': temp1 = temp1 - temp2;
		  break;
case '*': temp1 = temp1 * temp2;
		  break;
case '/': temp1 = temp1 / temp2;
		  break;
}
sstream << temp1;
sstream >> tempStr;
sstream.clear();
if(equation == "54/4+9-7")
std::string a4 = equation;
if(fall != 0)
switch(fall)
{ 
case 1: equation.replace(0, equation.find('+'), tempStr);
		break;
case 2: equation.replace(0, equation.find('-'), tempStr);
	break;
case 3: equation.replace(equation.find('-') + 1, equation.length()-1-equation.find('-'), tempStr);
	break;
case 4: equation.replace(equation.find('+') + 1, equation.length()-1-equation.find('+'), tempStr);
	break;
case 5: equation.replace(equation.find('+') + 1, equation.find('-')-1-equation.find('+'), tempStr);
	break;
case 6: equation.replace(equation.find('-') + 1, equation.find('+')-1-equation.find('-'), tempStr);
	break;
}
else
	
{equation.replace(posOperatorSecond-1, 3, tempStr);}

std::string a2 = equation;
int posAddition = equation.find('+');
int posSubtraction = equation.find('-');

if(posAddition < posSubtraction)
 	{posOperatorFirst = posAddition;
     posOperatorSecond = posSubtraction;
}
else
	{ posOperatorSecond = posAddition;
     posOperatorFirst = posSubtraction;
}
// calculate resulting term

//calculate first part
temp1 = 0;
temp2 = 0;

sstream << equation.substr(0, posOperatorFirst);
sstream >> temp1;
sstream.clear();

sstream << equation.substr(posOperatorFirst+1,posOperatorSecond-posOperatorFirst-1);
sstream >> temp2;
sstream.clear();
switch(equation[posOperatorFirst])
{
case '+': temp1 = temp1 + temp2;
		  break;
case '-': temp1 = temp1 - temp2;
		  break;
case '*': temp1 = temp1 * temp2;
		  break;
case '/': temp1 = temp1 / temp2;
		  break;
}
sstream << temp1;
sstream >> tempStr;
sstream.clear();

equation.replace(0,posOperatorSecond,tempStr);
std::string acdef = equation;
//calculate second part

if(equation.find('+') != -1)
posOperatorFirst = equation.find('+');
 else
posOperatorFirst = equation.find('-');

temp1 = 0;
temp2 = 0;

std::string acdse = equation.substr(0, posOperatorFirst);
sstream << equation.substr(0, posOperatorFirst);
sstream >> temp1;
sstream.clear();

sstream << equation.substr(posOperatorFirst+1);
sstream >> temp2;
sstream.clear();
switch(equation[posOperatorFirst])
{
case '+': temp1 = temp1 + temp2;
		  break;
case '-': temp1 = temp1 - temp2;
		  break;
case '*': temp1 = temp1 * temp2;
		  break;
case '/': temp1 = temp1 / temp2;
		  break;
}
std::string acd = equation;

valueOfEquation = temp1;



return valueOfEquation;
}

//bool numberNotIncludedInString(std::string text, int number)
//{
//	std::string numberStr;
//	std::stringstream sstream;
//	sstream << number;
//	sstream >> numberStr;
//
//	if(!text.length() > 1 && number == 0 && text.back() == '/')
//		return false;
//	for(int n = 0; n < text.length(); n= n+2)
//		if(text[n] == numberStr[0])
//				return false;
//	return true;
//}

bool divisionPossible(std::string text, int number)
{
	return !((text.length() > 1) && (number == 0) && (text.back() == '/'));
}

std::string findEquation(double x)
{
  while(1)
  {
	int randomNumber;
  std::string equation = "";
  for(int n = 0; n < 5; n++)
  {
	randomNumber = rand() % 10;
	if(!divisionPossible(equation, randomNumber))
	{
		n--;
		continue;
		
	}
	else
		equation += fromNumberToString(randomNumber);
	if(n != 4)
	{
    equation += getRandomOperator(equation);

	}
  }
   if(x == getValueOfEquation(equation))
	   return equation + " = " + fromNumberToString(getValueOfEquation(equation));
}
}

  int main(int argc, char* argv[])
{
 clock_t start, ending;
 long diff;
 double tmp;
 const int AMOUNT_OF_EQUATIONS = 90;
 double x = 3.0;
 for(int n = 0; n < AMOUNT_OF_EQUATIONS + 1; n++)
 std::cout << findEquation(n) << "\n";
 std::cout << "done";
 //start = clock();
 //for(int n = 0; n < 91; n++)
 //    findEquation(n);
 //ending = clock();
 //diff = ending - start;
 //std::cout << "It took " << diff/CLOCKS_PER_SEC << "seconds to execute!";

}