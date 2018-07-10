import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Random {
	public static void main(String[] args) throws IOException {

		FileOutputStream data = new FileOutputStream(
				"/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/rand/rand.txt",false);
		DataOutputStream rand = new DataOutputStream(data);
		float result;
		
		for (int i = 0; i < 15000; i++) {
			result = (float) (Math.random()*(60-45+1)+45);
			rand.writeFloat(result);
			System.out.println(result);
			result=0;	
		}
		data.close();
	}
}