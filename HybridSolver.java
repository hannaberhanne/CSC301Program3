import java.util.*;

// Solves Sudoku using a Hybrid BFS-DLS approach
public class HybridSolver {
    public List<int[][]> solveWithHybrid(int[][] puzzle) {
        Queue<int[][]> queue = new LinkedList<>();
        List<int[][]> solutions = new ArrayList<>();
        queue.add(copyGrid(puzzle));

        int depthLimit = 2; // Initial depth limit for DLS
        while (!queue.isEmpty()) {
            int[][] current = queue.poll();

            if (isSolved(current)) {
                solutions.add(copyGrid(current));
                continue;
            }

            int[] emptyCell = findEmptyCell(current);
            if (emptyCell == null) continue;

            for (int num = 1; num <= 9; num++) {
                if (isValidPlacement(current, emptyCell[0], emptyCell[1], num)) {
                    int[][] next = copyGrid(current);
                    next[emptyCell[0]][emptyCell[1]] = num;

                    if (dlsHelper(next, depthLimit, 0)) continue;
                    queue.add(next);
                }
            }
            depthLimit++;
        }

        if (solutions.isEmpty()) System.out.println("No solution found with Hybrid BFS-DLS.");
        return solutions;
    }

    private boolean dlsHelper(int[][] puzzle, int depthLimit, int depth) {
        if (depth > depthLimit) return false;
        if (isSolved(puzzle)) return true;

        int[] emptyCell = findEmptyCell(puzzle);
        if (emptyCell == null) return false;

        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(puzzle, emptyCell[0], emptyCell[1], num)) {
                puzzle[emptyCell[0]][emptyCell[1]] = num;
                if (dlsHelper(puzzle, depthLimit, depth + 1)) return true;
                puzzle[emptyCell[0]][emptyCell[1]] = 0; // Backtrack
            }
        }
        return false;
    }

    private int[] findEmptyCell(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) return new int[]{row, col};
            }
        }
        return null;
    }

    private boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
            int subgridRow = (row / 3) * 3 + i / 3;
            int subgridCol = (col / 3) * 3 + i % 3;
            if (grid[subgridRow][subgridCol] == num) return false;
        }
        return true;
    }

    private int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
        }
        return copy;
    }

    private boolean isSolved(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) return false;
            }
        }
        return true;
    }
}