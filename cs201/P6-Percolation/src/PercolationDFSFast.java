public class PercolationDFSFast extends PercolationDFS{
    public PercolationDFSFast(int n) {
        super(n);
    }

    @Override
    protected void updateOnOpen(int row, int col) {
        if (row == 0) {
            dfs(row, col);
        }else if (row+1 < myGrid.length && myGrid[row+1][col] == FULL) {
            dfs(row, col);
        }else if (row-1 >= 0 && myGrid[row-1][col] == FULL) {
            dfs(row, col);
        }else if (col+1 < myGrid.length && myGrid[row][col+1] == FULL) {
            dfs(row, col);
        }else if (col-1 >= 0 && myGrid[row][col-1] == FULL) {
            dfs(row, col);
        }
    }


}
