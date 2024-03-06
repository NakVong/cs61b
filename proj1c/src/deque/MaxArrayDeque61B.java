package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {
    private Comparator<T> comparator;
    public MaxArrayDeque61B(Comparator<T> c) {
        comparator = c;
    }

    public T max() {
        return max(comparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T maxItem = this.get(0);
        for (int i = 0; i < this.size(); i++) {
            T compare = this.get(i);
            if (c.compare(maxItem, compare) < 0) {
                maxItem = compare;
            }
        }
        return maxItem;
    }
}
