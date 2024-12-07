package sudoku;

import java.util.*;

//The SudokuGenerator class generates random Sudoku puzzles based on the given difficulty level.
//It ensures each puzzle has at least one valid solution before returning it.

public class SudokuGenerator {

    /**
     * Generates a Sudoku puzzle with the specified number of clues.
     * This method repeatedly generates random grids until a valid, solvable puzzle is produced.
     *
     * @param clues the number of pre-filled cells in the puzzle (difficulty level)
     * @return a valid Sudoku puzzle grid
     */
    public int[][] generatePuzzle(int clues) {
        int[][] puzzle;
        Random random = new Random();

        // Continuously generate until a valid puzzle with a solution is found
        do {
            puzzle = new int[9][9];

            // Randomly populate the grid with the specified number of clues
            for (int i = 0; i < clues; i++) {
                int row, col, num;
                do {
                    row = random.nextInt(9);
                    col = random.nextInt(9);
                    num = random.nextInt(9) + 1; // Numbers between 1 and 9
                } while (puzzle[row][col] != 0 || !isValidPlacement(puzzle, row, col, num));
                puzzle[row][col] = num;
            }
        } while (!isValidPuzzle(puzzle)); // Ensure the generated grid has at least one solution

        return puzzle;
    }

    /**
     * Validates whether placing a number in a specific cell violates Sudoku rules.
     *
     * @param grid the Sudoku grid
     * @param row  the row of the cell
     * @param col  the column of the cell
     * @param num  the number to place in the cell
     * @return true if the placement is valid, false otherwise
     */
    private boolean isValidPlacement(int[][] grid, int row, int col, int num) {
        for (int i = 0; i < 9; i++) {
            // Check row and column
            if (grid[row][i] == num || grid[i][col] == num) return false;

            // Check 3x3 subgrid
            int subgridRow = (row / 3) * 3 + i / 3;
            int subgridCol = (col / 3) * 3 + i % 3;
            if (grid[subgridRow][subgridCol] == num) return false;
        }
        return true;
    }

    /**
     * Verifies if the generated puzzle has at least one valid solution.
     *
     * @param puzzle the generated Sudoku puzzle
     * @return true if the puzzle has a solution, false otherwise
     */
    private boolean isValidPuzzle(int[][] puzzle) {
        return solveSudoku(copyGrid(puzzle));
    }

    /**
     * Solves the Sudoku puzzle using a backtracking algorithm.
     * This is used internally to validate puzzles during generation.
     *
     * @param grid the Sudoku grid to solve
     * @return true if the puzzle can be solved, false otherwise
     */
    private boolean solveSudoku(int[][] grid) {
        int[] emptyCell = findEmptyCell(grid);
        if (emptyCell == null) return true; // No empty cells mean the puzzle is solved

        int row = emptyCell[0], col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isValidPlacement(grid, row, col, num)) {
                grid[row][col] = num;

                // Recursively attempt to solve the rest of the puzzle
                if (solveSudoku(grid)) return true;

                grid[row][col] = 0; // Backtrack
            }
        }
        return false; // Backtracking failed
    }

    /**
     * Finds the first empty cell in the grid (represented by 0).
     *
     * @param grid the Sudoku grid
     * @return an array with row and column indices of the empty cell, or null if none found
     */
    private int[] findEmptyCell(int[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == 0) return new int[]{row, col};
            }
        }
        return null; // No empty cells
    }

    /**
     * Creates a deep copy of the given Sudoku grid.
     * This avoids altering the original grid during validation or solving.
     *
     * @param grid the Sudoku grid to copy
     * @return a new grid that is a deep copy of the original
     */
    private int[][] copyGrid(int[][] grid) {
        int[][] copy = new int[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            System.arraycopy(grid[row], 0, copy[row], 0, grid[row].length);
        }
        return copy;
    }
}