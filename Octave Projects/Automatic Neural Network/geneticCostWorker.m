function accuracy = geneticCostWorker(kind)

  anzahlLayer = kind(1);
  hidden_layer_size = kind(2);
  lambda = kind(3);
  
%% Setup the parameters you will use for this exercise

% ----------------------------------------------------------------------

trainingsbeispieleDatei = 'dateien.m';
trainingsbeispiele = load(trainingsbeispieleDatei);


testBeispieleDatei = 'testCases.m';
testBeispiele = load(testBeispieleDatei);



input_layer_size  = 10;  
num_labels = 1;           

maxiter = 10;
                          

       



       
% ----------------------------------------------------------------------                          

MULTICLASS = num_labels > 1;

X = trainingsbeispiele(:,1:end-1);
y = trainingsbeispiele(:,end);
m = size(X, 1);


initial_ThetaArray = cell(1,anzahlLayer-1);
if (anzahlLayer~= 2)
  initial_ThetaArray(1) = randInitializeWeights(input_layer_size, hidden_layer_size);
  for i=2:(anzahlLayer-2)
    initial_ThetaArray(i) = randInitializeWeights(hidden_layer_size, hidden_layer_size);
  endfor
  initial_ThetaArray(end) = randInitializeWeights(hidden_layer_size, num_labels);
else
  initial_ThetaArray(1) = randInitializeWeights(input_layer_size, num_labels);
endif

initial_nn_params = (cell2mat(initial_ThetaArray(1)))(:);
for i=2:(anzahlLayer-1)
  initial_nn_params = [initial_nn_params; (cell2mat(initial_ThetaArray(i)))(:)];
endfor

options = optimset('MaxIter', maxiter);


% Create "short hand" for the cost function to be minimized
costFunction = @(p) nnCostFunction5(p,
                                    anzahlLayer,
                                    MULTICLASS,
                                    input_layer_size, ...
                                    hidden_layer_size,
                                    num_labels, X, y, lambda);

[nn_params, cost] = fmincg(costFunction, initial_nn_params, options);
% Obtain Theta1 and Theta2 back from nn_params
ThetaArray = cell(1,anzahlLayer-1);

if anzahlLayer~=2
  indexArray = zeros(anzahlLayer-2,1);
  indexArray(1) = hidden_layer_size * (input_layer_size + 1);
  ThetaArray(1) = reshape(nn_params(1:indexArray(1)), ...
                 hidden_layer_size, (input_layer_size + 1));
  for i=2:(anzahlLayer-2)
    indexArray(i) = (indexArray(i-1) + hidden_layer_size*(hidden_layer_size +1));
    ThetaArray(i) = reshape(nn_params((1 + indexArray(i-1)):indexArray(i)), ...
                 hidden_layer_size, hidden_layer_size +1);
  endfor
  ThetaArray(end) = reshape(nn_params((indexArray(end)+1):end), ...
                 num_labels, hidden_layer_size + 1);
else
    ThetaArray(1) = reshape(nn_params(1:end), ...
                 num_labels, (input_layer_size + 1));
endif

X2 = testBeispiele(:,1:end-1);
y2 = testBeispiele(:,end);                
   
accuracy = predict4(ThetaArray, X2, MULTICLASS);
accuracy = mean(double(accuracy == y2)) * 100; 

endfunction