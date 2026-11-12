
public class Course {

    // These are the properties of a course.
    private String courseCode;
    private String courseTitle;
    private int units;

    // Constructor: creates a new Course object.
    public Course(String courseCode, String courseTitle, int units) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.units = units;
    }

    // Returns the course code.
    public String getCourseCode() {
        return courseCode;
    }

    // Returns the course title.
    public String getCourseTitle() {
        return courseTitle;
    }

    // Returns the course units.
    public int getUnits() {
        return units;
    }

    // Displays the course information.
    public void displayCourse() {
        System.out.printf("%-15s %-35s %5d%n",
                courseCode, courseTitle, units);
    }
}
