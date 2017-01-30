/**
 * Class implementing Edge of a Graph.
 *
 * @author Rammurthy Mudimadugula
 * @netid  rxm163730
 */

import java.lang.Exception;

public class Edge {
    Vertex from; // head vertex
    Vertex to; // tail vertex
    int weight;// weight of edge
    boolean critical;

    /**
     * Constructor for Edge
     * 
     * @param u
     *            : Vertex - Vertex from which edge starts
     * @param v
     *            : Vertex - Vertex on which edge lands
     * @param w
     *            : int - Weight of edge
     */
    Edge(Vertex u, Vertex v, int w) {
	from = u;
	to = v;
	weight = w;
    critical = false;
    }

    /**
     * Method to find the other end end of an edge, given a vertex reference
     * This method is used for undirected graphs
     * 
     * @param u
     *            : Vertex
     * @return
	 *	  : Vertex - other end of edge
     */
    public Vertex otherEnd(Vertex u) {
	assert from == u || to == u;
	// if the vertex u is the head of the arc, then return the tail else return the head
	if (from == u) {
	    return to;
	} else {
	    return from;
	} 
    }

    public String toString() {
	return from + " " + to + " " + weight;
    }
}