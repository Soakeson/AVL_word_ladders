/**
 * This class creates a LinkedList Queue that can have nodes added to the tail
 * and removed from the head.
 */

public class Queue<E> {
    public Node<E> head, tail;
    public int size;

    public Queue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Removes the head of the queue.
     * @return the data stored in the node that is being removed.
     */
    public E remove() {
        if (head != null) {
            // if tail is null or if head and tail are the only nodes
            if (tail == head) {
                Node<E> current = head;
                head = null;
                tail = null;
                size--;
                return current.data;
            // otherwise swap head with head.next
            } else {
                Node<E> current = head;
                head = head.next;
                size--;
                return current.data;
            }
        }
        return null;
    }

    /**
     * Adds a new Node<E> to the back of the queue
     * @param data
     */
    public void add(E data) {
        if (tail == null) {
            tail = new Node<E>(data);
            head = tail;
            size++;
        } else {
            tail.next = new Node<E>(data);
            tail = tail.next;
            size++;
        }
    }

    public String toString(int n) {
        Node<E> current = head;
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < n; i++) {
            if (current == null) {
                break;
            }
            s.append(current.data.toString() + " ");
            current = current.next;
        }
        return s.toString();
    }

    private static class Node<E> {
        public E data;
        public Node<E> next;

        public Node(E data) {
            this.data = data;
            this.next = null;
        }

        public Node<E> next() {
            return this.next;
        }
    }
}
