package calculator;

import java.util.Scanner;


public class RecursiveCalc {

	public static void main(String[] args) {
		System.out.println("Type the expression you wish to calculate and press enter:");
		Scanner scan = new Scanner(System.in);
		String exp = scan.nextLine();
		System.out.println(evaluate(exp));
		scan.close();
	}

	/**
	 * This method deals with the parens, breaking down the full expression. 
	 * If the equation contains a "(", it finds the last instance of "(", and
	 * the first ")" after that "(" (which must be a pair), evaluates the expression in them, and replaces the parens, and the expression 
	 * in it, with whatever was evaluated. So, for example, (2*3 + (2+2)) % 4 will be expressed as (2*3 + 4) % 4 after a single pass.
	 * If it just contains an expression, it evaluates the expression, and returns that, as the final answer.
	 * @param expression The full mathematical expression to be evaluated.
	 * @return Either the problem broken down, based off of the parens, or the final answer.
	 */
	private static int evaluate(String expression) {
		//If it contains a  parentesis.
		if(expression.contains("(")) {
			//Find the last index of open parens.
			int openIndex = expression.lastIndexOf("(");
			//The first index of closed parens after that.
			int closeIndex = expression.indexOf(")", openIndex);
			//Evaluates the expression in the parens
			String replaceParen = recursEval(expression.substring(openIndex + 1, closeIndex));
			//Replaces the parens with the evaluated expression
			String newExpression = expression.substring(0, openIndex) + replaceParen + expression.substring(closeIndex + 1, expression.length());
			//Recursively returns that to this method.
			return evaluate(newExpression);
		}
		//If it's just an expression or a number
		else {
			String answer = recursEval(expression);
			return Integer.parseInt(answer);

		}
	}


	/**
	 * The method that evaluates the expression, using reverse PEMDAS, starting from the right (except for "^", which starts
	 * from the left). It finds the last operator that will be used (called the "token"), and splits the expression around that, 
	 * recursively evaluating the left and right sides of that operator. If the term is simply an integer, that integer (in the
	 * form of a string) is returned.
	 * (There is some extra code here (If the token is either a + or -, and it's not a +, it's a -), but I put it in there for
	 * readability).
	 * @param expression The parentesis-less expression to be evaluated.
	 * @return The result of the expression passed, in the form of a String.
	 */
	private static String recursEval(String expression) {
		//Base Case
		if(isInteger(expression)) {
			return expression;
		}
		else {
			//First checks for a + or -, from the right
			for(int i = expression.length() - 2; i >= 1; i--) {
				//Assuming there is one, it finds the first instance of it (from the back), and splits it into 3 parts- the
				//leftSide, the rightSide, and the token (operator) itself.
				String leftSide = expression.substring(0, i);
				String rightSide = expression.substring(i + 1, expression.length());
				String token = expression.substring(i, i + 1);
				if(token.equals("+") || token.equals("-")) {
					if(token.equals("+")) {
						//Here, for example, it'll recursively evaluate the left and right sides, parse both of them to integers, 
						//add them, and return the String representation of that answer.
						return Integer.toString(Integer.parseInt(recursEval(leftSide)) + Integer.parseInt(recursEval(rightSide)));
					}
					else if(token.equals("-")) {
						return Integer.toString(Integer.parseInt(recursEval(leftSide)) - Integer.parseInt(recursEval(rightSide)));
					}
				}
			}
			//Then checks for a *, /, or %, again from the right
			for(int i = expression.length() - 2; i >= 1; i--) {
				String leftSide = expression.substring(0, i);
				String rightSide = expression.substring(i + 1, expression.length());
				String token = expression.substring(i, i + 1);
				if(token.equals("*") || token.equals("/") || token.equals("%")) {
					if(token.equals("*")) {
						return Integer.toString(Integer.parseInt(recursEval(leftSide)) * Integer.parseInt(recursEval(rightSide)));
					}
					else if(token.equals("/")) {
						return Integer.toString(Integer.parseInt(recursEval(leftSide)) / Integer.parseInt(recursEval(rightSide)));
					}
					else if(token.equals("%")) {
						return Integer.toString(Integer.parseInt(recursEval(leftSide)) % Integer.parseInt(recursEval(rightSide)));
					}
				}
			}
			//Finally checks for a ^ from the left.
			for(int i = 1; i <= expression.length() - 2; i++) {
				String leftSide = expression.substring(0, i);
				String rightSide = expression.substring(i + 1, expression.length());
				String token = expression.substring(i, i + 1);
				if(token.equals("^")) {
					//Stupid Math.pow only takes doubels...
					double base = (double) Integer.parseInt(recursEval(leftSide));
					double power = (double) Integer.parseInt(recursEval(rightSide));
					return Integer.toString( (int) Math.pow(base,power));
				}			
			}
		}
		//This should never be hit, but it was neater to write it this way, like I explained at the beginning of this method.
		return "";
	}

	/**
	 * From StackOverflow, kinda hackish, but whatever. If the parse throws an exception, it's because the "number" isn't a number,
	 * so the expression will be thrown.
	 * @param number The term that you may or may not be a number.
	 * @return True if the "number" is an int, false if it's not.
	 */
	public static boolean isInteger(String number) {
		try {
			Integer.parseInt(number);
			return true;
		}
		catch(NumberFormatException e ) {
			return false;
		}
	}
}

