function [children animation bisherigeMaxKosten allAnimations] = sortByCost2(spf, kinder, costFunction, generation, bisherigeMaxKosten, allAnimations, NACHRICHTENAUSSCHALTEN, MAKE_ANIMATION)
  
% ----------------------------------------------------------------------------  
  anzahlKinder = size(kinder,1);
  
  [cost_vec animation] = costFunction(kinder,MAKE_ANIMATION);
  [sorted indeces] = sort(cost_vec, 'ascend');
  
  if ~NACHRICHTENAUSSCHALTEN
    display(sorted);
  endif
  
  children = kinder(indeces,:);
  if MAKE_ANIMATION
  animation = animation(indeces);
  endif
  
  display(strcat('Gen ', num2str(generation-1), ": Kosten = ", num2str(sorted(1))));
  
  if (bisherigeMaxKosten ~= sorted(1))
    
    bisherigeMaxKosten = sorted(1);
    allAnimations(end+1) = cell2mat(animation(1));
    %createGif(strcat('.\Animationen\animateBestOfGen', num2str(indexGif) ,'.gif'),animation(:,:,:,1), spf);
  endif
% ----------------------------------------------------------------------------
  
endfunction
