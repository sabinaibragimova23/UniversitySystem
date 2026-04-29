package model.patterns;

import java.util.*;
import model.users.User;
import model.academic.Course;

public class DataStorage {

    private static DataStorage instance;

    private List<User> users = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    private DataStorage() {}

    public static DataStorage getInstance() {
        if(instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public List<Course> getCourses() {
        return courses;
    }
}