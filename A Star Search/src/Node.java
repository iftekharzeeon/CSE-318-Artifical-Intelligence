public class Node {
    public int[][] grid;
    public int gridSize;
    public int distanceToParent;
    public int hammingDistance;
    public int manhattanDistance;
    public int functionValue;

    public Node(int gridSize) {
        this.grid = new int[gridSize][gridSize];
        this.gridSize = gridSize;

        this.hammingDistance = 0;
        this.distanceToParent = 0;
    }

    public void setValues(int posR, int posC, int value) {
        this.grid[posR][posC] = value;
    }

    public void setGoalNode() {
        int count = 1;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (count == gridSize*gridSize) {
                    break;
                }
                grid[i][j] = count;
                count++;

            }
        }
    }


    public int getDistanceToParent() {
        return distanceToParent;
    }

    public void setDistanceToParent(int distanceToParent) {
        this.distanceToParent = distanceToParent;
    }

    public int getHammingDistance() {
        return hammingDistance;
    }

    public void setHammingDistance(int hammingDistance) {
        this.hammingDistance = hammingDistance;
    }

    public int getManhattanDistance() {
        return manhattanDistance;
    }

    public void setManhattanDistance(int manhattanDistance) {
        this.manhattanDistance = manhattanDistance;
    }

    public int getFunctionValue() {
        return functionValue;
    }

    public void setFunctionValue(int functionValue) {
        this.functionValue = functionValue;
    }


    public void printNode() {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }
}
