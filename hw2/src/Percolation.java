import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF grid;
    WeightedQuickUnionUF gridBackwash;
    boolean[][] connected;
    int openSites;
    int topSite;
    int bottomSite;

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("Grid cannot have zero or less cells.");
        }

        grid = new WeightedQuickUnionUF(N * N + 2); // nothing is connected
        gridBackwash = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++) { // top site (after original grid)
            grid.union(N * N, i);
            gridBackwash.union(N * N, i);
        }
        for (int i = N * (N - 1); i < N * N; i++) { // bottom site (after original grid and top site)
            grid.union(i, N * N + 1);
        }
        topSite = N * N;
        bottomSite = N * N + 1;

        connected = new boolean[N][N]; // nothing is open
        openSites = 0;
    }

    public void open(int row, int col) {
        if (row < 0 || row >= connected.length || col < 0 || col >= connected.length) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (connected.length - 1));
        }

        if (!connected[row][col]) {
            connected[row][col] = true;
            openSites++;
        }

        // Check surrounding cells and combine if open
        if (row - 1 >= 0) {
            if (connected[row - 1][col]) {
                grid.union(xyTo1D(row, col, connected.length), xyTo1D(row - 1, col, connected.length));
                gridBackwash.union(xyTo1D(row, col, connected.length), xyTo1D(row - 1, col, connected.length));
            }
        }
        if (col - 1 >= 0) {
            if (connected[row][col - 1]) {
                grid.union(xyTo1D(row, col, connected.length), xyTo1D(row, col - 1, connected.length));
                gridBackwash.union(xyTo1D(row, col, connected.length), xyTo1D(row, col - 1, connected.length));
            }
        }
        if (row + 1 < connected.length) {
            if (connected[row + 1][col]) {
                grid.union(xyTo1D(row, col, connected.length), xyTo1D(row + 1, col, connected.length));
                gridBackwash.union(xyTo1D(row, col, connected.length), xyTo1D(row + 1, col, connected.length));
            }
        }
        if (col + 1 < connected.length) {
            if (connected[row][col + 1]) {
                grid.union(xyTo1D(row, col, connected.length), xyTo1D(row, col + 1, connected.length));
                gridBackwash.union(xyTo1D(row, col, connected.length), xyTo1D(row, col + 1, connected.length));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= connected.length || col < 0 || col >= connected.length) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (connected.length - 1));
        }
        return connected[row][col];
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || row >= connected.length || col < 0 || col >= connected.length) {
            throw new IndexOutOfBoundsException("Index must be between 0 and " + (connected.length - 1));
        }
        if (connected[row][col]) {
            return gridBackwash.connected(xyTo1D(row, col, connected.length), topSite);
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        if (connected.length == 1) {
            return connected[0][0];
        } else {
            return grid.connected(bottomSite, topSite);
        }
    }

    private int xyTo1D(int x, int y, int n) {
        return x * n + y;
    }

}
