/** 
 * Driver program to test the EulerTours class methods.
 * 
 * @author Rammurthy Mudimadugula
 * @netid  rxm163730
 */

import java.io.*;
import java.util.*;

public class Driver {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws FileNotFoundException {
    	Scanner in;
            if (args.length > 0) {
                File inputFile = new File(args[0]);
                in = new Scanner(inputFile);
            } else {
                in = new Scanner(System.in);
            }

    	Graph g = Graph.readGraph(in);

        boolean isEulerian = EulerTours.isEulerian(g);
        
        if (isEulerian) {
            // Timer timer1 = new Timer();
            // timer1.start();

            List<CircularList<Vertex>> subTours = EulerTours.breakGraphIntoTours(g);

            // timer1.end();
            // System.out.println(timer1);

            // System.out.println();
            
            // Timer timer2 = new Timer();
            // timer2.start();

            CircularList<Vertex> tour = EulerTours.stitchTours(subTours);
            
            // timer2.end();
            // System.out.println(timer2);

            // System.out.println();

            for (Vertex u: tour) {
                System.out.println(u);
            }
        } else {
            System.out.println("Graph is not Eulerian");
        }
    }
}
