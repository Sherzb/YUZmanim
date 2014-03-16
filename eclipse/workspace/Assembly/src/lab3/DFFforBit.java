package lab3;

import java.util.Random;

public class DFFforBit
{
	private int clock = 0;
	private int input = 0;
	private int output = 0;
	private Random r = new Random();

	public static void main(String[] args)
	{
		DFFforBit dff = new DFFforBit();
		dff.run();
	}

	public void run() {	
		Thread clockThread = new Thread() {
			public void run() {
				while(true) {
					clock = (clock + 1) % 2;
					try {
						Thread.sleep(500);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
					output = input;
				}
			}
		};

		Thread inputThread = new Thread() {
			public void run() {
				while(true) {
					boolean isThereInput = r.nextBoolean();
					if(isThereInput) {
						input = r.nextInt(2);
					}
					else {
						input = 0;
					}	
					try {
						Thread.sleep(600);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};

		clockThread.start();
		inputThread.start();
	}
	
	public String starify(int x) {
		if (x == -1) {
			return "*";
		}
		return x + "";
	}
	
	public int getInput()
	{
		return input;
	}
	
	public int getOutput()
	{
		return output;
	}
	
	public int getClock() 
	{
		return clock;
	}
	
	public void setInput(int x)
	{
		input = x;
	}
}
