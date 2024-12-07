/**
 * Base class to integrate all solvers for solving Sudoku puzzles.
 * This class centralizes BFS, DLS, and Hybrid solvers, ensuring modularity and clean design.
 *
 * References:
 * - "Comparison Analysis of Breadth First Search and Depth Limited Search Algorithms in Sudoku Game"
 *   (used as inspiration for implementing BFS and DLS for solving puzzles).
 */
package sudoku;

import java.util.List;

/**
 * SudokuSolverBase acts as a bridge between the different solving algorithms.
 * It provides methods to solve a given Sudoku puzzle using BFS, DLS, or Hybrid approaches.
 * This design avoids redundancy by delegating tasks to specialized solver classes.
 */
public class SudokuSolverBase {
    private final BFsSolver bfsSolver;   // Solver using Breadth-First Search
    private final DLsSolver dlsSolver;  // Solver using Depth-Limited Search
    private final HybridSolver hybridSolver; // Solver combining BFS and DLS strategies

    /**
     * Constructor initializes instances of all solvers.
     * Each solver operates independently and is encapsulated for modular use.
     */
    public SudokuSolverBase() {
        bfsSolver = new BFsSolver();
        dlsSolver = new DLsSolver();
        hybridSolver = new HybridSolver();
    }

    /**
     * Solves a Sudoku puzzle using Breadth-First Search (BFS).
     * BFS systematically explores all possible solutions level by level.
     *
     * @param puzzle the Sudoku puzzle to solve
     * @return a list of all valid solutions found using BFS
     */
    public List<int[][]> solveWithBFS(int[][] puzzle) {
        return bfsSolver.solveWithBFS(puzzle);
    }

    /**
     * Solves a Sudoku puzzle using Depth-Limited Search (DLS).
     * DLS explores possible solutions up to a specified depth, allowing for controlled recursion.
     *
     * @param puzzle     the Sudoku puzzle to solve
     * @param depthLimit the maximum depth to explore in the search
     * @return a list of all valid solutions found using DLS
     */
    public List<int[][]> solveWithDLS(int[][] puzzle, int depthLimit) {
        return dlsSolver.solveWithDLS(puzzle, depthLimit);
    }

    /**
     * Solves a Sudoku puzzle using a Hybrid BFS-DLS approach.
     * Combines the breadth-first traversal of BFS with the depth-limited exploration of DLS
     * for more efficient solving.
     *
     * @param puzzle the Sudoku puzzle to solve
     * @return a list of all valid solutions found using the Hybrid approach
     */
    public List<int[][]> solveWithHybrid(int[][] puzzle) {
        return hybridSolver.solveWithHybrid(puzzle);
    }
}