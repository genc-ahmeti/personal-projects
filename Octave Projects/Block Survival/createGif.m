function createGif(filename, images, spf)
 
  imwrite(images(:,:,1),filename,'gif', 'Loopcount',inf, 'DelayTime',spf); 
  
  %pause(3);
  for i=2:size(images,3)
      imwrite(images(:,:,i),filename,'gif','WriteMode','append', 'DelayTime',spf);
  endfor

  endfunction
