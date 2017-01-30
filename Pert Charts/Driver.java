/** 
 * Driver program for MP4
 *
 * @author Rammurthy Mudimadugula
 * 
 */

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
	    if (args.length > 0) {
	        File inputFile = new File(args[0]);
	        in = new Scanner(inputFile);
	    } else {
	        in = new Scanner(System.in);
	    }
		Graph g = Graph.readDirectedGraph(in);

		Vertex s = g.getVertex(g.size-1);
		Vertex t = g.getVertex(g.size);

		for(Vertex u: g) {
		    u.d = in.nextInt();

		    if (u != s && u != t) {
			    if (u.adj.size() == 0) {
			    	Edge x = new Edge(u, t, 1);
			    	u.adj.add(x);
			    	t.revAdj.add(x);
			    }

			    if (u.revAdj.size() == 0) {
			    	Edge x = new Edge(s, u, 1);
			    	s.adj.add(x);
			    	u.revAdj.add(x);
			    }
		    }

		    u.outDegree = u.adj.size();
		    u.inDegree = u.revAdj.size();
		}

		Timer timer = new Timer();
		CriticalPaths cp = new CriticalPaths(g);
		cp.findCriticalPaths();
		System.out.println(timer.end());
    }
}

