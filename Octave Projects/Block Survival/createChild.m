function child = createChild(anzahlKinder,
                             maxLaufgeschwindigkeit, 
                             maxSprunggeschwindigkeit,
                             maxFluggeschwindigkeit,
                             maxFallgeschwindigkeit,
                             maxFlugdauer,
                             maxSprunghoehe,
                             maxFrames,
                             sprungwahrscheinlichkeit)
  
  laufListe = randi(maxLaufgeschwindigkeit+1,anzahlKinder,maxFrames)-1;
  sprungListe = randi(maxSprunggeschwindigkeit+1,anzahlKinder,maxFrames)-1;
  flugListe = randi(maxFluggeschwindigkeit+1,anzahlKinder,maxFrames)-1;
  fallListe = randi(maxFallgeschwindigkeit+1,anzahlKinder,maxFrames)-1;
  flugdauerListe = randi(maxFlugdauer+1,anzahlKinder,maxFrames)-1;
  sprunghoeheListe = randi(maxSprunghoehe+1,anzahlKinder,maxFrames)-1;
  jumpVec = repmat((1:maxFrames),anzahlKinder,1) .* (rand(anzahlKinder,maxFrames)<= sprungwahrscheinlichkeit);
  
  child = [laufListe sprungListe flugListe fallListe flugdauerListe sprunghoeheListe jumpVec];
  
endfunction
