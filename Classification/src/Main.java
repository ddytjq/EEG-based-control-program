import java.util.Random;

public class Main {
	
	private static double[][] dataset = new double[5][5];
	private static Random random = new Random();

	public static void main(String[] args) {
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				dataset[i][j] = random.nextInt(100);
			}
		}
		
		Normalization no = new Normalization(dataset, 0, 1);
		
	}

}
