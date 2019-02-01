package hackerrank.interviewPrepKit.sorting;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FraudActNotifCalculatorTest {

	/*@Test
	void testMedianOf() {
		FraudActNotifCalculator calc1 = new FraudActNotifCalculator(3, new int[] {1, 1, 2, 3, 4, 4, 5});
		FraudActNotifCalculator calc2 = new FraudActNotifCalculator(4, new int[] {1, 2, 3, 4, 4, 6, 9, 2, 5, 4});
		assertEquals(1.0, calc1.medianOf(0, 3));
		assertEquals(2.0, calc1.medianOf(1, 4));
		assertEquals(3.0, calc1.medianOf(2, 5));
		assertEquals(4.0, calc1.medianOf(3, 6));
		assertEquals(4.0, calc1.medianOf(4, 7));
		assertEquals(5.0/2, calc2.medianOf(0,  4));
		assertEquals(7.0/2, calc2.medianOf(1,  5));
		assertEquals(8.0/2, calc2.medianOf(2,  6));
		assertEquals(10.0/2, calc2.medianOf(3,  7));
		assertEquals(10.0/2, calc2.medianOf(4,  8));
		assertEquals(11.0/2, calc2.medianOf(5,  9));
		assertEquals(9.0/2, calc2.medianOf(6,  10));
	}*/
	
	@ParameterizedTest
	@MethodSource("notifCalcProvider")
	void testTotFreqOfNotif(FraudActNotifCalculator calc, int expecVal) {
		assertEquals(expecVal, calc.totFreqOfNotif());
	}
	
	static Stream<Arguments> notifCalcProvider() {
		try(Scanner scanner = new Scanner(new BufferedReader(new FileReader("test/resources/FraudNotifTestData.txt")))) {
			String[] arrSize = scanner.nextLine().split(" ");
	        int n = Integer.parseInt(arrSize[0]);
	        int d = Integer.parseInt(arrSize[1]);
	        int[] expenditure = new int[n];
	
	        String[] expenditureItems = scanner.nextLine().split(" ");
	        scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
	        for (int i = 0; i < n; i++) {
	            int expenditureItem = Integer.parseInt(expenditureItems[i]);
	            expenditure[i] = expenditureItem;
	        }
	        FraudActNotifCalculator calc1 = new FraudActNotifCalculator(4, new int[] {1, 2, 3, 4, 4});
			FraudActNotifCalculator calc2 = new FraudActNotifCalculator(3, new int[] {10, 20, 30, 40, 50});
			FraudActNotifCalculator calc3 = new FraudActNotifCalculator(5, new int[] {2, 3, 4, 2, 3, 6, 8, 4, 5});
			FraudActNotifCalculator calc4 = new FraudActNotifCalculator(2, new int[] {2, 2, 1, 0});
	        FraudActNotifCalculator calc5 = new FraudActNotifCalculator(d, expenditure);
	        int expecVal1 = 0;
			int expecVal2 = 1;
			int expecVal3 = 2;
			int expecVal4 = 0;
			int expecVal5 = 492;
			return Stream.of(Arguments.of(calc1, expecVal1), Arguments.of(calc2, expecVal2),
					Arguments.of(calc3, expecVal3), Arguments.of(calc4, expecVal4), Arguments.of(calc5, expecVal5));
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
