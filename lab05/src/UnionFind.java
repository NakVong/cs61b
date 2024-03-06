public class UnionFind {

    private int[] elements;
    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Number of items cannot be negative or zero.");
        }

        elements = new int[N];
        for (int i = 0; i < N; i++) {
            elements[i] = -1;
        }
    }

    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        if (v < 0 || v >= elements.length) {
            throw new IllegalArgumentException("Index is not within bounds.");
        }
        return parent(find(v)) * -1;
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        if (v < 0 || v >= elements.length) {
            throw new IllegalArgumentException("Index is not within bounds.");
        }
        return elements[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= elements.length || v2 >= elements.length) {
            throw new IllegalArgumentException("Index is not within bounds.");
        }
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        if (v < 0 || v >= elements.length) {
            throw new IllegalArgumentException("Index is not within bounds.");
        }

        int root = v;
        while (parent(root) >= 0) {
            root = parent(root);
        }

        int prev = v;
        while (parent(v) >= 0) {
            v = parent(v);
            elements[prev] = root;
            prev = v;
        }

        return root;
    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        if (v1 < 0 || v2 < 0 || v1 >= elements.length || v2 >= elements.length) {
            throw new IllegalArgumentException("Index is not within bounds.");
        } else if (v1 == v2) {
            return;
        }

        int p1 = find(v1);
        int p2 = find(v2);

        if (p1 == p2) {
            return;
        }

        if (sizeOf(p1) > sizeOf(p2)) {
            elements[p1] = parent(p1) + parent(p2);
            elements[p2] = p1;
        } else {
            elements[p2] = parent(p1) + parent(p2);
            elements[p1] = p2;
        }
    }

}
