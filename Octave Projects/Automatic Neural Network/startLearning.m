%% Machine Learning Online Class - Exercise 4 Neural Network Learning

addpath('../');



%  Instructions
%  ------------
% 
%  This file contains code that helps you get started on the
%  linear exercise. You will need to complete the following functions 
%  in this exericse:
%
%     sigmoidGradient.m
%     randInitializeWeights.m
%     nnCostFunction.m
%
%  For this exercise, you will not need to change any code in this file,
%  or any other files other than those mentioned above.
%

%% Initialization
clear ; close all; clc

%% Setup the parameters you will use for this exercise

% ----------------------------------------------------------------------


trainingsbeispieleDatei = 'dateien.m';
trainingsbeispiele = load(trainingsbeispieleDatei);
X = trainingsbeispiele(:,1:end-1);
y = trainingsbeispiele(:,end);
%load('ex4data1.mat');

testBeispieleDatei = 'testCases.m';
testBeispiele = load(testBeispieleDatei);
testBeispieleExistieren = true;

anzahlLayer = 3;


input_layer_size  = 10;  
hidden_layer_size = 5;  
num_labels = 1;           

lambda = 2.1516;  
maxiter = 20;
                
       



       
% ----------------------------------------------------------------------                          

%% =========== Part 1: Loading and Visualizing Data =============
%  We start the exercise by first loading and visualizing the dataset. 
%  You will be working with a dataset that contains handwritten digits.
%

MULTICLASS = num_labels > 1;


if ((anzahlLayer == 2 && hidden_layer_size ~= 0) || (anzahlLayer ~= 2 && hidden_layer_size == 0))
  display('Die Kombination an Layern und Hidden Layern ist nicht möglich');
else

% Load Training Data
fprintf('Loading and Visualizing Data ...\n')

interval = 1:343;

m = size(X, 1);

if MULTICLASS
  y_k = zeros(1,num_labels);
  y_k(y(1)) = 1;
  
  for i=2:m
    dummy = zeros(num_labels,1)';
    dummy(y(i)) = 1;
    y_k = [y_k; dummy];
  endfor
else
  y_k = y;
endif
 


%X = X(interval,:);
%y_k = y_k(interval,1);

%% ================ Part 6: Initializing Pameters ================
%  In this part of the exercise, you will be starting to implment a two
%  layer neural network that classifies digits. You will start by
%  implementing a function to initialize the weights of the neural network
%  (randInitializeWeights.m)

fprintf('\nInitializing Neural Network Parameters ...\n')

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
                                    num_labels, X, y_k, lambda);

% Now, costFunction is a function that takes in only one argument (the
% neural network parameters)

% IF the function TERMNINATES
% within a few iterations, it could be an indication that the function value
% and derivatives are not consistent (ie, there may be a bug in the
% implementation of your "f" function). The function returns the found
% solution "X", a vector of function values "fX" indicating the progress made
% and "i" the number of iterations (line searches or function evaluations,
% depending on the sign of "length") used.

[nn_params, cost] = fmincg(costFunction, initial_nn_params, options);

figure;

plot([1:length(cost)],cost);
xlabel = 'Anzahl an Iterationen';
ylabel = 'J(theta)';

% Obtain Theta1 and Theta2 back from nn_params
ThetaArray = cell(1,anzahlLayer-1);

if (anzahlLayer~= 2)
  indexArray = zeros(anzahlLayer-2,1);
  % Reshape nn_params back into the parameters Theta1 and Theta2, the weight matrices
  % for our 2 layer neural network
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
    ThetaArray(1) =reshape(nn_params(1:end), ...
                 num_labels, input_layer_size + 1);
endif
                       
%% ================= Part 10: Implement Predict =================
%  After training the neural network, we would like to use it to predict
%  the labels. You will now implement the "predict" function to use the
%  neural network to predict the labels of the training set. This lets
%  you compute the training set accuracy.

if testBeispieleExistieren
  X2 = testBeispiele(:,1:end-1);
  y2 = testBeispiele(:,end);
  m2 = size(X2,1);
  if MULTICLASS
    y2_k = zeros(1,num_labels);
    y2_k(y2(1)) = 1;
    for i=2:m2
      dummy = zeros(num_labels,1)';
      dummy(y2(i)) = 1;
      y2_k = [y2_k; dummy];
    endfor
  else
    y2_k = y2;
  endif
endif


% untrainierte Parameter

pred = predict4(initial_ThetaArray, X, MULTICLASS);
fprintf('\nuntrainierte Parameter:\n\nTraining Set Accuracy: %f\n', mean(double(pred == y)) * 100);

if testBeispieleExistieren
  pred = predict4(initial_ThetaArray, X2, MULTICLASS);
  fprintf('Test Set Accuracy: %f\n', mean(double(pred == y2)) * 100); 
endif

% trainierte Parameter

pred = predict4(ThetaArray, X, MULTICLASS);
fprintf('\ntrainierte Parameter:\n\nTraining Set Accuracy: %f\n', mean(double(pred == y)) * 100);

if testBeispieleExistieren
  pred = predict4(ThetaArray, X2, MULTICLASS);
  fprintf('Test Set Accuracy: %f\n', mean(double(pred == y2)) * 100);
endif 

endif