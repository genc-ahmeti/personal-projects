function geneticAlgo(anzahlGenerationen)
  
 addpath('../Genetic Algorithm Assets');
  
  close all;
  
  
  
  
  %minLayers = 2;
 % maxLayers = 10;
 
  %minHiddenUnits = 0;
  %maxHiddenUnits = 5;
 
  %minLambda = 0;
  %maxLambda = 3;
  
  %costFunction = @(children) geneticCost(children);
  %kinderKonstruktor = @(anzahlKinder) ([randi(maxLayers-1,anzahlKinder,1)+1 randi(maxHiddenUnits+1,anzahlKinder,1)-1 rand(anzahlKinder,1)*maxLambda]);
  
  %---------------------------------------
  wort = "Adhurim Ahmeti";
  
  minBuchstabe = 32;
  maxBuchstabe = 122;
  
  anzahlBuchstabenProWort = size(wort,2);
  
  costFunction = @(children) (100*(1 - sum((children == wort),2)/anzahlBuchstabenProWort));
  kinderKonstruktor = @(anzahlKinder) (randi((maxBuchstabe-minBuchstabe+1), anzahlKinder, (wort * (1./wort)')) + (minBuchstabe-1));
  %-------------------------------------
  
  
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
  
  plotVersions = zeros(anzahlBuchstabenProWort+1,anzahlGenerationen);
  plotRanges = zeros(1,anzahlGenerationen);
  plotMedians = zeros(1,anzahlGenerationen);
  plotStds = zeros(1,anzahlGenerationen);
  
  anzahlEigenschaften = size(kinderKonstruktor(1), 2);
 
  kinder = kinderKonstruktor(anzahlKinder);
  % kinder = ungueltigeKombiVerhindern(kinder,maxHiddenUnits);
  
  for i=1:anzahlGenerationen
    
    %display(kinder);
    %display(char(kinder));
    
    [kinder versions ranges medians stds] = sortByCost(wort, kinder, costFunction, i, NACHRICHTENAUSSCHALTEN);
    plotVersions(:,i) = versions;
    plotRanges(i) = ranges;
    plotMedians(i) = medians;
    plotStds(i) = stds;
    %display(kinder);
    
    %display(char(kinder));
    
    
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  
    ueberlebende = killPopulation(KATASTROPHE, minTodesrate, maxTodesrate, sicherheit, anteilObererSchicht, kinder);
    
    anzahlNochZuErzeugenderKinder = anzahlKinder - size(ueberlebende,1);
    
    neueKinder = growPopulation(ueberlebende, anteilObererSchicht, anzahlNochZuErzeugenderKinder, NACHRICHTENAUSSCHALTEN);
    mutierteKinder = mutatePopulation(neueKinder, mutationswahrscheinlichkeit, kinderKonstruktor, NACHRICHTENAUSSCHALTEN);
    %gueltigeKinder = ungueltigeKombiVerhindern(mutierteKinder,maxHiddenUnits);
    kinder = [ueberlebende; mutierteKinder];
  
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  
  endfor
  
  [kinder dummy1 dumyy2 dummy3 dummy4] = sortByCost(wort, kinder, costFunction, anzahlGenerationen + 1, NACHRICHTENAUSSCHALTEN);
  
  if ~NACHRICHTENAUSSCHALTEN
    display(kinder);
  endif
  
  bestConfig = kinder(1,:);
  display("\n\nBeste Konfigurationen:\n");
  
  %for i=1:anzahlEigenschaften
  %  display(strcat('Eigenschaft ', num2str(i), ':' ,'= ', num2str(bestConfig(i))));
  %endfor
  
  display(strcat('Namen: ', char(bestConfig(:))));
  
  display("\n");
 
  figure;
  hold on;
  xlabel = "Number of Generation";
  ylabel = "Share of the population";
  
  red = linspace(1,0,(anzahlBuchstabenProWort+1));
  quadraticFunction = @ (x) ((-x^2+((anzahlBuchstabenProWort+1) + 1)*x-(anzahlBuchstabenProWort+1))/(((anzahlBuchstabenProWort+1)^2-2*(anzahlBuchstabenProWort+1)+1)/4));
  %green = linspace(1,0,(anzahlBuchstabenProWort+1));
  blue = linspace(0,1,(anzahlBuchstabenProWort+1));
  
  for i=1:(anzahlBuchstabenProWort+1)
    plot(0:anzahlGenerationen-1,plotVersions(i,:), 'color', [red(i) quadraticFunction(i) blue(i)]);
  endfor
  
  namen = [num2str((0:anzahlBuchstabenProWort)') repmat(" Buchstaben stimmen ueberein.",(anzahlBuchstabenProWort+1),1)];
  legend(namen, "location", "northeastoutside");

  figure;
  xlabel = "Number of Generation";
  ylabel = "Range";
  plot(0:anzahlGenerationen-1,plotRanges);
  legend("range");

  figure;
  xlabel = "Number of Generation";
  ylabel = "Median";
  plot(0:anzahlGenerationen-1,plotMedians);
  legend("median");
  
  figure;
  xlabel = "Number of Generation";
  ylabel = "Std";
  plot(0:anzahlGenerationen-1,plotStds);
  legend("std");
 
 endfunction