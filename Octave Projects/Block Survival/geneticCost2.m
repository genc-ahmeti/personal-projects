function [cost_vec animation] = geneticCost2(kinder,maxFrames,pos, breite, background, MAKE_ANIMATION)
    
  anzahlKinder = size(kinder,1);
  
  oldBackground = background;
 
  cost_vec = zeros(anzahlKinder,1);
  
  animation = 1;
  
  if MAKE_ANIMATION
  animation = cell(1,1);
  endif
  
  [laufListe sprungListe flugListe fallListe flugdauerListe sprunghoeheListe jumpVec] = extractChild(kinder,maxFrames);
   
for childIndex=1:anzahlKinder
  
  if MAKE_ANIMATION
  animationAccu = ones(size(background,1),size(background,2),1);
endif

animationIndex = 1;

  
  background = oldBackground;
  
  scene = background; 
  scene(pos(1),pos(2)) = 0;
  
  if MAKE_ANIMATION
  animationAccu(:,:,1) = scene;
  endif
animationIndex = animationIndex+1;  
  
  stopIndex = maxFrames;
  stop = false;
  
  onAirTime = 0;
  distance = 0;
  
  
  for index=1:maxFrames
    
    if (jumpVec(childIndex,index) ~= index)
      
      distance = distance + laufListe(childIndex,index);
      
      for i=1:laufListe(childIndex,index)
        
        background = imScroll(background);
        stop = touchedTrap(background, pos);
        
        if stop
          break
        endif
      endfor
  
  scene = background;
  
  for i=0:(breite-1)
    for n=0:(breite-1)
  scene(pos(1)-i,pos(2)+n) = 0;  
  endfor
  endfor
  
  if MAKE_ANIMATION
  animationAccu(:,:,end+1) = scene;
  endif
  animationIndex = animationIndex+1;
  
else
  
  onAirTime = onAirTime + 2*sprunghoeheListe(childIndex,index)+flugdauerListe(childIndex,index);
  distance = distance + sprungListe(childIndex,index)*sprunghoeheListe(childIndex,index);
  distance = distance + fallListe(childIndex,index)*sprunghoeheListe(childIndex,index);
  distance = distance + flugListe(childIndex,index)*flugdauerListe(childIndex,index);
  
  for i=1:sprunghoeheListe(childIndex,index)
    
    for i=1:sprungListe(childIndex,index)
      background = imScroll(background);
    endfor
    
    [scene pos] = moveUp(background,pos,breite);
    
    if MAKE_ANIMATION
    animationAccu(:,:,end+1) = scene;
    endif
      animationIndex = animationIndex+1;
  
  endfor 
   
   for n=1:flugdauerListe(childIndex,index)
   
   for i=1:flugListe(childIndex,index)
  background = imScroll(background);
  endfor
  
  scene = background;
  for i=0:(breite-1)
    for n=0:(breite-1)
  scene(pos(1)-i,pos(2)+n) = 0;  
    endfor
  endfor
  
  if MAKE_ANIMATION
  animationAccu(:,:,end+1) = scene;
  endif
    animationIndex = animationIndex+1;
    
    endfor
    
   

for i=1:sprunghoeheListe(childIndex,index)
  
  for i=1:fallListe(childIndex,index)
    background = imScroll(background);
  endfor
  
  [scene pos] = moveDown(background,pos,breite);
  
  
  

  if MAKE_ANIMATION
   animationAccu(:,:,end+1) = scene;
  endif
  animationIndex = animationIndex+1;
  
endfor

stop = touchedTrap(background, pos);

endif

if stop
  stopIndex = index;
  break
endif

endfor

anzahlBewegungen = animationIndex -2;

spf = 1;

if (distance == 0)
  createGif('.\Animationen\animateDistanzNull.gif',animation(:,:,:,childIndex), spf);
  display("Distanz 0 5 sek");
  pause(5);
  imshow(oldBackground);
  pause(5);
  display("Distanz 0 5 sek");
  display((oldPos(1)+1):(oldPos(1)+2));
  display(oldPos(2));
  display(oldBackground((oldPos(1)+1):(oldPos(1)+2),oldPos(2)));
  oldBackground((oldPos(1)+1):(oldPos(1)+2),oldPos(2)) = [1;1];
  imshow(oldBackground);
  pause(5);
  display("Distanz 0 5 sek");
  endif
  
cost_vec(childIndex) = (onAirTime*10000 +anzahlBewegungen + (maxFrames-stopIndex)*10000 + 0) / distance;

if MAKE_ANIMATION
animation(end+1) = animationAccu;
endif

endfor

  

  
endfunction
