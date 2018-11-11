
import java.util.*;
/**
 *
 * @author khalil
 */
public class infix {
    public static void main(String[] args) {
        //Getting the INITIAL input
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();

        //Variables
        int result = 0; //This is going to hold our final result
        String finalInput; // This is going to hold the user's input after modification
        String str; // After modifying the finalInput, I am going to be using str instead of finalInput for speed.

        //***** Modifying the INITIAL input *****
            //removing whitespace
        String noWhitespace = input.replaceAll("\\s","");
            //Fixing some operators
                // - Loop throught the user's input to look if it contains any "special" operators.
        while(noWhitespace.contains("++") || noWhitespace.contains("--") || noWhitespace.contains("+-") || noWhitespace.contains("-+")){
            String newString = noWhitespace;
            noWhitespace = noWhitespace.replaceAll("\\-\\-", "\\+"); // -- => +
            noWhitespace = noWhitespace.replaceAll("\\-\\+","\\-");  // -+ => -
            noWhitespace = noWhitespace.replaceAll("\\+\\-","\\-");  // +- => -
            noWhitespace = noWhitespace.replaceAll("\\+\\+","\\+");  // ++ => +

        }finalInput = noWhitespace;

        str = finalInput;
        //System.out.println("Final String: " + str);

        StringBuffer str2; // Because Variables are immutable in Java, we use StringBuffer in order to do opperations on them.

        //Booleans that return TRUE if the user's input contains any Operator or Parentheses.
        boolean containsOp = str.contains("*") || str.contains("/") || str.contains("+") || str.contains("-");
        boolean containsPar = str.contains("(") || str.contains (")");

        //FIRST CASE:
            //If the user's input do not contain any parentheses.
        if(!containsPar){
            str = finalInput;
            if(str.charAt(0) == '+')
            {
                str = str.replaceAll("\\+","");}
            int result2 = compute(str);
            System.out.println("RESULT = " + result2);
            }
        //SECOND CASE:
            //If the user's input contains parentheses BUT do not contain any operator.
                // This CASE handles inputs in this formation ((((int))))
        else if(containsPar && !containsOp){
            str = finalInput;
            str=str.replaceAll("\\(", "").replaceAll("\\)","");
            int result3 = compute(str);
            System.out.println("RESULT = " + result3);
        }
        //THIRD CASE:
            //If the user's input contains parentheses and operators
        else{
        str = "(" + finalInput + ")"; //I added parentheses at the beginning and end of the user's input to help handle some cases.
        //MAIN LOOP:
        while(str.contains("*") || str.contains("/") || str.contains("+") || str.contains("-") || str.contains("(") || str.contains(")")){
            // This FOR loop visits the user's input from left to right looking fot the first ")"
        for(int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (c == ')'){
                String sub1;
                int end = i;
            // This FOR loop visits the user's input from right to left looking fot the first "("
            for(int y = end ; y >= 0; y--){
                char b = str.charAt(y);
                if(b == '('){
                    int start = y;
                    //Make sure that the first opened parentheses is not after the first closed parentheses.
                    if (start+1 > str.indexOf(")")){
                        for(int w = str.indexOf(")") ; w >= 0; w--){
                            char d = str.charAt(w);
                            if(b == '('){
                            int start2 = w;
                            sub1 = str.substring(start2 + 1, str.indexOf(")"));
                            }
                        }
                    }else{
                        sub1 = str.substring(start + 1, str.indexOf(")"));
                        if(sub1.charAt(0) == '+'){
                            sub1 = sub1.replaceFirst("\\+","\\");
                        }else if(sub1.charAt(0) == '-'){
                            sub1 = sub1.replaceFirst("\\-","\\+");
                            result = compute(sub1);
                            result = (-1) * result;
                            sub1 = Integer.toString(result);
                            String sub2 = sub1;
                            str2 = new StringBuffer(str);
                            //System.out.println("/// STR2 : " + str2);
                            //System.out.println(str2);
                            str2.replace(start, end + 1 , sub2);
                            str = str2.toString();
                            sub1 = str;
                            //System.out.println("STR:" + str);
                            break;
                        }
                        else if(sub1.contains("*-")){
                            result = compute(sub1);
                            result = -result;

                        }
                    result = compute(sub1);
                    sub1 = Integer.toString(result);
                    //sub1 = new Integer(result).toString();
                    String sub2 = sub1;
                    str2 = new StringBuffer(str);
                    //System.out.println("/// STR2 : " + str2);
                    //System.out.println(str2);
                    str2.replace(start, end + 1 , sub2);
                    str = str2.toString();
                    sub1 = str;
                    //System.out.println("STR:" + str);
                    if (containsOp)
                        break;
                    else
                        containsOp = false;
                    }
                }
            }
            }
        }
        } // END OF WHILE LOOP <<
        System.out.println("RESULT = " + result);
        } // << END OF THIRD CASE
    }
static int compute(String equation) {
        int result = 0;
        if(equation.contains("*+")){
            equation = equation.replace("*+", "*");
        }
        if(equation.contains("*--")){
            equation = equation.replace("*--", "*");
        }
        if(equation.contains("*-")){
            equation = equation.replace("*-", "*1*");
        }

        String noMinus = equation.replace("-", "+-");
        //String parts[] = equation.split("(?=[/*+-])|(?<=[/*+-])");
        String byPluses[] = noMinus.split("\\+");
        for (String multipl : byPluses) {
            String byMultipl[] = multipl.split("\\*");
            int multiplResult = 1;
            for (String operand : byMultipl) {
                if (operand.contains("/")) {
                    String division[] = operand.split("\\/");
                    int divident = Integer.parseInt(division[0]);
                    for (int i = 1; i < division.length; i++) {
                        divident /= Integer.parseInt(division[i]);
                    }
                    multiplResult *= divident;
                }/* else if (operand.contains("-")){
                    String substraction[] = operand.split("\\-");
                    int substract = Integer.parseInt(substraction[0]);
                    for (int i = 0; i < substraction.length; i++) {
                        substract -= Integer.parseInt(substraction[i]);
                    }
                    multiplResult *= substract;
                }*/
                else {
                    multiplResult *= Integer.parseInt(operand);
                }
            }
            result += multiplResult;
        }

        return result;
    }
    }
