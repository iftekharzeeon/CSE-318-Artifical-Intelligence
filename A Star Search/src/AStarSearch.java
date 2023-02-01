import java.util.*;

public class AStarSearch {
    HashMap<Node, ArrayList<Node>> nodes;
    PriorityQueue<Node> openList;
    ArrayList<Node> closeList;

    public AStarSearch() {
        openList = new PriorityQueue<>(new NodeComparator());
        closeList = new ArrayList<>();
        nodes = new HashMap<>();
    }

    public boolean solvable(Node startNode) {
        int[] linearGrid = new int[startNode.gridSize*startNode.gridSize - 1];

        int blankPosition = 0;

        int k = 0;
        for (int i = 0; i < startNode.gridSize; i++) {
            for (int j = 0; j < startNode.gridSize; j++) {
                if (startNode.grid[i][j] == 0) {
                    blankPosition = startNode.gridSize - i;
                    continue;
                }
                linearGrid[k++] = startNode.grid[i][j];
            }
        }

        if (startNode.gridSize % 2 == 0) {
            //Even Grid Size
            if (blankPosition % 2 == 0) {
                return inversionCount(linearGrid) % 2 != 0;
            } else {
                return inversionCount(linearGrid) % 2 == 0;
            }
        }
        else {
            //Odd Grid Size
            return inversionCount(linearGrid) % 2 == 0;
        }
    }

    private int inversionCount(int[] linearGrid) {
        int inversionCount = 0;
        for (int i = 0; i < linearGrid.length; i++) {
            for (int j = i+1; j < linearGrid.length; j++) {
                if (linearGrid[i] > linearGrid[j]) {
                    inversionCount++;
                }
            }
        }
        return inversionCount;
    }

    public void heuristicSearch(Node startNode, Node goalNode) {
        startNode.functionValue = startNode.distanceToParent + startNode.hammingDistance;

        int cost = 0;
        int numberOfExploredNodes;
        int numberOfExpandedNodes = 0;

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node dequeuedNode = openList.poll();
            nodes.put(dequeuedNode, new ArrayList<>());
            closeList.add(dequeuedNode);

            if (dequeuedNode.hammingDistance == 0) {
                cost = dequeuedNode.functionValue;
                break;
            }

            for (int i = 0; i < dequeuedNode.gridSize; i++) {
                for (int j  = 0; j < dequeuedNode.gridSize; j++) {
                    if (dequeuedNode.grid[i][j] == 0) {
//                        Node tempNode = new Node(dequeuedNode.gridSize);
//                        tempNode.grid = dequeuedNode.grid;

//                        Node tempNode = new Node(dequeuedNode.gridSize);
//                        tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                        if ((i-1) >= 0) {

                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i-1][j];
                            tempNode.grid[i-1][j] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        if (tempNode.grid[k][l] != goalNode.grid[k][l]) {
                                            tempNode.hammingDistance++;
                                        }
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.hammingDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }

                        if ((i+1) < dequeuedNode.gridSize) {

                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i+1][j];
                            tempNode.grid[i+1][j] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        if (tempNode.grid[k][l] != goalNode.grid[k][l]) {
                                            tempNode.hammingDistance++;
                                        }
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.hammingDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }
                        if ((j-1) >= 0) {

                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i][j-1];
                            tempNode.grid[i][j-1] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        if (tempNode.grid[k][l] != goalNode.grid[k][l]) {
                                            tempNode.hammingDistance++;
                                        }
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.hammingDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }

                        if ((j+1) < dequeuedNode.gridSize) {
                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i][j+1];
                            tempNode.grid[i][j+1] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        if (tempNode.grid[k][l] != goalNode.grid[k][l]) {
                                            tempNode.hammingDistance++;
                                        }
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.hammingDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }
                        break;
                    }
                }
            }
        }

        numberOfExploredNodes = closeList.size();

        System.out.println("Steps");

        for (Node node : closeList) {
            for (int i = 0; i < node.gridSize; i++) {
                for (int j = 0; j < node.gridSize; j++) {
                    if (node.grid[i][j] == 0) {
                        System.out.print("* ");
                    } else {
                        System.out.print(node.grid[i][j] + " ");
                    }

                }
                System.out.println("");
            }
            System.out.println("---->");
        }

        System.out.println("Cost " + cost);
        System.out.println("Number of explored nodes " + numberOfExploredNodes);
        System.out.println("Number of expanded nodes " + numberOfExpandedNodes);
    }

    public void manhattanSearch(Node startNode, Node goalNode) {
        startNode.functionValue = startNode.distanceToParent + startNode.hammingDistance;

        int cost = 0;

        int numberOfExploredNodes;
        int numberOfExpandedNodes=0;

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node dequeuedNode = openList.poll();
            nodes.put(dequeuedNode, new ArrayList<>());
            closeList.add(dequeuedNode);

            if (dequeuedNode.manhattanDistance == 0) {
                cost = dequeuedNode.functionValue;
                break;
            }

            for (int i = 0; i < dequeuedNode.gridSize; i++) {
                for (int j  = 0; j < dequeuedNode.gridSize; j++) {
                    if (dequeuedNode.grid[i][j] == 0) {
//                        Node tempNode = new Node(dequeuedNode.gridSize);
//                        tempNode.grid = dequeuedNode.grid;

//                        Node tempNode = new Node(dequeuedNode.gridSize);
//                        tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                        if ((i-1) >= 0) {

                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i-1][j];
                            tempNode.grid[i-1][j] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                int count = 0;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        int rowDistance = ((int) Math.ceil((double) tempNode.grid[k][l] / tempNode.gridSize)) - (k+1);
                                        int colDistance = tempNode.grid[k][l] % tempNode.gridSize == 0? tempNode.gridSize - (l+1) : tempNode.grid[k][l] % tempNode.gridSize - (l+1);
                                        tempNode.manhattanDistance = rowDistance + colDistance;
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.manhattanDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }

                        if ((i+1) < dequeuedNode.gridSize) {

                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i+1][j];
                            tempNode.grid[i+1][j] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        int rowDistance = ((int) Math.ceil((double) tempNode.grid[k][l] / tempNode.gridSize)) - (k+1);
                                        int colDistance = tempNode.grid[k][l] % tempNode.gridSize == 0? tempNode.gridSize - (l+1) : tempNode.grid[k][l] % tempNode.gridSize - (l+1);
                                        tempNode.manhattanDistance = rowDistance + colDistance;
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.manhattanDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }
                        if ((j-1) >= 0) {

                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i][j-1];
                            tempNode.grid[i][j-1] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        int rowDistance = ((int) Math.ceil((double) tempNode.grid[k][l] / tempNode.gridSize)) - (k+1);
                                        int colDistance = tempNode.grid[k][l] % tempNode.gridSize == 0? tempNode.gridSize - (l+1) : tempNode.grid[k][l] % tempNode.gridSize - (l+1);
                                        tempNode.manhattanDistance = rowDistance + colDistance;
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.manhattanDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }

                        if ((j+1) < dequeuedNode.gridSize) {
                            Node tempNode = new Node(dequeuedNode.gridSize);
                            tempNode.grid = Arrays.stream(dequeuedNode.grid).map(int[]::clone).toArray(int[][]::new);
                            tempNode.distanceToParent = dequeuedNode.distanceToParent;

                            int temp = tempNode.grid[i][j+1];
                            tempNode.grid[i][j+1] = 0;
                            tempNode.grid[i][j] = temp;

                            boolean visitedCheck = true;
                            for (Node node : closeList) {
                                for (int x = 0; x < node.gridSize; x++) {
                                    for (int y = 0; y < node.gridSize; y++) {
                                        if (node.grid[x][y] != tempNode.grid[x][y]) {
                                            visitedCheck = false;
                                            break;
                                        }
                                    }
                                }
                            }

                            if (!visitedCheck) {
                                tempNode.distanceToParent++;
                                for (int k = 0; k < tempNode.gridSize; k++) {
                                    for (int l = 0; l < tempNode.gridSize; l++) {
                                        int rowDistance = ((int) Math.ceil((double) tempNode.grid[k][l] / tempNode.gridSize)) - (k+1);
                                        int colDistance = tempNode.grid[k][l] % tempNode.gridSize == 0? tempNode.gridSize - (l+1) : tempNode.grid[k][l] % tempNode.gridSize - (l+1);
                                        tempNode.manhattanDistance = rowDistance + colDistance;
                                    }
                                }

                                tempNode.functionValue = tempNode.distanceToParent + tempNode.manhattanDistance;

                                nodes.get(dequeuedNode).add(tempNode);

                                openList.add(tempNode);
                                numberOfExpandedNodes++;
                            }
                        }
                        break;
                    }
                }
            }
        }

        numberOfExploredNodes = closeList.size();

        System.out.println("Steps");

        for (Node node : closeList) {
            for (int i = 0; i < node.gridSize; i++) {
                for (int j = 0; j < node.gridSize; j++) {
                    if (node.grid[i][j] == 0) {
                        System.out.print("* ");
                    } else {
                        System.out.print(node.grid[i][j] + " ");
                    }

                }
                System.out.println("");
            }
            System.out.println("---->");
        }

        System.out.println("Cost " + cost);
        System.out.println("Number of explored nodes " + numberOfExploredNodes);
        System.out.println("Number of expanded nodes " + numberOfExpandedNodes);
    }
}

class NodeComparator implements Comparator<Node> {

    // Overriding compare()method of Comparator
    public int compare(Node n1, Node n2) {
        if (n1.getFunctionValue() > n2.getFunctionValue())
            return 1;
        else if (n1.getFunctionValue() < n2.getFunctionValue())
            return -1;
        return 0;
    }
}
