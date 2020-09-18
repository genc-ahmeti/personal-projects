function ueberlebende = killPopulation(KATASTROPHE, minTodesrate, maxTodesrate, sicherheit, anteilObererSchicht, kinder)
  
   anzahlKinder = size(kinder,1);
  
   % wer stirbt, wer nicht 
    
    if ~KATASTROPHE
      wahrscheinlichkeiten = linspace(minTodesrate,maxTodesrate,anzahlKinder);
    else
      wahrscheinlichkeiten = logspace(log(minTodesrate),log(maxTodesrate),anzahlKinder);
    endif;
    
    anzahlSicherLebender = ceil(sicherheit*(1-anteilObererSchicht)*anzahlKinder);
    
    startUntereSchicht = ceil(anteilObererSchicht*anzahlKinder);
    
    indezesDerLeuteAbDerUnterenSchicht = randperm(anzahlKinder+1-startUntereSchicht) + startUntereSchicht - 1;
    
    wahrscheinlichkeiten(indezesDerLeuteAbDerUnterenSchicht(1:anzahlSicherLebender)) = 0;
    
    % auch wenn wahrsch = 0, wegen < kanns sein, dass trotzdem stirbt
    indezesDerUeberlebenden = sort(find((wahrscheinlichkeiten < rand(1,anzahlKinder)) == 1));
    
    ueberlebende = kinder(indezesDerUeberlebenden,:);
 
endfunction
