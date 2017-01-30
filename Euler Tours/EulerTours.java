/**
 * Class to implement methods to find Euler tour in a graph.
 * This class also has methods to check whether a graph contains Euler tour
 * or not, to verify a given path is Euler tour or not.
 *
 * @author Rammurthy Mudimadugula
 * @netid  rxm163730
 */

import java.util.*;

@SuppressWarnings("unchecked")
public class EulerTours {

    /**
     * Method to traverse a graph in breadth first manner.
     *
     * @param  src      Source vertex where the traversal should start.
     * @return Nothing.
     */
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

    /**
     * Method to check whether a graph is connected or not.
     *
     * @param  g       g is a graph.
     * @return boolean Boolean value indicating whether graph is connected.
     */
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

    /**
     * Method to verify whether given graph is an Eulerian graph
     *
     * @param  g       g is a graph.
     * @return boolean Boolean value indicating whether a graph is Eulerian.
     */
    public static boolean isEulerian(Graph g) {
        boolean isConnected = isConnected(g);
        boolean containsOddVertices = containsOddVertices(g);

        return (isConnected && !containsOddVertices) ? true : false;
    }

    /**
     * Method to check whether atleast one vertex of a graph contains odd number
     * of edges incident to it (or going away from it).
     *
     * @param  g       g is a graph.
     * @return boolean Boolean value indicating whether atleast one vertex of a
     *                 graph contains odd number of edges incident to it.
     */
    public static boolean containsOddVertices (Graph g) {
        boolean contains = false;

        Iterator<Vertex> it = g.iterator();
        while (it.hasNext()) {
            if (it.next().adj.size()%2 != 0) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    /**
     * Method to get index of neighbout vertex in adjacency list
     *
     * @param  x   from vertex.
     * @param  y   to vertex.
     * @return int index of the vertex in adjaceny list if exists or else -1.
     */
    public static int indexOf(Vertex x, Vertex y) {
        Iterator<Edge> it = x.adj.iterator();
        int count = 0;
        while (it.hasNext()) {
            Edge e = it.next();
            if (y.name == e.otherEnd(x).name) {
                return count;
            }
            count++;
        }
        return -1;
    }

    /**
     * Method to verify whether a given path is an Euler tour.
     *
     * @param  g       g is a graph.
     * @param  tour    Path to be verified.
     * @return boolean Boolean value indicating whether given path is Euler.
     */
    public static boolean verifyTour(Graph g, CircularList<Vertex> tour) {
        Iterator<Vertex> vit1 = g.iterator();
        while (vit1.hasNext()) {
            Vertex u = vit1.next();
            Iterator<Edge> eit1 = u.adj.iterator();
            while(eit1.hasNext()) {
                eit1.next().seen = false;
            }
        }

        boolean isEuler = true;
        Iterator<Vertex> it1 = tour.iterator();
        Iterator<Vertex> it2 = tour.iterator();

        Vertex u = it1.next();
        Vertex v = it2.next();

        while (it1.hasNext()) {
            v = it2.next();
            int index = indexOf(u, v);

            if (index >= 0) {
                Edge e = u.adj.get(index);
                if (e.seen) {
                    isEuler = false;
                    break;
                } else {
                    e.seen = true;
                    u = it1.next();
                }
            } else {
                isEuler = false;
                break;
            }
        }

        Iterator<Vertex> vit2 = g.iterator();
        while (vit2.hasNext()) {
            Vertex m = vit2.next();
            Iterator<Edge> eit2 = m.adj.iterator();
            while(eit2.hasNext()) {
                if (!eit2.next().seen) {
                    isEuler = false;
                    break;
                }
            }
        }
        return isEuler;
    }

    /**
     * Method to traverse unvisited vertices and mark them as read.
     *
     * @param  src           Source vertex where the traversal should start.
     * @param bridgeVertices The neighbours of source which has unseen edges.
     * @return CircularList  cl is a circular list containing euler path. 
     */
    public static <EulerTours extends Comparable<Vertex>> CircularList<Vertex> traverse(Vertex src, Set<Vertex> bridgeVertices) {
        CircularList<Vertex> cl = new CircularList<>();
        cl.insertAtEnd(src);
        Vertex cur = src;
        Vertex neighbour;
        while ((neighbour = cur.getUnvistedNeighbour()) != null) {
            cl.insertAtEnd(neighbour);
            if (neighbour.neighbours.size() > 1) {
                bridgeVertices.add(neighbour);
            } else {
                bridgeVertices.remove(neighbour);
            }
            cur = neighbour;
        }
        return cl;
    }

    /**
     * Overlaoded compare method to verify whether two vertices are same or not.
     *
     * @param  x       Vertex number one.
     * @param  y       Vertex number two.
     * @return boolean Boolean value indicating whether x and y are same or not.
     */
    private static <EulerTours extends Comparable<Vertex>> boolean compare (Vertex x, Vertex y) {
        return (x.name == y.name) ? true : false;
    }

    /**
     * Method to break given graph into Euler paths.
     *
     * @param  g    g is a graph.
     * @return List List containing possible Euler paths in a graph.
     */
    public static List<CircularList<Vertex>> breakGraphIntoTours(Graph g) {
        List<CircularList<Vertex>> subTours = new LinkedList<CircularList<Vertex>>();
        Set<Vertex> bridgeVertices = new HashSet<>();

        bridgeVertices.add(g.getVertex(1));
        while (bridgeVertices.size() > 0) {
            Vertex v = bridgeVertices.iterator().next();
            if (v.hasUnvisitedNeighbours()) {
                CircularList<Vertex> temp = traverse(v, bridgeVertices);
                if (temp.size() > 2) {
                    subTours.add(temp);
                }
            }
        }
        return subTours;
    }

    /**
     * Method to stitch the broken paths together.
     *
     * @param  listOfTours  LinkedList with a tour as entry.
     * @return CircularList Euler tour of the given graph.
     */
    public static CircularList<Vertex> stitchTours(List<CircularList<Vertex>> listOfTours) {
        CircularList.Node tmp1;
        CircularList.Node tmp2;
        CircularList.Node tmp3;

        tmp1 = listOfTours.get(0).head;

        while(listOfTours.size() != 1) {
            tmp2 = listOfTours.get(1).head;
            tmp3 = listOfTours.get(1).tail;
            while(!compare((Vertex) tmp2.data, (Vertex)tmp1.data)) {
                tmp1=tmp1.next;
            }

            tmp3.next = tmp1.next;
            tmp1.next = tmp2.next;
            tmp1 = tmp2;
            listOfTours.get(0).size += listOfTours.get(1).size - 1;
            listOfTours.remove(1);
        }
        return listOfTours.get(0);
    }
}
