import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TopPhrases {

	public static void main(String[] args) throws FileNotFoundException {
		
		// one phrase
		assertEquals( topPhrases(1, fromStr("a")), Arrays.asList(
			new Phrase("a", 1)
		));

		// multiple phrases
		assertEquals( topPhrases(2, fromStr("a a a a b b c")), Arrays.asList(
			new Phrase("a", 4), new Phrase("b", 2)
		));

		// n is greater than number of different phrases
		assertEquals( topPhrases(5, fromStr("a a a b b c")), Arrays.asList(
			new Phrase("a", 3), new Phrase("b", 2), new Phrase("c", 1)
		));

		// unsorted words
		assertEquals( topPhrases(3, fromStr("c a b a a d a d a b b c e b c")), Arrays.asList(
			new Phrase("a", 5), new Phrase("b", 4), new Phrase("c", 3)
		));

		// Read from file, using " | " or "\n" as delimiter for phrases.
		// The result is not tested, just shown.
		final List<Phrase> phrases = topPhrases(10, fromFile("tests/TopPhrases.txt", " \\| |\n"));
		System.out.println("Top phases: " + phrases);

		System.out.println("All tests OK!");
	}

	/**
	 * Reads phrases from {@link PhraseReader} and returns
	 * the n most frequent ones, sorted by frequency (from most frequent).
	 *
	 * Time efficiency: O(n)
	 * Space efficiency: O(n)
	 */
	static List<Phrase> topPhrases(int n, PhraseReader reader) {

		final PhraseCounts counts = countPhrases(reader);

		final List<Phrase>[] phrasesByFreq = arrangePhrasesByFrequency(counts);

		final List<Phrase> result = new ArrayList<>();

		// Now we collect phrases starting from those with the highest frequency

		int freq = phrasesByFreq.length - 1;

		while (result.size() < n && freq >= 0) {

			final List<Phrase> phrases = phrasesByFreq[freq];

			if (phrases != null) {

				if (result.size() + phrases.size() <= n) {
					result.addAll(phrases);
				} else {
					final int numPhrasesLeft = n - result.size();
					result.addAll(phrases.subList(0, numPhrasesLeft));
				}
			}

			freq--;
		}

		return result;
	}


	/**
	 * Returns an array indexed by frequency from the {@link PhraseCounts}.
	 * In each position there's a list with the phrases that have that frequency.
	 * phrasesByFreq[k] is a list of phrases with frequency k (or null if
	 * there are no phrases with that frequency).
	 */
	private static List<Phrase>[] arrangePhrasesByFrequency(PhraseCounts counts) {

		@SuppressWarnings("unchecked")
		final List<Phrase>[] result = new List[counts.getMaxCount() + 1];

		for (Phrase phrase : counts.getPhrases()) {
			if (result[phrase.frequency] == null) {
				result[phrase.frequency] = new ArrayList<>();
			}
			result[phrase.frequency].add(phrase);
		}

		return result;
	}

	/**
	 * Reads phrases from the given {@link PhraseReader} and
	 * returns a {@link PhraseCounts} object with the frequency of each phrase.
	 */
	private static PhraseCounts countPhrases(PhraseReader reader) {

		final PhraseCounts result = new PhraseCounts();

		String phrase;
		while( (phrase = reader.nextPhrase()) != null ) {
			result.addPhrase(phrase);
		}

		return result;
	}

	/** Reads phases from a file, using the provided separator regex to split phrases */
	private static PhraseReader fromFile(String filePath, String separator) throws FileNotFoundException {

		final Scanner scanner = new Scanner(new File(filePath)).useDelimiter(separator);

		return new PhraseReader() {
			@Override
			public String nextPhrase() {
				if (scanner.hasNext()) {
					return scanner.next();
				} else {
					scanner.close();
					return null;
				}
			}
		};
	}

	/** For testing purposes, reads phases from spaceSeparatedWords */
	private static PhraseReader fromStr(String spaceSeparatedWords) {

		final String[] words = spaceSeparatedWords.split(" ");

		return new PhraseReader() {

			int i = 0;

			@Override
			public String nextPhrase() {
				return i < words.length ? words[i++] : null;
			}
		};
	}

	static class PhraseCounts {

		private Map<String, Phrase> phraseMap = new HashMap<>();

		private int maxCount = 0;

		public void addPhrase(String text) {

			if (!phraseMap.containsKey(text)) {
				phraseMap.put(text, new Phrase(text, 0));
			}

			Phrase phrase = phraseMap.get(text);

			phrase.frequency++;

			maxCount = Math.max(maxCount, phrase.frequency);
		}

		public Collection<Phrase> getPhrases() {
			return phraseMap.values();
		}

		public int getMaxCount() {
			return maxCount;
		}

		@Override
		public String toString() {
			return phraseMap.toString();
		}
	}

	interface PhraseReader {
		/** Returns next phrase or null when there are no more */
		String nextPhrase();
	}

	/** A phrase with its text and its frequency */
	static class Phrase {

		String text;
		int frequency;

		public Phrase(String text, int frequency) {
			this.text = text;
			this.frequency = frequency;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Phrase phrase = (Phrase) o;
			return frequency == phrase.frequency &&
				Objects.equals(text, phrase.text);
		}

		@Override
		public int hashCode() {
			return Objects.hash(text, frequency);
		}

		@Override
		public String toString() {
			return "(" + text + "," + frequency + ")";
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
