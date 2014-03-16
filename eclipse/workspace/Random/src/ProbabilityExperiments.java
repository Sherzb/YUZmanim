import java.util.Random;

/**

 * @author Shmuel
 *
 */
public class ProbabilityExperiments
{
	private static Random rand = new Random();
	
	public static void main(String[] args) 
	{
		int total = 0;
		for (int i = 0; i < 1000000; i++) {
			total += noAces();
		}
		System.out.println(total / (double)1000000);
	}
	
	/**
	 * You choose a card from  a deck of cards. If it's not an ace, you get a dollar. You keep picking until you
	 * get an ace. What is the EV of this game?
	 * A: 9.60
	 * @return
	 */
	public static int noAces() 
	{
		int cards = 52;
		int chosen = 0;
		while(true) {
			int choice = rand.nextInt(cards);
			if(choice < 4) {
				return chosen;
			}
			else {
				chosen++;
				cards--;
			}
		}
	}
}
