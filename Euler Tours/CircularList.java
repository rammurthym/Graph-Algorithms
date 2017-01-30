/**
 * Class implementing a generic Circular Linked List.
 *
 * @author Rammurthy Mudimadugula
 * 
 */

import java.util.Iterator;

public class CircularList<T> implements Iterable<T> {

    // Node to hold generic data and pointer to next node.
    public class Node<T> {
        T data;
        Node<T> next;

        // Constructor for the CLL.
        Node(T x, Node<T> nxt) {
            data = x;
            next = nxt;
        }
    }

    Node<T> head; // Pointer to the head of CLL.
    Node<T> tail; // Pointer to the tail of CLL.
    int size; // Variable to store the size of CLL.

    /**
     * Initialization of head, tail and size.
     */
    CircularList() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Overloaded iterator method.
     *
     * @return Nothing.
     */
    public Iterator<T> iterator() {
        return new CLIterator<>(head);
    }

    /**
     * Overloaded iterator method.
     *
     * @return Nothing.
     */
    // Private class implementing overloaded methods for iterator.
    private class CLIterator<E> implements Iterator<E> {
        Node<E> cursor, prev;
        int count = 0; // Variable to avoid infinite loop.

        /**
         * Initialization of pointers.
         *
         * @return Nothing.
         */
        CLIterator(Node<E> head) {
            cursor = head;
            prev = null;
        }

        /**
         * Method to check whether next element is head.
         *
         * @return boolean Whether reached end of CLL or not.
         */
        public boolean hasNext() {
            return (count++) < (size-1);
        }

        /**
         * Method to retrieve next element in CLL.
         *
         * @return Node Next node in the CLL.
         */
        public E next() {
            prev = cursor;
            cursor = cursor.next;
            return prev.data;
        }
    }

    /**
     * Method to check whether CLL is empty.
     *
     * @return boolean true means CLL is empty, false implies CLL is not empty.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     *  Method to the get the size of a CLL.
     * 
     * @return int Size of the CLL instance.
     */
    public int size() {
        return size;
    }

    /**
     * Method to insert node at the end of CLL.
     *
     * @param  val      Generic object to be stored in the CLL.
     * @return Nothing.
     */
    public void insertAtEnd(T val) {
        Node<T> node = new Node<>(val,null);
        node.next = head;
        if(head == null) {
            head = node;
            node.next = head;
            tail = head;
        } else {
            tail.next = node;
            tail = node;
        }
        size++ ;
    }

    /**
     * Method to display CLL.
     *
     * @return Nothing.
     */
    public void display() {
        System.out.print("\nCircular Singly Linked List = ");
        Node ptr = head;

        if (size == 0) {
            System.out.print("empty\n");
            return;
        }

        if (head.next == head) {
            System.out.print(head.data + "->" + ptr.data + "\n");
            return;
        }

        System.out.print(head.data + "->");
        ptr = head.next;

        while (ptr.next != head) {
            System.out.print(ptr.data + "->");
            ptr = ptr.next;
        }

        System.out.print(ptr.data + "\n");
    }
}
