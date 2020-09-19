#include <iostream>

int main(int argc, char* argv[])
{
int sum = 0;

for(int n = 12; n < 21; n++)
	sum += 7*n;
std::cout << sum;
return 0;
}