package sudoku;

/**
 * Utility class for common Sudoku operations.
 * This class handles grid printing and formatting to make puzzles easier to visualize.
 */
public class Utils {

    /**
     * Prints the Sudoku grid in a clear, readable format.
     * Includes row and column numbers and separates subgrids visually.
     *
     * @param puzzle the Sudoku puzzle to print (9x9 or other valid sizes)
     */
    public static void printSudoku(int[][] puzzle) {
        if (puzzle == null || puzzle.length == 0) {
            System.out.println("Invalid Sudoku puzzle. Please check your input.");
            return;
        }

        int gridSize = puzzle.length; // Determine the size of the grid (e.g., 9 for a standard puzzle)

        // Print column headers to help users navigate the grid
        System.out.println("   " + generateColumnHeaders(gridSize));

        // Iterate through each row of the grid
        for (int row = 0; row < gridSize; row++) {
            // Add a horizontal divider between subgrids (e.g., every 3 rows for a 9x9 grid)
            if (row % Math.sqrt(gridSize) == 0 && row != 0) {
                System.out.println("  " + "-".repeat(gridSize * 2 + (int) Math.sqrt(gridSize) - 1));
            }

            // Print the row number, followed by grid contents
            System.out.print((row + 1) + " | "); // Row numbers start at 1 for clarity
            for (int col = 0; col < gridSize; col++) {
                // Add a vertical divider between subgrids
                if (col % Math.sqrt(gridSize) == 0 && col != 0) {
                    System.out.print("| ");
                }
                // Print the cell value, or 0 for an empty cell
                System.out.print(puzzle[row][col] + " ");
            }
            System.out.println(); // Move to the next row after printing the current row
        }
    }

    /**
     * Generates column headers for the Sudoku grid.
     * Helps users identify columns when solving or debugging puzzles.
     *
     * @param gridSize the size of the grid (e.g., 9 for a standard puzzle)
     * @return a formatted string representing the column numbers
     */
    private static String generateColumnHeaders(int gridSize) {
        StringBuilder headers = new StringBuilder();
        for (int i = 1; i <= gridSize; i++) {
            headers.append(i).append(" "); // Add column number
            // Add extra spacing after subgrid boundaries for clarity
            if (i % Math.sqrt(gridSize) == 0 && i != gridSize) {
                headers.append("  ");
            }
        }
        return headers.toString().trim(); // Remove trailing space for neatness
    }
}