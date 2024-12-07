package sudoku;

import java.util.*;

// DLS Solver class for Sudoku puzzles
// Citation: Concepts inspired by "Comparison Analysis of Breadth-First Search and Depth-Limited Search Algorithms in Sudoku Game"

public class DLsSolver {

    // Method to solve a Sudoku puzzle using Depth-Limited Search
    public List<int[][]> solveWithDLS(int[][] puzzle, int depthLimit) {
        List<int[][]> solutions = new ArrayList<>(); // Store all valid solutions
        int iterations = 0; // Track the number of iterations

        // Depth-Limited Search: Recursively explore up to the depth limit
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

        // If the depth exceeds the limit, stop the recursion and backtrack
        if (depth > depthLimit) return iterationCount;

        // Check if the puzzle is solved
        if (isSolved(puzzle)) {
            // Add the solution to the list of valid solutions
            solutions.add(copyGrid(puzzle));
            System.out.println("Solution found at depth: " + depth);
            return iterationCount;
        }

        // Locate the next empty cell in the grid
        int[] emptyCell = findEmptyCell(puzzle);
        if (emptyCell == null) return iterationCount; // No empty cells, backtrack

        int row = emptyCell[0], col = emptyCell[1];

        // Try placing numbers 1-9 in the empty cell
        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(puzzle, row, col, num)) {
                puzzle[row][col] = num; // Place the number in the grid
                // Recur to explore deeper states, incrementing depth
                iterationCount += dlsHelper(puzzle, depthLimit, depth + 1, solutions);
                puzzle[row][col] = 0; // Backtrack by resetting the cell
            }
        }
        return iterationCount;
    }

    // Check if the Sudoku grid is solved
    // A solved grid contains no empty cells (no cells with a value of 0)
    public boolean isSolved(int[][] grid) {
        for (int[] row : grid) {
            for (int cell : row) {
                if (cell == 0) return false; // If any cell is empty, the grid is not solved
            }
        }
        return true; // All cells are filled, grid is solved
    }

    // Locate the next empty cell in the grid
    // Returns the coordinates (row, col) of the empty cell, or null if none are found
    public int[] findEmptyCell(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) return new int[]{row, col}; // Return the first empty cell found
            }
        }
        return null; // No empty cells found
    }

    // Validate if placing a number in a cell is valid
    // Valid placement means no duplicate numbers in the row, column, or 3x3 subgrid
    public boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            // Check for duplicates in the row and column
            if (grid[row][i] == num || grid[i][col] == num) return false;

            // Check for duplicates in the 3x3 subgrid
            int subgridRow = (row / 3) * 3 + i / 3; // Calculate row index within the subgrid
            int subgridCol = (col / 3) * 3 + i % 3; // Calculate column index within the subgrid
            if (grid[subgridRow][subgridCol] == num) return false;
        }
        return true; // Placement is valid
    }

    // Create a deep copy of the grid
    // This ensures the original puzzle state remains unchanged during recursion
    public int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
        }
        return copy;
    }
}