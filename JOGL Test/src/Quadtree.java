import java.util.ArrayList;

public class Quadtree{
	
	//the Root Node of this Quadtree
    Node Root;
	
	//depth of the tree and length of one side
	private int TreeDepth;
	private int TreeSize;
	
	//List of triangles in this quadtree
	ArrayList<Triangle> Triangles = new ArrayList<Triangle>();
	
	public Quadtree(int depth){
		//set depth and Size
		TreeDepth = depth;
		TreeSize = (int) Math.pow(2, depth);
		//initialize
		init();
	}
	
	
	
    
	//node class for the tree
    public class Node{
    	
    	public Node(Node P, Point ul, Point ur, Point ll, Point lr){
    		Parent = P;
    		UpperLeft = ul;
    		UpperRight = ur;
    		LowerLeft = ll;
    		LowerRight = lr;
    	}
    	
    	//the pointers to the Parent
    	public Node Parent;
    	
    	//the pointers to children
    	public Node NorthWest;
    	public Node NorthEast;
    	public Node SouthWest;
    	public Node SouthEast;
    	
    	//the Points for this Node
    	public Point UpperLeft;
    	public Point UpperRight;
    	public Point LowerLeft;
    	public Point LowerRight;
    	
    	//the point for a SINGLE Datapoint (if any)
    	public Point Data;
    	
    }
    
    //initializing the tree
    public void init(){
    	
    	//setup root
    	Root = new Node(null,new Point(0,0,TreeSize),new Point(TreeSize,0,TreeSize),new Point(0,0,0), new Point(TreeSize,0,0));
    	Root.Parent = null;
    	
    	//not doing this recursively, because it's not jumping out at me, I think I'm seeing problems with initialization that way,
    	//you'd have to keep track of depth and that could be messy
    	ArrayList<Node> NodesToProcess = new ArrayList<Node>();
    	ArrayList<Node> NextNodesToProcess = new ArrayList<Node>();
    	NodesToProcess.add(Root);
    	for(int i = 0; i < TreeDepth; i ++){
    		
    		//process dem nodes
    		for(Node CurrentNode : NodesToProcess){
    			
    			//get the points for the child nodes corners
    			Point Up = new Point(CurrentNode.UpperLeft.X + ((CurrentNode.UpperRight.X - CurrentNode.UpperLeft.X)/2), 0 , CurrentNode.UpperLeft.Z);
    			Point Down = new Point(CurrentNode.LowerLeft.X + ((CurrentNode.LowerRight.X - CurrentNode.LowerLeft.X)/2),0,CurrentNode.LowerLeft.Z);
    			Point Left = new Point(CurrentNode.LowerLeft.X,0,CurrentNode.LowerLeft.Z +((CurrentNode.UpperLeft.Z - CurrentNode.LowerLeft.Z)/2));
    			Point Right = new Point(CurrentNode.LowerRight.X,0,CurrentNode.LowerRight.Z +((CurrentNode.UpperRight.Z - CurrentNode.LowerRight.Z)/2));
    			Point Center = new Point(CurrentNode.LowerLeft.X + ((CurrentNode.LowerRight.X - CurrentNode.LowerLeft.X)/2),0,CurrentNode.LowerRight.Z +((CurrentNode.UpperRight.Z - CurrentNode.LowerRight.Z)/2));
    			
    			//create the Child Nodes
    			Node NorthWestChild = new Node(CurrentNode,CurrentNode.UpperLeft, Up, Left, Center);
    			Node NorthEastChild = new Node(CurrentNode, Up, CurrentNode.UpperRight, Center, Right);
    			Node SouthWestChild = new Node(CurrentNode, Left, Center, CurrentNode.LowerLeft, Down);
    			Node SouthEastChild = new Node(CurrentNode,Center,Right,Down, CurrentNode.LowerRight);
    			
    			//set the children of the current node
    			CurrentNode.NorthWest = NorthWestChild;
    			CurrentNode.NorthEast = NorthEastChild;
    			CurrentNode.SouthWest = SouthWestChild;
    			CurrentNode.SouthEast = SouthEastChild;
    			
    			//add those nodes to the next nodes to process
    			NextNodesToProcess.add(NorthWestChild);
    			NextNodesToProcess.add(NorthEastChild);
    			NextNodesToProcess.add(SouthWestChild);
    			NextNodesToProcess.add(SouthEastChild);
    			
    		}
    		
    		//copy over the next nodes to process into the current nodes to process
    		NodesToProcess.clear();
    		for(Node n : NextNodesToProcess){
    			NodesToProcess.add(n);
    		}
    		NextNodesToProcess.clear();
    	}
    	
    }
    
    //get all the triangles in this quadtree, puts them in the ArrayList "Triangles"
    // is RECURSIVE
    //OOOoooohhhh recursive
    //*APPLAUSE*
    public void getTriangles(Node CurrentNode){
    	if(CurrentNode.NorthEast != null) getTriangles(CurrentNode.NorthEast);
    	if(CurrentNode.NorthWest != null) getTriangles(CurrentNode.NorthWest);
    	if(CurrentNode.SouthEast != null) getTriangles(CurrentNode.SouthEast);
    	if(CurrentNode.SouthWest != null) getTriangles(CurrentNode.SouthWest);
    	
    	//add the 2 triangles for this node if it is a leaf and doesn't have a datapoint
    	if(CurrentNode.NorthEast == null && CurrentNode.NorthWest == null && CurrentNode.SouthEast == null && CurrentNode.SouthWest == null && CurrentNode.Data == null){
    		Triangles.add(new Triangle(CurrentNode.LowerLeft, CurrentNode.UpperLeft, CurrentNode.UpperRight));
        	Triangles.add(new Triangle(CurrentNode.UpperRight, CurrentNode.LowerRight, CurrentNode.LowerLeft));
    	}
    	//if there's a datapoint we gotta make 4 triangles
    	else if(CurrentNode.NorthEast == null && CurrentNode.NorthWest == null && CurrentNode.SouthEast == null && CurrentNode.SouthWest == null && CurrentNode.Data != null){
    		Triangles.add(new Triangle(CurrentNode.UpperLeft, CurrentNode.UpperRight, CurrentNode.Data));
    		Triangles.add(new Triangle(CurrentNode.UpperRight, CurrentNode.LowerRight, CurrentNode.Data));
    		Triangles.add(new Triangle(CurrentNode.LowerRight,CurrentNode.LowerLeft,CurrentNode.Data));
    		Triangles.add(new Triangle(CurrentNode.UpperLeft, CurrentNode.LowerLeft, CurrentNode.Data));
    	}
    	
    	return;
    	
    }
    
    //search for a location in the quadtree
    public Node FoundNode;	//the node we found for the x and z values
    public Node SearchTree(Node CurrentNode, float x, float z){
    	
    	//find the center of this node
    	Point Center = new Point(CurrentNode.LowerLeft.X + ((CurrentNode.LowerRight.X - CurrentNode.LowerLeft.X)/2),0,CurrentNode.LowerRight.Z +((CurrentNode.UpperRight.Z - CurrentNode.LowerRight.Z)/2));
    	
    	//check east/west and north/south
    	boolean east = false;
    	boolean west = false;
    	boolean north = false;
    	boolean south = false;
    	if(x <= Center.X)	west = true;		//favor west
    	else 				east = true;
    	if(z >= Center.Z)	north = true;		//favor north
    	else 				south = true;
    	
    	//make sure that this node has children
    	if(CurrentNode.NorthEast != null && CurrentNode.NorthWest != null && CurrentNode.SouthEast != null && CurrentNode. SouthWest != null ){
    		//navigate to the correct node
        	if(north && east) SearchTree(CurrentNode.NorthEast, x,z);
        	if(north && west) SearchTree(CurrentNode.NorthWest, x,z);
        	if(south && east) SearchTree(CurrentNode.SouthEast, x,z);
        	if(south && west) SearchTree(CurrentNode.SouthWest, x,z);
    	}
    	else{
    		FoundNode = CurrentNode;
    	}
    	
    	return FoundNode;
    }
    
    //insert a datapoint
    public void InsertData(Node RootNode, float x, float y, float z) throws Exception{InsertData(RootNode, new Point(x,y,z));}
    public void InsertData(Node RootNode, Point DataPoint) throws Exception{
    	
    	//find the Node for this datapoint
    	Node NodeToWorkWith = SearchTree(RootNode, DataPoint.X, DataPoint.Z);
    	
    	//check to see if datapoint already exists
		if(NodeToWorkWith.Data == DataPoint) throw new Exception("You asshole, that one already exists");
		
		//check to see if the datapoint is on a boundary line
		if(DataPoint.X == NodeToWorkWith.UpperLeft.X || DataPoint.X == NodeToWorkWith.UpperRight.X ||
			DataPoint.Z == NodeToWorkWith.UpperLeft.Z || DataPoint.Z == NodeToWorkWith.LowerLeft.Z){
			System.out.println("Point is on boundary");
			return;
		}
    	
    	//check to see if this node already has a datapoint
    	if(NodeToWorkWith.Data != null){
    		
    		//if it does we need to partition it
    		//get the points for the child nodes corners
			Point Up = new Point(NodeToWorkWith.UpperLeft.X + ((NodeToWorkWith.UpperRight.X - NodeToWorkWith.UpperLeft.X)/2), 0 , NodeToWorkWith.UpperLeft.Z);
			Point Down = new Point(NodeToWorkWith.LowerLeft.X + ((NodeToWorkWith.LowerRight.X - NodeToWorkWith.LowerLeft.X)/2),0,NodeToWorkWith.LowerLeft.Z);
			Point Left = new Point(NodeToWorkWith.LowerLeft.X,0,NodeToWorkWith.LowerLeft.Z +((NodeToWorkWith.UpperLeft.Z - NodeToWorkWith.LowerLeft.Z)/2));
			Point Right = new Point(NodeToWorkWith.LowerRight.X,0,NodeToWorkWith.LowerRight.Z +((NodeToWorkWith.UpperRight.Z - NodeToWorkWith.LowerRight.Z)/2));
			Point Center = new Point(NodeToWorkWith.LowerLeft.X + ((NodeToWorkWith.LowerRight.X - NodeToWorkWith.LowerLeft.X)/2),0,NodeToWorkWith.LowerRight.Z +((NodeToWorkWith.UpperRight.Z - NodeToWorkWith.LowerRight.Z)/2));
			
			//create the Child Nodes
			Node NorthWestChild = new Node(NodeToWorkWith,NodeToWorkWith.UpperLeft, Up, Left, Center);
			Node NorthEastChild = new Node(NodeToWorkWith, Up, NodeToWorkWith.UpperRight, Center, Right);
			Node SouthWestChild = new Node(NodeToWorkWith, Left, Center, NodeToWorkWith.LowerLeft, Down);
			Node SouthEastChild = new Node(NodeToWorkWith,Center,Right,Down, NodeToWorkWith.LowerRight);
			
			//set the children of the current node
			NodeToWorkWith.NorthWest = NorthWestChild;
			NodeToWorkWith.NorthEast = NorthEastChild;
			NodeToWorkWith.SouthWest = SouthWestChild;
			NodeToWorkWith.SouthEast = SouthEastChild;
			
			//now we try to insert the two datapoints
			InsertData(NodeToWorkWith,NodeToWorkWith.Data);
			InsertData(NodeToWorkWith, DataPoint);
			
    	}
    	else{
    		NodeToWorkWith.Data = DataPoint;
    	}
    	
    	
    }
    	
    
    
}
