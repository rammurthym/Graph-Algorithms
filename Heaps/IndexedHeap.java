/**
 * Class implementing Indexed Heaps.
 *
 * @author Rammurthy Mudimadugula
 * 
 */

import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {

    /**
     * Build a priority queue with a given array q
     */
    IndexedHeap(T[] q, Comparator<T> comp) {
        super(q, comp);
    }

    /**
     * Create an empty priority queue of given maximum size
     */
    IndexedHeap(int n, Comparator<T> comp) {
        super(n, comp);
    }

    IndexedHeap() {
        super();
    }

    /**
     * restore heap order property after the priority of x has decreased
     */
    void decreaseKey(T x) {
        percolateUp(x.getIndex());
    }

    /**
     * Overrided move method for indexed heap.
     */
    @Override
    public void move (Object[] pq, int i, T x) {
        pq[i] = x;
        ((T) pq[i]).putIndex(i);
    }

    /**
     * Overrided swap method for indexed heap.
     */
    @Override
    public void swap(int i1, int i2) {
        T tmp = (T) pq[i1];
        pq[i1] = pq[i2];
        ((T) pq[i1]).putIndex(i1);
        pq[i2] = tmp;
        ((T) pq[i2]).putIndex(i2);
    }
}
