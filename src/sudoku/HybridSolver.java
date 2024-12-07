package sudoku;

import java.util.*;

/**
 * HybridSolver.java
 * 
 * This class implements a hybrid algorithm combining Breadth-First Search (BFS)
 * and Depth-Limited Search (DLS) to solve Sudoku puzzles. The approach is inspired by:
 * 
 * Chanchal Khemani, Jay Doshi, Juhi Duseja, Krapi Shah, Sandeep Udmale, Vijay Sambhe.
 * "Solving Rubik's Cube Using Graph Theory", ICCI-2017.
 * DOI: 10.1007/978-981-13-1132-1_24
 * 
 * Adjustments made to adapt the algorithm for Sudoku solving:
 * - BFS explores all possible paths level-by-level.
 * - DLS ensures depth-limited exploration to manage memory efficiently.
 * - Hybrid transition dynamically increases depth limit after each BFS level.
 * 
 * This implementation ensures efficient memory usage while retaining completeness.
 */
 
 public class HybridSolver {
     private final BFsSolver bfsSolver; // Handles broad-level exploration
     private final DLsSolver dlsSolver; // Handles focused depth exploration
 
     // Constructor to set up BFS and DLS solvers
     public HybridSolver() {
         bfsSolver = new BFsSolver();
         dlsSolver = new DLsSolver();
     }
 
     /**
      * Solves a Sudoku puzzle using a hybrid of BFS and DLS.
      * 
      * @param puzzle The puzzle we’re solving.
      * @return A list of solutions we found.
      */
     public List<int[][]> solveWithHybrid(int[][] puzzle) {
         Queue<int[][]> queue = new LinkedList<>(); // BFS queue for managing states
         List<int[][]> solutions = new ArrayList<>(); // Store any valid solutions
         queue.add(copyGrid(puzzle)); // Add the starting puzzle to the queue
 
         int depthLimit = 2;  // Start with a small depth limit for DLS
         int bfsLevel = 0;    // Tracks which BFS level we’re on
         int iterations = 0;  // Count how many states we’ve explored
         boolean solutionFound = false;
 
         System.out.println("\n--- Starting Hybrid BFS-DLS ---");
 
         // Main loop for BFS
         while (!queue.isEmpty()) {
             int size = queue.size(); // How many nodes we’re dealing with at this level
             System.out.printf("BFS Level: %-3d | Depth Limit: %-3d | Nodes to process: %-3d%n", bfsLevel, depthLimit, size);
 
             for (int i = 0; i < size; i++) {
                 iterations++;
                 int[][] current = queue.poll(); // Grab the next puzzle state from the queue
 
                 // Check if this state is a solved puzzle
                 if (bfsSolver.isSolved(current)) {
                     solutions.add(copyGrid(current)); // Save the solution
                     solutionFound = true;
                     System.out.println("\nSolution found at BFS Level " + bfsLevel + ":");
                     Utils.printSudoku(current); // Print the solution
                     continue; // Keep looking for more solutions
                 }
 
                 // Find the next empty spot in the puzzle
                 int[] emptyCell = bfsSolver.findEmptyCell(current);
                 if (emptyCell == null) continue; // If no empty spots, skip this state
 
                 int row = emptyCell[0], col = emptyCell[1];
 
                 // Try numbers 1-9 in the empty spot
                 for (int num = 1; num <= 9; num++) {
                     if (bfsSolver.isValidPlacement(current, row, col, num)) {
                         int[][] next = copyGrid(current); // Make a copy of the state
                         next[row][col] = num; // Place the number in the puzzle
 
                         // Use DLS to dig deeper into promising paths
                         if (dlsHelper(next, depthLimit, 0, solutions, iterations)) continue;
 
                         // If DLS doesn’t find a solution, add this state to the BFS queue
                         queue.add(next);
                     }
                 }
             }
 
             // Increase the depth limit and BFS level to keep exploring further
             bfsLevel++;
             depthLimit += 2; // Let DLS go deeper as BFS progresses
         }
 
         // Print results
         if (solutionFound) {
             System.out.println("\nTotal Solutions Found with Hybrid BFS-DLS: " + solutions.size());
         } else {
             System.out.println("\nNo solution found with Hybrid BFS-DLS.");
         }
         System.out.println("Hybrid BFS-DLS completed in " + iterations + " iterations.\n");
         return solutions;
     }
 
     /**
      * Recursive helper method for DLS.
      * 
      * @param puzzle Current state of the puzzle.
      * @param depthLimit Max depth DLS is allowed to go.
      * @param depth Current depth in the search.
      * @param solutions List of valid solutions found so far.
      * @param iterations Tracks how many states we’ve explored.
      * @return True if a solution is found, false otherwise.
      */
     private boolean dlsHelper(int[][] puzzle, int depthLimit, int depth, List<int[][]> solutions, int iterations) {
         iterations++;
 
         // If we’ve hit the depth limit, stop recursion
         if (depth > depthLimit) return false;
 
         // Check if this state solves the puzzle
         if (dlsSolver.isSolved(puzzle)) {
             solutions.add(copyGrid(puzzle)); // Save the solution
             System.out.println("\nSolution found by DLS at depth: " + depth);
             Utils.printSudoku(puzzle); // Print the solution
             return true;
         }
 
         // Find the next empty spot
         int[] emptyCell = dlsSolver.findEmptyCell(puzzle);
         if (emptyCell == null) return false;
 
         int row = emptyCell[0], col = emptyCell[1];
         boolean found = false;
 
         // Try numbers 1-9 in the empty spot
         for (int num = 1; num <= 9; num++) {
             if (dlsSolver.isValidPlacement(puzzle, row, col, num)) {
                 puzzle[row][col] = num; // Place the number
                 // Go deeper into the search
                 if (dlsHelper(puzzle, depthLimit, depth + 1, solutions, iterations)) found = true;
                 puzzle[row][col] = 0; // Backtrack to try the next number
             }
         }
         return found;
     }
 
     /**
      * Makes a deep copy of the puzzle grid.
      * 
      * @param grid The original puzzle grid.
      * @return A copy of the grid.
      */
     private int[][] copyGrid(int[][] grid) {
         int[][] copy = new int[grid.length][grid[0].length];
         for (int row = 0; row < grid.length; row++) {
             System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
         }
         return copy;
     }
 }