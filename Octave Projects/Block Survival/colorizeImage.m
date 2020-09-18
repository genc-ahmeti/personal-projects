function colorizedImage = colorizeImage(animationOfChild,dicke, neueFarbenKind, alteFarbenKind)
  

  farbeMehrereUebereinander = [0 0 0];
  
  rDecke = animationOfChild(1:dicke,:,:);
  rMittelschicht = animationOfChild((dicke+1):(end-dicke),:,:);
  rBoden = animationOfChild((end-dicke+1):end,:,:);
 
 gDecke = rDecke;
  gMittelschicht = rMittelschicht;
  gBoden = rBoden;
  
  bDecke = rDecke;
  bMittelschicht = rMittelschicht;
  bBoden = rBoden;

  
  %display(alteFarbenKind);
  
  for imageIndex=1:size(animationOfChild,3)
   
    currentFrame = rMittelschicht(:,:,imageIndex);
 
   for objectIndex=1:size(alteFarbenKind,1)
    
    temp1 = rMittelschicht(:,:,imageIndex);
    temp2 = gMittelschicht(:,:,imageIndex);
    temp3 = bMittelschicht(:,:,imageIndex);
    
    posOfChild = find(currentFrame == alteFarbenKind(objectIndex));
    %display(posOfChild);
    %pause(5);
    
    %display("a");
    %display(temp1);
    %imshow(currentFrame);
    %pause(5);
    
    temp1(posOfChild) = neueFarbenKind(objectIndex,1);
    %display("1");
    %imshow(temp1);
    %pause(5);
    
    temp2(posOfChild) = neueFarbenKind(objectIndex,2);
   % display("2");
   % imshow(temp2);
   % pause(5);
    
    temp3(posOfChild) = neueFarbenKind(objectIndex,3);
  %  display("3");
  %  imshow(temp3);
  %  pause(5);
    
    rMittelschicht(:,:,imageIndex) = temp1;
    gMittelschicht(:,:,imageIndex) = temp2;
    bMittelschicht(:,:,imageIndex) = temp3;
    
    
 endfor
 
 temp1 = rMittelschicht(:,:,imageIndex);
    temp2 = gMittelschicht(:,:,imageIndex);;
    
    temp3 = bMittelschicht(:,:,imageIndex);;
 
 posOfChild = find(temp1 ~= temp1(1));
 
 %display(posOfChild);
 %pause(5);
 
 muell = [];
 
 for posIndex=1:length(posOfChild)
  for objectIndex=1:size(alteFarbenKind,1)
     
     if(temp1(posOfChild(posIndex)) == alteFarbenKind(objectIndex))
     muell(end+1) = posIndex;
     break
     endif
      
    endfor
  endfor
  
  posOfChild(muell) = [];
  
 %display(posOfChild);
 %pause(5);
 
 
 temp1(posOfChild) = farbeMehrereUebereinander(1);
    %display("1 letze");
    %imshow(temp1);
    %pause(5);
    
    temp2(posOfChild) = farbeMehrereUebereinander(2);
    %display("2 letze");
    %imshow(temp2);
    %pause(5);
    
    temp3(posOfChild) = farbeMehrereUebereinander(3);
    %display("3 letze");
    %imshow(temp3);
    %pause(5);
    
    rMittelschicht(:,:,imageIndex) = temp1;
    gMittelschicht(:,:,imageIndex) = temp2;
    bMittelschicht(:,:,imageIndex) = temp3;
 
 
 
 
 pic = rMittelschicht(:,:,imageIndex);
    pic(:,:,2) = gMittelschicht(:,:,imageIndex);
    pic(:,:,3) = bMittelschicht(:,:,imageIndex);
   % display("b");
   % imshow(pic);
  %  pause(5);
 
    endfor
  
  rgbDecke = rDecke;
  rgbDecke(:,:,:,end+1) = gDecke;
  rgbDecke(:,:,:,end+1) = bDecke;
  
  rgbMittelschicht = rMittelschicht;
  rgbMittelschicht(:,:,:,end+1) = gMittelschicht;
  rgbMittelschicht(:,:,:,end+1) = bMittelschicht;
  
  rgbBoden = rBoden;
  rgbBoden(:,:,:,end+1) = gBoden;
  rgbBoden(:,:,:,end+1) = bBoden;
  
  colorizedImage = [rgbDecke; rgbMittelschicht; rgbBoden];
  
    %for i=1:size(colorizedImage,3)
        
   %   display(strcat(num2str(i),". Bild")); 
  %     pic = colorizedImage(:,:,i,1);
 % pic(:,:,2) = colorizedImage(:,:,i,2);
 % pic(:,:,3) = colorizedImage(:,:,i,3);
  
  %imshow(pic);
  %pause(5);
 % endfor
  
endfunction
