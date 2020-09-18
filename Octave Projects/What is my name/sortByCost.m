function [children versions ranges medians stds] = sortByCost(wort, kinder, costFunction, generation, NACHRICHTENAUSSCHALTEN)
  
% ----------------------------------------------------------------------------  
  anzahlKinder = size(kinder,1);
  
  cost_vec = costFunction(kinder);
  % sortieren mit höhe der genauigkeit
  %[sorted indeces] = sort(cost_vec, 'descend');
  [sorted indeces] = sort(cost_vec, 'ascend');
  if ~NACHRICHTENAUSSCHALTEN
    display(sorted);
  endif
  children = kinder(indeces,:);
  display(strcat('Gen ', num2str(generation-1) ,": ", char(children(1,:)) , "   <->   Test accuracy = ", num2str(sorted(1)), '%'));
 % display(strcat("Range: " ,num2str(sorted(end)-sorted(1)), "  Median: " ,num2str(mean(sorted)), "  Standardabweichung:" ,num2str(std(sorted))));
  
  ranges = sorted(end)-sorted(1);
  medians = mean(sorted);
  stds = std(sorted);
  
  versions = createVersions(wort, kinder);
  %display(strcat("Ver. 1: " ,num2str(version1), "%\nVer. 2: " ,num2str(version2) ,"%\nVer. 3: " ,num2str(version3) ,"%\nVer. 4: " ,num2str(version4), "%\nVer. 5: " ,num2str(version5), "%"));
  
  %display(strcat('Gen ', num2str(generation-1) ,': Test accuracy = ', num2str(sorted(1)), '%'));

% ----------------------------------------------------------------------------
  
endfunction
