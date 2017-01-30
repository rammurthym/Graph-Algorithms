/**
 * Class implementing Binary Heaps using array.
 *
 * @author Rammurthy Mudimadugula
 * 
 */

import java.util.*;

public class BinaryHeap<T> implements PQ<T> {
    public int DEFAULTSIZE = 11;
    Object[] pq;
    Comparator<T> c;
    int size;

    /**
     * Build a priority queue with a given array q
     */
    BinaryHeap(T[] q, Comparator<T> comp) {
        size = q.length;
        pq = new Object[size+1];
        c = comp;
        for (int i = 0; i < size; i++) {
            pq[i+1] = q[i];
        }
        buildHeap();
    }

    /**
     * Create an empty priority queue of given maximum size
     */
    BinaryHeap(int n, Comparator<T> comp) {
        pq = new Object[n+1];
        c = comp;
        size = 0;
    }

    /**
     * Default constructer.
     */
    BinaryHeap() {
        pq = new Object[DEFAULTSIZE];
        c = new DefaultComparator();
    }

    /**
     * Default comparator.
     */
    public class DefaultComparator<T extends Comparable<? super T>> implements Comparator<T> {

        /**
         *
         */
        public int compare(T a, T b) {
            if (a == null) {
                return 1;
            } else if (b == null) {
                return -1;
            } else {
                return a.compareTo(b);
            }
        }
    }

    /**
     * Insert method.
     */
    public void insert(T x) {
        add(x);
    }

    /**
     * Delete minimum.
     */
    public T deleteMin() {
        return remove();
    }

    /**
     * Get minimum.
     */
    public T min() { 
        return peek();
    }

    /**
     * Add an element to heap.
     */
    public void add(T x) {
        if (size == pq.length-1) {
            resize();
        }
        size++;
        pq[size] = x;
        percolateUp(size);
    }

    /**
     * Remove an element from the heap.
     */
    public T remove() {
        if (size > 0) {
            T x = (T) pq[1];
            move(pq, 1, (T) pq[size]);
            size--;
            percolateDown(1);
            return x;
        } else {
            return null;
        }
    }

    /**
     * Get an element from the priority queue.
     */
    public T peek() {
        if (size == 0) {
            return null;
        } else {
            return (T) pq[1];
        }
    }

    /**
     * pq[i] may violate heap order with parent
     */
    void percolateUp(int i) {
        pq[0] = pq[i];

        while (c.compare((T) pq[i/2], (T) pq[0]) > 0) {
            move(pq, i, (T) pq[i/2]);
            i = i/2;
        }
        move(pq, i, (T) pq[0]);
    }

    /**
     * Add an element to array.
     */
    public void move(Object[] q, int i, T x) {
        q[i] = x;
    }

    /**
     * Resize method to increase the size of priority queue.
     */
    public void resize() {
        Object[] temp = new Object[size*2];
        for (int i = 1; i <= size; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    /**
     * Method to swap elements.
     */
    public void swap(int i1, int i2) {
        T tmp = (T) pq[i1];
        pq[i1] = pq[i2];
        pq[i2] = tmp;
    }

    /**
     * pq[i] may violate heap order with children
     */
    void percolateDown(int i) {
        int schild;

        while (true) {
            if (i*2 > size) {
                break;
            } else {
                if (i*2 == size) {
                    schild = i*2;
                } else {
                    if (c.compare((T) pq[i*2], (T) pq[(i*2)+1]) <= 0) {
                        schild = i*2;
                    } else {
                        schild = (i*2) + 1;
                    }
                }

                if (c.compare((T) pq[i], (T) pq[schild]) <= 0) {
                    break;
                } else {
                    swap(i, schild);
                    i = schild;
                }
            }
        }
    }

    /**
     * Create a heap.  Precondition: none.
     */
    void buildHeap() {
        for(int i = size/2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Method to display the priority queue in no particular order.
     */
    void display() {
        for (int i = 1; i <= size; i++) {
            if (i > 1) {
                System.out.print(", ");
            }
            System.out.print(pq[i]);
        }
        System.out.println();
    }

    /**
     * Get size.
     */
    public int size () {
        return size;
    }

    /**
     * Method to check whether priority queue is empty.
     */
    public boolean isEmpty() {
        return size == 0;
    }
 
    /**
     * sort array A[1..n].  A[0] is not used. 
     * Sorted order depends on comparator used to buid heap.
     * min heap ==> descending order
     * max heap ==> ascending order
     */
    public static <T> void heapSort(T[] A, Comparator<T> comp) {
        BinaryHeap<T> bh = new BinaryHeap<>(A, comp);
        int n = bh.size;

        for (int i = n; i > 0; i--) {
            T min = bh.remove();
            A[i-1] = min;
        }
    }
}
