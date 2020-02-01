package DiGraph_A5;

public class Edge {
	long idNum;
	String sLabel;
	String dLabel;
	long weight;
	String eLabel;
	
	Edge(long idNum, String sLabel, String dLabel, long weight, String eLabel){
		this.idNum = idNum;
		this.sLabel = sLabel;
		this.dLabel = dLabel;
		this.weight = weight;
		this.eLabel = eLabel;
	}
	
	public long getID() {return idNum;}
	public String getSLabel() {return sLabel;}
	public String getDLabel() {return dLabel;}
	public long getWeight() {return weight;}
	public String getELabel() {return eLabel;}
	
	public void setID(long i) {idNum = i;}
	public void setSLabel(String s) {sLabel = s;}
	public void setDLabel(String d) {dLabel = d;}
	public void setWeight(long w) {weight = w;}
	public void setELabel(String e) {eLabel = e;}
}
