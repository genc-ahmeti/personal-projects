function neueKinder = growPopulation(ueberlebende,anteilObererSchicht,anzahlNochZuErzeugenderKinder, NACHRICHTENAUSSCHALTEN)
  
  anzahlDerLebenden = size(ueberlebende, 1);
  anzahlEigenschaften = size(ueberlebende,2);
  
  neueKinder = zeros(anzahlNochZuErzeugenderKinder,anzahlEigenschaften);
  
  for i=1:anzahlNochZuErzeugenderKinder
            
      mutter = ueberlebende(randi(ceil(anteilObererSchicht*anzahlDerLebenden)),:);
      vater = ueberlebende(randi(anzahlDerLebenden),:);
      
      if ~NACHRICHTENAUSSCHALTEN
        display(mutter);
        display(vater);
      endif
      
      % welche und wie viele eigenschaften austauschen
      anzahlZuTauschenderEig = randi(anzahlEigenschaften+1)-1;
      
      if ~NACHRICHTENAUSSCHALTEN
        display(anzahlZuTauschenderEig);
      endif
      
      % wahscheinlichkeit, dass man die Eigenschaft der Mutter kriegt
      %display('\nMUTTER WAHRSCH.:');
      
      neuesKind = mutter;
      
      if anzahlZuTauschenderEig ~= 0
        
        wahrschFuerWahl = rand(1,anzahlZuTauschenderEig)>=0.5;
        
        if ~NACHRICHTENAUSSCHALTEN
          display(wahrschFuerWahl);
        endif
      
        welcheEig = (randperm(anzahlEigenschaften))(1:anzahlZuTauschenderEig);
      
        if ~NACHRICHTENAUSSCHALTEN
          display(welcheEig);
        endif
      
        neuesKind(welcheEig)= mutter(welcheEig).*wahrschFuerWahl + vater(welcheEig).*(1-wahrschFuerWahl);
      
        if ~NACHRICHTENAUSSCHALTEN
          display(neuesKind);
        endif   
        
      endif
      
      neueKinder(i,:) = neuesKind;
     
endfor
endfunction

