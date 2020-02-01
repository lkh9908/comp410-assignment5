package DiGraph_A5;
import java.util.*;

public class DiGraph implements DiGraphInterface {

	// in here go all your data and methods for the graph
	private Set<Long> edgeID;
	private Set<Long> vertexID;
	private Map<String, Vertex> mapVertex;
	private List<String> listVertex;
	private Map<String, Edge> mapEdge;
	// map of destination vertices and their source vertices
	// use to delete edges
	private Map<String, Set<String>> mDestinations;
	private long numNode;
	private long numEdge;

	public DiGraph ( ) { // default constructor
		// explicitly include this
		// we need to have the default constructor
		// if you then write others, this one will still be there
		edgeID = new HashSet<Long>();
		vertexID = new HashSet<Long>();
		mapVertex = new HashMap<String, Vertex>();
		listVertex = new LinkedList<String>();
		mapEdge = new HashMap<String, Edge>();
		mDestinations = new HashMap<String, Set<String>>();
		numNode = 0;
		numEdge = 0;
	}

	@Override
	public boolean addNode(long idNum, String label) {
		//	 addNode
		//     in: unique id number of the node (0 or greater)
		//         string for name
		//           you might want to generate the unique number automatically
		//           but this operation allows you to specify any integer
		//           both id number and label must be unique
		//		return: boolean
		//                returns false if node number is not unique, or less than 0
		//                returns false if label is not unique (or is null)
		//                returns true if node is successfully added 
		if (label == null || idNum < 0 || vertexID.contains(idNum) || mapVertex.containsKey(label)) {
			return false;
		} else {
			Vertex newV = new Vertex(idNum, label);
			mapVertex.put(label, newV);
			vertexID.add(idNum);
			numNode++;

			listVertex.add(label);
			return true;
		}
	}

	@Override
	public boolean addEdge(long idNum, String sLabel, String dLabel, long weight, String eLabel) {
		//		addEdge
		//	      in: unique id number for the new edge, 
		//	          label of source node,
		//	          label of destination node,
		//	          weight for new edge (use 1 by default)
		//	          label for the new edge (allow null)
		//	      return: boolean
		//	                returns false if edge number is not unique or less than 0
		//	                returns false if source node is not in graph
		//	                returns false if destination node is not in graph
		//	                returns false is there already is an edge between these 2 nodes
		//	                returns true if edge is successfully added 
		if (idNum < 0 || sLabel == null || dLabel == null || edgeID.contains(idNum)
				|| !(mapVertex.containsKey(sLabel) && mapVertex.containsKey(dLabel))
				|| mapVertex.get(sLabel).containOutEdge(dLabel)){
			return false;
		} else {
			Edge newE = new Edge(idNum, sLabel, dLabel, weight, eLabel);
			mapEdge.put(eLabel, newE);
			edgeID.add(idNum);
			mapVertex.get(sLabel).addOutEdge(dLabel, newE);
			numEdge++;

			if(mDestinations.containsKey(dLabel)) {
				mDestinations.get(dLabel).add(sLabel);
			} else {
				mDestinations.put(dLabel,new HashSet<String>());
				mDestinations.get(dLabel).add(sLabel);
			}
			return true;
		}
	}

	@Override
	public boolean delNode(String label) {
		//		delNode
		//	      in: string 
		//	            label for the node to remove
		//	      out: boolean
		//	             return false if the node does not exist
		//	             return true if the node is found and successfully removed
		if (!mapVertex.containsKey(label)) {
			return false;
		} else {
			// delete all edges going from LABEL
			for (Edge ing: mapVertex.get(label).getAllOutEdges()) {
				edgeID.remove(ing.getID());
			}
			// delete all edges going to LABEL
			if (mDestinations.containsKey(label)) {
				for(String ing: mDestinations.get(label)) {
					delEdge(ing, label);
				}
				mDestinations.get(label).clear();
			}
			vertexID.remove(mapVertex.get(label).getID());
			mapVertex.remove(label);
			numNode--;

			// listVertex.remove(label); time consuming
			return true;
		}
	}

	@Override
	public boolean delEdge(String sLabel, String dLabel) {
		//		delEdge
		//	      in: string label for source node
		//	          string label for destination node
		//	      out: boolean
		//	             return false if the edge does not exist
		//	             return true if the edge is found and successfully removed
		if(!(mapVertex.containsKey(sLabel) && mapVertex.containsKey(dLabel)) ||
				!mapVertex.get(sLabel).containOutEdge(dLabel)) {
			return false;
		} else {
			edgeID.remove(mapVertex.get(sLabel).getOutEdge(dLabel).getID());
			mapVertex.get(sLabel).removeOutEdge(dLabel);
			mDestinations.remove(dLabel, sLabel);
			numEdge--;
			return true;
		}
	}

	@Override
	public long numNodes() {
		//		numNodes
		//	      in: nothing
		//	      return: integer 0 or greater
		//	                reports how many nodes are in the graph
		return numNode;
	}

	@Override
	public long numEdges() {
		//	    numEdges
		//	      in: nothing
		//	      return: integer 0 or greater
		//	                reports how many edges are in the graph
		return numEdge;
	}

	@Override
	public ShortestPathInfo[] shortestPath(String label) {
		//	    shortestPath:
		//	      in: string label for start vertex
		//	      return: array of ShortestPathInfo objects (ShortestPathInfo)
		//	              length of this array should be numNodes (as you will put in all shortest 
		//	              paths including from source to itself)
		//	              See ShortestPathInfo class for what each field of this object should contain

		if (!mapVertex.containsKey(label)) return null;

		int numNodeInt = (int) numNode;
		ShortestPathInfo[] sp = new ShortestPathInfo[numNodeInt];
		MinBinHeap pq = new MinBinHeap();

		for(Vertex ing: mapVertex.values()) {
			ing.setKnown(false);
			ing.setDv(-1);
		}

		// starting point
		mapVertex.get(label).setDv(0);
		mapVertex.get(label).setPv(null);
		pq.insert(new EntryPair(label,0));
		int i = 0;

		// loop all points
		while(pq.size() > 0) {
			String ing = pq.getMin().getValue();
			Vertex vIng = mapVertex.get(ing);
			pq.delMin();

			if (vIng.getKnown()) {
				continue;
			} else {
				vIng.setKnown(true);
				sp[i] = new ShortestPathInfo(ing, vIng.getDv());
				i++;

				for(Edge e: vIng.getAllOutEdges()) {
					long newWeight = e.getWeight() + vIng.getDv();
					Vertex destination = mapVertex.get(e.getDLabel());

					if(destination.getDv() < 0 || newWeight < destination.getDv()) {
						destination.setPv(vIng);
						destination.setDv(newWeight);
						pq.insert(new EntryPair(destination.getLabel(), (int) newWeight));
					}
				}
			}
		}

		// untouched == -1
		for (Vertex ing: mapVertex.values()) {
			if (!ing.getKnown()) {
				sp[i] = new ShortestPathInfo(ing.getLabel(), (long)-1);
				i++;
			}
		}
		return sp;
	}

	// optional classes
	public void print(){

		for(int i = 0; i < listVertex.size(); i++){
			// print all nodes in proper order
			String ingV = listVertex.get(i);
			if (mapVertex.containsKey(ingV)) {
				System.out.println("("+ mapVertex.get(ingV).getID() + ")" + mapVertex.get(ingV).getLabel());

				// print all outedges
				for(Edge ingE: mapVertex.get(ingV).getAllOutEdges()){
					System.out.println("	(" + ingE.getID() + ")--" + 
							((ingE.getELabel() == null) ? ingE.getWeight(): ingE.getELabel() + 
									"," + ingE.getWeight()) + "--> " + ingE.getDLabel());
				}
			}

		}

	}

}