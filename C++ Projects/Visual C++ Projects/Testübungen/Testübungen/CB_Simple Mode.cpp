//#include <iostream>
//#include <vector>
//using namespace std;
//
//int ArrayDurchschauen(vector <int> &container)
//{
//  int anzahl;
//  int temp = 1;
//  
//  int merk = -1;
//  
//  for(int i = 0; i <  container.size(); i++)
//  {
//	  
//  for(int n = 0; n < container.size(); n++)
//  {
//	  if(i != n && container[i] == container[n])
//	  ++temp;
//  }
//  
//  if(merk == -1 || temp > anzahl)
//  {
//	  merk = i;
//	  anzahl = temp;
//  }
//  
//  temp = 1;
//  }
//  
//  if(merk != -1)
//  return container[merk];
//  else
//	  return -1;
//
//}
//
//
//int main()
//{
//	
//	int arr[] = {1,1,1,2,2,2};
//	vector <int> aa(sizeof(arr) / 4);
//	
//    for(int n = 0; n < aa.size(); n++)
//		aa[n] = arr[n];
//
//cout << ArrayDurchschauen(aa) << endl << endl;
//}