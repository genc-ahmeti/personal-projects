function [J grad] = nnCostFunction5(nn_params,
                                   anzahlLayer,
                                   MULTICLASS,
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y_k, lambda)

% ------------------------ KOMPLETT VEKTORISIERT !!!!! -------------------------------------        

% y mit y_k getauscht (damit keine einzige for-schleife vorkommt!!!
% -> darauf achten, falls man wieder mit der anderen funktionen rechnen will!!!!
                  
                                   
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

ThetaArray = cell(1, anzahlLayer-1);
Theta_gradArray = cell(1, anzahlLayer-1);
deltaArray = cell(1, anzahlLayer-1);
aArray = cell(1, anzahlLayer-1);
zArray = cell(1, anzahlLayer-1);

if anzahlLayer~=2
  
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
                 
  ThetaArray(1) = reshape(nn_params, ...
                 num_labels, (input_layer_size + 1)); 
endif
% Setup some useful variables

m = size(X, 1);
         
% You need to return the following variables correctly 
J = 0;

for i=1:(anzahlLayer-1)
  Theta_gradArray(i) = zeros(size(cell2mat(ThetaArray(i))));
endfor

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
%


X = [ones(size(X,1),1) X];

aArray(1) = X;
zArray(1) = cell2mat(ThetaArray(1)) * (cell2mat(aArray(1)))';

for i=2:(anzahlLayer-1)
  aArray(i) = sigmoid(cell2mat(zArray(i-1)));
  zArray(i) = (cell2mat(ThetaArray(i)))*[ones(1,m); cell2mat(aArray(i))];
endfor

h_theta = sigmoid(cell2mat(zArray(end)));

if MULTICLASS
  J = (-sum(sum((y_k*log(h_theta)).*eye(m)))-sum(sum(((1-y_k)*log(1-h_theta)).*eye(m))))/m;
else
  J = (-log(h_theta)*y_k-(log(1-h_theta)*(1-y_k)))/m;
  endif


% J mit Regularisierung

for i=1:(anzahlLayer-1)
  Theta_withoutBias = [zeros(size(cell2mat(ThetaArray(i)),1),1) (cell2mat(ThetaArray(i)))(:, 2:end)];
  J = J + sum(sum((Theta_withoutBias' * Theta_withoutBias).*eye(size(cell2mat(ThetaArray(i)),2)))) * (lambda/(2*m)); 
endfor
% GRADIENT BERECHNEN

% Schritt 1

% schon oben erledigt 

% Schritt 2
deltaArray(1) = h_theta - y_k';

% Schritt 3
% Ganz ganz wichtig: bei den delta termen wird
% der bias nicht benutzt bzw. miteinberechnet

for i=2:(anzahlLayer-1)
  deltaArray(i) = ((cell2mat(ThetaArray(end-i+2)))'(2:end, :)*cell2mat(deltaArray(i-1))).*sigmoidGradient(cell2mat(zArray(anzahlLayer-i)));
endfor

% Schritt 4

Theta_gradArray(1) = (cell2mat(deltaArray(end))*(cell2mat(aArray(1))))/m;

for i=2:(anzahlLayer-1) 
  Theta_gradArray(i) = (cell2mat(deltaArray(anzahlLayer-i))*[ones(1,m); (cell2mat(aArray(i)))]')/m;
endfor

% Regularisierung hinzugefügt

for i=1:(anzahlLayer-1)
  Theta_gradArray(i) = cell2mat(Theta_gradArray(i)) + (lambda/m)*[zeros(size(cell2mat(ThetaArray(i)),1),1) (cell2mat(ThetaArray(i)))(:, 2:end)];
endfor
% -------------------------------------------------------------

% =========================================================================

% Unroll gradients

grad = (cell2mat(Theta_gradArray(1)))(:);

for i=2:(anzahlLayer-1)
  grad = [grad ; (cell2mat(Theta_gradArray(i)))(:)];
endfor

endfunction