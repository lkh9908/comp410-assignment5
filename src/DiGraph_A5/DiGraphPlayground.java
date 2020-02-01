package DiGraph_A5;


public class DiGraphPlayground {

  public static void main (String[] args) {
  
      // thorough testing is your responsibility
      //
      // you may wish to create methods like 
      //    -- print
      //    -- sort
      //    -- random fill
      //    -- etc.
      // in order to convince yourself your code is producing
      // the correct behavior
      //exTest();
      //exTest1();
      exTest2();
    }
  
    public static void exTest(){
      DiGraph d = new DiGraph();
      d.addNode(1, "f");
      d.addNode(3, "s");
      d.addNode(7, "t");
      d.addNode(0, "fo");
      d.addNode(4, "fi");
      d.addNode(6, "si");
      d.addEdge(0, "f", "s", 0, null);
      d.addEdge(1, "f", "si", 0, null);
      d.addEdge(2, "s", "t", 0, null);
      d.addEdge(3, "fo", "fi", 0, null);
      d.addEdge(4, "fi", "si", 0, null);
      System.out.println("numEdges: "+d.numEdges());
      System.out.println("numNodes: "+d.numNodes());
      d.print();
      
      d.delEdge("fi", "si");
      d.delNode("f");
      
      System.out.println("numEdges: "+d.numEdges());
      System.out.println("numNodes: "+d.numNodes());
      d.print();
    }
    
    public static void exTest1(){
    	DiGraph d = new DiGraph();
    	d.addNode(1,"f");
        d.addNode(3,"s");
        d.addNode(7,"t");
        d.addEdge(0, "f", "w", 0, null);
        System.out.println("numEdges: "+d.numEdges());
        System.out.println("numNodes: "+d.numNodes());
        d.print();
    }
    
    
    public static void exTest2(){
    	DiGraph d = new DiGraph();
    	d.addNode(1, "f");
        d.addNode(3, "s");
        d.addNode(7, "t");
        d.addNode(0, "fo");
        d.addNode(4, "fi");
        d.addNode(6, "si");
        d.addEdge(0, "f", "s", 1, null);
        d.addEdge(1, "f", "si", 2, null);
        d.addEdge(2, "s", "t", 3, null);
        d.addEdge(3, "fo", "fi", 4, null);
        d.addEdge(4, "fi", "si", 5, null);
        System.out.println("numEdges: "+d.numEdges());
        System.out.println("numNodes: "+d.numNodes());
    
        d.print();
        
        ShortestPathInfo[] result = d.shortestPath("f");
        if (result == null) System.out.println("cao");
        else {
        	for (ShortestPathInfo i: result) {
            	System.out.println(i.toString());
            }
        }
        
        
    }
}