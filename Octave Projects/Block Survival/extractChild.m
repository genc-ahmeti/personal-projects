function [laufListe sprungListe flugListe fallListe flugdauerListe sprunghoeheListe jumpVec] = extractChild(vec, maxFrames)
  
  anzahlKinder = size(vec,1);
  
  laufListe = vec(:,1:maxFrames); 
  sprungListe = vec(:,maxFrames+1:2*maxFrames);
  flugListe = vec(:,2*maxFrames+1:3*maxFrames);
  fallListe = vec(:,3*maxFrames+1:4*maxFrames);
  flugdauerListe = vec(:,4*maxFrames+1:5*maxFrames);
  sprunghoeheListe = vec(:,5*maxFrames+1:6*maxFrames);
  jumpVec = vec(:,6*maxFrames+1:7*maxFrames);
endfunction
