import static java.lang.System.exit;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;

public class Main{
	
	// Nomalization instance value
	private static Nomalization no = new Nomalization();
	private static BufferedReader eeg;
	private static String line, token;
	private static StringTokenizer tk;
	private static Vector<Integer> result = new Vector<Integer>();

	public static void main(String[] args)  throws IOException
    {
		eeg = new BufferedReader(new FileReader("/Users/kimyosub/eclipse-workspace/EEG-based-control-program/Classification/data/theta.txt"));
		token = null;
		while ((line = eeg.readLine()) != null) {
			tk = new StringTokenizer(line, " ");
			while (tk.hasMoreTokens()) {
				token = tk.nextToken();
				result.add(Integer.parseInt(token));
				token = null;
			}
		}
		
		Scanner in = new Scanner(System.in);

        System.out.print("Enter the range for age: ");
        int range = result.size();
        System.out.println(range);
        int[] age = new int[range];
                
        
        System.out.print("Enter the values for age: ");
        for(int i=0;i<range;i++)
        {
            age[i]=result.get(i);
        }
        System.out.println(age.length);
        
        double min = no.calc_min(age);
        double max = no.calc_max(age);
        
        for(;;)
        {
            System.out.println("\nNormalization");
            System.out.println("1: Min_Max");
            System.out.println("2: Decimal Scaling");
            System.out.println("3: Exit");
            System.out.println("Enter your choice: ");
            int choice = in.nextInt();
        
            switch(choice)
            {
                case 1: no.min_max(age,min,max);
                    break;
                case 2: no.decimal_scalling(age,min,max);
                    break;
                case 3: exit(0);
            }
        }
    }
}