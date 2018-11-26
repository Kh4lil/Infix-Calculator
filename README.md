# Infix-Calculator

Problem description:

Sometimes it is useful to have a program compute the value of a user-supplied string. For example an inventory program could ask for the number of flash drives you have in stock, and in counting you note that you have 3 crates of flash drives. Two of the crates contain 40 boxes of flash drives, each of which contain 25 flash drives. The third crate contains 10 boxes with 100 flash drives in each. Rather than having to hand-compute the number of flash drives, it would be great if you could enter 2*40*25 + 10*100 and let the program do the work. Another way of looking at this is that the program is reading an integer expression rather than only an integer. You will implement this ability (to read and evaluate an integer expression) in this project.

To be precise:

Input: syntactically-correct arithmetic expressions consisting of
Integers
Operators including +, -, *, /. (Integer division)
Balanced parentheses
Spaces
Output: the value of the arithmetic expression

The strategy
We will apply what is called a string-reduction algorithm, which, in short, works similarly to the following:

Procedure RemoveParens (exp) {
    While exp contains a pattern  '(#)'    // # = an int
        remove the ()'s surrounding ()'s
}
Procedure RemoveSpaces (exp) {
    While exp contains a ' '
       remove the space from exp
}
Function value (str) {
    Return the numeric value of str
}
read exp
RemoveSpaces(exp)
RemoveParens(exp)
while exp contains an operator
    if exp contains a * or /  surrounded by #'s then
       if the first such subexpression is n1 * n2 then
          Let v = value(n1) * value(n2)
          Replace  n1 * n2 with string(v) in exp
       else // the operator must be n1 / n2
          Let v = value(n1) / value(n2)
          Replace  n1 / n2 with string(v) in exp
     else if the first operator is + then
          Let v = value(n1) + value(n2)
          Replace  n1 + n2 with string(v) in exp
     else 
          Let v = value(n1) - value(n2)
          Replace  n1 - n2 with string(v) in exp
      RemoveSpaces(exp)
      RemoveParens(exp)
Let v = value(exp)  // must be a number since in contains no operators
An example should suffice to see how this works. Suppose you have as input 3 * (2-4) /(2). Then the algorithm above will perform the following transformations:

Action	Result
Input	3 * (2-4) /(2)
RemoveSpaces	3*(2-4)/(2)
RemoveParens	3*(2-4)/2
Match #[*/]#	fails
Match #+#	fails
Match #-#	3*(-2 )/2
RemoveSpaces	3*(-2)/2
RemoveParens	3*-2/2
Match #*#	-6 /2
RemoveSpaces	-6/2
RemoveParens	-6/2
Match #/#	-3
Done	-3
The removal of parentheses is essential to the success of the algorithm. Removal of blanks is not essential; however it may make it easier to simplify the pattern matching.
