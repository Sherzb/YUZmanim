package lab3;

import lab1.Gate;

public class Register{

	private int[] memory;
	private Gate g = new Gate();

	public Register()
	{
		memory = new int[16];
		for(int i = 0; i < 16; i++)
			memory[i] = 0;
	}

	public void setRegister(int[] input, int load)
	{
		for(int i = 0; i < 16; i++)
			memory[i] = g.Mux(memory[i], input[i], load);
	}

	public int[] getRegister()
	{
		return memory;
	}
}
