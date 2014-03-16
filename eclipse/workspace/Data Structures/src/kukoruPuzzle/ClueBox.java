package kukoruPuzzle;

public class ClueBox extends Box
{
private int upValue = 0;
private int leftValue = 0;
	
	public void setLeftValue(int x) {
		leftValue = x;
	}
	public void setUpValue(int x) {
		upValue = x;
	}
	
	public void printSpaceChar() {
		System.out.print("\\");
	}
	
	public void printLeftData() {
		if(leftValue == 0) {
			System.out.print("**");
		}
		//The next part makes sure the spacing is correct
		else if (String.valueOf(leftValue).length() == 1) {
			System.out.print(leftValue + " ");
		}
		else {
			System.out.print(leftValue);
		}
	}
	
	public void printLeftDataOrAnswer() {
		if(leftValue == 0) {
			System.out.print("**");
		}
		//The next part makes sure the spacing is correct
		else if (String.valueOf(leftValue).length() == 1) {
			System.out.print(leftValue + " ");
		}
		else {
			System.out.print(leftValue);
		}
	}
	
	public void printUpData() {
		if(upValue == 0) {
			System.out.print("**");
		}
		else if (String.valueOf(upValue).length() == 1) {
			System.out.print(upValue + " ");
		}
		else {
			System.out.print(upValue);
		}
	}
}
