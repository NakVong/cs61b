import java.util.Iterator;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class

public class LinkedListDeque61B<T> implements Deque61B<T> {

    public class Node {
        private T item;
        private Node prev;
        private Node next;

        public Node(T item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder("[");
        Iterator<T> iterator = iterator();
        while (iterator.hasNext()) {
            returnString.append(iterator.next());

            if (iterator.hasNext()) {
                returnString.append(", ");
            }
        }
        returnString.append("]");
        return returnString.toString();
    }
    @Override
    public boolean equals(Object other) {
        if (other instanceof LinkedListDeque61B otherLinkedListDeque) {
            if (this.size() == otherLinkedListDeque.size()) {
                for (int i = 0; i < this.size(); i++) {
                    if (this.get(i) != otherLinkedListDeque.get(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    private class LinkedListDequeIterator implements Iterator<T> {
        private Node wizPos;
        public LinkedListDequeIterator() {
            wizPos = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return wizPos != sentinel;
        }

        @Override
        public T next() {
            T item = wizPos.item;
            wizPos = wizPos.next;
            return item;
        }
    }

    @Override
    public void addFirst(T x) {
        Node newNode = new Node(x);
        newNode.next = sentinel.next;
        newNode.prev = sentinel;
        if (size == 0) {
            sentinel.prev = newNode;
        } else {
            sentinel.next.prev = newNode;
        }
        sentinel.next = newNode;
        size += 1;
    }

    @Override
    public void addLast(T x) {
        Node newNode = new Node(x);
        newNode.next = sentinel;
        newNode.prev = sentinel.prev;
        if (size == 0) {
            sentinel.next = newNode;
        } else {
            sentinel.prev.next = newNode;
        }
        sentinel.prev = newNode;
        size += 1;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node current = sentinel.next;
        while (current != sentinel) {
            returnList.add(current.item);
            current = current.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T removed = sentinel.next.item;
        if (size == 1) {
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            size -= 1;
        } else if (size > 1) {
            sentinel.next.next.prev = sentinel;
            sentinel.next = sentinel.next.next;
            size -= 1;
        }
        return removed;
    }

    @Override
    public T removeLast() {
        T removed = sentinel.prev.item;
        if (size == 1) {
            sentinel.next = sentinel;
            sentinel.prev = sentinel;
            size -= 1;
        } else if (size > 1) {
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            size -= 1;
        }
        return removed;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        Node current = sentinel.next;
        while (index > 0) {
            current = current.next;
            index -= 1;
        }
        return current.item;
    }

    @Override
    public T getRecursive(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return recursiveHelper(index, sentinel.next);
        }
    }

    public T recursiveHelper(int index, Node n) {
        if (index > 0) {
            return recursiveHelper(index - 1, n.next);
        } else {
            return n.item;
        }
    }

}
