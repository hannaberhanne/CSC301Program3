import java.io.*;
import java.util.*;

public class PuzzleLoader {
    // Reads Sudoku puzzles from a file and stores them in a list
    public static List<int[][]> loadPuzzles(String filePath) throws IOException {
        List<int[][]> puzzles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int[][] currentPuzzle = new int[9][9]; // Standard 9x9 grid
        int row = 0; // Keeps track of which row we're filling in the current puzzle

        while ((line = reader.readLine()) != null) {
            // Skip blank lines (they're used to separate puzzles)
            if (line.trim().isEmpty()) {
                // If we've filled out a puzzle, add it to the list
                if (row == 9) { // Only add complete puzzles
                    puzzles.add(currentPuzzle);
                } else {
                    System.err.println("Warning: Incomplete puzzle skipped.");
                }
                currentPuzzle = new int[9][9]; // Reset for the next puzzle
                row = 0; // Reset the row counter
            } else {
                // Split the line into numbers and fill the current puzzle
                String[] values = line.split("\\s+"); // Handle multiple spaces
                for (int col = 0; col < values.length; col++) {
                    try {
                        currentPuzzle[row][col] = Integer.parseInt(values[col]);
                    } catch (NumberFormatException e) {
                        throw new IOException("Invalid number in puzzle file: " + values[col]);
                    }
                }
                row++; // Move to the next row
            }
        }

        // Add the last puzzle if the file doesn't end with a blank line
        if (row == 9) {
            puzzles.add(currentPuzzle);
        } else if (row > 0) {
            System.err.println("Warning: Incomplete puzzle at the end of the file was skipped.");
        }

        reader.close(); // Close the file
        return puzzles;
    }
}