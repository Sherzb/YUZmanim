package lab4; 

/*
 *    ROM32K class
 *    To Use safely, always do the following:
 *    		- ROM can only be written to when it is first created
 *    		- Addresses must be exactly 14 bits
 *    		- Assumes big endian for addresses (aka 0001 = 1 and 1000 = 8)
 *    		- The inputted file must be in the same directory
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ROM32K {

	private int[][] memory;

	public ROM32K(int[] address, File file)
	{
		memory = new int[16384][16];
		for(int i = 0; i < 16384; i++)
			for(int j = 0; j < 16; j ++)
				memory[i][j] = 0;
		loadROM32K(address, file);
	}

	private void loadROM32K(int[] address, File file)
	{
		try {
			BufferedReader input =  new BufferedReader(new FileReader(file));
			
			//Converts the binary 14 bit address to a base-10 address
			int address_base10 = 0;
			for(int i = 13; i >= 0; i--) {
				address_base10 += address[i]*Math.pow(2.0, 13-i);
			}
			
			String str;
			while((str = input.readLine()) != null)
			{
				for(int i = 0; i < 16; i++){
					if(str.charAt(i) == '0'){
						memory[address_base10][i] = 0;
					}
					else{
						memory[address_base10][i] = 1;
					}
				}
				address_base10++;
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int[] getROM32K(int[] address)
	{
		int address_base10 = 0;
		for(int i = 13; i >= 0; i--)
			address_base10 += address[i]*Math.pow(2.0, 13-i);


		return memory[address_base10];
	}
}
