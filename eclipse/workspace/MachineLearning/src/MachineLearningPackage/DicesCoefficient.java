package MachineLearningPackage;

import java.util.HashSet;

public class DicesCoefficient
{
	public static void main(String[] args) {
		HashSet<String> bigrams1 = bigrammer(args[0]);
		HashSet<String> bigrams2 = bigrammer(args[1]);	
		double totalSize = bigrams1.size() + bigrams2.size();
		bigrams1.retainAll(bigrams2);
		
		double coefficient = (2.0 * bigrams1.size()) / totalSize;
		System.out.println(coefficient);
	}
	
	private static HashSet<String> bigrammer(String fullWord) {
		HashSet<String> bigramSet = new HashSet<String>();
		char[] charArray = fullWord.toCharArray();
		for(int i = 0; i < fullWord.length() - 1; i++) {
			bigramSet.add("" + charArray[i] + charArray[i+1]);
		}
		return bigramSet;
	}
}
