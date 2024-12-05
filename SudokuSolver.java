/*
 * Citation:
 * Comparison Analysis of Breadth First Search and Depth-Limited Search Algorithms in Sudoku Game.
 * Retrieved from: [Add the URL or DOI]
 * This implementation builds on the ideas from the cited paper to explore different Sudoku-solving approaches.
 */

 import java.util.*;

 public class SudokuSolver {
     private Graph graph;
 
     public SudokuSolver() {
         graph = new Graph();
         graph.buildSudokuGraph(9); // Setting up the graph for a 9x9 Sudoku grid
     }
 
     // Solve Sudoku using Breadth-First Search (BFS)
     public List<int[][]> solveWithBFS(int[][] puzzle) {
         Queue<int[][]> queue = new LinkedList<>();
         List<int[][]> solutions = new ArrayList<>(); // To store all valid solutions
         queue.add(copyGrid(puzzle));
         boolean solutionFound = false;
 
         while (!queue.isEmpty()) {
             int[][] current = queue.poll();
 
             // Check if the current grid is solved
             if (isSolved(current)) {
                 System.out.println("Solution found with BFS:");
                 Utils.printSudoku(current);
                 solutions.add(copyGrid(current)); // Keep track of the solution
                 solutionFound = true; 
                 continue; // Keep searching for more solutions
             }
 
             // Find the first empty cell in the grid
             int[] emptyCell = findEmptyCell(current);
             if (emptyCell == null) continue; // If no empty cell is found, skip this grid
 
             int row = emptyCell[0], col = emptyCell[1];
 
             // Try placing numbers 1-9 in the empty cell
             for (int num = 1; num <= 9; num++) {
                 if (isValidPlacement(current, row, col, num)) {
                     int[][] next = copyGrid(current);
                     next[row][col] = num;
                     queue.add(next);
                 }
             }
         }
 
         if (!solutionFound) {
             System.out.println("No solution found with BFS.");
         } else {
             System.out.println("Total Solutions Found with BFS: " + solutions.size());
         }
 
         return solutions; // Return all the solutions found
     }
 
     // Solve Sudoku using Depth-Limited Search (DLS)
     public List<int[][]> solveWithDLS(int[][] puzzle, int depthLimit) {
         List<int[][]> solutions = new ArrayList<>(); 
         boolean solutionFound = dlsHelper(puzzle, depthLimit, 0, solutions);
 
         if (!solutionFound) {
             System.out.println("No solution found with DLS.");
         } else {
             System.out.println("Solutions found with DLS:");
             for (int i = 0; i < solutions.size(); i++) {
                 System.out.println("Solution " + (i + 1) + ":");
                 Utils.printSudoku(solutions.get(i));
             }
             System.out.println("Total Solutions Found with DLS: " + solutions.size());
         }
 
         return solutions;
     }
 
     // Recursive helper for Depth-Limited Search
     private boolean dlsHelper(int[][] puzzle, int depthLimit, int depth, List<int[][]> solutions) {
         if (depth > depthLimit) return false; // Stop if we exceed the depth limit
 
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
                 if (dlsHelper(puzzle, depthLimit, depth + 1, solutions)) {
                     found = true;
                 }
                 puzzle[row][col] = 0; // Backtrack
             }
         }
 
         return found;
     }
 
     // Solve Sudoku using Hybrid BFS-DLS
     public List<int[][]> solveWithHybrid(int[][] puzzle) {
         Queue<int[][]> queue = new LinkedList<>();
         List<int[][]> solutions = new ArrayList<>();
         queue.add(copyGrid(puzzle));
 
         int depthLimit = 2; // Start with a small depth limit
         int bfsLevel = 0; 
 
         System.out.println("Starting Hybrid BFS-DLS...");
 
         while (!queue.isEmpty()) {
             int size = queue.size(); 
             System.out.println("BFS Level: " + bfsLevel + ", Depth Limit: " + depthLimit);
 
             for (int i = 0; i < size; i++) {
                 int[][] current = queue.poll();
 
                 if (isSolved(current)) {
                     System.out.println("Solution found with Hybrid BFS-DLS:");
                     Utils.printSudoku(current);
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
 
     // Helper to check if the puzzle is solved
     private boolean isSolved(int[][] grid) {
         for (int[] row : grid) {
             for (int cell : row) {
                 if (cell == 0) return false;
             }
         }
         return true;
     }
 
     // Finds the first empty cell in the grid
     private int[] findEmptyCell(int[][] grid) {
         for (int row = 0; row < grid.length; row++) {
             for (int col = 0; col < grid[row].length; col++) {
                 if (grid[row][col] == 0) return new int[]{row, col};
             }
         }
         return null;
     }
 
     // Validates if a number can be placed in a specific cell
     private boolean isValidPlacement(int[][] grid, int row, int col, int num) {
         for (int i = 0; i < 9; i++) {
             if (grid[row][i] == num || grid[i][col] == num) return false;
 
             int subgridRow = (row / 3) * 3 + i / 3;
             int subgridCol = (col / 3) * 3 + i % 3;
             if (grid[subgridRow][subgridCol] == num) return false;
         }
         return true;
     }
 
     // Copies the grid to avoid modifying the original
     private int[][] copyGrid(int[][] grid) {
         int[][] copy = new int[grid.length][grid[0].length];
         for (int row = 0; row < grid.length; row++) {
             System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
         }
         return copy;
     }
 }