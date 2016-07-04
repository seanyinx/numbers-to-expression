#Find a expression for a sequence of numbers
Input: a string of numbers separated with comma(,) for example: ```7,9,4,2```

Use three operators below to make a correct expression
- add (+)
- minus (-)
- equal (=)

For example: ```7-2+4=9``` or ```9-7=4-2``` or others.

equal(=) has to be included only once.
e.g. input:```1,1,1,1```
- ```1=1=1=1``` is not a valid expression
- ```1+1-1=1``` is a valid expression.

If the input string can be made of at least one such expression, output: YES, otherwise output: NO