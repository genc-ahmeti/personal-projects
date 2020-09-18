function animations = undoScroll(animations, background, dicke)
  
  breiteBild = size(background,2);
  background = background((end-dicke+1):end,:);
  
  for childIndex=1:size(animations,2)
    
    child = cell2mat(animations(childIndex));
    
    for imageIndex=1:size(child,3)
      
      for i=1:breiteBild
        
      currentBackground = child((end-dicke+1):end, :,imageIndex);
      
      if (sum(sum(abs(background - currentBackground))) ~= 0)
             
         child(:,:,imageIndex) = child(:,[end 1:(end-1)],imageIndex);
         
        else

        break
      endif
      
      endfor
    endfor

           animations(childIndex) = child;
    
  endfor
  
endfunction
