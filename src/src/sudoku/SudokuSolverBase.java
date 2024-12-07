// Base class to integrate all solvers
package sudoku;
import java.util.List;
import sudoku.BfsSolver;
import sudoku.DlsSolver;
public class SudokuSolverBase {
    private final BfsSolver bfsSolver;
    private final DlsSolver dlsSolver;
    private final HybridSolver hybridSolver;

    public SudokuSolverBase() {
        bfsSolver = new BfsSolver();
        dlsSolver = new DlsSolver();
        hybridSolver = new HybridSolver();
    }

    public List<int[][]> solveWithBFS(int[][] puzzle) {
        return bfsSolver.solveWithBFS(puzzle);
    }

    public List<int[][]> solveWithDLS(int[][] puzzle, int depthLimit) {
        return dlsSolver.solveWithDLS(puzzle, depthLimit);
    }

    public List<int[][]> solveWithHybrid(int[][] puzzle) {
        return hybridSolver.solveWithHybrid(puzzle);
    }
}