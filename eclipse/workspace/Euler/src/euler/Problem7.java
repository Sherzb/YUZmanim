package euler;

import java.util.ArrayList;

public class Problem7
{
	private static ArrayList<Integer> primes = new ArrayList<Integer>();

	public static void main(String[] args){
		primes.add(2);	
		for(int i = 2; primes.size() <= 10001; i++) {
			boolean prime = true;
			for(Integer primeNumber : primes) {
				if(i % primeNumber == 0) {
					prime = false;
				}
			}
			if(prime) {
				primes.add(i);
			}
		}
		System.out.println(primes.get(10000));
	}
}
