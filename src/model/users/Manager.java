package model.users;

import core.DataStorage;
import model.academic.Course;
import model.academic.Enrollment;
import model.comunication.Request;
import model.enums.Language;
import model.enums.ManagerType;
import model.research.News;
import java.util.*;
import java.util.stream.Collectors;


public class Manager extends Employee {

    private ManagerType type;
    private List<Student> managedStudents;

    public Manager(int id,
                   String login,
                   String password,
                   String firstName,
                   String lastName,
                   ManagerType type) {

    	super(id,
    	          login,
    	          password,
    	          firstName,
    	          lastName);
    	this.type = type;
        this.managedStudents = new ArrayList<>();
    }

    public void assignCourse(Course course, Teacher teacher) {

        teacher.assignCourse(course);
        course.addTeacher(teacher);

        System.out.println(
                "[Manager] "
                + teacher.getFirstName()
                + " assigned to "
                + course.getName()
        );
    }

    public void approveRegistration(Enrollment enrollment) {
        enrollment.approve();
    }

    public void addCourseForReg(Course course,
                                String major,
                                int year) {

        course.setMajor(major);
        course.setYear(year);

        DataStorage.getInstance().addCourse(course);
        System.out.println(
                "[Manager] Added for registration: "
                + course
        );
    }

    public String createReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Academic Performance Report ===\n");
        DataStorage.getInstance()
                .getUsers()
                .stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted()
                .forEach(s -> sb.append("  ").append(s).append("\n"));

        System.out.print(sb);
        return sb.toString();
    }

    public void manageNews(News news) {
        DataStorage.getInstance().addNews(news);
        System.out.println(
                "[Manager] News published: "
                + news.getTitle()
        );
    }

    public List<Student> viewStudentsInfo(Comparator<Student> comparator) {
        List<Student> list = DataStorage.getInstance()
                .getUsers()
                .stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());
        if (comparator != null) {
            list.sort(comparator);
        }

        for (Student s : list) {
            System.out.println("  " + s);
        }

        return list;
    }

    public void viewTeachersInfo() {
        DataStorage.getInstance()
                .getUsers()
                .stream()
                .filter(u -> u instanceof Teacher)
                .forEach(t -> System.out.println("  " + t));
    }

    public List<Request> viewRequests() {
        List<Request> requests = DataStorage.getInstance().getRequests();
        for (Request r : requests) {
            System.out.println("  " + r);
        }

        return requests;
    }

    public ManagerType getManagerType() {
        return type;
    }

    public List<Student> getManagedStudents() {
        return managedStudents;
    }

    @Override
    public String toString() {
        return "Manager{'"
                + firstName
                + " "
                + lastName
                + "', "
                + type
                + "}";
    }

    public void setManagerType(ManagerType mtType) {
        this.type = mtType;
    }
}