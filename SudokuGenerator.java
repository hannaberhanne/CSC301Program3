import java.util.*;

// Generates Sudoku puzzles based on difficulty level
public class SudokuGenerator {
    public int[][] generatePuzzle(int clues) {
        int[][] puzzle = new int[9][9];
        Random random = new Random();

        // Fill in the grid with random values for the specified number of clues
        for (int i = 0; i < clues; i++) {
            int row, col, num;
            do {
                row = random.nextInt(9);
                col = random.nextInt(9);
                num = random.nextInt(9) + 1;
            } while (puzzle[row][col] != 0 || !isValidPlacement(puzzle, row, col, num));
            puzzle[row][col] = num;
        }
        return puzzle;
    }

    private boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num || grid[i][col] == num) return false;
            int subgridRow = (row / 3) * 3 + i / 3;
            int subgridCol = (col / 3) * 3 + i % 3;
            if (grid[subgridRow][subgridCol] == num) return false;
        }
        return true;
    }
}