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
	private static Vector<String> result = new Vector<String>();

	public static void main(String[] args) throws IOException {
		
		i = 1;
		j = 0;
		count = 0;
		
		eeg = new BufferedReader(new FileReader("/Users/kimyosub/Desktop/test.txt"));
		token = null;
		while ((line = eeg.readLine()) != null) {
			tk = new StringTokenizer(line, "E");
			while (tk.hasMoreTokens()) {
				token = tk.nextToken();
				result.add(token);
				token=null;
			}
			tk=null;
			
			tk = new StringTokenizer(result.get(1), "+");
			while(tk.hasMoreTokens()) {
				token=tk.nextToken();
				result.add(token);
				token=null;
			}
		}

		System.out.println(Math.floor(Float.parseFloat((result.get(0))) * Math.pow (10, Integer.parseInt(result.get(2)))));
		
		// 뇌파 오차 범위 
		/*try {
			while (i <= 10) {
				data = new BufferedWriter(
						new FileWriter("/Users/kimyosub/Desktop/" + i + "_dleta.txt", true));
				for (int k = j; k < 1281 * i; k++) {
					if (0 <= Float.parseFloat(result.get(k) 
							&& Float.parseFloat(result.get(k)) <= 100) {
						data.write(result.get(k));
						data.newLine();
						count++;
					}
				}
				System.out.println(i + "번째 데이터 개수 : " + count);
				count = 0;
				j = j + 1281;
				i++;
				data.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}
}