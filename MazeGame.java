// assignment final
// Kaasila Erik
// ekaasila
// Yudysky Matthew
// Yud
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javalib.funworld.*;
import javalib.colors.*;
import javalib.worldimages.*;

// represents a maze that contains various nodes and edges,
// mazes can either be randomly generated or inputed by the user.
public class MazeGame extends World {
    ArrayList<Node> nodeList;
    ArrayList<Edge> edgeList;
    WorldImage back = new RectangleImage(new Posn(250, 250), 500, 500,
            new Black());
    WorldImage end = new RectangleImage(new Posn(1, 1), 1, 1, new Black());

    public MazeGame(ArrayList<Node> nodeList, ArrayList<Edge> edgeList) {
        this.nodeList = nodeList;
        this.edgeList = edgeList;
    }

    List<Node> path;
    ArrayList<Node> fillTile = new ArrayList<Node>(Arrays.asList(new Node(
            new Cord(0, 0), null)));
    ArrayList<Node> fillPath = new ArrayList<Node>();
    boolean runNow = false;
    int indexCount = 0;
    int indexCount2 = 0;
    static int dimm = 0;

    /**
     * the main method, runs the maze game
     * 
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to the Maze Game!");
        System.out.print("Enter the dimension of the maze (a single number)"
                + " between 1 and 40 (recommended): ");
        int x = sc.nextInt();
        dimm = x;
        System.out.println("\nNext, press either 'b' for breadth first search,"
                + "or 'd' for depth first search.\n");
        System.out.println("Finally, press 'z' repeatedly to "
                + "show the optimal path.\n");
        System.out.println("Press 's' to generate a new maze!");
        
        run(x);
    }

    /**
     * run the maze game!
     */
    private static void run(int s) {
        MazeGame mg = Algorithms.generateMaze(s);
        mg.edgeList = Algorithms.kruscal(mg, new Random());
        mg.bigBang(500, 500, 0.01);

    }

    /**
     * gets the path, using either breadth first search or depth first search
     */
    public void getPath(boolean b) {
        if (b) {
            path = Algorithms.depthFirstSearch(this);
        } 
        else {
            path = Algorithms.bredthFirstSearch(this);
        }
    }

    /**
     * Adds the given node to the variable fillTile 
     * which is used in drawing the maze
     * 
     * @param e
     *            the node to add to fillTile
     */
    public void addTile(Node e) {
        fillTile.add(e);
    }

    /**
     * adds a node to the final path, used for drawing
     * 
     * @param e
     *            the node to add to the final path
     */
    public void addFinalPath(Node e) {
        fillPath.add(e);
    }

    /**
     * creates the image to be displayed to the user
     */
    public WorldImage makeImage() {
        MazeGame maze = this;
        int dimensions = maze.nodeList.get(maze.nodeList.size() 
                - 1).name.x + 1;
        ArrayList<Node> nodeTemp = maze.nodeList;
        return new OverlayImages(back, new OverlayImages(imageHelper(nodeTemp,
                dimensions, 0, 0), new OverlayImages(imageHelper(fillPath,
                        dimensions, 2, 0), new OverlayImages(
                                imageHelper(fillTile,
                                dimensions, 1, 0), imageHelper(this.edgeList, 
                                        dimensions, 0)))));
    }

    // helps draw
    WorldImage imageHelper(ArrayList<Edge> edges, int dim, int i) {
        int size = 500 / dim;
        int size2 = 500 / dim / 2;
        ArrayList<Edge> theEdges = edges;
        if (theEdges.size() > i) {
            Edge n = theEdges.get(i);
            i++;

            return new OverlayImages(new LineImage(new Posn(n.start.name.x
                    * size + size2, n.start.name.y * size + size2), new Posn(
                            n.end.name.x * size + size2, n.end.name.y *
                                size + size2),
                                new Yellow()), imageHelper(theEdges, dim, i));
        } 
        else {
            return end;
        }
    }

    /**
     * helper for make Image()
     * 
     * @param nodes
     *            draws these nodes
     * @param dim
     *            this is the size of the maze
     * @param num
     *            a number, signifing the color
     * @param i
     *            a counter used in drawing
     * @return returns an image to be displayed on screen
     */
    public WorldImage imageHelper(ArrayList<Node> nodes, 
            int dim, int num, int i) {
        int size = 500 / dim;
        int size2 = 500 / dim / 2;
        ArrayList<Node> theNodes = nodes;
        if (num == 0) {
            if (theNodes.size() > i) {
                Node n = theNodes.get(i);
                i++;
                return new OverlayImages(new RectangleImage(new Posn(n.name.x
                        * size + size2, n.name.y * size + size2), size, size,
                            new Red()), new OverlayImages(new RectangleImage(
                                    new Posn(n.name.x * 
                                            size + size2, n.name.y * size
                                            + size2), size - 2, size - 2, 
                                            new Black()),
                                            imageHelper(theNodes, dim, 0, i)));
            } 
            else {
                return end;
            }
        } 
        else if (num == 1) {
            if (theNodes.size() > i) {
                Node n = theNodes.get(i);
                i++;
                return new OverlayImages(new RectangleImage(new Posn(n.name.x
                        * size + size2, n.name.y * size + size2), size - 2,
                        size - 2, new Green()),
                        imageHelper(theNodes, dim, 1, i));
            } 
            else {
                return end;
            }
        } 
        else {
            if (theNodes.size() > i) {
                Node n = theNodes.get(i);
                i++;
                return new OverlayImages(new RectangleImage(new Posn(n.name.x
                        * size + size2, n.name.y * size + size2), size - 2,
                        size - 2, new Blue()), imageHelper(theNodes, 
                                dim, 2, i));
            } 
            else {
                return end;
            }
        }
    }

    /**
     * when the key 'z' is pressed, the next step of the final path is displayed
     */
    public World onKeyEvent(String ke) {
        if (ke.equals("d")) {
            runNow = true;
            getPath(true);
        }
        if (ke.equals("b")) {
            runNow = true;
            getPath(false);
        }
        if (ke.equals("s")) {
            runNow = false;
            Algorithms.resetPath();
            this.nodeList = new ArrayList<Node>();
            this.edgeList = new ArrayList<Edge>();
            fillTile = new ArrayList<Node>(Arrays.asList(new Node(
                    new Cord(0, 0), null)));
            fillPath = new ArrayList<Node>();
            path = new LinkedList<Node>();
            MazeGame m = Algorithms.generateMaze(dimm);
            m.edgeList = Algorithms.kruscal(m, new Random());
            return m;
        }
        if (ke.equals("z")) {
            int index = getInc();
            if (path.size() > index) {
                addTile(path.get(index));
            }
            return this;
        } 
        else {
            return this;
        }
    }

    /**
     * increments the indexCount by 1
     * 
     * @return the index count
     */
    int getInc() {
        return indexCount++;
    }

    /**
     * increments the indexCount2 by 1
     * 
     * @return the index count
     */
    int getInc2() {
        return indexCount2++;
    }

    /**
     * draws the next tile that was visited by the search algorithm
     */
    public World onTick() {
        if (runNow) {
            int index = getInc2();
            if (Algorithms.getPath().size() > index) {
                addFinalPath(Algorithms.getPath().get(index));
            }
            return this;
        }

        return this;
    }
}