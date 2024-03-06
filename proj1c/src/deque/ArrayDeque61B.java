package deque;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
public class ArrayDeque61B<T> implements Deque61B<T> {

    public static final int RESIZE_CHECK = 16;
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    public ArrayDeque61B() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 3;
        nextLast = 4;
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
        if (other instanceof ArrayDeque61B otherArrayDeque) { // checks if other is an ArrayDeque61B
            if (this.size() == otherArrayDeque.size()) {
                for (int i = 0; i < this.size(); i++) {
                    if (!this.get(i).equals(otherArrayDeque.get(i))) {
                        return false;
                    }
                }
                return true;
            }
        } else if (other instanceof LinkedListDeque61B otherArrayDeque) {
            if (this.size() == otherArrayDeque.size()) {
                for (int i = 0; i < this.size(); i++) {
                    if (!this.get(i).equals(otherArrayDeque.get(i))) {
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
        return new ArrayDequeIterator();
    }
    private class ArrayDequeIterator implements Iterator<T> {
        private int wizPos;
        public ArrayDequeIterator() {
            wizPos = nextFirst + 1;
        }
        @Override
        public boolean hasNext() {
            return wizPos < nextLast;
        }

        @Override
        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }
    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        size += 1;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextLast] = x;
        size += 1;
        nextLast = Math.floorMod(nextLast + 1, items.length);
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        T newItem;
        for (int i = 0; i < size; i++) {
            newItem = get(i);
            returnList.add(newItem);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        T value;
        if (isEmpty()) {
            return null;
        } else {
            nextFirst = Math.floorMod(nextFirst + 1, items.length);
            value = items[nextFirst];
            items[nextFirst] = null;
            size -= 1;
        }
        if (size <= items.length / 4 && items.length >= RESIZE_CHECK) {
            resize(items.length / 2);
        }
        return value;
    }

    @Override
    public T removeLast() {
        T value;
        if (isEmpty()) {
            return null;
        } else {
            nextLast = Math.floorMod(nextLast - 1, items.length);
            value = items[nextLast];
            items[nextLast] = null;
            size -= 1;
        }
        if (size <= items.length / 4 && items.length >= RESIZE_CHECK) {
            resize(items.length / 2);
        }
        return value;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        } else {
            return items[Math.floorMod(nextFirst + 1 + index, items.length)];
        }
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }

    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        int space = ((capacity - size) / 2);
        for (int i = 0; i < size; i++) {
            nextFirst = Math.floorMod(nextFirst + 1, items.length);
            newItems[space + i] = items[nextFirst];
        }
        nextFirst = space - 1;
        nextLast = space + size;
        items = newItems;
    }
}
