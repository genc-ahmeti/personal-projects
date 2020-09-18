function createMixedGif(animations, background, dicke)
  
  animations = animations(2:end);
  anzahlKinder = size(animations,2);
 
  neueFarbenKind = [1 0 0; 0 1 0; 0 0 1;];
 

 
  animations = undoScroll(animations,background, dicke);
  
  
  indecesOfChosenChildren = [1 ceil(anzahlKinder/2) anzahlKinder];
  anzahlBilder = [size(cell2mat(animations(indecesOfChosenChildren(1))),3) size(cell2mat(animations(indecesOfChosenChildren(2))),3) size(cell2mat(animations(indecesOfChosenChildren(3))),3)];
  [dummy indexOfMainChild] = max(anzahlBilder,[],2);
  
  graustufen = linspace(0.5,0,size(indecesOfChosenChildren,2));
  
  for i=1:size(indecesOfChosenChildren,2)
    
    if indecesOfChosenChildren(i)==indecesOfChosenChildren(indexOfMainChild)
      continue
    endif
    
      otherChild = cell2mat(animations(indecesOfChosenChildren(i)));
      mainChild = cell2mat(animations(indecesOfChosenChildren(indexOfMainChild)));
      
      for n=1:size(otherChild,3)
      dummy = otherChild((dicke+1):(end-dicke),:,n);
      indecesOfPos = find(dummy == 0);
      dummy(indecesOfPos) = dummy(indecesOfPos) + graustufen(i); 
      otherChild((dicke+1):(end-dicke),:,n) = dummy;
      endfor
      
      mainChild(:,:,1:size(otherChild,3))  = mainChild(:,:,1:size(otherChild,3)) .* otherChild;
      
      
      animations(indecesOfChosenChildren(indexOfMainChild)) = mainChild;
  
  endfor
  
  createGif('.\Animationen\animateMixedBoth.gif',cell2mat(animations(indecesOfChosenChildren(indexOfMainChild))), 0.25);
  createColoredGif('.\Animationen\animateColored.gif', colorizeImage(cell2mat(animations(indecesOfChosenChildren(indexOfMainChild))), dicke, neueFarbenKind, graustufen'), 0.25);
  
  
endfunction
