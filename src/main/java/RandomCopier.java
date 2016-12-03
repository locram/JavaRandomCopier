import java.util.stream.IntStream;
import java.util.Random;

// Use this class to copy a given Random instance out of its output
public class RandomCopier {
	private static final long MULTIPLIER = 0x5DEECE66DL;
	private static final long ADDER = 0xBL;
	private static final int SIZE_HIDDEN = 16;
	private static final int SIZE_TOTAL = 48;
	private static final int MAX_HIDDEN = (1<<16) - 1;
	private static final String NO_RESULTS_FOUND = "No possible result found.";
	private static final String NO_UNIQUE_RESULT_FOUND = "No unique result found.";


	// Private constructor, this class is a Random factory
	private RandomCopier() {}


	// Takes two .nextInt() outputs in a row of a Random and returns a copy
	// of that Random instance
	public static Random copy(int firstOutput, int secondOutput) {

		// Bruteforce through the possible hidden state parts
		int[] possibleHidden = IntStream.range(0, MAX_HIDDEN)
			.filter(q -> nextInt(firstOutput, q) == secondOutput)
			.toArray();


		// Throw error if no or too many results
		int len = possibleHidden.length;
		if (len == 0) {
			throw new RuntimeException(NO_RESULTS_FOUND);
		} else if (len > 1) {
			throw new RuntimeException(NO_UNIQUE_RESULT_FOUND);
		}


		// Calculate the seed needed to obtain the current status
		int hidden = possibleHidden[0];
		long lastState = (((long) firstOutput) << SIZE_HIDDEN) + hidden;
		long currentState = nextState(lastState);
		long seed = currentState ^ MULTIPLIER;


		// Create a new Random and seed with the prepared seed
		Random retRandom = new Random(seed);
		return retRandom;
	}


	// Returns the next integer taking the last visible state and hidden state
	private static int nextInt(int visible, int hidden) {
		long state = ((long)visible << SIZE_HIDDEN) + hidden;
		long nextState = nextState(state);
		return (int) (nextState >>> SIZE_HIDDEN);
	}

	// Calculates the next state out of the previous state
	private static long nextState(long state) {
		long nextState = (state * MULTIPLIER + ADDER) & ((1L<<SIZE_TOTAL) - 1);
		return nextState;
	}
}
