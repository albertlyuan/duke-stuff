import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast{

    public PercolationBFS(int n) {
        super(n);
    }

    @Override
    protected void dfs(int row, int col) {
        if (! inBounds(row,col)) {
            throw new IndexOutOfBoundsException(
                    String.format("(%d,%d) not in bounds", row,col));
        }
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{row,col});
        while (!q.isEmpty()){
            int[] c = q.remove();
            if (myGrid[c[0]][c[1]] == FULL){
                continue;
            }
            myGrid[c[0]][c[1]] = FULL;
            if (c[0]+1 < myGrid.length && myGrid[c[0]+1][c[1]] == OPEN) {
                q.add(new int[] {c[0]+1, c[1]});
            }
            if (c[0]-1 >= 0 && myGrid[c[0]-1][c[1]] == OPEN) {
                q.add(new int[] {c[0]-1, c[1]});
            }
            if (c[1]+1 < myGrid.length && myGrid[c[0]][c[1]+1] == OPEN) {
                q.add(new int[] {c[0], c[1]+1});
            }
            if (c[1]-1 >= 0 && myGrid[c[0]][c[1]-1] == OPEN) {
                q.add(new int[] {c[0], c[1]-1});
            }
        }
    }
}
