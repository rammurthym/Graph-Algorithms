/** 
 * Contains methods to calculate diameter of a tree.
 *
 * @author Rammurthy Mudimadugula (rxm163730)
 * 
 */

import java.io.FileNotFoundException;
import java.util.*;
import java.io.File;

public class Driver2 {

	public static void bfs(Vertex src) {
        src.seen = true;

        Queue<Vertex> q = new LinkedList<>();
        q.add(src);
        
        while(!q.isEmpty()) {
            Vertex u = q.remove();

            Iterator<Edge> it = u.adj.iterator();

            while (it.hasNext()) {
                Vertex v = it.next().otherEnd(u);
                if(!v.seen) {
                    v.seen = true;
                    q.add(v);
                }
            }
        }
	}

	public static boolean isConnected(Graph g) {
        boolean connected = false;
        
        Iterator<Vertex> it1 = g.iterator();
        while (it1.hasNext()) {
            it1.next().seen = false;
        }

        bfs(g.getVertex(1));

        Iterator<Vertex> it3 = g.iterator();
        while (it3.hasNext()) {
            if (it3.next().seen) {
                connected = true;
            } else {
                connected = false;
                break;
            }
        }
        return connected;
    }

	public static boolean isCyclic(Graph g) {
        if (g.size == g.esize+1) {
        	return false;
        } else {
        	return true;
        }
    }

    public static Vertex getMaxDistanceVertex(Graph g, Vertex src) {
    	Vertex temp = src;

    	for (Vertex v: g) {
    		if (v.d > temp.d) {
    			temp = v;
    		}
    	}
    	return temp;
    }

    public static int diameter(Graph g) {
    	if (!isCyclic(g) && isConnected(g)) {
    		for (Vertex v: g) {
    			v.seen = false;
    		}

    		Vertex u = g.getVertex(1);

    		g.bfs(u);

    		Vertex z = getMaxDistanceVertex(g, u);

    		for (Vertex v: g) {
    			v.seen = false;
    		}

    		g.bfs(z);

    		Vertex x = getMaxDistanceVertex(g, z);
    		return x.d;
    	} else {
    		return -1;
    	}
    }

    public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
	        if (args.length > 0) {
	            File inputFile = new File(args[0]);
	            in = new Scanner(inputFile);
	        } else {
	            in = new Scanner(System.in);
	        }
		Graph g = Graph.readGraph(in);

		System.out.println(diameter(g));
    }
}
