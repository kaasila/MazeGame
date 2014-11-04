// assignment final
// Kaasila Erik
// ekaasila
// Yudysky Matthew
// Yud
import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;

// test all methods of the maze project
public class ExamplesTest {
    // Node examples
    Node n1 = new Node(new Cord(0, 0), null);
    Node n2 = new Node(new Cord(1, 0), null);
    Node n3 = new Node(new Cord(0, 1), null);
    Node n4 = new Node(new Cord(1, 1), null);
    ArrayList<Integer> tester = new ArrayList<Integer>();

    ArrayList<Edge> eMaze = new ArrayList<Edge>();
    ArrayList<Node> nMaze = new ArrayList<Node>();
    // Edge examples
    Edge e1 = new Edge(n1, n2);
    Edge e2 = new Edge(n2, n4);
    Edge e3 = new Edge(n1, n3);
    Edge e4 = new Edge(n3, n4);

    // ArrayList<Edge> examples
    ArrayList<Edge> el1 = new ArrayList<Edge>();
    ArrayList<Edge> el2 = new ArrayList<Edge>();
    ArrayList<Edge> el3 = new ArrayList<Edge>();
    ArrayList<Edge> el4 = new ArrayList<Edge>();
    MazeGame mg = Algorithms.generateMaze(2);
    MazeGame mg2 = Algorithms.generateMaze(1);

    public void makeExamples() {
        mg.edgeList = Algorithms.kruscal(mg, new Random(3));
        mg2.edgeList = Algorithms.kruscal(mg2, new Random(1));
        tester.add(0);
        tester.add(0);

    }

    @Test
    public void test() {
        MazeGame maze2 = Algorithms.generateMaze(0);
        makeExamples();
        assertEquals(Algorithms.getPath(), new ArrayList<Node>());
        assertEquals(
                Algorithms.depthFirstSearch(mg),
                new LinkedList<Node>(Arrays.asList(new Node(new Cord(0, 1),
                        null), new Node(new Cord(1, 1), null))));
        assertEquals(
                Algorithms.bredthFirstSearch(mg),
                new LinkedList<Node>(Arrays.asList(new Node(new Cord(0, 1),
                        null), new Node(new Cord(1, 1), null))));
        assertEquals(Algorithms.constructPath(n1), new ArrayList<Node>());
        assertEquals(Algorithms.getEdges(mg2.nodeList), new ArrayList<Node>());
        assertEquals(Algorithms.generateEdgedNodes(new ArrayList<Node>(), 0),
                new ArrayList<Edge>());
        assertEquals(Algorithms.getParent(tester, 0), (Integer) 0);
        assertEquals(Algorithms.kruscal(maze2, new Random()),
                new ArrayList<Edge>());
        mg.getPath(true);
        mg.getPath(false);
        mg.addTile(null);
        mg.addFinalPath(null);
        mg2.makeImage();
        mg2.imageHelper(mg.nodeList, 1, 1, 1);
        mg2.imageHelper(mg.edgeList, 1, 1);
        mg.onTick();
        mg.onKeyEvent("k");
        Algorithms.resetPath();
        assertEquals(mg.getInc(), 0);
        assertEquals(mg.getInc2(), 0);
    }
    
    @Test
    public void testEquals() {
        assertEquals(n1.equals(n2), false);
        assertEquals(n1.equals(n1), true);
        assertEquals(e1.equals(e2), false);
        assertEquals(e1.equals(e1), true);
    }
    
    @Test
    public void testToString() {
        assertEquals(n1.toString(), "(0, 0)");
        assertEquals(e1.toString(), "(0, 0) (1, 0)");
    }
}
