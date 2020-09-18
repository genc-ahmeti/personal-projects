function children = sortPopulation(kinder, costFunction, generation, NACHRICHTENAUSSCHALTEN, sortType)
  
% ----------------------------------------------------------------------------  
  anzahlKinder = size(kinder,1);
  
  cost_vec = costFunction(kinder);
  % sortieren mit höhe der Kosten
  [sorted indeces] = sort(cost_vec, sortType);
  
  if ~NACHRICHTENAUSSCHALTEN
    display(sorted);
  endif
  
  children = kinder(indeces,:);
  
  display(strcat('Gen ', num2str(generation-1) ,": \t Best test accuracy = ", num2str(sorted(1)), '%'));

% ----------------------------------------------------------------------------
  
endfunction
