import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {
    private Node root;
    private int size;

    private class Node {
        private K key;
        private V value;
        private Node left;
        private Node right;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }


    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node put(Node n, K key, V value) {
        if (n == null) {
            n = new Node(key, value);
            size++;
        }

        int compare = n.key.compareTo(key);
        if (compare == 0) {
            n.value = value;
        } else if (compare > 0) {
            n.left = put(n.left, key, value);
        } else {
            n.right = put(n.right, key, value);
        }
        return n;
    }

    @Override
    public V get(K key) {
        Node target = get(root, key);
        if (target != null) {
            return target.value;
        }
        return null;
    }

    private Node get(Node n, K key) {
        if (n == null) {
            return null;
        }

        int compare = n.key.compareTo(key);
        if (compare == 0) {
            return n;
        } else if (compare > 0) {
            return get(n.left, key);
        } else {
            return get(n.right, key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        Node target = get(root, key);
        return target != null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        getKeys(keys, root);
        return keys;
    }

    private void getKeys(Set<K> keys, Node n) {
        if (n != null) {
            getKeys(keys, n.left);
            keys.add(n.key);
            getKeys(keys, n.right);
        }
    }

    @Override
    public V remove(K key) {
        V value = get(key);
        if (value == null) {
            return null;
        }
        root = remove(root, key);
        size--;
        return value;
    }

    private Node remove(Node n, K key) {
        int compare = n.key.compareTo(key);
        if (compare < 0) {
            n.right = remove(n.right, key);
            return n;
        } else if (compare > 0) {
            n.left = remove(n.left, key);
            return n;
        } else {
            if (n.left == null && n.right == null) { // from recursive call, not root of whole tree
                n = null;
                return n;
            } else if (n.left == null) {
                n = n.right;
                return n;
            } else if (n.right == null) {
                n = n.left;
                return n;
            } else {
                Node successor = findSuccessorLeft(n);
                n.key = successor.key;
                n.value = successor.value;
                n.left = remove(n.left, n.key);
                return n;
            }
        }

    }
    private Node findSuccessorLeft(Node n) {
        Node cursor = n.left;
        while (cursor.right != null) {
            cursor = cursor.right;
        }
        return cursor;
    }


    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

}
