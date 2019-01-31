package hackerrank.interviewPrepKit.sorting;

import java.util.Arrays;
import java.util.Objects;

/**
 * This class is a simulation of the Solution for the Hacker Rank problem
 * <a href=https://bit.ly/2WvWWyg>Max And Toys</a>
 * 
 * @author praveen
 *
 */
public class MaxAndToysSolver {
	
	/**
	 * Returns a maximum no of toys Max can purchase for his son with the
	 * available amount	{@code k} from the list of toys having the prices
	 * {@code prices}.							
	 * @param prices the array of prices for a toys, available for purchase.
	 * @param k the amount of money max can spend over toys.
	 * @return the maximum no of toys max can purchase for his son.
	 * @throws IllegalArgumentException
	 * 			if {@code k < 0} 
	 */
	public int maximumToys(int[] prices, int k) {
		 Objects.requireNonNull(prices);
		 if (k < 0) {
			 throw new IllegalArgumentException("k must be a non-negative Integer: " + k);
		 }
		 Arrays.sort(prices);
		 int maxToys = 0;
		 for (int price : prices) {
			 k -= price;
			 if (k >= 0) {
				 maxToys++;
			 }
			 else return maxToys;
		 }
		 return maxToys;
	}
}