import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class DM {
	
	public static void main(String[] args) throws IOException{
		String filePath = null;
		int minSupport = 0;
		int minConfidence = 0;
		
		while(true){
			System.out.println("Please input file path(.csv):");
			Scanner scnr = new Scanner(System.in);
			filePath = scnr.nextLine();
			if(!filePath.substring(filePath.length()-4).equalsIgnoreCase(".csv")) continue;
			System.out.println("Please input Minimum Support(1-99):");
			minSupport = scnr.nextInt();
			if(minSupport < 1 || minSupport > 99) continue;
			System.out.println("Please input Minimum Confidence(1-99):");
			minConfidence = scnr.nextInt();
			if(minConfidence < 1 || minConfidence > 99) continue;
			break;
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		ArrayList<String> transactionList = new ArrayList<String>();
		String line = null;
		while((line = reader.readLine()) != null){
			transactionList.add(line);
		}
		
		Apriori apriori = new Apriori(transactionList, minSupport, minConfidence);
		System.out.println("\n======== Frequent ItemSets ========");
		apriori.printFrequentSet();
		System.out.println("\n======== Generated Association Rules ========");
		apriori.genAssociation();
	}
}
