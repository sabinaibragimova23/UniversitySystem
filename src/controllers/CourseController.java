package controllers;

import core.DataStorage;
import model.academic.Course;
import model.academic.Mark;
import model.enums.CourseType;
import model.exceptions.CreditLimitException;
import model.users.Manager;
import model.users.Student;
import model.users.Teacher;
import model.users.User;

public class CourseController {

    public static boolean addCourse(String courseId,
                                    String name,
                                    CourseType type,
                                    int credits) {
        Course c = new Course(courseId, name, type, credits);
        DataStorage.addCourse(c);
        boolean result = DataStorage.save();
        if (result) {
            System.out.println("[Course] Added: " + c);
        }
        return result;
    }

    public static void listCourses() {
        System.out.println("=== Courses ===");
        DataStorage.getCourses().forEach(System.out::println);
    }

    public static boolean assignToTeacher(Course course,
                                          Teacher teacher,
                                          Manager manager) {
        manager.assignCourse(course, teacher);
        DataStorage.save();
        return true;
    }

    public static boolean registerStudent(Student student,
                                          Course course) {
        try {
            student.registerCourse(course);
            DataStorage.save();
            return true;
        } catch (CreditLimitException e) {
            System.out.println("[Course] Registration failed: " + e.getMessage());
            return false;
        }
    }

    public static boolean putMark(Teacher teacher,
                                  Student student,
                                  Course course,
                                  Mark mark) {

        
        Student real = student;
        for (User u : DataStorage.getUsers()) {
            if (u instanceof Student
                    && u.getLogin() != null
                    && u.getLogin().equals(student.getLogin())) {
                real = (Student) u;
                break;
            }
        }

        
        Course realCourse = course;
        for (Course c : DataStorage.getCourses()) {
            if (c.getCourseId().equals(course.getCourseId())) {
                realCourse = c;
                break;
            }
        }

        teacher.putMark(real, realCourse, mark);
        DataStorage.save();
        return true;
    }
}