function geneticAlgo(anzahlGenerationen)
  
  close all;
 
  %minLayers = 2;
  maxLayers = 10;
 
  %minHiddenUnits = 0;
  maxHiddenUnits = 5;
 
  %minLambda = 0;
  maxLambda = 3;
  
  costFunction = @(children) geneticCost(children);
  kinderKonstruktor = @(anzahlKinder) ([randi(maxLayers-1,anzahlKinder,1)+1 randi(maxHiddenUnits+1,anzahlKinder,1)-1 rand(anzahlKinder,1)*maxLambda]);
 
 
  NACHRICHTENAUSSCHALTEN = true;
  
  anzahlKinder = 100;

  minTodesrate = 0;
  maxTodesrate = 0.9;
 
  KATASTROPHE = false;
 
  mutationswahrscheinlichkeit = 0.1;
 
  anteilObererSchicht = 0.1;
 
  % anteil der schlechteren, der nicht sterben wird
  sicherheit = 0.1;
  
 %----------------------------------------------------------------------------------------------
  anzahlEigenschaften = size(kinderKonstruktor(1), 2);
 
  kinder = kinderKonstruktor(anzahlKinder);
  kinder = ungueltigeKombiVerhindern(kinder,maxHiddenUnits);
  
  for i=1:anzahlGenerationen
    
    %display(kinder);
    %display(char(kinder));
    
    kinder = sortPopulation(kinder, costFunction, i, NACHRICHTENAUSSCHALTEN, 'descend');
    
    %display(kinder);
    
    %display(char(kinder));
    
    
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  
    ueberlebende = killPopulation(KATASTROPHE, minTodesrate, maxTodesrate, sicherheit, anteilObererSchicht, kinder);
    
    anzahlNochZuErzeugenderKinder = anzahlKinder - size(ueberlebende,1);
    
    neueKinder = growPopulation(ueberlebende, anteilObererSchicht, anzahlNochZuErzeugenderKinder, NACHRICHTENAUSSCHALTEN);
    mutierteKinder = mutatePopulation(neueKinder, mutationswahrscheinlichkeit, kinderKonstruktor, NACHRICHTENAUSSCHALTEN);
    gueltigeKinder = ungueltigeKombiVerhindern(mutierteKinder,maxHiddenUnits);
    kinder = [ueberlebende; mutierteKinder];
  
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  
  endfor
  
  kinder = sortPopulation(kinder, costFunction, anzahlGenerationen + 1, NACHRICHTENAUSSCHALTEN, 'descend');
  
  if ~NACHRICHTENAUSSCHALTEN
    display(kinder);
  endif
  
  bestConfig = kinder(1,:);
  display("\n\nBeste Konfigurationen:\n");
  
  display(strcat("Anzahl Layer: \t", num2str(bestConfig(1))));
  display(strcat("Anzahl Hidden Units: \t", num2str(bestConfig(2))));
  display(strcat("Lambda: \t", num2str(bestConfig(3))));
  
 endfunction