// assignment final
// Kaasila Erik
// ekaasila
// Yudysky Matthew
// Yud
// to represent an edge (connecting two nodes)
public class Edge {
    Node start;
    Node end;

    public Edge(Node start, Node end) {
        this.start = start;
        this.end = end;
    }

    // are these two edges equal?
    @Override
    public boolean equals(Object e) {
        if (e == null) {
            return false;
        }
        if (getClass() != e.getClass()) {
            return false;
        }
        final Edge other = (Edge) e;
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end
                        .equals(other.start));
    }

    // print this edge
    public String toString() {
        return this.start.toString() + " " + this.end.toString();
    }
}
