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
        boolean solutionFound = false;

        while (!queue.isEmpty()) {
            iterations++; // Increment iteration counter
            int[][] current = queue.poll(); // Get the current puzzle state from the queue

            if (isSolved(current)) {
                // If the current state is a valid solution, add it to the solutions list
                solutions.add(copyGrid(current));
                solutionFound = true;
                continue; // Continue to explore other possibilities
            }

            int[] emptyCell = findEmptyCell(current); // Find the next empty cell
            if (emptyCell == null) continue; // No empty cell means invalid state, skip it

            int row = emptyCell[0], col = emptyCell[1];

            // Try placing numbers 1-9 in the empty cell
            for (int num = 1; num <= 9; num++) {
                if (isValidPlacement(current, row, col, num)) {
                    int[][] next = copyGrid(current); // Create a copy to avoid modifying the original state
                    next[row][col] = num; // Place the number
                    queue.add(next); // Add the new state to the queue
                }
            }
        }

        // Reporting the results
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

    // Make this method public so it can be accessed outside
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