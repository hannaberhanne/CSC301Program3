package sudoku;

import java.util.*;

// DLS Solver class for Sudoku puzzles
// Citation: Concepts inspired by "Comparison Analysis of Breadth-First Search and Depth-Limited Search Algorithms in Sudoku Game"

public class DLsSolver {

    // Method to solve a Sudoku puzzle using Depth-Limited Search
    public List<int[][]> solveWithDLS(int[][] puzzle, int depthLimit) {
        List<int[][]> solutions = new ArrayList<>(); // Store all valid solutions
        int iterations = 0; // Track the number of iterations

        // Use the helper method to perform the actual DLS
        iterations += dlsHelper(puzzle, depthLimit, 0, solutions);

        // Reporting the results
        if (solutions.isEmpty()) {
            System.out.println("No solution found with DLS.");
        } else {
            System.out.println("Total DLS Solutions Found: " + solutions.size());
            for (int i = 0; i < solutions.size(); i++) {
                System.out.println("\nSolution " + (i + 1) + ":");
                Utils.printSudoku(solutions.get(i)); // Print each solution
            }
        }

        System.out.println("\nDLS completed with " + iterations + " iterations.");
        return solutions;
    }

    // Recursive helper method for Depth-Limited Search
    public int dlsHelper(int[][] puzzle, int depthLimit, int depth, List<int[][]> solutions) {
        int iterationCount = 1; // Each recursive call counts as one iteration

        // If depth exceeds the limit, backtrack
        if (depth > depthLimit) return iterationCount;

        // If the puzzle is solved, add it to the solutions list
        if (isSolved(puzzle)) {
            solutions.add(copyGrid(puzzle));
            System.out.println("Solution found at depth: " + depth);
            return iterationCount;
        }

        // Find the next empty cell in the puzzle
        int[] emptyCell = findEmptyCell(puzzle);
        if (emptyCell == null) return iterationCount; // No empty cells, backtrack

        int row = emptyCell[0], col = emptyCell[1];

        // Try placing numbers 1-9 in the empty cell
        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(puzzle, row, col, num)) {
                puzzle[row][col] = num; // Place the number
                iterationCount += dlsHelper(puzzle, depthLimit, depth + 1, solutions); // Recur deeper
                puzzle[row][col] = 0; // Backtrack to try the next number
            }
        }
        return iterationCount;
    }

    // Make this method public
    public boolean isSolved(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) return false; // If any cell is empty, it's not solved
            }
        }
        return true;
    }

    // Make this method public
    public int[] findEmptyCell(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) return new int[]{row, col};
            }
        }
        return null; // No empty cells found
    }

    // Make this method public
    public boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false; // Check row and column

            // Check the 3x3 subgrid
            int subgridRow = (row / 3) * 3 + i / 3;
            int subgridCol = (col / 3) * 3 + i % 3;
            if (grid[subgridRow][subgridCol] == num) return false;
        }
        return true;
    }

    // Helper method to create a deep copy of the grid
    public int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
        }
        return copy;
    }
}