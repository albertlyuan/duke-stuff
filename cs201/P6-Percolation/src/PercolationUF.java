import java.util.Arrays;

public class PercolationUF implements IPercolate{

    private IUnionFind myFinder;
    private boolean[][] myGrid;
    private final int VTOP;
    private final int VBOTTOM;
    private int myOpenCount;

    public PercolationUF(IUnionFind finder, int size){
        myGrid = new boolean[size][size];
        for (boolean[] row : myGrid)
            Arrays.fill(row, false);
        myOpenCount = 0;
        VTOP = size*size;
        VBOTTOM = size*size+1;
        finder.initialize(size*size + 2);
        myFinder = finder;

    }
    @Override
    public void open(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        if(!myGrid[row][col]){
            myGrid[row][col] = true;
            myOpenCount += 1;
            int cell_id = row*myGrid.length + col;
            if (row == 0){
                myFinder.union(VTOP, cell_id);
            }
            if (row == myGrid.length-1){
                myFinder.union(VBOTTOM, cell_id);
            }
            if (row+1 < myGrid.length && myGrid[row+1][col]){
                int other_cell = (row+1)*myGrid.length + col;
                myFinder.union(cell_id, other_cell);
            }
            if (row-1 >= 0 && myGrid[row-1][col]){
                int other_cell = (row-1)*myGrid.length + col;
                myFinder.union(cell_id, other_cell);
            }
            if (col+1 < myGrid.length && myGrid[row][col+1]){
                int other_cell = row*myGrid.length + (col+1);
                myFinder.union(cell_id, other_cell);
            }
            if (col-1 >= 0 && myGrid[row][col-1]){
                int other_cell = row*myGrid.length + (col-1);
                myFinder.union(cell_id, other_cell);
            }

        }

    }
    @Override
    public boolean isOpen(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        return myGrid[row][col];
    }
    @Override
    public boolean isFull(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        int cell_id = row*myGrid.length + col;
        return myFinder.connected(cell_id, VTOP);
    }

    @Override
    public boolean percolates() {
        return myFinder.connected(VTOP, VBOTTOM);
    }
    @Override
    public int numberOfOpenSites() {
        return myOpenCount;
    }
    private boolean inBounds(int row, int col) {
        if (row < 0 || row >= myGrid.length) return false;
        if (col < 0 || col >= myGrid[0].length) return false;
        return true;
    }
}
