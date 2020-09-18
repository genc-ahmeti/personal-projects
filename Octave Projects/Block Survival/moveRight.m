function [newImg newPos] = moveRight(img, pos)
  newPos = pos+[0 1];
  img(pos(1),pos(2)) = 1;
  img(newPos(1),newPos(2)) = 0;
  newImg = img;
endfunction