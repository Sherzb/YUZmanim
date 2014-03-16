package euler;

import java.util.ArrayList;

public class Problem10
{
	public static void main(String[] args) {
		int sum = 0;
		ArrayList<Integer> primes = new ArrayList<Integer>();
		primes.add(2);
		for(int counter = 3; counter < 2000000; counter++) {
			System.out.println(counter);
			boolean testPrime = true;
			for(Integer prime : primes) {
				if(counter % prime == 0) {
					testPrime = false;
					break;
				}
			}
			if(testPrime) {
				primes.add(counter);
			}
		}
		for(int prime : primes) {
			sum += prime;
		}
		System.out.println(sum);
	}
}
