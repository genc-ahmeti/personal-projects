function touched = touchedTrap(image, pos)
  
  touched = image(pos(1)+1,pos(2)) == 1;
    
endfunction
