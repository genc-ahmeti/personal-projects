function J_vec = geneticCost(kinder)

  m = size(kinder,1);
  J_vec = zeros(m,1);
  
  for i=1:m
    J_vec(i) = geneticCostWorker(kinder(i,:));
  endfor

endfunction
