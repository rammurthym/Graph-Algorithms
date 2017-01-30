/** 
 * Contains methods to calculate number of components in a graph
 *
 * @author Rammurthy Mudimadugula (rxm163730)
 * 
 */

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Driver {

    static void DFS(Vertex src) {
    	src.seen = true;

    	for (Edge e: src.adj) {
    		Vertex v = e.otherEnd(src);

    		if (!v.seen) {
    			v.cno = src.cno;
    			v.p = src;
    			DFS(v);
    		}
    	}
    }

    static int stronglyConnectedComponents(Graph g) {
    	for (Vertex v: g) {
    		v.seen = false;
    		v.cno = 0;
    		v.p = null;
    	}
    	
    	int cno = 0;

    	for (Vertex v: g) {
    		if (!v.seen) {
    			v.cno = ++cno;
    			DFS(v);
    		}
    	}
    	return cno;
    }

    public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
	        if (args.length > 0) {
	            File inputFile = new File(args[0]);
	            in = new Scanner(inputFile);
	        } else {
	            in = new Scanner(System.in);
	        }
		Graph g = Graph.readDirectedGraph(in);

		System.out.println(stronglyConnectedComponents(g));
    }
}
