/**
 * Interface for a Priority Queue.
 *
 * @author Rammurthy Mudimadugula
 * 
 */

public interface PQ<T> {
    public void insert(T x);
    public T deleteMin();
    public T min();

    public void add(T x);
    public T remove();
    public T peek();
}
