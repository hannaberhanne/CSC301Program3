package src.sudoku;
public class Utils {
    // Prints the Sudoku grid in a readable format
    public static void printSudoku(int[][] puzzle) {
        if (puzzle == null || puzzle.length == 0) {
            System.out.println("Invalid Sudoku puzzle.");
            return;
        }

        int gridSize = puzzle.length;

        // Print column numbers for clarity
        System.out.println("   " + generateColumnHeaders(gridSize));

        for (int row = 0; row < gridSize; row++) {
            if (row % Math.sqrt(gridSize) == 0 && row != 0) {
                // Add horizontal divider between sub-grids
                System.out.println("  " + "-".repeat(gridSize * 2 + (int) Math.sqrt(gridSize) - 1));
            }

            System.out.print(row + 1 + " | "); // Add row numbers for clarity
            for (int col = 0; col < gridSize; col++) {
                if (col % Math.sqrt(gridSize) == 0 && col != 0) {
                    // Add vertical divider between sub-grids
                    System.out.print("| ");
                }
                System.out.print(puzzle[row][col] + " ");
            }
            System.out.println(); // Move to the next row
        }
    }

    // Generate column headers (for visual clarity)
    private static String generateColumnHeaders(int gridSize) {
        StringBuilder headers = new StringBuilder();
        for (int i = 1; i <= gridSize; i++) {
            headers.append(i).append(" ");
            if (i % Math.sqrt(gridSize) == 0 && i != gridSize) {
                headers.append("  "); // Space for sub-grid separation
            }
        }
        return headers.toString().trim();
    }
}