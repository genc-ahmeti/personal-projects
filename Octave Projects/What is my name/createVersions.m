function versions = createVersions(wort, woerter)
  
  anzahlWoerter = size(woerter,1);
  anzahlBuchstabenProWort = size(wort,2);
  
  versions = zeros(anzahlBuchstabenProWort+1,1);
  
  buchstabenUebereinstimmungen = sum(woerter == wort,2);
  
  for i=1:size(versions,1)
    versions(i) = sum(buchstabenUebereinstimmungen==(i-1));
  endfor
  
  versions = 100* versions / anzahlWoerter;
  
  %version1 = 100*sum((sorted <= 100) .* (sorted > 80))/anzahlKinder;
  %version2 = 100*sum((sorted <= 80) .* (sorted > 60))/anzahlKinder;
  %version3 = 100*sum((sorted <= 60) .* (sorted > 40))/anzahlKinder;
  %version4 = 100*sum((sorted <= 40) .* (sorted > 10))/anzahlKinder;
  %version5 = 100*sum((sorted <= 10))/anzahlKinder;
  %versions = [version1; version2; version3; version4; version5];

  endfunction