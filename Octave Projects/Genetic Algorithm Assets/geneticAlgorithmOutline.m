function geneticAlgo(anzahlGenerationen)

    
  %-----------------------
  
  
  %        MIN MAX VARIABLES
  
  
  %--------------------------------
  
  
  costFunction = @(children) (...children...);
  kinderKonstruktor = @(anzahlKinder) (...anzahlKinder...);
  
  %---------------------------------------
 
  % ------------- STEUERN DES ALGORITHMUSES ----------------- 
  
  NACHRICHTENAUSSCHALTEN = true;
  
  anzahlKinder = 10000;

  minTodesrate = 0.01;
  maxTodesrate = 0.9;
 
  KATASTROPHE = true;
 
  mutationswahrscheinlichkeit = 0.1;
 
  anteilObererSchicht = 0.1;
 
  % anteil der schlechteren, der nicht sterben wird
  sicherheit = 0.1;
  
 %----------------------------------------------------------------------------------------------

  anzahlEigenschaften = size(kinderKonstruktor(1), 2);

  kinder = kinderKonstruktor(anzahlKinder);
  
  for i=1:anzahlGenerationen
    
    %display(kinder);
    %display(char(kinder));
    
    kinder = sortPopulation(kinder, costFunction, i, NACHRICHTENAUSSCHALTEN);
    
    %display(kinder);
    
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  
    ueberlebende = killPopulation(KATASTROPHE, minTodesrate, maxTodesrate, sicherheit, anteilObererSchicht, kinder);
    
    anzahlNochZuErzeugenderKinder = anzahlKinder - size(ueberlebende,1);
    
    neueKinder = growPopulation(ueberlebende, anteilObererSchicht, anzahlNochZuErzeugenderKinder, NACHRICHTENAUSSCHALTEN);
    mutierteKinder = mutatePopulation(neueKinder, mutationswahrscheinlichkeit, kinderKonstruktor, NACHRICHTENAUSSCHALTEN);
    kinder = [ueberlebende; mutierteKinder];
  
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  
  endfor
  
  kinder = sortPopulation(kinder, costFunction, anzahlGenerationen + 1, NACHRICHTENAUSSCHALTEN);
  
  if ~NACHRICHTENAUSSCHALTEN
    display(kinder);
  endif
  
  bestConfig = kinder(1,:);
  display("\n\nBeste Konfigurationen:\n");
  
  for i=1:anzahlEigenschaften
    display(strcat('Eigenschaft ', num2str(i), ':' ,'= ', num2str(bestConfig(i))));
  endfor
  
 endfunction