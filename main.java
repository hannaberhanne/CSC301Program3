import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        SudokuSolver solver = new SudokuSolver();

        List<int[][]> puzzles = null;
        try {
            puzzles = PuzzleLoader.loadPuzzles("puzzles.txt");
        } catch (IOException e) {
            System.err.println("Error loading puzzles: " + e.getMessage());
            return;
        }

        for (int i = 0; i < puzzles.size(); i++) {
            int[][] puzzle = puzzles.get(i);
            System.out.println("\n=== Testing Puzzle " + (i + 1) + " ===");
            Utils.printSudoku(puzzle);

            // BFS
            System.out.println("Breadth-First Search (BFS):");
            long startTime = System.nanoTime();
            List<int[][]> bfsSolutions = solver.solveWithBFS(puzzle);
            long endTime = System.nanoTime();
            System.out.println("Solutions found: " + bfsSolutions.size());
            System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms");

            // DLS
            System.out.println("Depth-Limited Search (DLS):");
            startTime = System.nanoTime();
            List<int[][]> dlsSolutions = solver.solveWithDLS(puzzle, 50);
            endTime = System.nanoTime();
            System.out.println("Solutions found: " + dlsSolutions.size());
            System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms");

            // Hybrid BFS-DLS
            System.out.println("Hybrid BFS-DLS:");
            startTime = System.nanoTime();
            List<int[][]> hybridSolutions = solver.solveWithHybrid(puzzle);
            endTime = System.nanoTime();
            System.out.println("Solutions found: " + hybridSolutions.size());
            System.out.println("Time taken: " + (endTime - startTime) / 1_000_000 + " ms");
        }

        System.out.println("\n=== Testing Complete ===");
    }
}
