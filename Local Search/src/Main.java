import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static boolean isExponential = true;
    private static int penaltyIterationTime = 3000;

    public static void main(String[] args) throws IOException {
        //Exponential Penalty
        isExponential = true;
        System.out.println(">>>>>>>>>>>>>>Exponential Penalty>>>>>>>>>>>>>");
        runScheme("car-f-92");
        runScheme("car-s-91");
        runScheme("kfu-s-93");
        runScheme("tre-s-92");
        runScheme("yor-f-83");

        //Linear Penalty
        isExponential = false;
        System.out.println(">>>>>>>>>>>>>>Linear Penalty>>>>>>>>>>>>>");
        runScheme("car-f-92");
        runScheme("car-s-91");
        runScheme("kfu-s-93");
        runScheme("tre-s-92");
        runScheme("yor-f-83");
    }

    private static void penaltyReduction(ArrayList<Course> courseList, ArrayList<Student> studentList, int penaltyType) {
        if (penaltyType == 1) {
            //Kempe
            for (int i = 0; i < penaltyIterationTime; i++) {
                int courseIndex = (int) (Math.random() * courseList.size());
                Course randomCourse = courseList.get(courseIndex);
                if (randomCourse.conflictingCourses.size() != 0) {
                    int conflictCourseIndex = (int) (Math.random() * randomCourse.conflictingCourses.size());
                    penaltyReductionByKempeChain(courseList, studentList, randomCourse, randomCourse.conflictingCourses.get(conflictCourseIndex).timeSlot);
                }
            }
        } else if (penaltyType == 2) {
            //Pair swap
            for (int i = 0; i < penaltyIterationTime; i++) {
                int course1Index = (int) (Math.random() * courseList.size());
                int course2Index = (int) (Math.random() * courseList.size());
                if (course1Index == course2Index)
                    continue;
                Course course1 = courseList.get(course1Index);
                Course course2 = courseList.get(course2Index);
                penaltyReductionByPairSwap(studentList, course1, course2);
            }
        }
    }

    private static void penaltyReductionByPairSwap(ArrayList<Student> studentList, Course randomCurrentCourse1, Course randomCurrentCourse2) {
        int timeslot1 = randomCurrentCourse1.timeSlot;
        int timeslot2 = randomCurrentCourse2.timeSlot;

        //Get the current penalty
        double currentPenalty = averagePenalty(studentList);

        // Check if swapping violates constraints
        for (Course conflictingCourse1 : randomCurrentCourse1.conflictingCourses) {
            if (conflictingCourse1.timeSlot == timeslot2)
                return;
        }

        for (Course conflictingCourse2 : randomCurrentCourse2.conflictingCourses) {
            if (conflictingCourse2.timeSlot == timeslot1) {
                return;
            }
        }

        randomCurrentCourse1.timeSlot = timeslot2;
        randomCurrentCourse2.timeSlot = timeslot1;

        //Check if penalty reduced, if not revert
        double newPenalty = averagePenalty(studentList);
        if (newPenalty > currentPenalty) {
            randomCurrentCourse1.timeSlot = timeslot1;
            randomCurrentCourse2.timeSlot = timeslot2;
        }
    }

    private static void penaltyReductionByKempeChain(ArrayList<Course> courseList, ArrayList<Student> studentList, Course randomCurrentCourse, int adjacentTimeSlot) {
        buildKempeChain(randomCurrentCourse, adjacentTimeSlot);

        int currentTimeSlot = randomCurrentCourse.timeSlot;

        //Get the current penalty
        double currentPenalty = averagePenalty(studentList);

        // Interchange timeslots
        for (Course course : courseList) {
            if (course.flagValue == 1) {
                if (course.timeSlot == currentTimeSlot) {
                    course.timeSlot = adjacentTimeSlot;
                } else {
                    course.timeSlot = currentTimeSlot;
                }
            }
        }

        //Check if penalty reduced, if not revert
        double newPenalty = averagePenalty(studentList);
        if (newPenalty > currentPenalty) {
            for (Course course : courseList) {
                if (course.flagValue == 1) {
                    if (course.timeSlot == adjacentTimeSlot) {
                        course.timeSlot = currentTimeSlot;
                    } else {
                        course.timeSlot = adjacentTimeSlot;
                    }
                }
            }
        }

        // Undoing the flag
        for (Course course : courseList) {
            if (course.flagValue == 1) {
                course.flagValue = 0;
            }
        }
    }

    private static void buildKempeChain(Course course, int adjacentTimeSlot) {
        course.flagValue = 1;
        for (Course conflictingCourse : course.conflictingCourses) {
            if (conflictingCourse.flagValue == 0 && conflictingCourse.timeSlot == adjacentTimeSlot) {
                buildKempeChain(conflictingCourse, course.timeSlot);
            }
        }
    }

    private static double averagePenalty(ArrayList<Student> studentList) {
        double totalPenalty = 0;
        for (Student student : studentList) {
            if (isExponential) {
                totalPenalty += student.getExponentialPenalty();
            } else {
                totalPenalty += student.getLinearPenalty();
            }
        }
        return totalPenalty / studentList.size();
    }

    private static int create(Course course) {
        int totalTimeSlot = 0;
        ArrayList<Course> conflictingCourses = course.conflictingCourses;
        int timeSlot = 0;
        int[] conflictingTimeSlots = new int[course.conflictingCourses.size()];
        int i = 0;
        for (Course conflictingCourse : conflictingCourses) {
            conflictingTimeSlots[i] = conflictingCourse.timeSlot;
            i++;
        }
        Arrays.sort(conflictingTimeSlots);
        for (int conflictingSlot : conflictingTimeSlots) {
            if (conflictingSlot == timeSlot)
                timeSlot++;
            if (timeSlot < conflictingSlot)
                course.timeSlot = timeSlot;
        }
        if (course.timeSlot == -1) {
            course.timeSlot = timeSlot;
        }
        if (course.timeSlot > totalTimeSlot) {
            totalTimeSlot = course.timeSlot;
        }
        return totalTimeSlot;
    }

    private static void createExamSchedule(ArrayList<Course> courseList) {
        int totalTimeSlot = 0;
        for (Course course : courseList) {
            ArrayList<Course> conflictingCourses = course.conflictingCourses;
            int timeSlot = 0;
            int[] conflictingTimeSlots = new int[course.conflictingCourses.size()];
            int i = 0;
            for (Course conflictingCourse : conflictingCourses) {
                conflictingTimeSlots[i] = conflictingCourse.timeSlot;
                i++;
            }
            Arrays.sort(conflictingTimeSlots);
            for (int conflictingSlot : conflictingTimeSlots) {
                if (conflictingSlot == timeSlot)
                    timeSlot++;
                if (timeSlot < conflictingSlot)
                    course.timeSlot = timeSlot;
            }
            if (course.timeSlot == -1) {
                course.timeSlot = timeSlot;
            }
            if (course.timeSlot > totalTimeSlot) {
                totalTimeSlot = course.timeSlot;
            }

        }
        totalTimeSlot++;
        System.out.println("Total time slot: " + totalTimeSlot);
    }

    private static void largestDegreeHeuristic(ArrayList<Course> courseList) {
        // Course with the largest number of conflicts is scheduled first
        courseList.sort((c1, c2) -> c2.conflictingCourses.size() - c1.conflictingCourses.size());
        createExamSchedule(courseList);
    }

    private static void saturationDegreeHeuristic(ArrayList<Course> courseList) {
        // Course with the highest saturation degree will come first
        int totalTimeSlot = 0;
        Course course;
        while ((course = getMaximumSaturatedDegree(courseList)) != null) {
            ArrayList<Course> conflictingCourses = course.conflictingCourses;
            int timeSlot = 0;
            int[] conflictingTimeSlots = new int[course.conflictingCourses.size()];
            int i = 0;
            for (Course conflictingCourse : conflictingCourses) {
                conflictingTimeSlots[i] = conflictingCourse.timeSlot;
                i++;
            }
            Arrays.sort(conflictingTimeSlots);
            for (int conflictingSlot : conflictingTimeSlots) {
                if (conflictingSlot == timeSlot)
                    timeSlot++;
                if (timeSlot < conflictingSlot)
                    course.timeSlot = timeSlot;
            }
            if (course.timeSlot == -1) {
                course.timeSlot = timeSlot;
            }
            if (course.timeSlot > totalTimeSlot) {
                totalTimeSlot = course.timeSlot;
            }
        }
        totalTimeSlot++;
        System.out.println("Total time slot: " + totalTimeSlot);
    }

    private static void largestEnrollmentHeuristic(ArrayList<Course> courseList) {
        // Course with the largest number of enrollment is scheduled first
        courseList.sort((c1, c2) -> c2.numberOfStudentsEnrolled - c1.numberOfStudentsEnrolled);
        int totalTimeSlot = 0;
//        createExamSchedule(courseList);
        for (Course course : courseList) {
            int temp = create(course);
            if (temp > totalTimeSlot)
                totalTimeSlot = temp;
        }
        totalTimeSlot++;
        System.out.println("Total time slot: " + totalTimeSlot);
    }

    private static void randomHeuristic(ArrayList<Course> courseList) {
        Collections.shuffle(courseList);
        int totalTimeSlot = 0;
//        createExamSchedule(courseList);
        for (Course course : courseList) {
            int temp = create(course);
            if (temp > totalTimeSlot)
                totalTimeSlot = temp;
        }
        totalTimeSlot++;
        System.out.println("Total time slot: " + totalTimeSlot);
    }

    private static Course getMaximumSaturatedDegree(ArrayList<Course> courseList) {
        updateSaturationDegree(courseList);
        courseList.sort((c1, c2) -> {
            if (c1.saturationDegree == c2.saturationDegree)
                return c2.conflictingCourses.size() - c1.conflictingCourses.size();
            return c2.saturationDegree - c1.saturationDegree;
        });
        int i = 0;
        Course course = courseList.get(i);
        while (course.timeSlot != -1) {
            i++;
            if (i == courseList.size()) {
                return null;
            }
            course = courseList.get(i);
        }
        return course;
    }

    private static void updateSaturationDegree(ArrayList<Course> courseList) {
        for (Course course : courseList) {
            course.saturationDegree = 0;
            ArrayList<Integer> conflictingTimeSlots = new ArrayList<>();
            for (Course conflictingCourse : course.conflictingCourses) {
                if (conflictingCourse.timeSlot != -1 && !conflictingTimeSlots.contains(conflictingCourse.timeSlot)) {
                    course.saturationDegree++;
                    conflictingTimeSlots.add(conflictingCourse.timeSlot);
                }
            }
        }
    }

    private static void runScheme(String fileNameWithoutExt) throws IOException {
        System.out.println(fileNameWithoutExt);
        System.out.println("------------------------------");
        String courseFileName = fileNameWithoutExt + ".crs";
        String studentFileName = fileNameWithoutExt + ".stu";

        String directoryName = "TorontoDataset/";
        File courseFile = new File(directoryName + courseFileName);
        File studentFile = new File(directoryName + studentFileName);

        Scanner scanner = new Scanner(courseFile);

        ArrayList<Course> courseList = new ArrayList<>();
        ArrayList<Student> studentList = new ArrayList<>();

        // Initializing courseList with enrolled student number
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String courseId = line.split(" ")[0];
            int numberOfStudentsEnrolled = Integer.parseInt(line.split(" ")[1]);
            Course course = new Course(courseId, numberOfStudentsEnrolled);
            courseList.add(course);
        }

        // Initializing studentList with enrolled courses
        scanner = new Scanner(studentFile);
        while (scanner.hasNext()) {
            Student student = new Student();
            String[] coursesEnrolled = scanner.nextLine().split(" ");
            for (String courseId : coursesEnrolled) {
                for (Course course : courseList) {
                    if (course.courseId.equals(courseId)) {
                        student.enrolledCourses.add(course);
                        break;
                    }
                }
            }
            studentList.add(student);
        }

        // Add conflicting courses to each course
        for (Student student : studentList) {
            for (Course course : student.enrolledCourses) {
                for (Course conflictingCourse : student.enrolledCourses) {
                    if (!course.equals(conflictingCourse) && !course.conflictingCourses.contains(conflictingCourse)) {
                        course.conflictingCourses.add(conflictingCourse);
                    }
                }
            }
        }

        // Running hueuritstics
//        largestDegreeHeuristic(courseList);
//        saturationDegreeHeuristic(courseList);
        largestEnrollmentHeuristic(courseList);
//        randomHeuristic(courseList);

        System.out.println("Penalty before: " + averagePenalty(studentList));
        penaltyReduction(courseList, studentList, 1); // 1->Kempe
        System.out.println("Penalty After Kempe: " + averagePenalty(studentList));
        penaltyReduction(courseList, studentList, 2); // 1->Pair Swap
        System.out.println("Penalty After Pair Swap: " + averagePenalty(studentList));
        System.out.println();

        File solFile = new File("Output/" + fileNameWithoutExt + ".sol");
        FileWriter fileWriter = new FileWriter(solFile);
        StringBuilder outputString = new StringBuilder();

        courseList.sort(Comparator.comparingInt(c -> c.timeSlot));
        for (Course course : courseList) {
            outputString.append(course.courseId).append(" ").append(course.timeSlot).append("\n");
        }

        fileWriter.write(outputString.toString());
        fileWriter.close();
    }
}
