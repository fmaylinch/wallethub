public class Palindrome {

	public static void main(String[] args) {

		runTests();
	}

	/**
	 * Returns true if the string is a palindrome, or false otherwise.
	 *
	 * Time efficiency: O(n)
	 */
	public static boolean isPalindrome(String str) {
		for (int i = 0; i < str.length() / 2; i++) {
			if (str.charAt(i) != str.charAt(str.length() - i - 1)) return false;
		}
		return true;
	}


	// --- tests ---

	private static void runTests() {

		assertEquals( isPalindrome(""), true );
		assertEquals( isPalindrome("a"), true );
		assertEquals( isPalindrome("aaaaaa"), true );
		assertEquals( isPalindrome("aba"), true );
		assertEquals( isPalindrome("abba"), true );
		assertEquals( isPalindrome("abcdcba"), true );
		assertEquals( isPalindrome("baba"), false );
		assertEquals( isPalindrome("ba"), false );

		System.out.println("All tests OK!");
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
