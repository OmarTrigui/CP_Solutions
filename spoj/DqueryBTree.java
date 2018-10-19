import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

// TLE 

public class DqueryBTree {

	static InputReader in = new InputReader(System.in);
	static OutputWriter out = new OutputWriter(System.out);
	static int exp = (int) (1e9);

	private static int query(int BIT[], int index) {
		int sum = 0;
		while (index > 0) {
			sum += BIT[index];
			index -= index & (-index);
		}
		return sum;
	}

	private static void update(int BIT[], int n, int index, int val) {
		while (index <= n) {
			BIT[index] += val;
			index += index & (-index);
		}
	}

	public static void main(String[] args) {

		int n = in.nextInt();
		int[] arr = new int[n];
		int[] BIT = new int[n + 1];

		for (int i = 0; i < n; i++) {
			arr[i] = in.nextInt();
		}

		int m = in.nextInt();
		int[] res = new int[m];

		HashMap<Integer, ArrayList<Node>> hm = new HashMap<>();

		for (int i = 0; i < m; i++) {
			int l = in.nextInt() - 1;
			int r = in.nextInt() - 1;
			if (hm.containsKey(l)) {
				hm.get(l).add(new Node(r, i));
			} else {
				ArrayList<Node> list = new ArrayList<>();
				list.add(new Node(r, i));
				hm.put(l, list);
			}
		}

		HashMap<Integer, Integer> last = new HashMap<>();

		for (int i = n - 1; i >= 0; i--) {

			if (last.containsKey(arr[i])) {
				update(BIT, n, last.get(arr[i]) + 1, -1);
			}

			update(BIT, n, i + 1, 1);
			last.put(arr[i], i);

			if (hm.containsKey(i)) {
				ArrayList<Node> list = hm.get(i);
				for (Node node : list) {
					res[node.idx] = query(BIT, node.r + 1) - query(BIT, i);
				}
			}
		}

		for (Integer v : res) {
			out.println(v);
		}

		out.close();

	}
}

class Node {

	int r;
	int idx;

	public Node(int r, int idx) {
		super();
		this.r = r;
		this.idx = idx;
	}

	@Override
	public String toString() {
		return "Node [r=" + r + ", idx=" + idx + "]";
	}

}

class OutputWriter {
	public PrintWriter writer;

	public OutputWriter(OutputStream outputStream) {
		writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
	}

	public OutputWriter(Writer writer) {
		this.writer = new PrintWriter(writer);
	}

	public void print(Object... objects) {
		for (int i = 0; i < objects.length; i++) {
			if (i != 0)
				writer.print(' ');
			writer.print(objects[i]);
		}
	}

	public void println(Object... objects) {
		print(objects);
		writer.println();
	}

	public void close() {
		writer.close();
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

	public char nextChar() {
		return next().charAt(0);
	}

	public int nextInt() {
		return Integer.parseInt(next());
	}

	public long nextLong() {
		return Long.parseLong(next());
	}

	public double nextDouble() {
		return Double.parseDouble(next());
	}

	public float nextFloat() {
		return Float.parseFloat(next());
	}

}