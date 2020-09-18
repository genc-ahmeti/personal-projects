function p = predict4(cellWithThetas, X, MULTICLASS)
%PREDICT Predict the label of an input given a trained neural network
%   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
%   trained weights of a neural network (Theta1, Theta2)

% Useful values
aArray = cell(1,((size(cellWithThetas))(2)));
zArray = cell(1,((size(cellWithThetas))(2)));

m = size(X, 1);
num_labels = size(cell2mat(cellWithThetas(end)), 1);

% You need to return the following variables correctly 
p = zeros(size(X, 1), 1);

X = [ones(size(X,1),1) X];

aArray(1) = X;
zArray(1) = (cell2mat(aArray(1))) * (cell2mat(cellWithThetas(1)))';

for i=2:((size(cellWithThetas))(2))
  aArray(i) = sigmoid(cell2mat(zArray(i-1)));
  zArray(i) = [ones(m,1) cell2mat(aArray(i))] * (cell2mat(cellWithThetas(i)))';
endfor

h_theta = sigmoid(cell2mat(zArray(end)));

if MULTICLASS
  %display(h_theta(4990:5000,:));
  [dummy, p] = max(h_theta, [], 2);
  %display(p');
else
  p = h_theta >=0.5;
endif

% =========================================================================


end