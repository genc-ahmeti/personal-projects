function mutierteKinder = mutatePopulation(kinder, mutationswahrscheinlichkeit, kinderKonstruktor, NACHRICHTENAUSSCHALTEN) 
  
  mutierteKinder = kinder;
  
  anzahlKinder = size(kinder,1);
  anzahlEigenschaften = size(kinder,2);
  
  % Mutation möglich
  wahrscheinlichkeiten = (rand(anzahlKinder,1)) <= mutationswahrscheinlichkeit; 
  anzahlZuTauschenderEig = randi(anzahlEigenschaften+1,anzahlKinder,1)-1;
  
  for i=1:anzahlKinder
    
    welcheEig = (randperm(anzahlEigenschaften))(1:anzahlZuTauschenderEig(i));
    
    if ~NACHRICHTENAUSSCHALTEN
      display('Mutation');
    endif
    
    if ((anzahlZuTauschenderEig(i) ~= 0) && (wahrscheinlichkeiten(i) == 1))
      mutierteKinder(i, welcheEig) = (kinderKonstruktor(1))(welcheEig);
    endif
  endfor
  
endfunction