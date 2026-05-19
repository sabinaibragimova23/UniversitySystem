package core;

import model.users.Admin;
import views.MainView;
import java.io.IOException;
import model.academic.Course;
import model.enums.CourseType;

public class Launcher {

    public static void main(String[] args) throws NumberFormatException, IOException {
    	
    	boolean adminExists = DataStorage.getUsers().stream()
                .anyMatch(u -> "admin".equals(u.getLogin()));
        if (!adminExists) {
            Admin admin = new Admin(0, "admin", "admin123", "System", "Admin");
            DataStorage.addUser(admin);
            DataStorage.save();
            System.out.println("Admin created: login=admin  password=admin123");
        }

        if (DataStorage.getCourses().isEmpty()) {

            Course c1 = new Course(
                    "CS101",
                    "Object Oriented Programming",
                    CourseType.MAJOR,
                    5
            );

            Course c2 = new Course(
                    "CS102",
                    "Database Systems",
                    CourseType.MAJOR,
                    5
            );

            Course c3 = new Course(
                    "CS103",
                    "Data Structures",
                    CourseType.MAJOR,
                    5
            );

            Course c4 = new Course(
                    "MATH201",
                    "Calculus",
                    CourseType.MINOR,
                    4
            );

            Course c5 = new Course(
                    "PHYS101",
                    "Physics",
                    CourseType.MINOR,
                    4
            );

            Course c6 = new Course(
                    "NET301",
                    "Computer Networks",
                    CourseType.FREE_ELECTIVE,
                    5
            );

            DataStorage.addCourse(c1);
            DataStorage.addCourse(c2);
            DataStorage.addCourse(c3);
            DataStorage.addCourse(c4);
            DataStorage.addCourse(c5);
            DataStorage.addCourse(c6);

            DataStorage.save();

            System.out.println("Courses loaded successfully.");
        }

        MainView.run();
    }
}