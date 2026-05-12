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

        System.out.println("\n=== Manager Panel: " + manager + " ===");
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
        System.out.println("1  - Courses");
        System.out.println("2  - Add course");
        System.out.println("3  - Assign course");
        System.out.println("4  - Students by GPA");
        System.out.println("5  - Students by name");
        System.out.println("6  - Teachers");
        System.out.println("7  - Report");
        System.out.println("8  - Requests");
        System.out.println("9  - Publish news");
        System.out.println("10 - News list");
        System.out.println("11 - Create organization"); // ➕ added
        System.out.println("0  - Logout");
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

        System.out.print("Course ID: ");
        String cid = reader.readLine().trim();

        System.out.print("Teacher login: ");
        String login = reader.readLine().trim();

        Course course = DataStorage.getCourses().stream()
                .filter(c -> c.getCourseId().equals(cid))
                .findFirst()
                .orElse(null);

        Teacher teacher = DataStorage.getUsers().stream()
                .filter(u -> u instanceof Teacher && u.getLogin().equals(login))
                .map(u -> (Teacher) u)
                .findFirst()
                .orElse(null);

        if (course == null || teacher == null) {
            errorMsg("Not found.");
            return;
        }

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
}