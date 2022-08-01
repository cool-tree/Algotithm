/*
data type used to model a percolation system
先设计首尾两个点，这两个点和首尾两行相连形成一个以首尾两个点为根的树，在此基础上，所有后续的可能连上首行节点或尾行节点的中间点，都只能以这两个点为根
特殊情况：n=1
设计两个QuickUnion结构，一个用于percolation的判断，一个用于full的判断，因为percolation之后会影响full的判断
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // representing n-by-n grid using an n*n 1-dimension array grads, the (i,j) grid in n-by-n grid refers to (i-1)*n+(j-1) in grids.
    // use grads[(i-1)*n+(j-1)]=0 to represent the (i,j) grid is blocked.
    private boolean[] grads;
    private int size;
    private final WeightedQuickUnionUF gradsisfull; // used for judge whether one block is full
    private final WeightedQuickUnionUF gradsispercolation; // used for judge whether percolation
    private int opennum = 0;



    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new IllegalArgumentException("the size n is illegal");

        this.size = n;
        this.grads = new boolean[n*n+1];
        for (int i = 0; i < n*n+1; i++)
            grads[i] = false;
        grads[0] = true;

        gradsisfull = new WeightedQuickUnionUF(n*n+1);
        gradsispercolation = new WeightedQuickUnionUF(n*n+2);

        for (int i = 1; i < n+1; i++) {
            gradsisfull.union(0, i);
            gradsispercolation.union(0, i);
        }

        for (int i = size*size; i > size*(size-1); i--) {
            gradsispercolation.union(size*size+1, i);
        }

    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (1 > row || size < row || 1 > col || size < col)
            throw new IllegalArgumentException("the index is out of range!");

        int index = (row-1)*this.size + col;

        if (!grads[index]) {
            grads[index] = true;
            opennum++;
            if (index - size >= 0 && grads[index - size]) {
                gradsisfull.union(index - size, index);
                gradsispercolation.union(index - size, index);
            }
            if (index + size <= size * size && grads[index + size]) {
                gradsisfull.union(index + size, index);
                gradsispercolation.union(index + size, index);
            }
            if (col != 1 && grads[index - 1]) {
                gradsisfull.union(index - 1, index);
                gradsispercolation.union(index - 1, index);
            }
            if (col != size && grads[index + 1]) {
                gradsisfull.union(index + 1, index);
                gradsispercolation.union(index + 1, index);
            }
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (1 > row || size < row || 1 > col || size < col)
            throw new IllegalArgumentException("the index is out of range!");

        int index = (row-1)*this.size + col;
        return grads[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (1 > row || size < row || 1 > col || size < col)
            throw new IllegalArgumentException("the index is out of range!");

        int index = (row-1)*this.size + col;

        return gradsisfull.find(index) == gradsisfull.find(0)  && isOpen(row, col); // full必须满足和0同源
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opennum;
    }

    // does the system percolate?
    //注意 n=1 ，n=2的边界情形
    public boolean percolates() {
        if (size > 1)
            return gradsispercolation.find(size*size+1) == gradsispercolation.find(0);
        else
            return isOpen(1, 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        Percolation square = new Percolation(n);

        while (!square.percolates()) {
            int row = StdRandom.uniform(1, n + 1); // return int type
            int col = StdRandom.uniform(1, n + 1);

            while (square.isOpen(row, col)) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
            }

            square.open(row, col);
        }

        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++)
                StdOut.println(square.isFull(i, j));

        StdOut.println(square.percolates());
    }
}
