function erg = ungueltigeKombiVerhindern(kinder, maxHiddenUnits)
  m = size(kinder,1);
  for i=1:m
    if (kinder(i,1) == 2 && kinder(i,2) ~= 0)
      kinder(i,2) = 0;
    endif
    if (kinder(i,1) ~= 2 && kinder(i,2) == 0)
      kinder(i,2) = randi(maxHiddenUnits,1,1);
    endif
  endfor
  erg = kinder;
endfunction
