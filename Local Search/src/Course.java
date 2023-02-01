import java.util.ArrayList;

public class Course {
    String courseId;
    int numberOfStudentsEnrolled;
    ArrayList<Course> conflictingCourses;
    int timeSlot;
    int flagValue;
    int saturationDegree;

    public Course(String courseId, int numberOfStudentsEnrolled) {
        this.courseId = courseId;
        this.numberOfStudentsEnrolled = numberOfStudentsEnrolled;
        this.conflictingCourses = new ArrayList<>();
        this.timeSlot = -1;
        this.flagValue = 0;
        this.saturationDegree = 0;
    }
}
