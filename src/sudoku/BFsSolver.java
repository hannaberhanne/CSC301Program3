package sudoku;

import java.util.*;

// BFS Solver class for Sudoku puzzles
// Citation: Adapted from concepts in the paper "Comparison Analysis of Breadth-First Search and Depth-Limited Search Algorithms in Sudoku Game"

public class BFsSolver {

    // Method to solve a Sudoku puzzle using BFS
    public List<int[][]> solveWithBFS(int[][] puzzle) {
        Queue<int[][]> queue = new LinkedList<>(); // Queue for BFS traversal
        List<int[][]> solutions = new ArrayList<>(); // To store all valid solutions
        queue.add(copyGrid(puzzle)); // Start with the initial puzzle state
        int iterations = 0; // Track how many states we explored
        boolean solutionFound = false; // Flag to indicate if a solution has been found

        // BFS: Explore states level by level
        while (!queue.isEmpty()) {
            iterations++; // Increment iteration counter
            int[][] current = queue.poll(); // Get the current puzzle state from the queue

            // Check if the current state is a valid solution
            if (isSolved(current)) {
                // If valid, add the solved puzzle to the solutions list
                solutions.add(copyGrid(current));
                solutionFound = true;
                continue; // Continue exploring for additional solutions
            }

            // Locate the next empty cell in the puzzle grid
            int[] emptyCell = findEmptyCell(current); 
            if (emptyCell == null) continue; // If no empty cells are found, skip this state

            int row = emptyCell[0], col = emptyCell[1];

            // Try placing numbers 1-9 in the empty cell
            for (int num = 1; num <= 9; num++) {
                if (isValidPlacement(current, row, col, num)) {
                    int[][] next = copyGrid(current); // Create a copy of the current state to preserve immutability
                    next[row][col] = num; // Place the number in the empty cell
                    queue.add(next); // Add the modified state to the queue for further exploration
                }
            }
        }

        // Reporting the results of BFS
        if (solutionFound) {
            System.out.println("BFS completed successfully!");
            System.out.println("Total BFS Solutions Found: " + solutions.size());
            for (int i = 0; i < solutions.size(); i++) {
                System.out.println("Solution " + (i + 1) + ":");
                Utils.printSudoku(solutions.get(i)); // Print each solution
            }
        } else {
            System.out.println("No solution found with BFS.");
        }

        // Log the number of iterations it took to complete BFS
        System.out.println("BFS completed in " + iterations + " iterations.");
        return solutions;
    }

    // Check if a Sudoku grid is solved
    // A solved grid must have no empty cells (no cells with a value of 0)
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

    // Check if placing a number in a cell is valid
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

    // Helper method to create a deep copy of the grid
    // This ensures that the original grid remains unchanged during BFS exploration
    public int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
        }
        return copy;
    }
}