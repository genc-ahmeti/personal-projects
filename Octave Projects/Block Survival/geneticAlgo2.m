function geneticAlgo2(anzahlGenerationen)

 addpath('../Genetic Algorithm Assets');


  maxFrames = 200;
  
  maxLaufgeschwindigkeit = 3; 
  maxSprunggeschwindigkeit = 3;
  maxFluggeschwindigkeit = 3;
  maxFallgeschwindigkeit = 3;
  maxFlugdauer = 0;
  maxSprunghoehe = 3;
  
  sprungwahrscheinlichkeit = 0.3;
  trapProbability = 0.3;
  
  zwischenschichten = 10;
  laenge = 50;
  dicke = 2;
  
  fps = 5;
  startpos = [3 3];
  
  breite = 1;
  
  %----------------------------------------------------------------------------------
  fallen = rand(1,laenge) <= trapProbability;
  background = [zeros(dicke,laenge); ones(zwischenschichten,laenge); repmat(fallen,dicke,1)];
  
  pos = [size(background,1)+1-startpos(2) startpos(1)];
  background((pos(1)+1):(pos(1)+2),pos(2)) = [0;0];
  
  %-------------------------------------------------------------------------------------
  
  costFunction = @(children,MAKE_ANIMATION) (geneticCost2(children, maxFrames, pos, breite, background,MAKE_ANIMATION));
  kinderKonstruktor = @(anzahlKinder) (createChild(anzahlKinder,
                                                   maxLaufgeschwindigkeit, 
                                                   maxSprunggeschwindigkeit,
                                                   maxFluggeschwindigkeit,
                                                   maxFallgeschwindigkeit,
                                                   maxFlugdauer,
                                                   maxSprunghoehe,
                                                   maxFrames,
                                                   sprungwahrscheinlichkeit));
  anzahlKinder = 1000;
  %----------------------------------------------------------------------------------------
  
  
  NACHRICHTENAUSSCHALTEN = true;
  
  minTodesrate = 0;
  maxTodesrate = 0.9;
 
  KATASTROPHE = false;
 
  mutationswahrscheinlichkeit = 0.1;
 
  anteilObererSchicht = 0.1;
 
  % anteil der schlechteren, der nicht sterben wird
  sicherheit = 0.1;
  
 %----------------------------------------------------------------------------------------------
  
  %plotVersions = zeros(anzahlBuchstabenProWort+1,anzahlGenerationen);
  %plotRanges = zeros(1,anzahlGenerationen);
  %plotMedians = zeros(1,anzahlGenerationen);
  %plotStds = zeros(1,anzahlGenerationen);
  
  %anzahlEigenschaften = size(kinderKonstruktor(1), 2);
  spf = 1/fps;
  
  allAnimations = cell(1,1);
  maxKosten = -1;
  
  kinder = kinderKonstruktor(anzahlKinder);
  % kinder = ungueltigeKombiVerhindern(kinder,maxHiddenUnits);
  
  for i=1:anzahlGenerationen
    
    %display(kinder);
    %display(char(kinder));
   % display('sortieren1 anfang');
    [kinder animation maxKosten allAnimations] = sortByCost2(spf, kinder, costFunction, i, maxKosten, allAnimations, NACHRICHTENAUSSCHALTEN, true);
    %display('sortieren1 ende');
    
     %[kinder versions ranges medians stds] = sortByCost(wort, kinder, costFunction, i, NACHRICHTENAUSSCHALTEN);
    %plotVersions(:,i) = versions;
    %plotRanges(i) = ranges;
    %plotMedians(i) = medians;
    %plotStds(i) = stds;
    %display(kinder);
    
    %display(char(kinder));
    
    
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  %display('kill anfang');
    ueberlebende = killPopulation(KATASTROPHE, minTodesrate, maxTodesrate, sicherheit, anteilObererSchicht, kinder);
    %display('kill ende');
    anzahlNochZuErzeugenderKinder = anzahlKinder - size(ueberlebende,1);
    %display('grow anfang');
    neueKinder = growPopulation(ueberlebende, anteilObererSchicht, anzahlNochZuErzeugenderKinder, NACHRICHTENAUSSCHALTEN);
   % display('grow ende');
    %display('mutate anfang');
    mutierteKinder = mutatePopulation(neueKinder, mutationswahrscheinlichkeit, kinderKonstruktor, NACHRICHTENAUSSCHALTEN);
    %display('mutate ende');
    %gueltigeKinder = ungueltigeKombiVerhindern(mutierteKinder,maxHiddenUnits);
    kinder = [ueberlebende; mutierteKinder];
  
    if ~NACHRICHTENAUSSCHALTEN
      display(kinder);
    endif
  
  endfor
  %display('sortieren2 anfang');
  [kinder animation maxKosten allAnimations] = sortByCost2(spf, kinder, costFunction, anzahlGenerationen+1, maxKosten, allAnimations, NACHRICHTENAUSSCHALTEN, true);
  %display('sortieren2 ende');
 
  if ~NACHRICHTENAUSSCHALTEN
    display(kinder);
  endif
  
  %bestConfig = kinder(1,:);
  display(strcat("\n\nBeste Konfigurationen: \t", num2str(size(allAnimations,2) - 1), " Video[s]"));
  %createGif('.\Animationen\animateBest.gif',cell2mat(allAnimations(end)), spf);
  createMixedGif(allAnimations, background, dicke);

 endfunction
