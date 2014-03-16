package kukoruPuzzle;

public class EmptyBox extends Box
{
	private int value = 0;
	
	public void setValue(int x) {
		value = x;
	}
	public int getValue() {
		return value;
	}
	public void printSpaceChar() {
		System.out.print(" ");
	}
	public void printLeftData() {
		System.out.print("  ");	
	}
	public void printUpData() {
		System.out.print("  ");
	}
	public void printLeftDataOrAnswer() {
		System.out.print(value + " ");
	}
}
