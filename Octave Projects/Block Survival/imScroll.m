function imgScrolled = imScroll(img)
  
 % if (size(img,2) == 1)
 %   imgScrolled = [ones(1, size(img,2));
 % else
   imgScrolled = img(:,[2:end 1]);
 % endif
  
endfunction
