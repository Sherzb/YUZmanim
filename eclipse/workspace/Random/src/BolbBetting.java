
public class BolbBetting
{
	public static void main(String[] args) {
		System.out.println(bold(1, 4.0/15, .5));
	}
	
	public static double bold(int goal, double capital, double p) 
	{
		if (goal == capital) {
			return 1;
		}
		else if (capital == 0) {
			return 0;
		}
		else {
			double bet = Math.min(capital, goal - capital);
			return (double)p * bold(goal, capital + bet, p) + (1-p) * bold(goal, capital - bet, p);
		}
		
		
	}
	
}
