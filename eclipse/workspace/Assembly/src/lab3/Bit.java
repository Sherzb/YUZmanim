package lab3;

import java.util.Random;

import lab1.Gate;

public class Bit
{
	private DFFforBit dff = new DFFforBit();
	private int dffOutput = 0;
	private int bitOutput = 0;
	private int prevLoad = 0;
	private int nextLoad = 0;
	private Random r = new Random();
	private Gate g = new Gate();
	
	public static void main(String[] args) 
	{
		Bit b = new Bit();
		b.run();
	}
	
	public void run() 
	{
		System.out.println("Output" + "    Clock" + "    Input" + "     Load");
		dff.run();
		while(true) {
			nextLoad = prevLoad;
			prevLoad = r.nextInt(2);
			
			dffOutput = dff.getOutput();
			
			bitOutput = g.Mux(bitOutput, dffOutput, nextLoad);
			
			System.out.print(" " + bitOutput);
			System.out.print("         " + dff.getClock());
			try {
				Thread.sleep(500);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
			System.out.print("        " + dff.getInput());
			System.out.println("          " + prevLoad);
		}
	}
}
