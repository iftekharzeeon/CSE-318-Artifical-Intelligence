import java.util.ArrayList;

public class Student {
    String studentId;
    ArrayList<Course> enrolledCourses;

    public Student() {
        this.enrolledCourses = new ArrayList<>();
    }

    public double getExponentialPenalty() {
        double penalty = 0;
        for (int i = 0; i < enrolledCourses.size() - 1; i++) {
            for (int j = i+1; j < enrolledCourses.size(); j++) {
                int gapBetweenExam = Math.abs(enrolledCourses.get(i).timeSlot - enrolledCourses.get(j).timeSlot);
                if (gapBetweenExam <= 5) {
                    penalty += Math.pow(2, (5-gapBetweenExam));
                }
            }
        }
        return penalty;
    }

    public double getLinearPenalty() {
        double penalty = 0;
        for (int i = 0; i < enrolledCourses.size() - 1; i++) {
            for (int j = i+1; j < enrolledCourses.size(); j++) {
                int gapBetweenExam = Math.abs(enrolledCourses.get(i).timeSlot - enrolledCourses.get(j).timeSlot);
                if (gapBetweenExam <= 5) {
                    penalty += 2*(5 - gapBetweenExam);
                }
            }
        }
        return penalty;
    }
}
