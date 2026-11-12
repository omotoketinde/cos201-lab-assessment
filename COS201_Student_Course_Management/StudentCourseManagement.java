
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Main class of the application.
public class StudentCourseManagement {

    // Stores all course objects.
    private static ArrayList<Course> courses = new ArrayList<>();

    // Allows the user to enter information.
    private static Scanner input = new Scanner(System.in);

    // Name of the file used to store courses.
    private static final String FILE_NAME = "courses.txt";


    // MAIN METHOD: Program execution starts here.
    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("   STUDENT COURSE MANAGEMENT SYSTEM");
        System.out.println("========================================");

        showMenu();
    }


    // Displays the application menu.
  public static void showMenu() {

    while (true) {

        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Add Course");
        System.out.println("2. View All Courses");
        System.out.println("3. Search Course by Code");
        System.out.println("4. Compute Total Units");
        System.out.println("5. Save Courses to File");
        System.out.println("6. Load Courses from File");
        System.out.println("7. Exit Program");
        System.out.println("================================");

        System.out.print("Enter your choice (1-7): ");

        String choice = input.nextLine().trim();

        switch (choice) {

            case "1":
                addCourse();
                break;

            case "2":
                viewCourses();
                break;

            case "3":
                searchCourse();
                break;

            case "4":
                computeTotalUnits();
                break;

            case "5":
                saveCourses();
                break;

            case "6":
                loadCourses();
                break;

            case "7":
                System.out.println("Thank you for using the system!");
                input.close();
                return;

            default:
                System.out.println("Invalid choice. Please enter a number from 1 to 7.");
        }
    }
}


    // FUNCTION 1: ADD A NEW COURSE.
    public static void addCourse() {

        System.out.println("\n--- ADD NEW COURSE ---");

        System.out.print("Enter course code: ");
        String code = input.nextLine().trim().toUpperCase();

        if (code.isEmpty() || code.contains("|") || code.contains("\n")) {
            System.out.println("Invalid course code.");
            return;
        }

        // Check whether the course already exists.
        for (Course course : courses) {

            if (course.getCourseCode().equalsIgnoreCase(code)) {
                System.out.println("This course code already exists.");
                return;
            }
        }

        System.out.print("Enter course title: ");
        String title = input.nextLine().trim();

        if (title.isEmpty() || title.contains("|") || title.contains("\n")) {
            System.out.println("Invalid course title.");
            return;
        }

        System.out.print("Enter course units: ");
        String unitInput = input.nextLine().trim();

        try {

            int units = Integer.parseInt(unitInput);

            if (units <= 0) {
                System.out.println("Course units must be greater than zero.");
                return;
            }

            // Create a new Course object.
            Course newCourse = new Course(code, title, units);

            // Add the object to the ArrayList.
            courses.add(newCourse);

            System.out.println("Course added successfully!");

        } catch (NumberFormatException e) {

            System.out.println("Invalid input. Course units must be a whole number.");
        }
    }


    // FUNCTION 2: DISPLAY ALL COURSES.
    public static void viewCourses() {

        System.out.println("\n--- REGISTERED COURSES ---");

        if (courses.isEmpty()) {

            System.out.println("No courses have been recorded yet.");
            return;
        }

        System.out.printf("%-15s %-35s %5s%n",
                "COURSE CODE", "COURSE TITLE", "UNITS");

        System.out.println("------------------------------------------------------------");

        // Iteration: loops through every course.
        for (Course course : courses) {

            course.displayCourse();
        }
    }


    // FUNCTION 3: SEARCH FOR A COURSE.
    public static void searchCourse() {

        System.out.println("\n--- SEARCH COURSE ---");

        System.out.print("Enter course code to search: ");

        String searchCode = input.nextLine().trim().toUpperCase();

        boolean found = false;

        for (Course course : courses) {

            if (course.getCourseCode().equalsIgnoreCase(searchCode)) {

                System.out.println("\nCourse found!");

                System.out.printf("%-15s %-35s %5s%n",
                        "COURSE CODE", "COURSE TITLE", "UNITS");

                course.displayCourse();

                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Course not found.");
        }
    }


    // FUNCTION 4: COMPUTE TOTAL COURSE UNITS.
    public static void computeTotalUnits() {

        System.out.println("\n--- TOTAL COURSE UNITS ---");

        int total = calculateUnitsRecursively(0);

        System.out.println("Number of courses: " + courses.size());
        System.out.println("Total credit units: " + total);
    }


    // RECURSIVE METHOD: Calculates total units.
    public static int calculateUnitsRecursively(int index) {

        // Base case: stop when all courses have been counted.
        if (index >= courses.size()) {
            return 0;
        }

        // Add current course units to the remaining total.
        return courses.get(index).getUnits()
                + calculateUnitsRecursively(index + 1);
    }


    // FUNCTION 5: SAVE COURSES TO A FILE.
    public static void saveCourses() {

        System.out.println("\n--- SAVE COURSES ---");

        try (BufferedWriter writer =
                new BufferedWriter(new FileWriter(FILE_NAME))) {

            for (Course course : courses) {

                writer.write(
                        course.getCourseCode() + "|" +
                        course.getCourseTitle() + "|" +
                        course.getUnits()
                );

                writer.newLine();
            }

            System.out.println("Courses saved successfully to " + FILE_NAME);

        } catch (IOException e) {

            System.out.println("Error saving courses: " + e.getMessage());
        }
    }


    // FUNCTION 6: LOAD COURSES FROM A FILE.
    public static void loadCourses() {

        System.out.println("\n--- LOAD COURSES ---");

        ArrayList<Course> loadedCourses = new ArrayList<>();

        try (BufferedReader reader =
                new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                try {

                    // Separate the information using the | symbol.
                    String[] details = line.split("\\|", -1);

                    if (details.length != 3) {
                        System.out.println("Skipping invalid record: " + line);
                        continue;
                    }

                    String code = details[0].trim().toUpperCase();
                    String title = details[1].trim();
                    int units = Integer.parseInt(details[2].trim());

                    if (code.isEmpty() || title.isEmpty()
                            || code.contains("|") || title.contains("|")
                            || units <= 0) {

                        System.out.println("Skipping invalid record: " + line);
                        continue;
                    }

                    // Check for duplicate course codes in the loaded file.
                    boolean duplicate = false;

                    for (Course course : loadedCourses) {

                        if (course.getCourseCode().equalsIgnoreCase(code)) {
                            duplicate = true;
                            break;
                        }
                    }

                    if (!duplicate) {
                        loadedCourses.add(new Course(code, title, units));
                    }

                } catch (NumberFormatException e) {

                    System.out.println("Skipping record with invalid units: " + line);
                }
            }

            // Replace current courses with the loaded records.
            courses.clear();
            courses.addAll(loadedCourses);

            System.out.println("Courses loaded successfully!");
            System.out.println("Total courses loaded: " + courses.size());

        } catch (IOException e) {

            System.out.println("Error loading file: " + e.getMessage());
        }
    }
}
