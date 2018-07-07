import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.StringTokenizer;
import java.util.Vector;

public class Classification extends IOException {

	private static BufferedReader eeg;
	private static BufferedWriter data;
	private static String line, token;
	private static int i, j, count;
	private static StringTokenizer tk;
	private static Vector<Integer> result = new Vector<Integer>();

	public static void main(String[] args) throws IOException {

		i = 1;
		j = 0;
		count = 0;

		eeg = new BufferedReader(new FileReader("/Users/kimyosub/Desktop/test.txt"));
		token = null;
		while ((line = eeg.readLine()) != null) {
			tk = new StringTokenizer(line, " ");
			while (tk.hasMoreTokens()) {
				token = tk.nextToken();
				result.add(Integer.parseInt(token));
				token = null;
			}
		}

		for(int i = 0; i < result.size(); i++)
		{
			System.out.println(result.get(i));
		}
	}
}