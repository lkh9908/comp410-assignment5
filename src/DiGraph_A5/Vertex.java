package DiGraph_A5;
import java.util.*;
public class Vertex {
	private long idNum;
	private String label;
	
	// out edges
	private Map<String, Edge> outEdges = new HashMap<String, Edge>();
	
	// Dijkstra stuff
	private boolean known;
	private long dv;
	private Vertex pv;
	
	
	public Vertex(long idNum, String label){
		this.idNum = idNum;
		this.label = label;
	}
	
	public long getID() {return idNum;}
	public String getLabel() {return label;}
	
	public void setID(long i) {idNum = i;}
	public void setLabel(String l) {label = l;}
	
	// out edge classes
	public boolean containOutEdge(String dLabel)
	{
		return outEdges.containsKey(dLabel);
	}
	public Edge getOutEdge(String dLabel)
	{
		return outEdges.get(dLabel);
	}
	public void addOutEdge(String dLabel, Edge e){
		outEdges.put(dLabel, e);
	}
	public void removeOutEdge(String dLabel){
		outEdges.remove(dLabel);
	}
	public Collection<Edge> getAllOutEdges()
	{
		return outEdges.values();
	}
	
	// // Dijkstra stuff
	public boolean getKnown() {
		return known;
	}
	public long getDv() {
		return dv;
	}
	public Vertex getPv() {
		return pv;
	}
	
	public void setKnown(boolean k) {
		known = k;
	}
	public void setDv(long dv) {
		this.dv = dv;
	}
	public void setPv(Vertex pv) {
		this.pv = pv;
	}
	
	
}
