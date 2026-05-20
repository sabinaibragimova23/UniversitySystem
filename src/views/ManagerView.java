package views;

import controllers.*;
import core.DataStorage;
import model.academic.Course;
import model.comparators.StudentGPAComparator;
import model.comparators.StudentNameComparator;
import model.enums.CourseType;
import model.research.News;
import model.users.Manager;
import model.users.Student;
import model.users.Teacher;

import java.io.IOException;
import java.util.Date;

public class ManagerView extends BaseView {

    public static void run(Manager manager) throws IOException {

        System.out.println("\n--- Manager Panel: " + manager + " ---");
        boolean active = true;

        while (active) {
            menu();

            int option;
            try {
                option = Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                errorMsg("Invalid input.");
                continue;
            }

            switch (option) {
                case 1 -> CourseController.listCourses();
                case 2 -> addCourse();
                case 3 -> assignCourse(manager);
                case 4 -> manager.viewStudentsInfo(new StudentGPAComparator());
                case 5 -> manager.viewStudentsInfo(new StudentNameComparator());
                case 6 -> manager.viewTeachersInfo();
                case 7 -> manager.createReport();
                case 8 -> manager.viewRequests();
                case 9 -> publishNews(manager);
                case 10 -> NewsController.listNews();
                case 11 -> createOrgMenu();
                case 12 -> addCommentMenu();
                case 0 -> {
                    System.out.println("[Manager] Logged out.");
                    active = false;
                }
                default -> errorMsg("Unknown option.");
            }
        }
    }

    private static void menu() {
        separator();
        System.out.println("MANAGER MENU:");
        System.out.println("1 - Courses");
        System.out.println("2 - Add course");
        System.out.println("3 - Assign course");
        System.out.println("4 - Students by GPA");
        System.out.println("5 - Students by name");
        System.out.println("6 - Teachers");
        System.out.println("7 - Report");
        System.out.println("8 - Requests");
        System.out.println("9 - Publish news");
        System.out.println("10 - News list");
        System.out.println("11 - Create organization");
        System.out.println("12 - Add comment to news");
        System.out.println("0 - Logout");
        System.out.print("> ");
    }


    private static void addCourse() throws IOException {

        System.out.print("Course ID: ");
        String id = reader.readLine().trim();

        System.out.print("Name: ");
        String name = reader.readLine().trim();

        System.out.println("Type: 1-MAJOR 2-MINOR 3-FREE_ELECTIVE");
        int t = Integer.parseInt(reader.readLine().trim());

        CourseType type = switch (t) {
            case 2 -> CourseType.MINOR;
            case 3 -> CourseType.FREE_ELECTIVE;
            default -> CourseType.MAJOR;
        };

        System.out.print("Credits: ");
        int credits = Integer.parseInt(reader.readLine().trim());
        boolean ok = CourseController.addCourse(id, name, type, credits);
        if (ok) successMsg("Course added!");
    }

    private static void assignCourse(Manager manager) throws IOException {
        java.util.List<Course> courses = DataStorage.getCourses();
        if (courses.isEmpty()) {
            errorMsg("No courses available. Add a course first.");
            return;
        }

        System.out.println("COURSES");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println("  " + (i + 1) + " - " + courses.get(i));
        }

        int ci = readIntRange("Pick course: ", 1, courses.size());
        Course course = courses.get(ci - 1);
        java.util.List<Teacher> teachers = DataStorage.getUsers().stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u)
                .collect(java.util.stream.Collectors.toList());

        if (teachers.isEmpty()) {
            errorMsg("No teachers registered yet.");
            return;
        }

        System.out.println("TEACHERS");

        for (int i = 0; i < teachers.size(); i++) {
            System.out.println(
                    "  " + (i + 1)
                    + " - "
                    + teachers.get(i).getFirstName()
                    + " "
                    + teachers.get(i).getLastName()
                    + " ("
                    + teachers.get(i).getPosition()
                    + ")"
            );
        }

        int ti = readIntRange("Pick teacher: ", 1, teachers.size());
        Teacher teacher = teachers.get(ti - 1);

        CourseController.assignToTeacher(course, teacher, manager);
        successMsg("Assigned.");
    }


    private static void publishNews(Manager manager) throws IOException {

        String title = readString("Title: ");
        String content = readString("Content: ");

        System.out.println("Topic: 1-Research  2-General");
        int t = readIntRange("> ", 1, 2);

        String topic = (t == 1) ? "Research" : "General";

        News news = new News(title, content, topic, new Date());

        manager.manageNews(news);

        if (t == 1) {
            System.out.println("[PINNED] Research news published!");
        }

        successMsg("News published.");
    }


    private static void createOrgMenu() throws IOException {
        String name = readString("Organization name: ");
        model.users.StudentOrganization org =
                new model.users.StudentOrganization(name);
        DataStorage.addOrganization(org);
        DataStorage.save();
        successMsg("Organization created: " + name);
    }

    private static void addCommentMenu() throws IOException {
        java.util.List<model.research.News> newsList = DataStorage.getNews();
        if (newsList.isEmpty()) {
            System.out.println("No news yet.");
            return;
        }

        System.out.println("NEWS LIST");
        for (int i = 0; i < newsList.size(); i++) {
            System.out.println("  " + (i + 1) + " - " + newsList.get(i).getTitle());
        }

        int idx = readIntRange("Pick news: ", 1, newsList.size());
        model.research.News chosen = newsList.get(idx - 1);
        String comment = readString("Comment: ");
        chosen.addComment(comment);
        successMsg("Comment added to: " + chosen.getTitle());
    }
}