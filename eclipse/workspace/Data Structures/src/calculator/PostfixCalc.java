package calculator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Postfix calculator. It takes an expression written in regular notation, changes it to postfix notation, and then evaluates the 
 * postfix notation. It then outputs that final answer. 
 * @author Shmuel
 *
 */
public class PostfixCalc
{

	private static ArrayList<String> numbers = new ArrayList<String>();
	private static HashMap<String, int[]> weights = new HashMap<String, int[]>();

	public static void main(String[] args) {
		//Add the numbers 0-9 to an ArrayList
		for(int i = 0; i < 10; i++) {
			numbers.add(String.valueOf(i));
		}

		//Add the weights to the HashMap
		weights.put("+", new int[]{21,20});
		weights.put("-", new int[]{21,20});
		weights.put("*", new int[]{31,30});
		weights.put("/", new int[]{31,30});
		weights.put("%", new int[]{31,30});
		weights.put("^", new int[]{40,41});
		weights.put("(", new int[]{0,100});
		weights.put(")", new int[]{1,0});

		//Scanner Stuff
		System.out.println("Type the expression you wish to calculate and press enter:");
		Scanner scan = new Scanner(System.in);
		String exp = scan.nextLine();
		ArrayList<String> posf = convert(exp);
		System.out.println(evaluate(posf));
		scan.close();
	}

	/**
	 * Converts the regular input notation into postfix notation. It does this basically the way we had described this in lab. It goes
	 * through each element of the input array, and, if it's a number, adds it to the final array (called postFix). If it's an 
	 * operator, it adds it to the operator stack. That stack is popped based off the weight defined in the global weights 
	 * variable.
	 * @param norm The expression inputed.
	 * @return The postfix notation of the input.
	 */
	public static ArrayList<String> convert(String norm) {
		ArrayList<String> allTokens = getTokens(norm);       //Sets an ArrayList that contains all the tokens
		ArrayList<String> postFix = new ArrayList<String>(); //The final postFix ArrayList
		Stack<String> expressions = new Stack<String>();     //The stack that contains the operators

		for(int i = 0; i < allTokens.size(); i++) {
			//If the next token in the ArrayList is a number
			if(isInteger((allTokens.get(i)))) {
				postFix.add(allTokens.get(i));
			}
			//If the next token is an operator
			else {
				while(!expressions.isEmpty() && weights.get(expressions.peek())[0] >= weights.get(allTokens.get(i))[1]) {
					//The [0] index is the outgoing weight, and the [1] is the incoming, so the [0] weight of the top
					//member of the stack is compared to the [1] weight of the incoming token.
					if(expressions.peek().equals("(") && allTokens.get(i).equals(")")) {
						//If it's a parentesis pair, pops off the open one, and break the loop.
						expressions.pop();
						break;
					}
					else {
						//If it's popped and not a parentises, add it to the final array.
						postFix.add(expressions.pop());
					}
				}
				if(!allTokens.get(i).equals(")")) { //We're not adding the closed paren to the postfix array
					expressions.push(allTokens.get(i));
				}
			}
		}
		//Once we went through all the tokens, we pop the rest of the operators on.
		while(expressions.size() != 0) {
			postFix.add(expressions.pop());
		}
		return postFix;
	}


	/**
	 * Evaluates the postfix notation. It first sees if the token is an operator. If it is, it pushes the result of the operator
	 * operating on the past two operands. This sometimes necessitates the creation of a temp int. For example, "35/" is equal to
	 * 3/5, not 5/3, so I pop and save 5 as a temp int, and then divide 3 (the next popped thing) by 5.  Addition and multiplication are simpler.
	 * Exponents used a for loop instead of Math.exp because of parsing Doubles vs. parsing Integers issues. 
	 * 
	 * If the token is a number, it simply pushes it onto the stack.
	 * @param posf An ArrayList in postfix notation
	 * @return The evaluated expression
	 */
	public static int evaluate(ArrayList<String> posf) {
		//Creates a stack that'll hold the operands not yet operated on.
		Stack<String> results = new Stack<String>();
		//Iterates through all the tokens.
		for(int i = 0; i < posf.size(); i++) {
			String item = posf.get(i);
			if(item.equals("+")) {
				results.push(Integer.toString(Integer.parseInt(results.pop()) + Integer.parseInt(results.pop())));
			} 
			else if(item.equals("-")) {
				int temp = Integer.parseInt(results.pop());
				results.push(Integer.toString(Integer.parseInt(results.pop()) - temp));
			}
			else if(item.equals("*")) {
				results.push(Integer.toString(Integer.parseInt(results.pop()) * Integer.parseInt(results.pop())));
			}
			else if(item.equals("/")) {
				String temp = results.pop();
				results.push(Integer.toString(Integer.parseInt(results.pop()) / Integer.parseInt(temp)));
			}
			else if(item.equals("%")) {
				String temp = results.pop();
				results.push(Integer.toString(Integer.parseInt(results.pop()) % Integer.parseInt(temp)));
			}
			else if(item.equals("^")) {				
				int pow = Integer.parseInt(results.pop());
				results.push(Integer.toString((int) Math.pow(Integer.parseInt(results.pop()), (double) pow)));
			}
			else {  //If the next item is a number, simply push it.
				results.push(item);
			}
		}
		//We now should have a single answer on the stack.
		return Integer.parseInt(results.pop());
	}

	/**
	 * The method used to split the input string into tokens. It checks if the next part of the string is a digit. If it is, it appends it
	 * to whatever digits were added before. If it's not, it adds whatever number was created (if there was one), adds the operator, and 
	 * resets the digit.
	 * @param norm The input stream
	 * @return An ArrayList that contains each individual operator and number in a separate index.
	 */
	public static ArrayList<String> getTokens(String norm) {
		//Creates an ArrayList to hold all the tokens
		ArrayList<String> allTokens = new ArrayList<String>();
		//A new token to store the digit.
		String token = "";
		for(int i = 0; i < norm.length(); i++) {
			String part = norm.substring(i, i + 1);
			//If the element is a digit
			if(numbers.contains(part)) {
				//Append it to whatever digits we had before
				token = token + part;
			}
			else if(!token.equals("")){
				//We hit an operator that follows a number, which is why token isn't empty.
				allTokens.add(token); //Add the number
				allTokens.add(part); //Add the operator
				token= ""; //Reset the token
			}
			else {
				//We hit an operator that follows an operator
				allTokens.add(part); //Add the operator
			}
		}
		//If the last thing is a number (and not a parentesis, which would already have been added).
		if(!token.equals("")) {
			allTokens.add(token);
		}
		return allTokens;
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