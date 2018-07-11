import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Random {
	public static void main(String[] args) throws IOException {

		FileOutputStream data = new FileOutputStream(
				"/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/rand/result.txt", false);
		DataOutputStream rand = new DataOutputStream(data);
		String result;

		for (int i = 0; i < 15000; i++) {
			result = ((String.valueOf((float) (Math.random() * (55 - 35 + 1) + 35))));
			rand.writeBytes(result + "\n");
			System.out.println(result);
			result = null;
		}
		data.close();


		String[] name = { "1", "2", "3", "4", "5", "6" };
		for (int j = 0; j < name.length; j++) {
			FileOutputStream data2 = new FileOutputStream(
					"/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/rand/"+name[j]+".txt", false);
			DataOutputStream rand2 = new DataOutputStream(data2);
			String result2;
			for (int i = 0; i < 15000; i++) {
				result2 = ((String.valueOf((float) (Math.random() * (40 - 15 + 1) + 15))));
				rand2.writeBytes(result2 + "\n");
				System.out.println(result2);
				result2 = null;
			}
			data2=null;
			rand2=null;
			data.close();
		}
	}
}
