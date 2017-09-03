import java.util.*;

public class ComplementaryPairs {

	public static void main(String[] args) {

		// Simple array
		assertEquals( findKComplementaryPairs(6, new int[]{1, 2, 3, 4, 5}),
			Arrays.asList(
				pair(0,4), pair(1,3)));

		// No pairs, array is empty
		assertEquals( findKComplementaryPairs(3, new int[]{}),
			Collections.emptyList());

		// No pairs, K is too high
		assertEquals( findKComplementaryPairs(6, new int[]{1, 2, 3}),
			Collections.emptyList());

		// No pairs, K is too low
		assertEquals( findKComplementaryPairs(2, new int[]{1, 2, 3}),
			Collections.emptyList());

		// All combinations of repeated values
		assertEquals( findKComplementaryPairs(2, new int[]{1, 1, 1, 1}),
			Arrays.asList(
				pair(0,1), pair(0,2), pair(0,3), pair(1,2), pair(1,3), pair(2,3)));

		// Test more complex and unsorted array (it is sorted by the method anyway)
		assertEquals( findKComplementaryPairs(10, new int[]{8, 6, 2, 5, 10, 1, 4, 5, 8, 5}),
			Arrays.asList(
				pair(0,2), pair(1,6), pair(2,8), pair(3,7), pair(3,9), pair(7,9)));

		System.out.println("All tests OK!");
	}

	/**
	 * Returns K-complementary pairs in a given array of integers.
	 * Given Array A, pair (i, j) is K-complementary if K = A[i] + A[j].
	 *
	 * The returned pairs are sorted, first by the left index and then by the second.
	 * In each pair, the first index is always lower than the second index.
	 *
	 * Time efficiency: O(n * log n)
	 * Space efficiency: O(n)
	 */
	public static List<IntPair> findKComplementaryPairs(int k, int[] a) {

		final IndexedInt[] a2 = indexed(a);

		// Sort by value
		Arrays.sort(a2, Comparator.comparingInt(p -> p.value));

		// Start looking for pairs from the extremes
		int left = 0;              // this index starts at min value
		int right = a2.length - 1; // this index starts at max value

		final List<IntPair> result = new ArrayList<>();

		while (left < right) {

			final int sum = a2[left].value + a2[right].value;

			if (sum > k) {
				right--; // sum is too high, move to a lower value from the right

			} else if (sum < k){
				left++; // sum is too low, move to a higher value from the left

			} else {  // (sum == k)

				if (a2[left].value != a2[right].value) {

					// Add result with first index always lower that the second index
					result.add(sortedPair(a2[left].index, a2[right].index));

					// Try to move to a similar value, to find another pair
					if (a2[left].value == a2[left+1].value) {
						left++;
					} else {
						right--;
					}

				} else {

					// If both values are the same, all the values in the middle
					// are the same so add all possible index combinations and exit loop

					for (int i = left; i < right ; i++) {
						for (int j = i+1; j <= right; j++) {
							result.add(pair(a2[i].index, a2[j].index));
						}
					}
					break;

				}
			}
		}

		Collections.sort(result);

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


	/**
	 * Checks that the actual value we have is equal to the one we expect.
	 * In case they are different, throws an exception.
	 */
	public static void assertEquals(Object actual, Object expected) {

		if (!actual.equals(expected)) {
			throw new RuntimeException("Actual value " + actual + " is not equal to expected value " + expected);
		}
	}
}
