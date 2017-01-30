/**
 * Class containing methods to calculate minimum spanning tree of a Graph.
 *
 * @author Rammurthy Mudimadugula
 * @netid  rxm163730
 */

import java.util.Scanner;
import java.lang.Comparable;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.*;

public class MST {
    static final int Infinity = Integer.MAX_VALUE;

    /**
     * Class for implementing vertex comparator
     */
    static class VertexComparator implements Comparator<Vertex> {

        /**
         *
         */
        public int compare(Vertex a, Vertex b) {
            if (a == null) {
                return 1;
            } else if (b == null) {
                return -1;
            } else {
                return a.d - b.d;
            }
        }
    }

    /**
     * Class for implementing edge comparator
     */
    static class EdgeComparator implements Comparator<Edge> {

        /**
         *
         */
        public int compare(Edge a, Edge b) {
            if (a == null) {
                return 1;
            } else if (b == null) {
                return -1;
            } else {
                return a.weight - b.weight;
            }
        }
    }

    /**
     * PrimMST on vertices.
     */
    static int PrimMST(Graph g, Vertex s) {
        int wmst = 0;

        for (Vertex u: g) {
            u.d = Infinity;
            u.p = null;
            u.seen = false;
        }

        s.d = 0;
        Comparator<Vertex> c = new VertexComparator();
        IndexedHeap<Vertex> q = new IndexedHeap(g.size, c);

        for (Vertex u: g) {
            q.add(u);
        }

        while (!q.isEmpty()) {
            Vertex u = q.remove();
            wmst += u.d;
            u.inTree = true;
            for (Edge e: u.adj) {
                Vertex v = e.otherEnd(u);
        
                if (!v.inTree && e.weight < v.d) {
                    v.d = e.weight;
                    v.p = u;
                    q.decreaseKey(v);
                }
            }
        }

        return wmst;
    }

    /**
     * PrimMST on edges.
     */
    static int PrimMSTOnEdges(Graph g, Vertex s) {
        int wmst = 0;

        for (Vertex u: g) {
            u.inTree = false;
            u.p = null;
        }

        s.inTree = true;
        s.p = s;
        int size = 1;

        PriorityQueue<Edge> pq = new PriorityQueue<Edge>(g.esize, new EdgeComparator());
        while(size < g.size) {

            for (Edge e: s.adj) {
                Vertex v = e.otherEnd(s);
                if (!v.inTree) {
                    pq.add(e);
                }
            }

            Edge x = pq.poll();
            
            if (!x.from.inTree || !x.to.inTree) {

                wmst += x.weight;
                size++;

                if (x.from.inTree) {
                    x.to.inTree = true;
                    x.to.p = x.from;
                    s = x.to;
                } else {
                    x.from.inTree = true;
                    x.from.p = x.to;
                    s = x.from;
                }
            }
        }

        return wmst;
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

        if (args[1].equals("0")) {
            Timer t = new Timer();
            t.start();
            Vertex s = g.getVertex(1);
            System.out.println("MST: " + PrimMST(g, s));
            t.end();
            System.out.println(t);
        } else if (args[1].equals("1")) {
            Timer t = new Timer();
            t.start();
            Vertex s = g.getVertex(1);
            System.out.println("MST: " + PrimMSTOnEdges(g, s));
            t.end();
            System.out.println(t);
        }
    }
}
