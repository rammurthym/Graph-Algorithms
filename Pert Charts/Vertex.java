/**
 * Class implementing Vertex of a Graph.
 *
 * @author Rammurthy Mudimadugula
 * 
 */

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

public class Vertex implements Iterable<Edge> {
    int name; // name of the vertex
    boolean seen; // flag to check if the vertex has already been visited
    int d;  // duration of task corresponding to vertex
    List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList
    int inDegree;
    int outDegree;
    int top;
    int ec, lc, slack;
    boolean critical;
    int num;
    int numOfCriticalNodes;

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
    	adj = new ArrayList<Edge>();
    	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
        inDegree = 0;
        outDegree = 0;
        top = 0;
        ec = 0;
        lc = 0;
        slack = 0;
        critical = false;
        num = 0;
        numOfCriticalNodes = 0;
    }

    public Iterator<Edge> iterator() { return adj.iterator(); }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	return Integer.toString(name);
    }
}
