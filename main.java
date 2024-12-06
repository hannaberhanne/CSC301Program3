import java.util.*;

// Main class for the Sudoku Solver program
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Sudoku Solver!");

        try {
            while (true) {
                // Show menu options
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
  // Solves a puzzle using all three solvers
private static void solvePuzzle(int[][] puzzle) {
    // Validate the puzzle
    if (!isPuzzleValid(puzzle)) {
        System.out.println("The provided puzzle is invalid. Please check the input.");
        return;
    }

    try {
        // Solve using Breadth-First Search (BFS)
        System.out.println("\n--- Solving with BFS ---");
        solveAndMeasureTime("BFS", () -> new BfsSolver().solveWithBFS(puzzle));

        // Solve using Depth-Limited Search (DLS)
        System.out.println("\n--- Solving with DLS ---");
        solveAndMeasureTime("DLS", () -> new DlsSolver().solveWithDLS(puzzle, 50));

        // Solve using Hybrid BFS-DLS
        System.out.println("\n--- Solving with Hybrid BFS-DLS ---");
        solveAndMeasureTime("Hybrid BFS-DLS", () -> new HybridSolver().solveWithHybrid(puzzle));

    } catch (Exception e) {
        // Catch any unexpected exceptions and print a user-friendly message
        System.err.println("An error occurred while solving the puzzle: " + e.getMessage());
    }
}

// Helper method to validate the Sudoku puzzle
private static boolean isPuzzleValid(int[][] puzzle) {
    // Check for null or empty puzzle
    if (puzzle == null || puzzle.length != 9 || puzzle[0].length != 9) {
        return false;
    }

    // Check each cell for valid values (0-9)
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            int value = puzzle[row][col];
            if (value < 0 || value > 9) {
                return false;
        solveAndMeasureTime("DLS", () -> new DlsSolver().solveWithDLS(puzzle, 50));
        
                // Solve using Hybrid BFS-DLS
                solveAndMeasureTime("Hybrid BFS-DLS", () -> new HybridSolver().solveWithHybrid(puzzle));
            }
        
            private static void solveAndMeasureTime(String solverName, Main.SolverMethod solver) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'solveAndMeasureTime'");
            }
        
            // Measure execution time of solvers
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