import java.util.*;

public class SudokuSolver {

    // Solve Sudoku using Breadth-First Search (BFS)
    public List<int[][]> solveWithBFS(int[][] puzzle) {
        Queue<int[][]> queue = new LinkedList<>();
        List<int[][]> solutions = new ArrayList<>();
        queue.add(copyGrid(puzzle));

        while (!queue.isEmpty()) {
            int[][] current = queue.poll();

            if (isSolved(current)) {
                solutions.add(copyGrid(current));
                continue;
            }

            int[] emptyCell = findEmptyCell(current);
            if (emptyCell == null) continue;

            int row = emptyCell[0], col = emptyCell[1];

            for (int num = 1; num <= 9; num++) {
                if (isValidPlacement(current, row, col, num)) {
                    int[][] next = copyGrid(current);
                    next[row][col] = num;
                    queue.add(next);
                }
            }
        }
        return solutions;
    }

    // Solve Sudoku using Depth-Limited Search (DLS)
    public List<int[][]> solveWithDLS(int[][] puzzle, int depthLimit) {
        List<int[][]> solutions = new ArrayList<>();
        dlsHelper(puzzle, depthLimit, 0, solutions);
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

        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(puzzle, row, col, num)) {
                puzzle[row][col] = num;
                dlsHelper(puzzle, depthLimit, depth + 1, solutions);
                puzzle[row][col] = 0;
            }
        }
        return false;
    }

    // Solve Sudoku using Hybrid BFS-DLS
    public List<int[][]> solveWithHybrid(int[][] puzzle) {
        Queue<int[][]> queue = new LinkedList<>();
        List<int[][]> solutions = new ArrayList<>();
        queue.add(copyGrid(puzzle));

        int depthLimit = 2;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[][] current = queue.poll();

                if (isSolved(current)) {
                    solutions.add(copyGrid(current));
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
            depthLimit += 2;
        }
        return solutions;
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
