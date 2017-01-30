/**
 * Class implementing Dijkstra's Algorithm.
 *
 * @author Rammurthy Mudimadugula
 * @netid  rxm163730
 */

import java.util.Scanner;
import java.lang.Comparable;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class ShortestPath {
    static final int Infinity = Integer.MAX_VALUE;

    /**
     * Method implementing Dijkstra Algorithm.
     */
    static void DijkstraShortestPaths(Graph g, Vertex s) {
        for (Vertex u: g) {
            u.d = Infinity;
            u.p = null;
        }

        Set<Vertex> vs = new HashSet<Vertex>();
        s.d = 0;

        IndexedHeap<Vertex> q = new IndexedHeap(g.size, new Vertex(0));

        for (Vertex u: g) {
            q.add(u);
        }


        while (!q.isEmpty()) {
            Vertex u = q.remove();
            vs.add(u);
            for (Edge e: u.adj) {
                Vertex v = e.otherEnd(u);  

                if (!vs.contains(v) && (u.d + e.weight) < v.d) {
                    v.d = u.d + e.weight;
                    v.p = u;
                    q.decreaseKey(v);
                }
            }
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
        int src = in.nextInt();
        int target = in.nextInt();
        Vertex s = g.getVertex(src);
        Vertex t = g.getVertex(target);
        Timer timer = new Timer();
        timer.start();
        DijkstraShortestPaths(g, s);
        timer.end();

        System.out.println(t.d);
        System.out.println(timer);
    }
}
