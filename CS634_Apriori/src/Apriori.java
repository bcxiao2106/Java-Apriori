import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;

public class Apriori {
	private Hashtable<String, Integer> itemSetTable;			//store itemsets/Frequent itemsets
	private ArrayList<String> showAllResult;
	private ArrayList<String> sortKeys;
	
	private int minSupport;			//minimum support rate %
	private int minConfidence;		//minimum confidence rate %
	private int totalTansaction;	//total transactions
	private String SEPARATOR = ",";
	
	public Apriori(ArrayList<String> transactionStrArray, int minSupport, int minConfidence){

		this.itemSetTable = new Hashtable<String, Integer>();
		this.showAllResult = new ArrayList<String>();
		this.minSupport = minSupport;
		this.minConfidence = minConfidence;
		this.totalTansaction = transactionStrArray.size();
		sortKeys = new ArrayList<String>();
		
		//initialization
		for(int i = 0; i < transactionStrArray.size(); i++){
			String [] items = transactionStrArray.get(i).split(this.SEPARATOR);
			Arrays.sort(items);//Sort the array
			for(int j = 0; j < items.length; j++){
				this.putItems(items[j]);//put single item
			}
			CombinationUtil cbn = new CombinationUtil(SEPARATOR);

			ArrayList<String> combinationList = (ArrayList<String>)cbn.getCombination(items, 2);
			this.putItems(combinationList);//put multi-item
		}

		//update itemsets table, remove unqualified itemsets
		this.reduceItemTable(this.itemSetTable, this.minSupport);
	}
	
	//put frequent itemset(single item)
	public void putItems(String item){
		if(this.itemSetTable.containsKey(item)){
			int value = this.itemSetTable.get(item);
			this.itemSetTable.put(item, ++value);
		} else {
			this.itemSetTable.put(item, 1);
			this.sortKeys.add(item);
		}
	}
	
	//put frequent itemset(multi-items)
	public void putItems(ArrayList<String> itemList){
		for(String item : itemList){
			if(this.itemSetTable.containsKey(item)){
				int value = this.itemSetTable.get(item);
				this.itemSetTable.put(item, ++value);
			} else {
				this.itemSetTable.put(item, 1);
				sortKeys.add(item);
			}
		}
	}
	
	//Update itemTable based on threshold
	public void reduceItemTable(Hashtable<String, Integer> itemTable, int threshold){
		Enumeration<String> e = itemTable.keys();
		while(e.hasMoreElements()){
			String item = e.nextElement();
			int value = itemTable.get(item);
			int size = item.split(this.SEPARATOR).length;
			if((value * 100.0 / this.totalTansaction) < threshold){
				itemTable.remove(item);
				sortKeys.remove(item);
				showAllResult.add(item + ":" + value + "@" +size + " X");
			} else {
				showAllResult.add(item + ":" + value + "@" +size);
			}
		}
	}
	
	//Generate Association Rules
	public void genAssociation(){
		for(int k = 0; k < this.sortKeys.size(); k++){
			String item = sortKeys.get(k);
			String[] items = item.split(this.SEPARATOR);
			if(items.length > 1){
				int fullItemSetSupport = this.itemSetTable.get(item);
				for(int i = items.length - 1; i >= 0; i--){
					String impliedItem = items[i];
					String implyingItem = "";
					int n = 1;
					for(int j = 0; j < items.length; j++){
						if(j != i){
							implyingItem += items[j];
							if(n < items.length - 1) implyingItem += this.SEPARATOR;
							n++;
						}
					}
					if(this.itemSetTable.containsKey(implyingItem)){
						int implyingItemSetSupport = this.itemSetTable.get(implyingItem);
						double confidence = Double.parseDouble(String.format("%.2f", (fullItemSetSupport * 1.0 / implyingItemSetSupport * 100)));
						double supportRate = Double.parseDouble(String.format("%.2f", (fullItemSetSupport * 1.0 / this.totalTansaction * 100)));
						if(confidence >= this.minConfidence)
						System.out.println(new Association(implyingItem, impliedItem, supportRate, confidence).toString());
					}
				}
			}
		}
	}
	
	public void printFrequentSet(){
		for(int i = 0; i < sortKeys.size(); i++){
			String item = sortKeys.get(i);
			System.out.println("\t" + item + " : " + this.itemSetTable.get(item));
		}
	}
	
	public void printProcess(){
		Collections.sort(showAllResult);
		for(String item : showAllResult){
			System.out.println(item);
		}
	}
	
	public int getTotalTansaction() {
		return totalTansaction;
	}

	public void setTotalTansaction(int totalTansaction) {
		this.totalTansaction = totalTansaction;
	}

	public Hashtable<String, Integer> getItemSetTable() {
		return itemSetTable;
	}

	public void setItemSetTable(Hashtable<String, Integer> itemSetTable) {
		this.itemSetTable = itemSetTable;
	}
}
