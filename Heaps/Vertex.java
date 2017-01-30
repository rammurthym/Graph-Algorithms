/**
 * Class implementing Vertex of a Graph.
 *
 * @author Rammurthy Mudimadugula
 * @netid  rxm163730
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Vertex implements Index, Comparator<Vertex>, Iterable<Edge> {
    int name; // name of the vertex
    boolean seen; // flag to check if the vertex has already been visited
    int d;  Vertex p;  // fields used in algorithms of Prim and Dijkstra
    List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList
    int index;
    int adjSize;
    boolean inTree;

    /**
     * Get Index.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * Put Index.
     */
    public void putIndex(int i) {
        this.index = i;
    }

    /**
     * Comparator.
     */
    @Override
    public int compare(Vertex u, Vertex v) {
        if (u == null) {
            return 1;
        } else if (v == null) {
            return -1;
        } else {
            return u.d - v.d;
        }
    }

    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) {
        name = n;
        seen = false;
        d = Integer.MAX_VALUE;
        p = null;
        adj = new ArrayList<Edge>();
        revAdj = new ArrayList<Edge>();   /* only for directed graphs */
        adjSize = 0;
        inTree = false;
    }

    public Iterator<Edge> iterator() { return adj.iterator(); }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
        return Integer.toString(name);
    }
}
