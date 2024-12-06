import java.io.IOException; // For handling potential input/output exceptions
import java.util.*;         // For working with collections like List

public class Main {
    public static void main(String[] args) {
        // Create an instance of SudokuSolver to solve puzzles using various methods
        SudokuSolver solver = new SudokuSolver();

        // List to store loaded Sudoku puzzles
        List<int[][]> puzzles = null;

        try {
            // Attempt to load puzzles from a file named "puzzles.txt"
            puzzles = PuzzleLoader.loadPuzzles("puzzles.txt");
        } catch (IOException e) {
            // Handle the exception if the file cannot be read
            System.err.println("Error loading puzzles: " + e.getMessage());
            return; // Exit the program since puzzles cannot be loaded
        }

        // Iterate over each puzzle loaded from the file
        for (int i = 0; i < puzzles.size(); i++) {
            int[][] puzzle = puzzles.get(i); // Retrieve the current puzzle
            System.out.println("\n=== Testing Puzzle " + (i + 1) + " ===");

            // Print the puzzle in a human-readable format using a utility method
            Utils.printSudoku(puzzle);

            // Solve the puzzle using Breadth-First Search (BFS)
            System.out.println("Breadth-First Search (BFS):");
            long startTime = System.nanoTime(); // Record start time
            List<int[][]> bfsSolutions = solver.solveWithBFS(puzzle); // Solve the puzzle
            long endTime = System.nanoTime();   // Record end time
            System.out.println("Solutions found: " + bfsSolutions.size()); // Output solution count
            System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms"); // Output time taken

            // Solve the puzzle using Depth-Limited Search (DLS)
            System.out.println("Depth-Limited Search (DLS):");
            startTime = System.nanoTime(); // Record start time
            List<int[][]> dlsSolutions = solver.solveWithDLS(puzzle, 50); // Solve with a depth limit of 50
            endTime = System.nanoTime();   // Record end time
            System.out.println("Solutions found: " + dlsSolutions.size()); // Output solution count
            System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms"); // Output time taken

            // Solve the puzzle using a Hybrid BFS-DLS approach
            System.out.println("Hybrid BFS-DLS:");
            startTime = System.nanoTime(); // Record start time
            List<int[][]> hybridSolutions = solver.solveWithHybrid(puzzle); // Solve using the hybrid method
            endTime = System.nanoTime();   // Record end time
            System.out.println("Solutions found: " + hybridSolutions.size()); // Output solution count
            System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms"); // Output time taken
        }

        // Indicate that testing is complete
        System.out.println("\n=== Testing Complete ===");
    }
}
