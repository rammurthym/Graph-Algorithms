/**
 * Class implementing methods to calculate number of critical paths in a PERT charts.
 * And also methods to enumerate the critical paths.
 *
 * @author Rammurthy Mudimadugula
 * 
 */

import java.util.*;

public class CriticalPaths {

	Graph g;

    /**
     * Constructor for the class.
     */
    CriticalPaths(Graph g) {
    	this.g = g;
    }

    /**
     * Method to return maximum of two numbers.
     *
     * @param i,j numbers to be compared
     * @return Integer Maximum of i and j.
     */
    int max(int i, int j) {
    	if (i < j) {
    		return j;
    	} else {
    		return i;
    	}
    }

    /**
     * Method to return minimum of two numbers.
     *
     * @param i,j numbers to be compared
     * @return Integer Minimum of i and j.
     */
    int min(int i, int j) {
    	if (i < j) {
    		return i;
    	} else {
    		return j;
    	}
    }

    /**
     * Method to initialize a graph with seen as false and 
     * set indegree and outdegree of vertices.
     *
     * @return Nothing
     */
    void initialize() {
        for (Vertex v: g) {
            v.seen = false;
            v.outDegree = v.adj.size();
            v.inDegree = v.revAdj.size();
        }
    }

    /**
     * Recursive method to process an unseen vertex.
     *
     * @return Nothing
     */
    void DFSVisit(Vertex u, List<Vertex> topListRev) {
        if (!u.seen) {
            u.seen = true;
            for (Edge e: u.adj) {
                Vertex v = e.otherEnd(u);
                if (!v.seen) {
                    DFSVisit(v, topListRev);
                }
            }
            topListRev.add(u);
        }
    }

    /**
     * Recursive method to return a list of vertices in reverse topological order.
     *
     * @return List Linked list containing vertices in reverse topological order.
     */
    List<Vertex> topologicalOrderRecursive() {
        initialize();
        List<Vertex> topListRev = new LinkedList<Vertex>();

        for (Vertex u: g) {
            if (!u.seen) {
                DFSVisit(u, topListRev);
            }
        }
        return topListRev;
    }

    /**
     * Iterative method to return a list of vertices in topological order.
     *
     * @return List Linked list containing vertices in topological order.
     */
    List<Vertex> topologicalOrderIterative() {
        initialize();
    	List<Vertex> topList = new LinkedList<Vertex>();

    	Queue<Vertex> q = new LinkedList<>();

    	int count = 0;

    	for (Vertex u: g) {
    		if (u.inDegree == 0) {
    			q.add(u);
    		}
    	}

    	while (!q.isEmpty()) {
    		Vertex u = q.remove();
    		topList.add(u);
    		u.top = ++count;

    		for (Edge e: u.adj) {
    			Vertex v = e.otherEnd(u);
    			v.inDegree--;
    			v.ec = max(v.ec, u.ec+v.d);
    			v.lc = v.ec;
    			if (v.inDegree == 0) {
    				q.add(v);
    			}
    		}

    	}

    	if (count != g.size) {
    		return new LinkedList<Vertex>();
    	} else {
    		return topList;
    	}
    }

    /**
     * Method to mark vertices as critical or not.
     *
     * @return Nothing.
     */
    void markCriticalNodes() {
        int i = 0;
        for (Vertex v: g) {
            if (v.lc == v.ec) {
                v.critical = true;
                i++;
            }
            v.num = 0;
        }
        g.getVertex(g.size).numOfCriticalNodes = i-2;
    }

    /**
     * Method to calculate number of critical paths in a PERT chart.
     *
     * @return Nothing.
     */
    void calcNoOfCriticalPaths(List<Vertex> topList) {
        markCriticalNodes();
        g.getVertex(g.size-1).num = 1;

        for (Vertex s: topList) {
            if (s.critical) {
                for (Edge e: s.adj) {
                    Vertex v = e.otherEnd(s);
                    if (v.critical && (s.lc + v.d == v.lc)) {
                        v.num += s.num;
                        e.critical = true;
                    }
                }
            }
        }
    }

    /**
     * Method to build a single critical path.
     *
     * @return Nothing.
     */
    List<Vertex> buildCriticalPath() {
        Vertex s = g.getVertex(g.size-1);
        Vertex t = g.getVertex(g.size);

        List<Vertex> criticalPath = new LinkedList<Vertex>();

        while(s != t) {
            for (Edge e: s.adj) {
                Vertex v = e.otherEnd(s);
                if ((s.lc == s.ec) && (v.lc == v.ec) && (s.lc + v.d == v.lc)) {
                    if (v != t) {
                        criticalPath.add(v);
                    }
                    s = v;
                    break;
                }
            }
        }

        return criticalPath;
    }

    /**
     * Method to print a single critical path.
     *
     * @param criticalPath List containing a single critical path.
     * @param check        Boolean value to check whether to print distance or not.
     * @return Nothing.
     */
    void printCriticalPath(List<Vertex> criticalPath, boolean check) {
        if (check) {
            int distance = 0;

            for (Vertex v: criticalPath) {
                distance += v.d;
            }
            System.out.println(distance);
        }
        
        for (Vertex v: criticalPath) {
            System.out.print(v + " ");
        }
        System.out.println();
        System.out.println();
    }

    /**
     * Method to print a critical path.
     *
     * @param vArray Array to hold the critical paths.
     * @param index  size of the critical path.
     * @return Nothing.
     */
    void visit(Vertex[] vArray, int index) {
        for(int i = 1; i < index; i++) {
            System.out.print(vArray[i] + " ");
        }
        System.out.println();
    }

    /**
     * Recursive method to enumerate critical paths in PERT charts.
     * 
     * @param vArray Array to hold the critical paths.
     * @param u      Vertex to process.
     * @param index  Index in which u has to be inserted.
     * @return Nothing.
     */
    void enumeratePaths(Vertex[] vArray, Vertex u, int index) {
        vArray[index] = u;
        if (u == g.getVertex(g.size)) {
            visit(vArray, index);
        } else {
            for (Edge e: u.adj) {
                if (e.critical) {
                    Vertex v = e.otherEnd(u);
                    enumeratePaths(vArray, v, index+1);
                }
            }
        }
    }

    /**
     * Method to calculate number of critical paths in PERT charts
     * and also to enumerate the critical paths.
     *
     * @return Nothing.
     */
    void findCriticalPaths() {
    	List<Vertex> topList = topologicalOrderIterative();

        if (topList.size() > 0) {

            List<Vertex> topListRev = topologicalOrderRecursive();

        	int maxLC = g.getVertex(g.size).lc;

        	for (Vertex v: g) {
        		v.lc = maxLC;
        	}

        	for (int i = topList.size()-1; i >= 0; i--) {
        		Vertex u = topList.get(i);
                for (Edge e: u.revAdj) {
                    Vertex v = e.otherEnd(u);
        			v.lc = min(v.lc, u.lc-u.d);
        			v.slack = v.lc - v.ec;
        		}
        	}

            for (Vertex u: topListRev) {
                u.slack = u.lc - u.ec;
                for (Edge e: u.revAdj) {
                    Vertex v = e.otherEnd(u);
                    v.lc = min(v.lc, u.lc-u.d);
                }
            }

            List<Vertex> criticalPath = buildCriticalPath();

            calcNoOfCriticalPaths(topList);
            printCriticalPath(criticalPath, true);

            System.out.println("Task      " + "EC       " + "LC       " + "Slack");
            for (Vertex v: g) {
                if (v != g.getVertex(g.size-1) && v != g.getVertex(g.size)) {
                    System.out.println(v + "        " + v.ec + "        " + v.lc + "        " + v.slack);
                }
            }
            System.out.println();
            System.out.println(g.getVertex(g.size).numOfCriticalNodes);
            System.out.println(g.getVertex(g.size).num);

            if (g.getVertex(g.size).num == 1) {
                printCriticalPath(criticalPath, false);
            } else {
                Vertex[] vArray = new Vertex[g.getVertex(g.size).numOfCriticalNodes];
                enumeratePaths(vArray, g.getVertex(g.size-1), 0);
                System.out.println();
            }
        } else {
            System.out.println("Given directed graph is not a DAG");
        }
    }
}
