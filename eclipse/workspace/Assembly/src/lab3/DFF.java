package lab3;

import java.util.Random;

public class DFF
{
	private int clock = 0;
	private int input = 0;
	private int output = 0;
	private Random r = new Random();

	public static void main(String[] args)
	{
		DFF d = new DFF();
		d.run();
	}

	public void run() {	
		Thread clockThread = new Thread() {
			public void run() {
				while(true) {
					System.out.print(starify(output) + "   ");
					clock = (clock + 1) % 2;
					System.out.print(clock + "    ");	
					output = input;
					System.out.println(starify(input) + "    ");
					try {
						Thread.sleep(500);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
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
						input = -1;
					}	
					try {
						Thread.sleep(700);
					} catch(InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		};

		System.out.println("* means no input");
		System.out.println("O" + "   " + "C" + "    " + "I");
		try {
			Thread.sleep(4000);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		clockThread.start();
		inputThread.start();
	}
	
	public String starify(int x) {
		if (x == -1) {
			return "*";
		}
		return x + "";
	}
}
