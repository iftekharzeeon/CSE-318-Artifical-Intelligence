import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Enter the grid size: ");
        Scanner scanner = new Scanner(System.in);

        int gridSize = Integer.parseInt(scanner.nextLine());

        Node startNode = new Node(gridSize);
        Node goalNode = new Node(gridSize);
        goalNode.setGoalNode();

        int h = 0;
        for (int i = 1; i <= gridSize; i++) {
            for (int j = 1; j <= gridSize; j++) {
                System.out.println("enter row " + i + " col " + j);
                String value = scanner.nextLine().trim();
                if (value.equals("*")) {
                    startNode.setValues(i-1, j-1, 0);
                } else {
                    startNode.setValues(i-1, j-1, Integer.parseInt(value));
                    if (startNode.grid[i-1][j-1] != goalNode.grid[i-1][j-1]) {
                        startNode.hammingDistance++;
                    }
                }
            }
        }

        startNode.setDistanceToParent(0);

        AStarSearch a = new AStarSearch();

        if (a.solvable(startNode)) {
            System.out.println("Solvable");
            System.out.println("Hamming Distance==============>");
//            a.heuristicSearch(startNode, goalNode);
            System.out.println("Manhattan Distance==============>");
            a.manhattanSearch(startNode, goalNode);
        } else {
            System.out.println("Not solvable");
        }

    }
}
