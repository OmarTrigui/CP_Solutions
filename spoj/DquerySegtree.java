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

public class DquerySegtree {

	static InputReader in = new InputReader(System.in);
	static OutputWriter out = new OutputWriter(System.out);
	static int exp = (int) (1e9);

	static int[] arr;
	static int[] tree;
	static int n;

	static void upd(int node, int l, int r, int pos, int val) {
		if (l == r) {
			tree[node] += val;
			return;
		}
		int mid = (l + r) >> 1;
		if (pos <= mid)
			upd((node << 1) + 1, l, mid, pos, val);
		else
			upd((node << 1) + 2, mid + 1, r, pos, val);
		tree[node] = tree[(node << 1) + 1] + tree[(node << 1) + 2];
	}

	static int qry(int node, int tl, int tr, int l, int r) {
		if (l > r)
			return 0;
		if (tl == l && tr == r)
			return tree[node];
		int mid = (tl + tr) >> 1;
		return qry((node << 1) + 1, tl, mid, l, Math.min(mid, r))
				+ qry((node << 1) + 2, mid + 1, tr, Math.max(mid + 1, l), r);
	}

	static int QRY(int l, int r) {
		return qry(0, 0, n - 1, l, r);
	}

	public static void main(String[] args) {

		n = in.nextInt();
		arr = new int[n];
		tree = new int[n * 4];

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
				upd(0, 0, n - 1, last.get(arr[i]), -1);
			}

			upd(0, 0, n - 1, i, 1);
			last.put(arr[i], i);

			if (hm.containsKey(i)) {
				ArrayList<Node> list = hm.get(i);
				for (Node node : list) {
					res[node.idx] = QRY(i, node.r);
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