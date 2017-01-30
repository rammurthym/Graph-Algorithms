/**
 * Class to represent a vertex of a graph
 * @author Rammurthy Mudimadugula
 *
 */

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Vertex {
    int name; // name of the vertex
    boolean seen; // flag to check if the vertex has already been visited
    int distance; // distance of vertex from a source
    List<Edge> adj, revAdj; // adjacency list; use LinkedList or ArrayList
    Set<Vertex> neighbours;

    /**
     * Constructor for the vertex
     * 
     * @param n
     *            : int - name of the vertex
     */
    Vertex(int n) {
    	name = n;
    	seen = false;
    	distance = Integer.MAX_VALUE;
    	adj = new ArrayList<Edge>();
    	revAdj = new ArrayList<Edge>();   /* only for directed graphs */
        neighbours = new HashSet<>();
    }

    /**
     * Method to get next unvisited neighbour.
     * 
     * @return Nothing.
     */
    public Vertex getUnvistedNeighbour() {
        if (neighbours.size() > 0) {
            Vertex n = neighbours.iterator().next();
            neighbours.remove(n);
            n.neighbours.remove(this);
            return n;
        }
        return null;
    }

    /**
     * Method to check whether the vertex has unvisited neighbour.
     *
     * @return boolean Boolean value to check the vertex has unvisited neighbour.
     */
    boolean hasUnvisitedNeighbours() {
        return neighbours.size() > 0;
    }

    /**
     * Method to represent a vertex by its name
     */
    public String toString() {
	   return Integer.toString(name);
    }
}
