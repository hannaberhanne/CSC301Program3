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
    private final BFsSolver bfsSolver;
    private final DLsSolver dlsSolver;

    // Constructor that reuses solvers
    public HybridSolver() {
        bfsSolver = new BFsSolver();
        dlsSolver = new DLsSolver();
    }

    /**
     * Solves the given Sudoku puzzle using a hybrid BFS-DLS approach.
     *
     * @param puzzle The 9x9 Sudoku puzzle to solve.
     * @return A list of solutions found.
     */
    public List<int[][]> solveWithHybrid(int[][] puzzle) {
        Queue<int[][]> queue = new LinkedList<>();
        List<int[][]> solutions = new ArrayList<>();
        queue.add(copyGrid(puzzle));

        int depthLimit = 2;  // Initial depth limit for DLS
        int bfsLevel = 0;    // Tracks BFS levels
        int iterations = 0;  // Tracks the number of iterations across BFS and DLS
        boolean solutionFound = false;

        System.out.println("\n--- Starting Hybrid BFS-DLS ---");

        while (!queue.isEmpty()) {
            int size = queue.size();  // Nodes at the current BFS level
            // Neatly format the BFS level and depth limit information
            System.out.printf("BFS Level: %-3d | Depth Limit: %-3d | Nodes to process: %-3d%n", bfsLevel, depthLimit, size);

            for (int i = 0; i < size; i++) {
                iterations++;  // Increment iteration count
                int[][] current = queue.poll();

                // Check if the current puzzle state is solved
                if (bfsSolver.isSolved(current)) {
                    solutions.add(copyGrid(current));
                    solutionFound = true;
                    System.out.println("\nSolution found with Hybrid BFS-DLS at BFS Level " + bfsLevel + ":");
                    Utils.printSudoku(current);
                    continue;  // Continue to find other solutions
                }

                // Find the next empty cell to explore
                int[] emptyCell = bfsSolver.findEmptyCell(current);
                if (emptyCell == null) continue;

                int row = emptyCell[0], col = emptyCell[1];

                // Try placing numbers 1-9 in the empty cell
                for (int num = 1; num <= 9; num++) {
                    if (bfsSolver.isValidPlacement(current, row, col, num)) {
                        int[][] next = copyGrid(current);
                        next[row][col] = num;

                        // Use DLS to explore deeper paths
                        if (dlsHelper(next, depthLimit, 0, solutions, iterations)) continue;

                        // Add to the BFS queue for further exploration
                        queue.add(next);
                    }
                }
            }

            // Increment depth limit and BFS level as BFS progresses
            bfsLevel++;
            depthLimit += 2;  // Increase depth limit after each BFS level
        }

        // Log the results
        if (solutionFound) {
            System.out.println("\nTotal Solutions Found with Hybrid BFS-DLS: " + solutions.size());
        } else {
            System.out.println("\nNo solution found with Hybrid BFS-DLS.");
        }
        System.out.println("Hybrid BFS-DLS completed in " + iterations + " iterations.\n");
        return solutions;
    }

    /**
     * Recursive helper method for Depth-Limited Search (DLS).
     *
     * @param puzzle The current Sudoku puzzle state.
     * @param depthLimit The maximum depth allowed.
     * @param depth The current depth in the search.
     * @param solutions The list to store found solutions.
     * @param iterations Tracks the number of iterations.
     * @return True if a solution is found, false otherwise.
     */
    private boolean dlsHelper(int[][] puzzle, int depthLimit, int depth, List<int[][]> solutions, int iterations) {
        iterations++;  // Increment iteration count

        if (depth > depthLimit) return false;  // Stop if depth exceeds limit

        // Check if the current puzzle state is solved
        if (dlsSolver.isSolved(puzzle)) {
            solutions.add(copyGrid(puzzle));
            System.out.println("\nSolution found by DLS at depth: " + depth);
            Utils.printSudoku(puzzle);
            return true;
        }

        // Find the next empty cell
        int[] emptyCell = dlsSolver.findEmptyCell(puzzle);
        if (emptyCell == null) return false;

        int row = emptyCell[0], col = emptyCell[1];
        boolean found = false;

        // Try placing numbers 1-9 in the empty cell
        for (int num = 1; num <= 9; num++) {
            if (dlsSolver.isValidPlacement(puzzle, row, col, num)) {
                puzzle[row][col] = num;
                if (dlsHelper(puzzle, depthLimit, depth + 1, solutions, iterations)) found = true;
                puzzle[row][col] = 0;  // Backtrack
            }
        }
        return found;
    }

    /**
     * Create a deep copy of the grid.
     * 
     * @param grid The Sudoku grid to copy.
     * @return A new copy of the grid.
     */
    private int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
        }
        return copy;
    }
}