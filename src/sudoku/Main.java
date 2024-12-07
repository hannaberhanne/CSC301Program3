/**
 * Sudoku Solver 
 * CSC 301: Advanced Data Structures and Algorithms 
 * Date: Decembet 6, 2024
 * 
 * Contributors:
 * - Hanna Berhane
 * - Julius Smith
 * - Anthony Eccleston
 * 
 * Description:
 * This program implements and compares three algorithms for solving Sudoku:
 * Breadth-First Search (BFS), Depth-Limited Search (DLS), and a Hybrid BFS-DLS.
 * It also includes a Sudoku puzzle generator with validation for testing purposes.
 * 
 * Notes:
 * - BFS and DLS implementation inspired by "Comparison Analysis of Breadth First Search and Depth Limited Search Algorithms 
 *   in Sudoku Game," with a hybrid design adapted based on concepts from "Solving Rubik's Cube Using Graph Theory."
 * 
 * Citations:
 * - Tarsa Ninia Lina, Matheus Supriyanto Rumetna. "Comparison Analysis of Breadth First Search 
 *   and Depth Limited Search Algorithms in Sudoku Game." Bulletin of Computer Science and Electrical 
 *   Engineering, Vol. 2, No. 2, December 2021, pp. 74-83.
 *   [DOI: 10.25008/bcsee.v2i2.1146]
 * - Asraful, et al. "Solving Rubik's Cube Using Graph Theory." ICCI-2017.
 *   [ResearchGate Link: https://www.researchgate.net/publication/326749335_Solving_Rubik's_Cube_Using_Graph_Theory_ICCI-2017]
 */

package sudoku;

import sudoku.BFsSolver;
import sudoku.DLsSolver;
import sudoku.SudokuGenerator;
import sudoku.HybridSolver;
import sudoku.Utils;

import java.util.List;
import java.util.Scanner;

// Main class for the Sudoku Solver program
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Sudoku Solver!");

        try {
            while (true) {
                // Display menu options
                System.out.println("\nMenu:");
                System.out.println("1. Generate and solve a new puzzle");
                System.out.println("2. Exit");
                System.out.print("Your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1 -> generateAndSolvePuzzle(scanner);
                    case 2 -> {
                        System.out.println("Exiting program. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    // Generate and solve a new puzzle
    private static void generateAndSolvePuzzle(Scanner scanner) {
        System.out.println("\nSelect difficulty level:");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");
        System.out.print("Your choice: ");
        int difficulty = scanner.nextInt();

        SudokuGenerator generator = new SudokuGenerator();
        int[][] puzzle;

        // Generate puzzle based on difficulty level
        switch (difficulty) {
            case 1 -> puzzle = generator.generatePuzzle(35); // Easy: 35 clues
            case 2 -> puzzle = generator.generatePuzzle(25); // Medium: 25 clues
            case 3 -> puzzle = generator.generatePuzzle(17); // Hard: 17 clues
            default -> {
                System.out.println("Invalid choice. Defaulting to Medium difficulty.");
                puzzle = generator.generatePuzzle(25);
            }
        }

        // Print the generated puzzle
        System.out.println("\nGenerated Puzzle:");
        Utils.printSudoku(puzzle);

        // Solve the puzzle
        System.out.println("\n--- Solving Generated Puzzle ---");
        solvePuzzle(puzzle);
    }

    // Solves a puzzle using all three solvers
    private static void solvePuzzle(int[][] puzzle) {
        System.out.println("\nSolving using BFS...");
        solveAndMeasureTime("BFS", () -> new BFsSolver().solveWithBFS(puzzle));

        System.out.println("\nSolving using DLS...");
        solveAndMeasureTime("DLS", () -> new DLsSolver().solveWithDLS(puzzle, 50));

        System.out.println("\nSolving using Hybrid BFS-DLS...");
        solveAndMeasureTime("Hybrid BFS-DLS", () -> new HybridSolver().solveWithHybrid(puzzle));
    }

    // Utility method to measure and display the time taken by a solver
    private static void solveAndMeasureTime(String solverName, SolverMethod solver) {
        System.out.println("\nUsing " + solverName + "...");
        long startTime = System.nanoTime();
        List<int[][]> solutions = solver.solve();
        long endTime = System.nanoTime();

        // Log results
        System.out.println("Solutions found: " + solutions.size());
        if (!solutions.isEmpty()) {
            System.out.println("First Solution:");
            Utils.printSudoku(solutions.get(0));
        } else {
            System.out.println("No solutions found.");
        }
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    // Functional interface for solver methods
    @FunctionalInterface
    private interface SolverMethod {
        List<int[][]> solve();
    }
}