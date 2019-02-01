package hackerrank.interviewPrepKit.sorting;

import java.util.Arrays;
import java.util.Objects;


/**
 * This class is a simulation of solution for the hackerrank problem 
 * <a href=https://bit.ly/2Uzaozi>Fraudulent Activity Notifications</a>.
 * 
 * @author praveen
 *
 */
public class FraudActNotifCalculator {
	
	/*
	 * The exact no of trailing days from the day(exclusive) of the most recent
	 * transaction, for which the bank will trace the transaction data for deciding
	 * whether the current transaction does qualify as a possibility of fraud
	 * activity so that they can notify about it to the concerned user.
	 * 
	 */
	private int trailDays;
	
	/*
	 * The expenditure data of the user where each entry is an expenditure for a 
	 * particular day and the entries are filled in ascending order of date. 
	 */
	private int [] expenditures;
	
	/* The prefix sum array that tracks the position of transactions in the sub-array
	 *  of expenditure having length {@code trailDays}  
	 */
	private final int [] prefCountSortArr;
	
	/*
	 * The state about whether the {@code prefCountSortArr} has been prepared atleast 
	 * once or for the first time.
	 */
	private boolean allReadySortedOnce;
	
	/**
	 * The minimum no of trailing days that can be picked by a bank for tracing,
	 * possibly the fraud transaction, for a user.
	 */
	public static final int MINM_TRAIL_DAYS = 1;
	
	/**
	 * The Minimum no of transactions(where each transaction is mapped to a single day) that 
	 * can be picked for a calculations.
	 */
	public static final int MINM_SPENT_DAYS = 1;
	
	/**
	 * The Maximum no of transactions(where each transaction is mapped to a single day) that 
	 * can be picked for a calculations.
	 */
	public static final int MAXM_SPENT_DAYS = 200000;
	
	/**
	 * This is actually the possible maximum length of the array passed in as a parameter of constructor
	 * of this class.
	 */
	public static final int MAXM_TRANSACT_RANGE = 200;
	
	/*
	 * Returns a String formatted allowed range for a total no of transactions or the length
	 * of the expenditures.
	 */
	private static String possibSpendDaysRange() {
		return "[" + MINM_SPENT_DAYS + ", " + MAXM_SPENT_DAYS + "]";
	}
	
	/* 
	 * Returns a String formatted allowed range for a trailing days.  
	 */
	private static String possibTrailDaysRange(int maxTrailDays) {
		return "[" + MINM_TRAIL_DAYS + ", " + maxTrailDays + "]";
	}
	
	private static int search(int[] arr, int start, int elem) {
		for (int i = 0; i < arr.length - start; i++) {
			if ((arr[start + i] == elem)
					|| (i != 0 && arr[start + i] > elem && arr[start + i - 1] < elem)) {
				return start + i;
			}
		}
		return -1;
	}
	
	/*
	 * calculate the prefix sum array for the range of elements from the index i, inclusive
	 * to the index j, exclusive.
	 */
	private void calPrefCountSortArrFor(int i, int j) {
		if (!allReadySortedOnce) {
			for (int k = 0; k < j - i; k++) {
				prefCountSortArr[expenditures[k + i]]++;
			}
			for (int p = 0; p < MAXM_TRANSACT_RANGE; p++) {
				prefCountSortArr[p + 1] += prefCountSortArr[p]; 
			}
			allReadySortedOnce = true;
		}
		else {
			int elemJustEntered = expenditures[j - 1];
			int elemJustGone = expenditures[i - 1];
			if (elemJustEntered > elemJustGone) {
				for (int h = 0; h < elemJustEntered - elemJustGone; h++) {
					prefCountSortArr[elemJustGone + h]--;
				}
			}
			else if(elemJustEntered < elemJustGone) {
				for (int h = 0; h < elemJustGone - elemJustEntered; h++) {
					prefCountSortArr[elemJustEntered + h]++;
				}
			}
		}
	}
	
	/*
	 * Implementation: Returns median of the range specified by i(inclusive) to 
	 * j(exclusive) in expenditures. it will be only called once for each sub-array
	 * of expenditures having length trailDays.
	 */
	private double medianOf(int i, int j) {
		calPrefCountSortArrFor(i, j);
		int firMedIndx = ((j - i - 1) / 2) + 1;
		int firstMedian = search(prefCountSortArr, 0, firMedIndx);
		if (((j - i) & 1) != 0) {
			return firstMedian;
		}
		else {
			double secondMedian = search(prefCountSortArr, 0, firMedIndx + 1);
			double avgMedian = (firstMedian + secondMedian) / 2.0;
			return avgMedian;
		}
	}
	
	/**
	 * Allocates a newly created {@code FraudActNotifCalculator} which is responsible for
	 * doing all the calculations concerned with marking the fraud transactions.
	 * @param trailDays The no of trailing days.
	 * @param expenditures The transactions data on which calculations are performed.
	 * @throws IllegalArgumentException
	 * 			if {@code trailDays} and {@code expenditures} are out of the range allowed.
	 */
	public FraudActNotifCalculator(int trailDays, int [] expenditures) {
		Objects.requireNonNull(expenditures);
		if (expenditures.length < MINM_SPENT_DAYS || expenditures.length > MAXM_SPENT_DAYS) {
			throw new IllegalArgumentException("Total no of spending days must follow a constraint on its limit of values"
					+ "(possible Range -> " + possibSpendDaysRange() + "): " + expenditures.length);
		}
		if(	trailDays < MINM_TRAIL_DAYS || trailDays > expenditures.length) {
			throw new IllegalArgumentException("Trail Days must follow a constraint on its limit of values(possible Range for"
					+ "expenditures given -> " + possibTrailDaysRange(expenditures.length) + "): " + trailDays);
		}
		this.trailDays = trailDays;
		this.expenditures = Arrays.copyOf(expenditures, expenditures.length);
		this.prefCountSortArr = new int[MAXM_TRANSACT_RANGE + 1];
		allReadySortedOnce = false;
	}
	/**
	 * Returns a total no of times the concerned bank user will be notified about
	 * the fraudulent activity for the <em>expenditures</em> s/he spent.
	 * 
	 * @return Total no of times the user will be notified for possibility of fraud.
	 */
	public int totFreqOfNotif() {
		int freq = 0;
		for (int i = 0; i < expenditures.length - trailDays; i++) {
			double median = medianOf(i, i + trailDays);
			if (!(expenditures[i + trailDays] < 2 * median)) {
				freq++;
			}
		}
		return freq;
	}
}
