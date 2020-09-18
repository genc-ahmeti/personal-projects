function [newImg newPos] = moveUp(img, pos, breite)
  newPos =  pos+[-1 0];
  
  for i=0:(breite-1)
  img(pos(1),pos(2)+i) = 1;
  img(newPos(1)-(breite-1),newPos(2)+i) = 0;
  endfor

   for i=0:(breite-1)
   for n=0:(breite-1)
  img(newPos(1)-n,newPos(2)+i) = 0;
endfor
endfor
  
  newImg = img;
endfunction
