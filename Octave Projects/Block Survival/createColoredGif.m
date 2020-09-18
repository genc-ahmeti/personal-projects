function createColoredGif(filename, rgbImages, spf)
  

  pic = rgbImages(:,:,1,1);
  pic(:,:,2) = rgbImages(:,:,1,2);
  pic(:,:,3) = rgbImages(:,:,1,3);
  
  
  imwrite(pic,filename,'gif', 'Loopcount',inf, 'DelayTime',spf); 

  for i=2:size(rgbImages,3)
       pic = rgbImages(:,:,i,1);
  pic(:,:,2) = rgbImages(:,:,i,2);
  pic(:,:,3) = rgbImages(:,:,i,3);

      imwrite(pic,filename,'gif','WriteMode','append', 'DelayTime',spf);
            
  endfor

  endfunction