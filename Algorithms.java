// assignment final
// Kaasila Erik
// ekaasila
// Yudysky Matthew
// Yud
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;

// this class contains the algorithm used to traverse through the maze and find
// a path. It has the ability to execute both a BFS and DFS on the maze.
public class Algorithms {
    boolean searchType;
    static ArrayList<Node> path = new ArrayList<Node>();

    /**
     * gets the path of the maze
     * 
     * @return the path
     */
    public static ArrayList<Node> getPath() {
        return path;
    }

    /**
     * Executed a depth first search on the given maze to find an the ideal
     * path, it then returns this path
     * 
     * @param maze
     *            consumes a maze to execute the search on
     * @return the solution for the optimal path found via depth first search
     */
    public static List<Node> depthFirstSearch(MazeGame maze) {
        Stack<Node> toDo = new Stack<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        toDo.add(maze.nodeList.get(0));
        while (!toDo.isEmpty()) {
            Node n = toDo.pop();
            if (n.equals(maze.nodeList.get(maze.nodeList.size() - 1))) {
                path.add(n);
                return constructPath(maze.nodeList
                        .get(maze.nodeList.size() - 1));
            }
            if (!n.nodeEdges.isEmpty()) {
                for (Edge e : n.nodeEdges) {
                    if (maze.edgeList.contains(e)) {
                        if (!e.start.equals(n)) {
                            if (!visited.contains(e.start)
                                    && !toDo.contains(e.start)) {
                                e.start.pathParent = n;
                                toDo.add(e.start);

                            }
                        }

                        if (!e.end.equals(n)) {
                            if (!visited.contains(e.end)
                                    && !toDo.contains(e.end)) {
                                e.end.pathParent = n;
                                toDo.add(e.end);
                            }
                        }
                    }
                }
            }
            visited.add(n);
            path.add(n);
        }

        return null;
    }

    /**
     * Executed a breadth first search on the given maze to find an the ideal
     * path, it then returns this path
     * 
     * @param maze
     *            consumes a maze to execute the search on
     * @return the solution for the optimal path found via breadth first search
     */
    public static List<Node> bredthFirstSearch(MazeGame maze) {
        Queue<Node> toDo = new LinkedList<Node>();
        ArrayList<Node> visited = new ArrayList<Node>();
        toDo.add(maze.nodeList.get(0));
        while (!toDo.isEmpty()) {
            Node n = toDo.peek();
            if (n.equals(maze.nodeList.get(maze.nodeList.size() - 1))) {
                path.add(n);
                return constructPath(maze.nodeList
                        .get(maze.nodeList.size() - 1));
            }
            if (!n.nodeEdges.isEmpty()) {
                for (Edge e : n.nodeEdges) {
                    if (maze.edgeList.contains(e)) {
                        if (!e.start.equals(n)) {
                            if (!visited.contains(e.start)
                                    && !toDo.contains(e.start)) {
                                e.start.pathParent = n;
                                toDo.add(e.start);

                            }
                        }

                        if (!e.end.equals(n)) {
                            if (!visited.contains(e.end)
                                    && !toDo.contains(e.end)) {
                                e.end.pathParent = n;
                                toDo.add(e.end);
                            }
                        }
                    }
                }
            }
            visited.add(toDo.remove());
            path.add(n);
        }

        return null;
    }

    /**
     * "Backtracks" through the maze and constructs the optimal path
     * 
     * @param node
     *            ending node, used to start the backtrack
     * @return the best path
     */
    public static List<Node> constructPath(Node node) {
        LinkedList<Node> path = new LinkedList<Node>();
        while (node.pathParent != null) {
            path.addFirst(node);
            node = node.pathParent;
        }
        return path;
    }

    /**
     * generates the edges for all the nodes
     * 
     * @param nodes
     *            the list of nodes to find edges for
     * @return returns the list of all the edges in the maze
     */
    public static ArrayList<Edge> getEdges(ArrayList<Node> nodes) {
        ArrayList<Edge> returnNodes = new ArrayList<Edge>();
        for (Node n : nodes) {
            for (Edge e : n.nodeEdges) {
                if (!returnNodes.contains(e)) {
                    returnNodes.add(e);
                }
            }
        }
        return returnNodes;
    }

    /**
     * geerates a maze given the initial size
     * 
     * @param size
     *            the resulting maze is size x size
     * @return returns the auto-generated maze
     */
    public static MazeGame generateMaze(int size) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        for (int i = 0; i < size; i++) {
            for (int ii = 0; ii < size; ii++) {
                nodes.add(new Node(new Cord(i, ii), null));
            }
        }
        nodes = generateEdgedNodes(nodes, size);
        MazeGame maze = new MazeGame(nodes, getEdges(nodes));
        return maze;
    }

    /**
     * used by generateMaze to find each nodes specific edges
     * 
     * @param nodes
     *            the nodes to add edges to
     * @param size
     *            the size of the maze
     * @return returns the modified nodes with their correct edges
     */
    static ArrayList<Node> generateEdgedNodes(ArrayList<Node> nodes, int size) {
        for (int i = 0; i < nodes.size(); i++) {
            ArrayList<Edge> edges = new ArrayList<Edge>();
            if (nodes.size() >= i + 2) {
                if (nodes.get(i).name.y < size - 1) {
                    Edge e = new Edge(nodes.get(i), nodes.get(i + 1));
                    edges.add(e);
                }
            }
            if (nodes.size() >= i + size + 1) {
                Edge ee = new Edge(nodes.get(i), nodes.get(i + size));
                edges.add(ee);
            }
            if (nodes.get(i).name.y > 0) {
                Edge eee = new Edge(nodes.get(i), nodes.get(i - 1));
                edges.add(eee);
            }
            if (nodes.get(i).name.x > 0) {
                Edge eeee = new Edge(nodes.get(i), nodes.get(i - size));
                edges.add(eeee);
            }
            nodes.get(i).nodeEdges = edges;
        }
        return nodes;
    }
    
    /**
     * Executes a variant of the Kruscal Algorithm to remove/add edges in a
     * random fashion to produce a unique simple spanning tree graph (with no
     * cycles)
     * 
     * @param maze
     *            the maze we want to modify
     * @return the maze with modified edges
     */
    public static ArrayList<Edge> kruscal(MazeGame maze, Random rand) {
        ArrayList<Edge> mazeEdges = maze.edgeList;
        ArrayList<Node> mazeNodes = maze.nodeList;
        ArrayList<Edge> returnEdges = new ArrayList<Edge>();
        ArrayList<Integer> parentList = new ArrayList<Integer>();
        for (int i = 0; i < mazeNodes.size(); i++) {
            parentList.add(i);
        }
        while (returnEdges.size() < mazeNodes.size() - 1) {
            int index = rand.nextInt(mazeEdges.size());
            Edge e = mazeEdges.get(index);
            int n1 = mazeNodes.indexOf(e.start);
            int n2 = mazeNodes.indexOf(e.end);
            int x = getParent(parentList, n1);
            int y = getParent(parentList, n2);
            if (x != y) {
                parentList.set(x, n2);
                returnEdges.add(e);
                mazeEdges.remove(e);
            }
        }
        return returnEdges;

    }

    /**
     * get the parent
     */
    public static Integer getParent(ArrayList<Integer> spots, int index) {
        while (index != spots.get(index)) {
            index = spots.get(index);
        }
        return index;
    }

    public static void resetPath() {
        path.clear();
        
    }
}
