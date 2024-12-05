public class Utils {
    // This method prints out the Sudoku grid in a readable format
    public static void printSudoku(int[][] puzzle) {
        for (int[] row : puzzle) {
            for (int num : row) {
                // Print each number in the row, separated by a space
                System.out.print(num + " ");
            }
            // Move to the next line after printing a row
            System.out.println();
        }
    }
}