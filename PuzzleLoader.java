import java.io.*;
import java.util.*;

public class PuzzleLoader {
    // Reads Sudoku puzzles from a file and stores them in a list
    public static List<int[][]> loadPuzzles(String filePath) throws IOException {
        List<int[][]> puzzles = new ArrayList<>();
        int[][] currentPuzzle = new int[9][9]; // Standard 9x9 grid
        int row = 0; // Keeps track of which row we're filling in the current puzzle

        // Use try-with-resources to ensure BufferedReader is closed automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Remove leading and trailing spaces
                // Skip blank lines or lines with comments (starting with //)
                if (line.isEmpty() || line.startsWith("//")) {
                    continue;
                }

                // Only process lines with numbers and spaces
                if (line.matches("[0-9\\s]+")) {
                    String[] values = line.split("\\s+"); // Split by spaces
                    for (int col = 0; col < values.length; col++) {
                        try {
                            currentPuzzle[row][col] = Integer.parseInt(values[col]);
                        } catch (NumberFormatException e) {
                            // If there's a non-numeric value, skip this line and move on
                            System.err.println("Invalid number in puzzle file: " + values[col]);
                            continue;
                        }
                    }
                    row++; // Move to the next row
                }

                // If we've filled out a puzzle, add it to the list
                if (row == 9) {
                    puzzles.add(currentPuzzle);
                    currentPuzzle = new int[9][9]; // Reset for the next puzzle
                    row = 0; // Reset the row counter
                }
            }

            // Add the last puzzle if the file doesn't end with a blank line
            if (row == 9) {
                puzzles.add(currentPuzzle);
            } else if (row > 0) {
                System.err.println("Warning: Incomplete puzzle at the end of the file was skipped.");
            }
        }

        return puzzles;
    }
}
