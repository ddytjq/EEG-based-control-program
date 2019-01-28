import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Nomalization {

	private static BufferedWriter data;

	// To calculate the minimum age from dataset
	public Double calc_min(Double age[]) {
		int i, j;
		Double min = 0.0;
		min = age[0];
		for (i = 1; i < age.length; i++) {
			if (age[i] < min) {
				min = age[i];
			}
		}
		return min;
	}

	// To calculate the maximum age from dataset
	public Double calc_max(Double age[]) {
		int i, j;
		Double max = 0.0;
		max = age[0];
		for (i = 0; i < age.length; i++) {
			if (age[i] > max) {
				max = age[i];
			}
		}
		return max;
	}

	// Function to perform min_max normalization
	public void min_max(Double age[], double min, double max, String a) throws IOException {
		double new_min = 20;
		double new_max = 45;

		System.out.println("\nmin= " + min + " max= " + max + "\n");

		double v;
		data = new BufferedWriter(new FileWriter
				("/Users/kimyosub/Desktop/json/med/n_"+a+".txt", false));
		for (int i = 0; i < age.length; i++) {
			v = (((age[i] - min) / (max - min)) * (new_max - new_min)) + new_min;
			System.out.println("value for " + age[i] + "=" + v);
			data.write(String.valueOf(v));
			data.newLine();
		}
		System.out.println(" ");
		data.close();
	}

	// Function to perform Decimal_Scalling
	public void decimal_scalling(Double age[], double min, double max) {
		int count;

		for (int i = 0; i < age.length; i++) {
			int length = (int) Math.log10(max) + 1;
			double v1 = age[i]/ Math.pow(10, length);
			System.out.println("Decimal Scalled value of " + age[i] + "=" + v1);
		}
	}
}