import java.util.Scanner;

import sudoku.BFsSolver;
import sudoku.DLsSolver;

import java.util.List; // Ensure List is imported

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

        System.out.println("\nGenerated Puzzle:");
        Utils.printSudoku(puzzle);

        System.out.println("\n--- Solving Generated Puzzle ---");
        solvePuzzle(puzzle);
    }

    // Solves a puzzle using all three solvers
    private static void solvePuzzle(int[][] puzzle) {
        solveAndMeasureTime("BFS", () -> new BFsSolver().solveWithBFS(puzzle));
        solveAndMeasureTime("DLS", () -> new DLsSolver().solveWithDLS(puzzle, 50));
        solveAndMeasureTime("Hybrid BFS-DLS", () -> new HybridSolver().solveWithHybrid(puzzle));
    }

    // Utility method to measure and display the time taken by a solver
    private static void solveAndMeasureTime(String solverName, SolverMethod solver) {
        System.out.println("\nUsing " + solverName + "...");
        long startTime = System.nanoTime();
        List<int[][]> solutions = solver.solve();
        long endTime = System.nanoTime();
        System.out.println("Solutions found: " + solutions.size());
        System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    // Functional interface for solver methods
    @FunctionalInterface
    private interface SolverMethod {
        List<int[][]> solve();
    }
}