package views;

import controllers.*;
import model.users.*;
import model.academic.*;
import model.communication.RecommendationLetter;
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
        System.out.println("\n--- TEACHER PANEL: " + teacher + " ---");
        boolean running = true;
        while (running) {

            menu();
            int option = readIntRange("> ", 0, 11);

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
                        "Rating: " + String.format("%.1f", teacher.getRating())
                );
                case 11 -> submitRequestMenu(teacher);
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
        System.out.println("1 - My courses");
        System.out.println("2 - Put mark");
        System.out.println("3 - Students list");
        System.out.println("4 - Send message");
        System.out.println("5 - Send complaint");
        System.out.println("6 - Simple report");
        System.out.println("7 - Detailed report");
        System.out.println("8 - Research");
        System.out.println("9 - Recommendation letter");
        System.out.println("10 - Rating");
        System.out.println("11 - Submit request");
        System.out.println("0 - Logout");
    }

    private static void putMarkMenu(Teacher teacher) throws IOException {
        List<Course> myCourses = teacher.getCourses();
        if (myCourses.isEmpty()) {
            errorMsg("You have no assigned courses.");
            return;
        }

        System.out.println("My Courses:");

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

        System.out.println("Students:");

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.println(
                    (i + 1) + " - " + s.getFirstName() + " " + s.getLastName());
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

    private static void viewStudents() {

        System.out.println("--- All Students ---");

        DataStorage.getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .forEach(s ->
                        System.out.println(s.getFirstName() + " " + s.getLastName())
                );
    }

    private static void sendMessageMenu(Teacher teacher) throws IOException {
        List<User> allUsers = DataStorage.getUsers().stream()
                .filter(u -> u.getLogin() != null
                        && !u.getLogin().equals(teacher.getLogin()))
                .collect(Collectors.toList());

        if (allUsers.isEmpty()) {
            errorMsg("No other users in the system.");
            return;
        }
        System.out.println("USERS:");

        for (int i = 0; i < allUsers.size(); i++) {
            User u = allUsers.get(i);
            System.out.println(
                    (i + 1)
                    + " - ["
                    + u.getClass().getSimpleName()
                    + "] "
                    + u.getFirstName()
                    + " "
                    + u.getLastName()
            );
        }

        int ri = readIntRange("Send to: ", 1, allUsers.size());
        String msg = readString("Message: ");
        teacher.sendMessage(allUsers.get(ri - 1), msg);
        successMsg("Message sent.");
    }

    private static void sendComplaintMenu(Teacher teacher) throws IOException {
        List<Student> students = DataStorage.getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            errorMsg("No students in the system.");
            return;
        }

        System.out.println("STUDENTS:");

        for (int i = 0; i < students.size(); i++) {
            System.out.println(
                    (i + 1)
                    + " - "
                    + students.get(i).getFirstName()
                    + " "
                    + students.get(i).getLastName()
            );
        }

        int si = readIntRange("Pick student: ", 1, students.size());
        Student student = students.get(si - 1);
        System.out.println("Urgency: 1-LOW  2-MEDIUM  3-HIGH");
        int u = readIntRange("> ", 1, 3);
        UrgencyLevel level = UrgencyLevel.values()[u - 1];
        String reason = readString("Reason: ");
        teacher.sendComplaint(student, level, reason);
        successMsg("Complaint sent");
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


    private static void researchMenu(Teacher teacher) throws IOException {
        if (!teacher.isResearcher()) {
            System.out.println("Become researcher? 1-Yes 0-No");
            if (readIntRange("> ", 0, 1) == 1) {
                teacher.becomeResearcher();
            }
            return;
        }

        ResearchDecorator profile = teacher.getResearchProfile();

        System.out.println("1 - Publish paper");
        System.out.println("2 - My papers by citations");
        System.out.println("3 - My papers by date");
        System.out.println("4 - My papers by pages");
        System.out.println("5 - My h-index");
        System.out.println("6 - All university papers");
        System.out.println("7 - Top cited researcher");
        System.out.println("8 - Top cited by year");

        int opt = readIntRange("> ", 1, 8);

        switch (opt) {

            case 1 -> publishPaperMenu(teacher);
            case 2 -> ResearchController.printPapers(profile, new PaperCitationComparator());
            case 3 -> ResearchController.printPapers(profile, new PaperDateComparator());
            case 4 -> ResearchController.printPapers(profile, new PaperPageComparator());
            case 5 -> ResearchController.showHIndex(profile);
            case 6 -> ResearchController.printAllPapers(new PaperCitationComparator());
            case 7 -> ResearchController.showTopCited();
            case 8 -> {
                int year = readInt("Year: ");
                ResearchController.showTopCitedByYear(year);
            }
        }
    }

    private static void publishPaperMenu(Teacher teacher) throws IOException {

        String title = readString("Title: ");
        String journal = readString("Journal: ");
        int pages = readInt("Pages: ");
        String doi = readString("DOI: ");
        String authors = readString("Authors: ");

        ResearchPaper paper = new ResearchPaper(title, Arrays.asList(authors.split(",")), journal, pages, new Date(), doi);

        ResearchController.publishPaper(
                teacher.getResearchProfile(),
                paper
        );

        successMsg("Paper published");
    }

    private static void writeRecommendationMenu(Teacher teacher) throws IOException {

        List<Student> students = DataStorage.getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());

        if (students.isEmpty()) {
            errorMsg("No students in the system");
            return;
        }

        System.out.println("STUDENTS:");

        for (int i = 0; i < students.size(); i++) {
            System.out.println(
                    (i + 1)
                    + " - "
                    + students.get(i).getFirstName()
                    + " "
                    + students.get(i).getLastName()
                    + " | GPA: "
                    + String.format("%.2f", students.get(i).getGpa())
            );
        }

        int si = readIntRange("Pick student: ", 1, students.size());
        Student student = students.get(si - 1);
        String purpose = readString("Purpose: ");
        String body = readString("Body: ");
        RecommendationLetter letter =
                teacher.writeRecommendation(student, purpose, body);

        letter.print();
    }

   

    private static void submitRequestMenu(Teacher teacher) throws IOException {
        String desc = readString("Describe the problem: ");
        TechSupportController.createRequest(desc, teacher);
        successMsg("Request submitted. Tech support will be notified.");
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