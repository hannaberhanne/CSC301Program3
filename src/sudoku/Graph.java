package sudoku;

import java.util.*;

// Graph class represents the Sudoku grid as a graph, where each cell is a node, and edges connect nodes based on Sudoku constraints (rows, columns, subgrids).
// Citation: Concepts inspired by "Comparison Analysis of Breadth-First Search and Depth-Limited Search Algorithms in Sudoku Game"

public class Graph {
    private final Map<Integer, List<Integer>> adjacencyList; // Tracks connections between nodes

    // Constructor initializes the adjacency list for the graph
    public Graph() {
        adjacencyList = new HashMap<>();
    }

    // Adds an edge between two nodes (e.g., cells in the grid)
    public void addEdge(int from, int to) {
        adjacencyList.putIfAbsent(from, new ArrayList<>()); // Ensure the node exists in the graph
        if (!adjacencyList.get(from).contains(to)) { // Avoid duplicate edges
            adjacencyList.get(from).add(to); // Add the edge
        }
    }

    // Returns all nodes connected to a given node (its "neighbors")
    public List<Integer> getNeighbors(int node) {
        return adjacencyList.getOrDefault(node, Collections.emptyList()); // Return neighbors or an empty list if the node is not connected
    }

    // Builds the graph for a Sudoku grid based on the given grid size
    // Ensures each cell is connected to others in the same row, column, or subgrid
    public void buildSudokuGraph(int gridSize) {
        int totalCells = gridSize * gridSize; // Total number of cells in the grid

        for (int i = 0; i < totalCells; i++) {
            // Connect nodes in the same row
            int rowStart = (i / gridSize) * gridSize; // Calculate the starting index of the row
            for (int j = rowStart; j < rowStart + gridSize; j++) {
                if (j != i) addEdge(i, j); // Connect to all other cells in the row
            }

            // Connect nodes in the same column
            int colStart = i % gridSize; // Calculate the starting index of the column
            for (int j = colStart; j < totalCells; j += gridSize) {
                if (j != i) addEdge(i, j); // Connect to all other cells in the column
            }

            // Connect nodes in the same subgrid
            int subgridSize = (int) Math.sqrt(gridSize); // Determine the size of the subgrid (e.g., 3x3 for a 9x9 grid)
            int boxRowStart = (i / gridSize / subgridSize) * subgridSize * gridSize; // Calculate starting row index of the subgrid
            int boxColStart = (i % gridSize / subgridSize) * subgridSize; // Calculate starting column index of the subgrid
            for (int row = 0; row < subgridSize; row++) {
                for (int col = 0; col < subgridSize; col++) {
                    int cell = boxRowStart + row * gridSize + boxColStart + col; // Calculate the index of each cell in the subgrid
                    if (cell != i) addEdge(i, cell); // Connect to all other cells in the subgrid
                }
            }
        }
    }

    // Validates the graph's structure by checking that all nodes are connected correctly
    public boolean validateGraph(int gridSize) {
        int totalCells = gridSize * gridSize; // Total number of cells in the grid

        for (int i = 0; i < totalCells; i++) {
            List<Integer> neighbors = getNeighbors(i); // Retrieve neighbors of the current node
            Set<Integer> uniqueNeighbors = new HashSet<>(neighbors); // Use a set to remove duplicates

            // A valid Sudoku graph should have exactly 20 neighbors for each node:
            // 8 from the row, 8 from the column, and 4 from the subgrid (minus overlaps)
            if (uniqueNeighbors.size() != 20) {
                System.err.println("Node " + i + " has an incorrect number of neighbors: " + uniqueNeighbors.size());
                return false; // If any node has the wrong number of neighbors, the graph is invalid
            }
        }
        return true; // The graph is valid if all nodes have the correct number of neighbors
    }

    // Prints the graph to the console (useful for debugging or testing)
    public void printGraph() {
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            System.out.println("Node " + entry.getKey() + " -> " + entry.getValue()); // Display each node and its neighbors
        }
    }
}