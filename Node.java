// assignment final
// Kaasila Erik
// ekaasila
// Yudysky Matthew
// Yud
import java.util.ArrayList;

// to represent one node in a Maze of nodes
public class Node {
    Cord name;
    ArrayList<Edge> nodeEdges;
    public Node pathParent;

    public Node(Cord name, ArrayList<Edge> nodeEdges) {
        this.name = name;
        this.nodeEdges = nodeEdges;
        this.pathParent = null;

    }

    // are these two nodes equal?
    @Override
    public boolean equals(Object n) {
        if (n == null) {
            return false;
        }
        if (getClass() != n.getClass()) {
            return false;
        }
        final Node other = (Node) n;
        return (other.name.x == this.name.x) && (other.name.y == this.name.y);
    }

    // print this node
    public String toString() {
        return "(" + this.name.x + ", " + this.name.y + ")";
    }
}
