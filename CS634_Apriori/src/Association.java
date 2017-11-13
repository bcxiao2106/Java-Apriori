public class Association {
	private String implyingItems;
	private String impliedItems;
	private double support;
	private double confidence;

	public Association(String implyingItems, String impliedItems, double support, double confidence){
		this.implyingItems = implyingItems;
		this.impliedItems = impliedItems;
		this.support = support;
		this.confidence = confidence;
	}

	public String getImplyingItems() {
		return implyingItems;
	}

	public void setImplyingItems(String implyingItems) {
		this.implyingItems = implyingItems;
	}

	public String getImpliedItems() {
		return impliedItems;
	}

	public void setImpliedItems(String impliedItems) {
		this.impliedItems = impliedItems;
	}

	public double getSupport() {
		return support;
	}

	public void setSupport(double support) {
		this.support = support;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	public String toString(){
		return "\t" + this.implyingItems  + " -> " + this.impliedItems + " : " + this.support + "%, " + this.confidence + "%";
	}
}
