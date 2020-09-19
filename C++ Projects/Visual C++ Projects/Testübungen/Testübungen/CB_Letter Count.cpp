//#include <iostream>
//#include <vector>
//# include <string>
//using namespace std;
//
//string MultipleLetters(string satz)
//{
//	vector <string> wort (10);
//    int i = 0;
//	int merk = -1;
//	satz[0] = tolower(satz[0]);
//	
//	for(int n = 0; n <= satz.length(); n++)
//	{
//		while(n < satz.length() && satz[n] != ' ')
//			wort[i] += satz[n++];
//		++i;
//	}
//	i = 1;
//
//	int temp = 0;
//	for(int element = 0; element < wort.size(); element++)
//	{
//		if(wort[element].empty())
//		break;
//	for(int car = 0; car < wort[element].length(); car++)
//	{
//	
//		for(int n = 0; n < wort[element].length(); n++)
//	{
//		if(car != n && wort[element][car] == wort[element][n])
//			++temp;
//	}
//	
//		if(temp > i)
//		{
//			i = temp;
//			merk = element;
//		}
//	temp = 0;
//	}
//}
//	if(merk != -1)
//		return wort[merk];
//	else
//		return "-1";
//	 
// 
//}
//
//int main()
//{
//	string satz = "Hahaha hiiiiiiiiii lllolololoseses";
//
//	cout << MultipleLetters(satz);
//
//	return 0;
//
//}