import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CSPSolver {
    private static int order;
    private static int nodeCount;
    private static int backtrackCount;

    public static void main(String[] args) throws FileNotFoundException {

        Scanner sc = new Scanner(new File("in.txt"));
        while (sc.hasNext()) {
            String fileName = sc.nextLine();
            System.out.println("---------------------------------");
            System.out.println("File: " + fileName);
            System.out.println("---------------------------------");

            //Input file
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            //Read the order from the first line of the input file
            order = Integer.parseInt(scanner.nextLine().split("=")[1].replaceAll(";", ""));

            //Ignore next two lines
            scanner.nextLine();
            scanner.nextLine();

            //Store the values into a matrix
            int[][] matrix = new int[order][order];
            for (int i = 0; i < order; i++) {
                String[] line = scanner.nextLine().replaceAll("\\||]|;", "").split(",");
                for (int j = 0; j < order; j++) {
                    matrix[i][j] = Integer.parseInt(line[j].trim());
                }
            }

            //Initialize the variables
            ArrayList<Variables> variables = new ArrayList<>();
            ArrayList<Variables> variables1 = new ArrayList<>();

            for (int i = 0; i < order; i++) {
                for (int j = 0; j < order; j++) {
                    Variables variable = new Variables(i * order + j, matrix[i][j]);
                    Variables variable2 = new Variables(i * order + j, matrix[i][j]);
                    if (matrix[i][j] == 0) {
                        for (int k = 1; k <= order; k++) {
                            variable.domainList.add(k);
                            variable2.domainList.add(k);
                        }
                    }
                    variables.add(variable);
                    variables1.add(variable2);
                }

            }

            //Initialize the constraints
            for (int i = 0; i < order; i++) {
                for (int j = 0; j < order; j++) {
                    Variables variable = variables.get(i * order + j);
                    for (int k = 0; k < order; k++) {
                        if (k != j) {
                            if (variables.get(i * order + k).value != 0) {
                                variable.domainList.remove((Integer) variables.get(i * order + k).value); //Remove the value from the domain list
                            } else {
                                variable.unassignedConstraints.add(variables.get(i * order + k)); //Add the variable to the unassigned constraints list
                            }
                        }
                        if (k != i) {
                            if (variables.get(k * order + j).value !=0) {
                                variable.domainList.remove((Integer) variables.get(k * order + j).value); //Remove the value from the domain list
                            } else {
                                variable.unassignedConstraints.add(variables.get(k * order + j)); //Add the variable to the unassigned constraints list
                            }
                        }
                    }
                }
            }

            //Initialize the constraints for variable2
            for (int i = 0; i < order; i++) {
                for (int j = 0; j < order; j++) {
                    Variables variable = variables1.get(i * order + j);
                    for (int k = 0; k < order; k++) {
                        if (k != j) {
                            if (variables1.get(i * order + k).value !=0) {
                                variable.domainList.remove((Integer) variables1.get(i * order + k).value); //Remove the value from the domain list
                            } else {
                                variable.unassignedConstraints.add(variables1.get(i * order + k)); //Add the variable to the unassigned constraints list
                            }
                        }
                        if (k != i) {
                            if (variables1.get(k * order + j).value !=0) {
                                variable.domainList.remove((Integer) variables1.get(k * order + j).value); //Remove the value from the domain list
                            } else {
                                variable.unassignedConstraints.add(variables1.get(k * order + j)); //Add the variable to the unassigned constraints list
                            }
                        }
                    }
                }
            }

            //Start with node count 1 as the initial state is also a node
            nodeCount = 1;
            backtrackCount = 0;

//            callVah1(variables, variables1);
//            callVah2(variables, variables1);
//            callVah3(variables, variables1);
//            callVah4(variables, variables1);
            callVah5(variables, variables1);


        }
    }

    private static void callVah1(ArrayList<Variables> variables, ArrayList<Variables> variablesArrayList) {
        System.out.println("");
        System.out.println("For VAH 1");
        System.out.println("Search with simple backtrack");
        System.out.println(">>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        long startTime = System.currentTimeMillis();
        ArrayList<Variables> result = backtrackingSolveWithVAH1(variables);
        long endTime = System.currentTimeMillis();

        printResult(result, endTime, startTime);
        System.out.println("");
        System.out.println(">>>>>>>>");
        System.out.println("Search with forward checking");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        startTime = System.currentTimeMillis();
        result = backtrackingSolveWithForwardCheckingWithVAH1(variablesArrayList);
        endTime = System.currentTimeMillis();

        printResult(result, endTime, startTime);
    }

    private static void callVah2(ArrayList<Variables> variables, ArrayList<Variables> variablesArrayList) {
        System.out.println("");
        System.out.println("For VAH 2");
//        System.out.println("Search with simple backtrack");
//        System.out.println(">>>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        long startTime = System.currentTimeMillis();
//        ArrayList<Variables> result = backtrackingSolveWithVAH2(variables);
        long endTime = System.currentTimeMillis();

//        printResult(result, endTime, startTime);
        System.out.println();
        System.out.println("Search with forward checking");
        System.out.println(">>>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        startTime = System.currentTimeMillis();
        ArrayList<Variables> result = backtrackingSolveWithForwardCheckingWithVAH2(variablesArrayList);
        endTime = System.currentTimeMillis();

        printResult(result, endTime, startTime);
    }

    private static void callVah3(ArrayList<Variables> variables, ArrayList<Variables> variablesArrayList) {
        System.out.println();
        System.out.println("For VAH 3");
        System.out.println("Search with simple backtrack");
        System.out.println(">>>>>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        long startTime = System.currentTimeMillis();
        ArrayList<Variables> result = backtrackingSolveWithVAH3(variables);
        long endTime = System.currentTimeMillis();


        printResult(result, endTime, startTime);
        System.out.println();
        System.out.println("Search with forward checking");
        System.out.println(">>>>>>>>>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        startTime = System.currentTimeMillis();
        result = backtrackingSolveWithForwardCheckingWithVAH3(variablesArrayList);
        endTime = System.currentTimeMillis();

        printResult(result, endTime, startTime);
    }

    private static void callVah4(ArrayList<Variables> variables, ArrayList<Variables> variablesArrayList) {
        System.out.println();
        System.out.println("For VAH 4");
        System.out.println("Search with simple backtrack");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        long startTime = System.currentTimeMillis();
        ArrayList<Variables> result = backtrackingSolveWithVAH4(variables);
        long endTime = System.currentTimeMillis();

        printResult(result, endTime, startTime);
        System.out.println();
        System.out.println("Search with forward checking");
        System.out.println(">>>>>>>>>>>>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        startTime = System.currentTimeMillis();
        result = backtrackingSolveWithForwardCheckingWithVAH4(variablesArrayList);
        endTime = System.currentTimeMillis();

        printResult(result, endTime, startTime);
    }

    private static void callVah5(ArrayList<Variables> variables, ArrayList<Variables> variablesArrayList) {
        Collections.shuffle(variables);
        Collections.shuffle(variablesArrayList);
        System.out.println("");
        System.out.println("For VAH 5");
        System.out.println("Search with simple backtrack");
        System.out.println(">>>>>>>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        long startTime = System.currentTimeMillis();
        ArrayList<Variables> result = backtrackingSolveWithVAH5(variables);
        long endTime = System.currentTimeMillis();


        printResult(result, endTime, startTime);

        System.out.println();
        System.out.println("Search with forward checking");
        System.out.println(">>>>>>>>>>");
        //Start the search
        nodeCount = 1;
        backtrackCount = 0;
        startTime = System.currentTimeMillis();
        result = backtrackingSolveWithForwardCheckingWithVAH5(variablesArrayList);
        endTime = System.currentTimeMillis();

        printResult(result, endTime, startTime);
    }

    private static void printResult(ArrayList<Variables> result, Long endTime, Long startTime) {
        //Print the solution
        if (result != null) {
            System.out.println("Solution: ");
            for (int i = 0; i < result.size(); i++) {
                if (i % order == 0)
                    System.out.println();
                System.out.print(result.get(i).value + " ");
            }
            System.out.println();
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            System.out.println("Number of nodes explored: " + nodeCount);
            System.out.println("Number of backtracks: " + backtrackCount);
        } else {
            System.out.println("No solution found");
        }
    }

    private static boolean isConsistentWithConstraints(ArrayList<Variables> variablesList, Variables variable, int value) {
        for (int i = 0; i < variablesList.size(); i++) {
            Variables var = variablesList.get(i);
            //Check if the value is in the same row
            if ((var.id - variable.id) % order == 0 && var.value == value && var.value != 0) {
                return false;
            }
            //Check if the value is in the same column
            if (variable.id / order == var.id / order && var.value == value && var.value != 0) {
                return false;
            }
        }
        return true;
    }

    private static void forwardChecking(Variables variable) {
        for (Variables constraint : variable.unassignedConstraints) {
            constraint.domainList.remove((Integer) variable.value);
        }
    }

    private static void reassignDomainList(Variables variable) {
        for (Variables constraint : variable.unassignedConstraints) {
            constraint.domainList.add(variable.value);
        }
    }

    private static ArrayList<Variables> backtrackingSolveWithVAH1(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH1(variables);
//        variable.domainList.sort(Collections.reverseOrder());
        Collections.shuffle(variable.domainList);
//        ArrayList<Integer> domainList = variable.domainList;
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                ArrayList<Variables> result = backtrackingSolveWithVAH1(variables);
                if (result != null) {
                    return result;
                }
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithForwardCheckingWithVAH1(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH1(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
        Collections.shuffle(variable.domainList);
//        ArrayList<Integer> domainList = variable.domainList;
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                forwardChecking(variable);
                ArrayList<Variables> result = backtrackingSolveWithForwardCheckingWithVAH1(variables);
                if (result != null) {
                    return result;
                }
                reassignDomainList(variable);
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithVAH2(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH2(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
        ArrayList<Integer> domainList = getDomainList(variable);
//        ArrayList<Integer> domainList = variable.domainList;
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                ArrayList<Variables> result = backtrackingSolveWithVAH2(variables);
                if (result != null) {
                    return result;
                }
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithForwardCheckingWithVAH2(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH2(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
        ArrayList<Integer> domainList = getDomainList(variable);
//        ArrayList<Integer> domainList = variable.domainList;
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                forwardChecking(variable);
                ArrayList<Variables> result = backtrackingSolveWithForwardCheckingWithVAH2(variables);
                if (result != null) {
                    return result;
                }
                reassignDomainList(variable);
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithVAH3(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH3(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                ArrayList<Variables> result = backtrackingSolveWithVAH3(variables);
                if (result != null) {
                    return result;
                }
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithForwardCheckingWithVAH3(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH3(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                forwardChecking(variable);
                ArrayList<Variables> result = backtrackingSolveWithForwardCheckingWithVAH3(variables);
                if (result != null) {
                    return result;
                }
                reassignDomainList(variable);
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithVAH4(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH4(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
//        Collections.shuffle(variable.domainList);
//        ArrayList<Integer> domainList = variable.domainList;
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                ArrayList<Variables> result = backtrackingSolveWithVAH4(variables);
                if (result != null) {
                    return result;
                }
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithForwardCheckingWithVAH4(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH4(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
//        Collections.shuffle(variable.domainList);
//        ArrayList<Integer> domainList = variable.domainList;
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                forwardChecking(variable);
                ArrayList<Variables> result = backtrackingSolveWithForwardCheckingWithVAH4(variables);
                if (result != null) {
                    return result;
                }
                reassignDomainList(variable);
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithVAH5(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH5(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                ArrayList<Variables> result = backtrackingSolveWithVAH5(variables);
                if (result != null) {
                    return result;
                }
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static ArrayList<Variables> backtrackingSolveWithForwardCheckingWithVAH5(ArrayList<Variables> variables) {
        if (isComplete(variables)) {
            return variables;
        }
        Variables variable = selectUnassignedVariableVAH5(variables);
        if (variable == null)
            return null;
//        variable.domainList.sort(Collections.reverseOrder());
        ArrayList<Integer> domainList = getDomainList(variable);
        for (int i = 0; i < domainList.size(); i++) {
            nodeCount++;
            int value = domainList.get(i);
            if (isConsistentWithConstraints(variables, variable, value)) {
                variable.value = value;
                forwardChecking(variable);
                ArrayList<Variables> result = backtrackingSolveWithForwardCheckingWithVAH5(variables);
                if (result != null) {
                    return result;
                }
                reassignDomainList(variable);
                variable.value = 0;
            }
        }
        backtrackCount++;
        return null;
    }

    private static int getUnassignedVariablesCount(Variables variable) {
        int count = 0;
        for (int i = 0; i < variable.unassignedConstraints.size(); i++) {
            if (variable.unassignedConstraints.get(i).value == 0) {
                count++;
            }
        }
        return count;
    }

    private static Variables selectUnassignedVariableVAH5(ArrayList<Variables> variables) {
//        Collections.shuffle(variables);
        for (Variables variable : variables) {
            if (variable.value == 0) {
                return variable;
            }
        }
        return null;
    }

    private static Variables selectUnassignedVariableVAH1(ArrayList<Variables> variables) {
        //Find the variable with the smallest domain
        int min = Integer.MAX_VALUE;
        Variables variableWithSmallestDomain = null;
        for (Variables var : variables) {
            if (var.domainList.size() < min && var.domainList.size() != 0 && var.value == 0) {
                min = var.domainList.size();
                variableWithSmallestDomain = var;
            }
        }
        return variableWithSmallestDomain;
    }

    private static Variables selectUnassignedVariableVAH2(ArrayList<Variables> variables) {
        // Find the variable with the largest number of unassigned constraints
        int max = Integer.MIN_VALUE;
        Variables variableWithLargestUnassignedConstraints = null;
        for (Variables var : variables) {
            if (getUnassignedVariablesCount(var) > max && var.value == 0) {
                max = var.unassignedConstraints.size();
                variableWithLargestUnassignedConstraints = var;
            }
        }
        return variableWithLargestUnassignedConstraints;
    }

    private static Variables selectUnassignedVariableVAH3(ArrayList<Variables> variables) {
        //Find the variable with the smallest domain, ties broken by the largest number of unassigned constraints
        int min = Integer.MAX_VALUE;
        Variables variableWithSmallestDomain = null;
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).domainList.size() < min && variables.get(i).domainList.size() != 0 && variables.get(i).value == 0) {
                min = variables.get(i).domainList.size();
                variableWithSmallestDomain = variables.get(i);
            }
            else if (variables.get(i).domainList.size() == min && variables.get(i).value == 0) {
                int a = getUnassignedVariablesCount(variables.get(i));
                int b = getUnassignedVariablesCount(variableWithSmallestDomain);
                if (a > b) {
                    variableWithSmallestDomain = variables.get(i);
                }
            }
        }
        return variableWithSmallestDomain;
    }

    private static Variables selectUnassignedVariableVAH4(ArrayList<Variables> variables) {
        //Find the variable that minimizes the ratio of VAH1 and VAH2
        double min = Double.MAX_VALUE;
        Variables variableWithSmallestRatio = null;
        Variables var1 = selectUnassignedVariableVAH1(variables);
        Variables var2 = selectUnassignedVariableVAH2(variables);
        double a = getUnassignedVariablesCount(var1);
        double b = getUnassignedVariablesCount(var2);
        double ratio1 = a == 0 ? var1.domainList.size() : var1.domainList.size() / a;
        double ratio2 = b == 0 ? var2.domainList.size() : var2.domainList.size() / b;
        if (ratio1 < ratio2) {
            return var1;
        }
        else {
            return var2;
        }
    }

    private static boolean isComplete(ArrayList<Variables> variables) {
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).value == 0) {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<Integer> getDomainList(Variables variable) {
        //Get the domain list for the variable by least constraining value
        HashMap<Integer, Integer> domainMap = new HashMap<>();
        for (int i = 0; i < variable.domainList.size(); i++) {
            int value = variable.domainList.get(i);
            int count = 0;
            for (int j = 0; j < variable.unassignedConstraints.size(); j++) {
                Variables unassignedVariable = variable.unassignedConstraints.get(j);
                if (unassignedVariable.domainList.contains(value)) {
                    count++;
                }
            }
            domainMap.put(value, count);
        }
        //Sort the domain map keyset by ascending order of the value
        List<Integer> domainMapKeySet = new ArrayList<>(domainMap.keySet());
        domainMapKeySet.sort(Comparator.comparing(domainMap::get));
        return new ArrayList<>(domainMapKeySet);
    }


}
