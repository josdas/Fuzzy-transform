# Fuzzy-search

The way for utilazing neural network as transformation from string to vector. 

The distance between two vectors is similar to Levenshtein. This way can be used to draw words on a plot.

I used recurent neural network. It lets process letters one by one.

The traning process:
1) Take two random words
2) Calc Levenshtein distance between words (L)
3) Calc distance between vectors after transformation (D)
4) Lets take such position that minimize (L - D)^2
5) Start back propogetion for that position
