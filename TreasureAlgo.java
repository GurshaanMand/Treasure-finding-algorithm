//PURPOSE: Simulates navigating through a cave using a pathfinding algorithm and tracks the explorer’s movement.

import java.util.ArrayList;

public class CaveExplorer {
    public static final char[][] CAVE = {
        "##################################################".toCharArray(),
        "#         #              #              #       T#".toCharArray(),
        "# ####### ########## ##### ########### ### #######".toCharArray(),
        "#       #        #    #   #        #    #        #".toCharArray(),
        "####### # #### # ####### ####### #### #####  #### #".toCharArray(),
        "#     # # #    #        #       #    #        #  #".toCharArray(),
        "# ### # # # ########## ######### ####### #### # ##".toCharArray(),
        "# # #   # # #        #                   #   #  #".toCharArray(),
        "# # ####### ######## ######## ######## # ###### ##".toCharArray(),
        "# #       #        # #      # #      # #        ##".toCharArray(),
        "# ######## ###### # # #### # ###### # ######### ##".toCharArray(),
        "#        #      # #    #   #      # #        #   #".toCharArray(),
        "######## ###### # ####### ###### # ######## # ###".toCharArray(),
        "#      #        #       #      # #      #   #   ##".toCharArray(),
        "# #### ######## ######## #### # ###### # ##### ##".toCharArray(),
        "#    #        #        #    # #      # # #     ##".toCharArray(),
        "#### ######## ######## #### ######## # # ##### ##".toCharArray(),
        "#    #      #        #      #        # #     #  #".toCharArray(),
        "# ####### # ######## ######### ####### ##### # ##".toCharArray(),
        "#        # #        #        # #    #       #   #".toCharArray(),
        "########## ######## ######### # ## ########## ###".toCharArray(),
        "#        #      #        #     #  #        #    #".toCharArray(),
        "# ###### #### # ####### ####### ########## # ####".toCharArray(),
        "#      #    # # #     #       #    #     # #    #".toCharArray(),
        "###### #### # # ### ####### # #### # ### # #### #".toCharArray(),
        "#    #    # # #   #       # # #    # # # # #    #".toCharArray(),
        "# ## #### # # ##### ##### # # ####### # # # ####".toCharArray(),
        "#  #      # #     # #   #   #        # # #      #".toCharArray(),
        "## ######## ##### # # # ########### # # ###### #".toCharArray(),
        "#         #     # # # #       #     # #        #".toCharArray(),
        "######### ##### # # ######### # ##### ##########".toCharArray(),
        "#       #     # # #         # #     #        # #".toCharArray(),
        "# ##### ##### # # ######### ####### ####### # # #".toCharArray(),
        "#     #     # # # #       #       #       # # # #".toCharArray(),
        "##### ##### # # # ###### ######### ##### # # # #".toCharArray(),
        "#     #   # # # #      # #       #     # # # # #".toCharArray(),
        "# ### # # # # ####### # ##### # ##### # # # # #".toCharArray(),
        "# #   # # # #       #     #   # #     # # # # #".toCharArray(),
        "# # ##### # ####### #### # ##### ##### # # # #".toCharArray(),
        "# #     # #       #    # #       #     # # # #".toCharArray(),
        "# ##### # ###### #### # ####### # ##### # # # #".toCharArray(),
        "#     # # #    #    # #       #       # # # # #".toCharArray(),
        "##### # # # ## #### ######## ######### # # # #".toCharArray(),
        "#     # # #  #      #       # #       # # # # #".toCharArray(),
        "# ##### # ###### ####### # # ####### # # # # #".toCharArray(),
        "#       #        #     # # #       # # # # # #".toCharArray(),
        "# ############## ### # # ####### # # # # # # #".toCharArray(),
        "#              #     #         # # # # # # #  #".toCharArray(),
        "############## ################# # # # # # ####".toCharArray(),
        "#                                #   #   #    #".toCharArray(),
        "##################################################".toCharArray()
    };

    public static void main(String[] args) {
        char[][] caveLayout = copyLayout(CAVE);
        exploreCave(caveLayout, new Location(1, 1));
    }

    public static char[][] copyLayout(char[][] layout) {
        //TODO: Implement a method to copy over the original layout
        char[][] newLayout = new char[layout.length][];
        //just copy pasting the layout with mda's
        for(int i = 0; i < newLayout.length; i++){
            newLayout[i] = new char[layout[i].length];

            for(int j = 0; j < newLayout[i].length; j++){
                newLayout[i][j] = layout[i][j];
            }
        }

        return newLayout;
    }

    public static void displayCave(char[][] layout) {
        //printing the lkayout with mda's
        for(int i = 0; i < layout.length; i++){
            for(int j = 0; j < layout[i].length; j++){
                System.out.print(layout[i][j]);
            }
            System.out.println();
        }
    }

    public static void exploreCave(char[][] layout, Location start) {
        if (findTreasure(layout, start)) {
            System.out.println("Treasure found!");
        } else {
            System.out.println("No path to the treasure.");
        }
        displayCave(layout);
    }
    /**
     * PURPOSE: Searches the cave to find the treasure location.
     * Updates the path or state if treasure is found.
     */
    public static boolean findTreasure(char[][] layout, Location start) {
        PathStackLL stack = new PathStackLL();
        PathStackLL path = new PathStackLL(); 
    
        // Start from the initial location.
        stack.push(start);
        path.push(start);
    
        while (!stack.isEmpty()) {
            // Peek at the current location.
            Location current = stack.peek();
            int row = current.row();
            int col = current.col();
    
            // Check for treasure.
            if (layout[row][col] == 'T') {
                markPath(layout, path);
                return true;
            }
    
            // Mark visited indices
            if (layout[row][col] == ' ') {
                layout[row][col] = 'X';
            }
    
            // get how long the current path is
            int branchDepth = path.Size();
    
            boolean moved = false;
            // Check Down.
            if (row + 1 < layout.length && layout[row + 1][col] != '#' && layout[row + 1][col] != 'X') {
                Location newLoc = new Location(row + 1, col); //give the new location direction
                stack.push(newLoc);//adding to stack and path
                path.push(newLoc);
                moved = true;//set to true
            }
            // Check Up.
            else if (row - 1 >= 0 && layout[row - 1][col] != '#' && layout[row - 1][col] != 'X') {
                Location newLoc = new Location(row - 1, col);
                stack.push(newLoc);
                path.push(newLoc);
                moved = true;
            }
            // Check Right.
            else if (col + 1 < layout[0].length && layout[row][col + 1] != '#' && layout[row][col + 1] != 'X') {
                Location newLoc = new Location(row, col + 1);
                stack.push(newLoc);
                path.push(newLoc);
                moved = true;
            }
            // Check Left.
            else if (col - 1 >= 0 && layout[row][col - 1] != '#' && layout[row][col - 1] != 'X') {
                Location newLoc = new Location(row, col - 1);
                stack.push(newLoc);
                path.push(newLoc);
                moved = true;
            }
            //if u didnt move 
            if (!moved) {
                // Dead end so we backtrackl
                stack.pop();
                // Remove the most recent path
                while (path.Size() > branchDepth) {
                    path.pop();
                }
            }
        }
        return false;
    }
    /**
     * PURPOSE: Marks the path to the Treasure
     */

    public static void markPath(char[][] layout, PathStack path){
            while(!path.isEmpty()){
                Location curr = path.pop();
                int r = curr.row();
                int c = curr.col();
                //just putting dots
                if(layout[r][c] != 'T' && layout[r][c] != '#'){//place a dot from the path stakc everywhere except where T and # are
                    layout[r][c] = '.';
                }
        }
    }
}

// Interface for stack operations
interface PathStack {
    void push(Location loc); //adds an item to the top of the stack
    Location pop(); //remvoes and return the top element  
    boolean isEmpty();//check if its empty 
}


class PathStackLL implements PathStack{

    private Node top;
	private int numElements;

	public PathStackLL() {
		this.top = null;
		this.numElements = 0;
	}

    @Override
    public void push(Location loc) {
        addToFront(loc); //adds object to the list
        numElements++;
    }

    @Override
    public Location pop() {
        Location loc = (Location)top.value; //gets the top element
        top = top.next; //removes it
        numElements--;

        return loc; //return the top element

    }

    @Override
    public boolean isEmpty() {
        return numElements == 0;
    }

    public void addToFront(Object obj){
        Node newNode = new Node(obj, top);
        this.top = newNode;
    }

    public int Size(){
        return numElements;
    }
    public Location peek() {
        if (top == null) {
            System.out.println("Stack is empty");
            return null;
        }
        return (Location) top.value;
    }

	class Node {
		Object value;
		Node next;

		public Node(Object value, Node next) {
			this.value = value;
			this.next = next;
		}

	}


}



class Location {
    private int row, col;
    public Location(int r, int c) { 
        this.row = r; 
        this.col = c; 
    }
    public int row() { 
        return row; 
    }
    public int col() { 
        return col; 
    }
}

