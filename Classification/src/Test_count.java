import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

public class Test_count {
	public static void main(String[] args) throws IOException {

		int c1 = 0, c2 = 0, c3 = 0, c4 = 0, c5 = 0, c6=0;
		StringTokenizer tk;
		String line, token;
		Vector<Double> result = new Vector<Double>();

		BufferedReader eeg = new BufferedReader(new FileReader(
				"/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/data/output.txt"));
		token = null;
		while ((line = eeg.readLine()) != null) {
			tk = new StringTokenizer(line, " ");
			while (tk.hasMoreTokens()) {
				token = tk.nextToken();
				result.add(Double.valueOf(token));
				token = null;
			}
		}

		for(int i = 0; i < result.size(); i++) {
			if(0 <= result.get(i) && result.get(i) <= 14.999999)
				c1+=1;
			else if (15 <= result.get(i) && result.get(i) <= 29.999999)
				c2+=1;
			else if (30 <= result.get(i) && result.get(i) <= 44.999999)
				c3+=1;
			else if (45 <= result.get(i) && result.get(i) <= 59.999999)
				c4+=1;
			else if (60 <= result.get(i) && result.get(i) <= 74.999999)
				c5+=1;
			else
				c6+=1;
		}
			System.out.println("1번 개수 : "+ c1);
			System.out.println("2번 개수 : "+c2);
			System.out.println("3번 개수 : "+c3);
			System.out.println("4번 개수 : "+c4);
			System.out.println("5번 개수 : "+c5);
			System.out.println("6번 개수 : "+c6);

	}
}
