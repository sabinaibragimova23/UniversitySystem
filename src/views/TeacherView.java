package views;

import controllers.*;
import model.users.*;
import model.academic.*;
import model.comunication.RecommendationLetter;
import model.comparators.*;
import model.enums.*;
import model.research.*;
import core.DataStorage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.*;

public class TeacherView extends BaseView {

    public static void run(Teacher teacher) throws IOException {

        System.out.println("\n=== TEACHER PANEL: " + teacher + " ===");

        boolean running = true;

        while (running) {

            menu();

            int option = readIntRange("> ", 0, 10);

            switch (option) {

                case 1 -> teacher.viewCourses();

                case 2 -> putMarkMenu(teacher);

                case 3 -> viewStudents();

                case 4 -> sendMessageMenu(teacher);

                case 5 -> sendComplaintMenu(teacher);

                case 6 -> teacher.generateReport();

                case 7 -> detailedReportMenu(teacher);

                case 8 -> researchMenu(teacher);

                case 9 -> writeRecommendationMenu(teacher);

                case 10 -> System.out.println(
                        "Rating: "
                                + String.format("%.1f", teacher.getRating())
                );

                case 0 -> {
                    System.out.println("Session closed.");
                    running = false;
                }

                default -> errorMsg("Unknown option.");
            }
        }
    }

    private static void menu() {

        separator();

        System.out.println("TEACHER MENU");
        System.out.println("1  - My courses");
        System.out.println("2  - Put mark");
        System.out.println("3  - Students list");
        System.out.println("4  - Send message");
        System.out.println("5  - Send complaint");
        System.out.println("6  - Simple report");
        System.out.println("7  - Detailed report");
        System.out.println("8  - Research");
        System.out.println("9  - Recommendation letter");
        System.out.println("10 - Rating");
        System.out.println("0  - Logout");
    }

    // ===== IMPROVED PUT MARK (from second code) =====
    private static void putMarkMenu(Teacher teacher) throws IOException {

        List<Course> myCourses = teacher.getCourses();

        if (myCourses.isEmpty()) {
            errorMsg("You have no assigned courses.");
            return;
        }

        System.out.println("=== My Courses ===");

        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + " - " + myCourses.get(i).getName());
        }

        int ci = readIntRange("Pick course: ", 1, myCourses.size());
        Course course = myCourses.get(ci - 1);

        List<Student> students = course.getStudents();

        if (students.isEmpty()) {
            errorMsg("No students in this course.");
            return;
        }

        System.out.println("=== Students ===");

        for (int i = 0; i < students.size(); i++) {

            Student s = students.get(i);

            System.out.println(
                    (i + 1)
                            + " - "
                            + s.getFirstName()
                            + " "
                            + s.getLastName()
            );
        }

        int si = readIntRange("Pick student: ", 1, students.size());
        Student student = students.get(si - 1);

        double a1  = readIntRange("Att1 (0-30): ", 0, 30);
        double a2  = readIntRange("Att2 (0-30): ", 0, 30);
        double fin = readIntRange("Final (0-40): ", 0, 40);

        Mark mark = new Mark(a1, a2, fin, course);

        CourseController.putMark(teacher, student, course, mark);

        successMsg("Mark saved: " + mark.getGrade());
    }

    // ===== SAFE VIEW STUDENTS (FIXED NULL ISSUES) =====
    private static void viewStudents() {

        System.out.println("=== All Students ===");

        DataStorage.getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .forEach(s ->
                        System.out.println(
                                s.getFirstName()
                                        + " "
                                        + s.getLastName()
                        )
                );
    }

    // ===== SAFE SEND MESSAGE =====
    private static void sendMessageMenu(Teacher teacher) throws IOException {

        String login = readString("Recipient login: ");

        String msg = readString("Message: ");

        User receiver = DataStorage.getUsers().stream()

                .filter(u -> u.getLogin() != null)
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);

        if (receiver == null) {
            errorMsg("User not found: " + login);
            return;
        }

        teacher.sendMessage(receiver, msg);

        successMsg("Message sent.");
    }

    private static void sendComplaintMenu(Teacher teacher) throws IOException {

        String slog = readString("Student login: ");

        Student student = findStudent(slog);

        if (student == null) {
            errorMsg("Student not found: " + slog);
            return;
        }

        System.out.println("Urgency: 1-LOW 2-MEDIUM 3-HIGH");

        int u = readIntRange("> ", 1, 3);

        UrgencyLevel level = UrgencyLevel.values()[u - 1];

        String reason = readString("Reason: ");

        teacher.sendComplaint(student, level, reason);

        successMsg("Complaint sent.");
    }

    private static void detailedReportMenu(Teacher teacher) throws IOException {

        String cid = readString("Course ID: ");

        Course course = findCourse(cid);

        if (course == null) {
            errorMsg("Course not found: " + cid);
            return;
        }

        teacher.generateDetailedReport(course);
    }

    // ===== RESEARCH (kept your version) =====
    private static void researchMenu(Teacher teacher) throws IOException {

        if (!teacher.isResearcher()) {

            System.out.println("Become researcher? 1-Yes 0-No");

            if (readIntRange("> ", 0, 1) == 1) {
                teacher.becomeResearcher();
            }
            return;
        }

        ResearchDecorator profile = teacher.getResearchProfile();

        System.out.println("1-Publish 2-Citations 3-Date 4-Pages 5-H-index 6-All 7-Top");

        int opt = readIntRange("> ", 1, 7);

        switch (opt) {

            case 1 -> publishPaperMenu(teacher);

            case 2 -> ResearchController.printPapers(profile, new PaperCitationComparator());

            case 3 -> ResearchController.printPapers(profile, new PaperDateComparator());

            case 4 -> ResearchController.printPapers(profile, new PaperPageComparator());

            case 5 -> ResearchController.showHIndex(profile);

            case 6 -> ResearchController.printAllPapers(new PaperCitationComparator());

            case 7 -> ResearchController.showTopCited();
        }
    }

    private static void publishPaperMenu(Teacher teacher) throws IOException {

        String title = readString("Title: ");
        String journal = readString("Journal: ");
        int pages = readInt("Pages: ");
        String doi = readString("DOI: ");
        String authors = readString("Authors: ");

        ResearchPaper paper = new ResearchPaper(
                title,
                Arrays.asList(authors.split(",")),
                journal,
                pages,
                new Date(),
                doi
        );

        ResearchController.publishPaper(
                teacher.getResearchProfile(),
                paper
        );

        successMsg("Paper published.");
    }

    private static void writeRecommendationMenu(Teacher teacher) throws IOException {

        String slog = readString("Student login: ");

        Student student = findStudent(slog);

        if (student == null) {
            errorMsg("Student not found: " + slog);
            return;
        }

        String purpose = readString("Purpose: ");
        String body = readString("Body: ");

        RecommendationLetter letter =
                teacher.writeRecommendation(student, purpose, body);

        letter.print();
    }

   
    private static Student findStudent(String login) {

        return DataStorage.getUsers().stream()

                .filter(u -> u instanceof Student)

                .map(u -> (Student) u)

                .filter(s -> s.getLogin() != null && s.getLogin().equals(login))

                .findFirst()
                .orElse(null);
    }

    private static Course findCourse(String id) {

        return DataStorage.getCourses().stream()

                .filter(c -> c.getCourseId().equals(id))

                .findFirst()
                .orElse(null);
    }
}