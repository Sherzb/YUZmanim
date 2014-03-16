package MachineLearningPackage;

public class LevenShtein
{
	public static void main(String[] args) {		
		System.out.println(levenshtein(args[0], args[1]));

		}
	
	private static int levenshtein(String str1, String str2) {
		int length1 = str1.length();
		int length2 = str2.length();
			
		if(length1 == 0) {
			return length2;
		}
		else if(length2 == 0) {
			return length1;
		}
		
		int cost = 1;
		if(str1.charAt(str1.length() - 1) == str2.charAt(str2.length() - 1)) {
			cost = 0;
		}
		
		return Math.min(Math.min(levenshtein(str1, str2.substring(0, length2 -1)) + 1, 
				                 levenshtein(str1.substring(0, length1 - 1), str2) + 1),		
		                         levenshtein(str1.substring(0, length1 - 1), str2.substring(0, length2 - 1)) + cost);		
	}
}
