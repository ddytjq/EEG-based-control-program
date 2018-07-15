import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class Main {

	// Nomalization instance value
	private static Nomalization no = new Nomalization();
	private static BufferedReader eeg;
	private static String line, token;
	private static StringTokenizer tk;
	private static Vector<Double> result = new Vector<Double>();
	private static String[] name = { "delta", "theta", "alpha", "smr", "low_beta", "high_beta", "gamma" };

	public static void main(String[] args) throws IOException {
		for (int j = 0; j < name.length; j++) {
			eeg = new BufferedReader(
					new FileReader("/Users/kimyosub/Desktop/"+ name[j] + ".txt"));
			token = null;
			while ((line = eeg.readLine()) != null) {
				tk = new StringTokenizer(line, " ");
				while (tk.hasMoreTokens()) {
					token = tk.nextToken();
					result.add(Double.parseDouble((token)));
					token = null;
				}
			}

			Scanner in = new Scanner(System.in);

			Double[] age = new Double[result.size()];

			System.out.print("Number of "+name[j]+" value : ");
			for (int i = 0; i < result.size(); i++) {
				age[i] = result.elementAt(i);
			}
			System.out.println(age.length);

			double min = no.calc_min(age);
			double max = no.calc_max(age);

			System.out.println("1: 0~30 value");
			System.out.println("2: 0~1 value");
			System.out.println("3: Exit");
			System.out.println("Enter your choice: ");
			
			int choice = in.nextInt();

			switch (choice) {
			case 1:
				no.min_max(age, min, max, name[j]);
				result.clear();
				break;
			case 2:
				no.decimal_scalling(age, min, max);
				result.clear();
				break;
			case 3:
				exit(0);
			}
		}
	}
}