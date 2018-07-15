import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class New {
	private static BufferedReader eeg, eeg2;
	private static String line, token;
	private static StringTokenizer tk;
	private static Vector<Double> result = new Vector<Double>();
	private static Vector<Double> result2 = new Vector<Double>();
	private static Vector<Double> result3 = new Vector<Double>();
	
	public static void main(String[] args) throws IOException {
		
		eeg = new BufferedReader(
					new FileReader("/Users/kimyosub/Desktop/alpha.txt"));
			token = null;
			while ((line = eeg.readLine()) != null) {
				tk = new StringTokenizer(line, " ");
				while (tk.hasMoreTokens()) {
					token = tk.nextToken();
					result.add(Double.parseDouble((token)));
					token = null;
				}
			}
			
			eeg2 = new BufferedReader(
					new FileReader("/Users/kimyosub/Desktop/low_beta.txt"));
			token = null;
			while ((line = eeg2.readLine()) != null) {
				tk = new StringTokenizer(line, " ");
				while (tk.hasMoreTokens()) {
					token = tk.nextToken();
					result2.add(Double.parseDouble((token)));
					token = null;
				}
			}
			
			for(int i = 0; i < result2.size(); i++) {
				result3.add((result2.elementAt(i)+result.elementAt(i)) / 2);
				System.out.println(result3.elementAt(i));
			}
			
			FileOutputStream data = new FileOutputStream(
					"/Users/kimyosub/Desktop/smr.txt", false);
			DataOutputStream rand = new DataOutputStream(data);
			String r;
			for (int i = 0; i < result3.size(); i++) {
				r = String.valueOf(result3.elementAt(i));
				rand.writeBytes(r + "\n");
				System.out.println(r);
				r = null;
			}
			
			data.close();
		}
	}