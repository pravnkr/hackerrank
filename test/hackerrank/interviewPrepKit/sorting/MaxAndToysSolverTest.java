package hackerrank.interviewPrepKit.sorting;

import static java.time.Duration.ofMillis; 

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.Arrays;

import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import hackerrank.interviewPrepKit.sorting.MaxAndToysSolver;

import org.junit.jupiter.api.DisplayName;;

class MaxAndToysSolverTest {

	private static final int MAX_TIME_IN_SECONDS = 4000;
	
	@ParameterizedTest
	@DisplayName("Test for maximum no of toys Max can purchase.")
	@CsvFileSource(resources = "/MaxAndToysTestData.csv")
	void testMaximumToys(@ConvertWith(MaxAndToysSolverTest.ToIntArrayArgumentConverter.class)int [] toyPrices,
			int availAmt, int maxToysDesired) {
		MaxAndToysSolver solver = new MaxAndToysSolver();
		int maxToysCal = assertTimeout(ofMillis(MAX_TIME_IN_SECONDS), () -> solver.maximumToys(toyPrices, availAmt));
		assertEquals(maxToysDesired, maxToysCal);
	}
	
	static class ToIntArrayArgumentConverter extends SimpleArgumentConverter {
		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			if (source instanceof String && int[].class.isAssignableFrom(targetType)) {
				String src = (String) source;
				int[] res = Arrays.stream(src.split("\\s*,\\s*"))
								.mapToInt(Integer::parseInt)
								.toArray();
				return res;
			}
			else {
				throw new ArgumentConversionException("Unsupported Conversion: " + source.getClass() + "To" + targetType);
			}
		}
	}
}
