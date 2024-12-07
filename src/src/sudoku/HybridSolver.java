import java.util.*;

// Solver class using a hybrid BFS-DLS approach for Sudoku
public class HybridSolver {
    public List<int[][]> solveWithHybrid(int[][] puzzle) {
        Queue<int[][]> queue = new LinkedList<>();
        List<int[][]> solutions = new ArrayList<>();
        queue.add(copyGrid(puzzle));

        int depthLimit = 2; // Initial depth limit for DLS
        int bfsLevel = 0; // Tracks BFS levels for dynamic depth adjustment

        System.out.println("Starting Hybrid BFS-DLS...");

        while (!queue.isEmpty()) {
            int size = queue.size(); // Nodes at the current BFS level
            System.out.println("BFS Level: " + bfsLevel + ", Depth Limit: " + depthLimit);

            for (int i = 0; i < size; i++) {
                int[][] current = queue.poll();

                if (isSolved(current)) {
                    solutions.add(copyGrid(current));
                    System.out.println("Solution found with Hybrid BFS-DLS:");
                    Utils.printSudoku(current);
                    continue;
                }

                int[] emptyCell = findEmptyCell(current);
                if (emptyCell == null) continue;

                int row = emptyCell[0], col = emptyCell[1];

                for (int num = 1; num <= 9; num++) {
                    if (isValidPlacement(current, row, col, num)) {
                        int[][] next = copyGrid(current);
                        next[row][col] = num;

                        if (dlsHelper(next, depthLimit, 0, solutions)) continue;

                        queue.add(next);
                    }
                }
            }

            // Increment depth limit as BFS progresses
            bfsLevel++;
            depthLimit += 2;
        }

        if (solutions.isEmpty()) {
            System.out.println("No solution found with Hybrid BFS-DLS.");
        } else {
            System.out.println("Total Solutions Found with Hybrid BFS-DLS: " + solutions.size());
        }

        return solutions;
    }

    private boolean dlsHelper(int[][] puzzle, int depthLimit, int depth, List<int[][]> solutions) {
        if (depth > depthLimit) return false;

        if (isSolved(puzzle)) {
            solutions.add(copyGrid(puzzle));
            return true;
        }

        int[] emptyCell = findEmptyCell(puzzle);
        if (emptyCell == null) return false;

        int row = emptyCell[0], col = emptyCell[1];
        boolean found = false;

        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(puzzle, row, col, num)) {
                puzzle[row][col] = num;
                if (dlsHelper(puzzle, depthLimit, depth + 1, solutions)) found = true;
                puzzle[row][col] = 0; // Backtrack
            }
        }
        return found;
    }

    private boolean isSolved(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) return false;
            }
        }
        return true;
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
}