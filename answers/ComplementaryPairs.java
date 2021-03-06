import java.util.*;

public class ComplementaryPairs {

	public static void main(String[] args) {

		runTests();
	}

	/**
	 * Returns K-complementary pairs in a given array of integers.
	 * Given Array A, pair (i, j) is K-complementary if K = A[i] + A[j].
	 *
	 * In each pair returned, the first index is always lower than the second index.
	 *
	 * Time efficiency: O(n * log n)
	 * Space efficiency: O(n)
	 */
	public static List<IntPair> findKComplementaryPairs(int k, int[] a) {

		final IndexedInt[] indexed = indexed(a);

		// Sort by value
		Arrays.sort(indexed, Comparator.comparingInt(p -> p.value));

		return findKComplementaryPairs(k, indexed);
	}

	/**
	 * Returns K-complementary pairs given an array of {@link IndexedInt} sorted by value.
	 * Given Array A, pair (i, j) is K-complementary if K = A[i] + A[j].
	 *
	 * In each pair returned, the first index is always lower than the second index.
	 */
	private static List<IntPair> findKComplementaryPairs(int k, IndexedInt[] a) {

		// Start looking for pairs from the extremes
		int lower = 0;            // this index starts at min value
		int upper = a.length - 1; // this index starts at max value

		final List<IntPair> result = new ArrayList<>();

		while (lower < upper) {

			final int sum = a[lower].value + a[upper].value;

			if (sum > k) {
				upper--; // sum is too high, move to a lower value

			} else if (sum < k){
				lower++; // sum is too low, move to a higher value

			} else {  // (sum == k)

				if (a[lower].value != a[upper].value) {

					// Add result with first index always lower than the second index
					result.add(sortedPair(a[lower].index, a[upper].index));

					// Try to move to a similar value, to find another pair
					if (a[lower].value == a[lower+1].value) {
						lower++;
					} else {
						upper--;
					}

				} else {

					// If both values are the same, all the values in the middle
					// are the same so add all possible index combinations and exit loop

					for (int i = lower; i < upper ; i++) {
						for (int j = i+1; j <= upper; j++) {
							result.add(pair(a[i].index, a[j].index));
						}
					}
					break;

				}
			}
		}

		return result;
	}


	/** Returns an array with indexes */
	private static IndexedInt[] indexed(int[] a) {

		// Create array of (value, index) from given array `a`
		// so we can sort by value and preserve index.
		final IndexedInt[] result = new IndexedInt[a.length];
		for (int i = 0; i < a.length; i++) {
			result[i] = new IndexedInt(a[i], i);
		}

		return result;
	}

	/** Returns an {@link IntPair} with the lowest value on the left */
	static IntPair sortedPair(int a, int b) {
		return a <= b ? pair(a, b) : pair(b, a);
	}

	/** Convenience method for creating an {@link IntPair} */
	static IntPair pair(int a, int b) {
		return new IntPair(a, b);
	}

	/** A pair of integer values, with no special meaning */
	static class IntPair implements Comparable<IntPair> {

		final int a;
		final int b;

		IntPair(int a, int b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			IntPair intPair = (IntPair) o;
			return a == intPair.a && b == intPair.b;
		}

		@Override
		public int hashCode() {
			return Objects.hash(a, b);
		}

		@Override
		public String toString() {
			return "(" + a + "," + b + ")";
		}

		@Override
		public int compareTo(IntPair other) {
			if (this.a != other.a) {
				return this.a - other.a;
			} else {
				return this.b - other.b;
			}
		}
	}

	/** A pair of value and index */
	static class IndexedInt {

		final int value;
		final int index;

		IndexedInt(int value, int index) {
			this.value = value;
			this.index = index;
		}
	}


	// --- tests ---

	private static void runTests() {

		// Simple array
		assertEqualElements( findKComplementaryPairs(6, new int[]{1, 2, 3, 4, 5}),
			Arrays.asList(
				pair(0,4), pair(1,3)));

		// No pairs, array is empty
		assertEqualElements( findKComplementaryPairs(3, new int[]{}),
			Collections.emptyList());

		// No pairs, K is too high
		assertEqualElements( findKComplementaryPairs(6, new int[]{1, 2, 3}),
			Collections.emptyList());

		// No pairs, K is too low
		assertEqualElements( findKComplementaryPairs(2, new int[]{1, 2, 3}),
			Collections.emptyList());

		// All combinations of repeated values
		assertEqualElements( findKComplementaryPairs(2, new int[]{1, 1, 1, 1}),
			Arrays.asList(
				pair(0,1), pair(0,2), pair(0,3), pair(1,2), pair(1,3), pair(2,3)));

		// Test more complex and unsorted array (it is sorted by the method anyway)
		assertEqualElements( findKComplementaryPairs(10, new int[]{8, 6, 2, 5, 10, 1, 4, 5, 8, 5}),
			Arrays.asList(
				pair(0,2), pair(1,6), pair(2,8), pair(3,7), pair(3,9), pair(7,9)));

		System.out.println("All tests OK!");
	}

	/**
	 * Checks that the actual list contains the same elements as the one we expect.
	 * Lists elements are compared after sorting so actual order is not important.
	 * In case lists are different (after being sorted), throws an exception.
	 */
	public static <T extends Comparable<T>> void assertEqualElements(List<T> actual, List<T> expected) {

		final ArrayList<T> actualSorted = new ArrayList<>(actual);
		Collections.sort(actualSorted);

		final ArrayList<T> expectedSorted = new ArrayList<>(expected);
		Collections.sort(expectedSorted);

		if (!actualSorted.equals(expectedSorted)) {
			throw new RuntimeException("Actual list (sorted) " + actualSorted + " is not equal to expected list (sorted) " + expectedSorted);
		}
	}
}
