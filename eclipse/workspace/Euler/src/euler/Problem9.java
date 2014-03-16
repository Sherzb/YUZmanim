package euler;

public class Problem9
{
	public static void main(String[] args) {
		for(int c = 700; c > 200; c--) {
			int remainder = 1000 - c;
			int a = 0;
			int b = 0;
			if(remainder % 2 == 0) {
				a = remainder/2 - 1;
				b = remainder/2 + 1;
			}
			else {
				a = remainder/2;
				b = remainder/2 + 1;
			}
			while(a*a + b*b <= c*c && a > 0 && a <= c && b <= c) {
				System.out.println("Checking: " +  a + " " + b + " " + c);
				if(a*a + b*b == c*c) {
					System.out.println("The answer is " + a + " " + b + " " + c);
					return;
				}
				else {
					a--;
					b++;
				}
			}
		}		
	}
}

