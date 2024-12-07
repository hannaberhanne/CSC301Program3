package sudoku;

import java.util.*;

// Graph class represents the Sudoku grid as a graph, where each cell is a node, and edges connect nodes based on Sudoku constraints (rows, columns, subgrids).
// Citation: Concepts inspired by "Comparison Analysis of Breadth-First Search and Depth-Limited Search Algorithms in Sudoku Game"

public class Graph {
    private final Map<Integer, List<Integer>> adjacencyList; // Tracks connections between nodes

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // Adds an edge between two nodes (e.g., cells in the grid)
    public void addEdge(int from, int to) {
        adjacencyList.putIfAbsent(from, new ArrayList<>());
        if (!adjacencyList.get(from).contains(to)) {
            adjacencyList.get(from).add(to);
        }
    }

    // Returns all nodes connected to a given node (its "neighbors")
    public List<Integer> getNeighbors(int node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList());
    }

    // Builds the graph for a Sudoku grid based on the given grid size
    // This ensures each cell is connected to others in the same row, column, or subgrid
    public void buildSudokuGraph(int gridSize) {
        int totalCells = gridSize * gridSize;

        for (int i = 0; i < totalCells; i++) {
            // Connect nodes in the same row
            int rowStart = (i / gridSize) * gridSize;
            for (int j = rowStart; j < rowStart + gridSize; j++) {
                if (j != i) addEdge(i, j);
            }

            // Connect nodes in the same column
            int colStart = i % gridSize;
            for (int j = colStart; j < totalCells; j += gridSize) {
                if (j != i) addEdge(i, j);
            }

            // Connect nodes in the same subgrid
            int subgridSize = (int) Math.sqrt(gridSize);
            int boxRowStart = (i / gridSize / subgridSize) * subgridSize * gridSize;
            int boxColStart = (i % gridSize / subgridSize) * subgridSize;
            for (int row = 0; row < subgridSize; row++) {
                for (int col = 0; col < subgridSize; col++) {
                    int cell = boxRowStart + row * gridSize + boxColStart + col;
                    if (cell != i) addEdge(i, cell);
                }
            }
        }
    }

    // Validates the graph's structure by checking that all nodes are connected correctly
    public boolean validateGraph(int gridSize) {
        int totalCells = gridSize * gridSize;

        for (int i = 0; i < totalCells; i++) {
            List<Integer> neighbors = getNeighbors(i);
            Set<Integer> uniqueNeighbors = new HashSet<>(neighbors);

            // A valid Sudoku graph should have exactly 20 neighbors for each node
            // (8 in the row, 8 in the column, and 4 in the subgrid, minus overlaps)
            if (uniqueNeighbors.size() != 20) {
                System.err.println("Node " + i + " has an incorrect number of neighbors: " + uniqueNeighbors.size());
                return false;
            }
        }
        return true;
    }

    // Prints the graph to the console (useful for debugging or testing)
    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.println("Node " + entry.getKey() + " -> " + entry.getValue());
        }
    }
}