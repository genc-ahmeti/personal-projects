#include <iostream>

int sumOfOddNumbersRec(int number1, int number2)
{
	if(number1 % 2 == 0)
		number1++;
	
	if(number2 % 2 == 0)
		number2--;
	
	if(number2 == number1) 
		return number1;	
		
	return number2 + sumOfOddNumbersRec(number1, number2-2);
}

int sumOfOddNumbersIter(int number1, int number2)
{
	int sum = 0;
	for(int n = number1; n <= number2; n++)
		if(n % 2 != 0)
			sum+=n;
	
	return sum;
}


int main()
{
	int a = 32; 
	int b = 323;

		std::cout << sumOfOddNumbersRec(a, b);
	std::cout << std::endl << sumOfOddNumbersIter(a, b) << std::endl << std::endl;
	
return 0;
}