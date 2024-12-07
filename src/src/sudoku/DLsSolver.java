package sudoku;
import java.util.*;

// Solver class using Depth-Limited Search (DLS) for Sudoku
public class DLsSolver {
    public List<int[][]> solveWithDLS(int[][] puzzle, int depthLimit) {
        List<int[][]> solutions = new ArrayList<>();
        dlsHelper(puzzle, depthLimit, 0, solutions);
        if (!solutions.isEmpty()) {
            System.out.println("Total DLS Solutions Found: " + solutions.size());
        } else {
            System.out.println("No solution found with DLS.");
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