/*
 * Program: Sudoku Solver
 * Authors: Hanna Berhane, Julius Smith, Anthony Eccleston
 * Date: 11/20/2024
 * Course: CSC 301 - Algorithms and Data Structures
 * 
 * Description:
 * This program solves Sudoku puzzles using three approaches: Breadth-First Search (BFS), Depth-Limited Search (DLS),
 * and a Hybrid BFS-DLS algorithm with dynamic depth adjustment. The implementation uses a graph data structure to
 * represent the puzzle constraints (rows, columns, and subgrids).
 * 
 * Citation:
 * Comparison Analysis of Breadth First Search and Depth-Limited Search Algorithms in Sudoku Game.
 * Retrieved from [https://www.researchgate.net/publication/358642884_Comparison_Analysis_of_Breadth_First_Search_and_Depth_Limited_Search_Algorithms_in_Sudoku_Game].
 */

 import java.util.*;
 import java.io.*;
 
 public class Main {
     public static void main(String[] args) {
         System.out.println("Welcome to the Sudoku Solver!");
         
         try {
             // Step 1: Load puzzles from the file
             System.out.println("Loading puzzles from file...");
             List<int[][]> puzzles = PuzzleLoader.loadPuzzles("puzzles.txt");
 
             // Step 2: Print all puzzles for verification
             for (int i = 0; i < puzzles.size(); i++) {
                 System.out.println("Puzzle " + (i + 1) + ":");
                 Utils.printSudoku(puzzles.get(i));
                 System.out.println();
             }
 
             // Step 3: Solve each puzzle using BFS, DLS, and Hybrid BFS-DLS
             SudokuSolver solver = new SudokuSolver();
 
             for (int i = 0; i < puzzles.size(); i++) {
                 int[][] puzzle = puzzles.get(i);
                 System.out.println("\n--- Solving Puzzle " + (i + 1) + " ---");
 
                 // Solve with BFS
                 System.out.println("\nAttempting BFS...");
                 List<int[][]> bfsSolutions = solver.solveWithBFS(puzzle);
                 if (bfsSolutions.isEmpty()) {
                     System.out.println("BFS couldn't solve the puzzle.");
                 }
 
                 // Solve with DLS
                 System.out.println("\nAttempting DLS...");
                 List<int[][]> dlsSolutions = solver.solveWithDLS(puzzle, 50); // Example depth limit
                 if (dlsSolutions.isEmpty()) {
                     System.out.println("DLS couldn't solve the puzzle.");
                 }
 
                 // Solve with Hybrid BFS-DLS
                 System.out.println("\nAttempting Hybrid BFS-DLS...");
                 List<int[][]> hybridSolutions = solver.solveWithHybrid(puzzle);
                 if (hybridSolutions.isEmpty()) {
                     System.out.println("Hybrid BFS-DLS couldn't solve the puzzle.");
                 }
             }
         } catch (IOException e) {
             System.err.println("Error loading puzzles: " + e.getMessage());
         } catch (Exception e) {
             System.err.println("Something unexpected happened: " + e.getMessage());
         }
     }
 }