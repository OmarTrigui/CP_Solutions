import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// https://www.hackerrank.com/challenges/2s-complement
// 2's complement
// Author : Omar Trigui

class CF {

	static InputReader in = new InputReader(System.in);
	static long[] values;

	public static void main(String[] args) {

		values = new long[32];
		values[0] = 1;
		values[1] = 4;

		for (int i = 2; i < 32; i++) {
			values[i] = (1l << i) + (values[i - 1] << 1);
		}

		int n = in.nextInt();

		for (int i = 0; i < n; i++) {
			long left = in.nextLong();
			long right = in.nextLong();

			if (left >= 1) {
				System.out.println(countOnes(right) - countOnes(left - 1));
			} else if (right <= -1) {
				long val = 32 * (right - left + 1);
				left = -left - 1;
				if (right == -1)
					right = 1;
				else
					right = -right - 1;
				System.out.println(val
						- (countOnes(left) - countOnes(right - 1)));

			} else {
				long negInit = 32 * -left;
				long rightVal = (countOnes(right));
				if (left == 0)
					System.out.println(rightVal);
				else {
					left = -left - 1;
					long res = negInit - countOnes(left);
					System.out.println(res + rightVal);
				}
			}
		}

	}

	static long countOnes(long l) {

		if (l == 0)
			return 0;

		long value = l;
		long count = 0;

		while (value != 0) {

			int bit = (int) (Math.log(value & -value) / Math.log(2));
			if (1l << bit == value) {
				if (bit == 0)
					count += 1;
				else
					count += (1 + values[bit - 1]);
				break;
			}
			if (bit == 0)
				count += Long.bitCount(value);
			else
				count += leftmost(bit, Long.bitCount(value) - 1);
			value &= ~(1l << bit);
		}
		return count;
	}

	static long leftmost(int index, int rest) {
		return rest * (1l << index) + values[index - 1] + 1;
	}
}

class InputReader {
	private BufferedReader reader;
	private StringTokenizer tokenizer;

	public InputReader(InputStream stream) {
		reader = new BufferedReader(new InputStreamReader(stream), 32768);
		tokenizer = null;
	}

	public String next() {
		while (tokenizer == null || !tokenizer.hasMoreTokens()) {
			try {
				tokenizer = new StringTokenizer(reader.readLine());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return tokenizer.nextToken();
	}

	public String readLine() {
		try {
			return reader.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}

	public long nextLong() {
		return Long.parseLong(next());
	}

}