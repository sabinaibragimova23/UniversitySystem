package views;

import controllers.*;
import core.DataStorage;
import model.academic.Course;
import model.academic.Mark;
import model.communication.RecommendationLetter;
import model.comparators.*;
import model.enums.UrgencyLevel;
import model.research.*;
import model.users.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class TeacherView extends BaseView {

    public static void run(Teacher teacher) throws IOException {
        System.out.println("\n=== Teacher: " + teacher.getFirstName()
            + " " + teacher.getLastName() + " ===");
        boolean running = true;
        while (running) {
            printMenu();
            int option = readIntRange("> ", 0, 12);
            switch (option) {
                case 1  -> teacher.viewCourses();
                case 2  -> putMark(teacher);
                case 3  -> showStudents();
                case 4  -> sendMsg(teacher);
                case 5  -> sendComplaint(teacher);
                case 6  -> teacher.generateReport();
                case 7  -> detailedReport(teacher);
                case 8  -> researchMenu(teacher);
                case 9  -> recommendationMenu(teacher);
                case 10 -> System.out.println("Rating: "
                    + String.format("%.1f", teacher.getRating()));
                case 11 -> submitRequest(teacher);
                case 12 -> markAttendanceMenu(teacher);
                case 0  -> { System.out.println("Logged out."); running = false; }
            }
        }
    }

    private static void printMenu() {
        separator();
        System.out.println("TEACHER MENU:");
        System.out.println("1  - My courses");
        System.out.println("2  - Put mark");
        System.out.println("3  - View students");
        System.out.println("4  - Send message");
        System.out.println("5  - Send complaint");
        System.out.println("6  - Report");
        System.out.println("7  - Detailed report");
        System.out.println("8  - Research");
        System.out.println("9  - Recommendation letter");
        System.out.println("10 - My rating");
        System.out.println("11 - Submit tech request");
        System.out.println("12 - Mark attendance");
        System.out.println("0  - Logout");
    }

    private static void putMark(Teacher teacher) throws IOException {
        List<Course> myCourses = teacher.getCourses();
        if (myCourses.isEmpty()) { errorMsg("No courses assigned."); return; }
        System.out.println("\nYour courses:");
        for (int i = 0; i < myCourses.size(); i++) {
            System.out.println((i + 1) + " - " + myCourses.get(i).getName());
        }
        int ci = readIntRange("Choose course: ", 1, myCourses.size());
        Course course = myCourses.get(ci - 1);
        List<Student> students = course.getStudents();
        if (students.isEmpty()) { errorMsg("No students in " + course.getName()); return; }
        System.out.println("\nStudents:");
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            String existing = s.getMarks().containsKey(course)
                ? " [" + s.getMarks().get(course).getGrade() + "]" : "";
            System.out.println((i + 1) + " - " + s.getFirstName()
                + " " + s.getLastName() + existing);
        }
        int si = readIntRange("Choose student: ", 1, students.size());
        Student student = students.get(si - 1);
        double a1  = readIntRange("Att1 (0-30): ", 0, 30);
        double a2  = readIntRange("Att2 (0-30): ", 0, 30);
        double fin = readIntRange("Final (0-40): ", 0, 40);
        Mark mark = new Mark(a1, a2, fin, course);
        CourseController.putMark(teacher, student, course, mark);
        successMsg("Mark saved: " + mark.getGrade() + " (total: " + mark.getTotal() + ")");
    }

    private static void showStudents() {
        System.out.println("\nAll students:");
        boolean found = false;
        for (User u : DataStorage.getUsers()) {
            if (u instanceof Student) {
                Student s = (Student) u;
                System.out.println("  " + s.getFirstName() + " " + s.getLastName()
                    + " | " + s.getMajor()
                    + " | GPA: " + String.format("%.2f", s.getGpa()));
                found = true;
            }
        }
        if (!found) System.out.println("No students registered.");
    }

    private static void sendMsg(Teacher teacher) throws IOException {
        List<User> users = new ArrayList<>();
        for (User u : DataStorage.getUsers()) {
            if (u.getLogin() != null && !u.getLogin().equals(teacher.getLogin()))
                users.add(u);
        }
        if (users.isEmpty()) { errorMsg("No other users."); return; }
        System.out.println("\nUsers:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + " - ["
                + users.get(i).getClass().getSimpleName() + "] "
                + users.get(i).getFirstName() + " " + users.get(i).getLastName());
        }
        int ri = readIntRange("Send to: ", 1, users.size());
        String msg = readString("Message: ");
        teacher.sendMessage(users.get(ri - 1), msg);
        successMsg("Sent.");
    }

    private static void sendComplaint(Teacher teacher) throws IOException {
        List<Student> students = new ArrayList<>();
        for (User u : DataStorage.getUsers()) {
            if (u instanceof Student) students.add((Student) u);
        }
        if (students.isEmpty()) { errorMsg("No students."); return; }
        System.out.println("\nStudents:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + " - "
                + students.get(i).getFirstName() + " "
                + students.get(i).getLastName());
        }
        int si = readIntRange("Pick student: ", 1, students.size());
        System.out.println("Urgency:  1-LOW  2-MEDIUM  3-HIGH");
        int u = readIntRange("> ", 1, 3);
        UrgencyLevel level = UrgencyLevel.values()[u - 1];
        String reason = readString("Reason: ");
        teacher.sendComplaint(students.get(si - 1), level, reason);
        successMsg("Complaint sent.");
    }

    private static void detailedReport(Teacher teacher) throws IOException {
        List<Course> courses = teacher.getCourses();
        if (courses.isEmpty()) { errorMsg("No courses."); return; }
        System.out.println("Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + " - " + courses.get(i).getName());
        }
        int ci = readIntRange("Pick: ", 1, courses.size());
        teacher.generateDetailedReport(courses.get(ci - 1));
    }

    private static void researchMenu(Teacher teacher) throws IOException {
        if (!teacher.isResearcher()) {
            System.out.println("You are not a researcher yet.");
            System.out.println("1 - Become researcher  0 - Back");
            if (readIntRange("> ", 0, 1) == 1) teacher.becomeResearcher();
            return;
        }
        ResearchDecorator profile = teacher.getResearchProfile();
        System.out.println("RESEARCH  [H-index: " + profile.calculateHIndex()
            + "  Papers: " + profile.getPapers().size() + "]");
        System.out.println("1-Publish  2-By citations  3-By date  4-By pages");
        System.out.println("5-H-index  6-All papers    7-Top cited  8-Add citation");
        int opt = readIntRange("> ", 1, 8);
        switch (opt) {
            case 1 -> publishPaper(teacher);
            case 2 -> ResearchController.printPapers(profile, new PaperCitationComparator());
            case 3 -> ResearchController.printPapers(profile, new PaperDateComparator());
            case 4 -> ResearchController.printPapers(profile, new PaperPageComparator());
            case 5 -> ResearchController.showHIndex(profile);
            case 6 -> ResearchController.printAllPapers(new PaperCitationComparator());
            case 7 -> ResearchController.showTopCited();
            case 8 -> addCitationMenu(profile);
        }
    }

    private static void publishPaper(Teacher teacher) throws IOException {
        String title   = readString("Title: ");
        String journal = readString("Journal: ");
        int pages      = readInt("Pages: ");
        String doi     = readString("DOI: ");
        String authors = readString("Authors (comma separated): ");
        ResearchPaper paper = new ResearchPaper(
            title, Arrays.asList(authors.split(",")),
            journal, pages, new Date(), doi
        );
        ResearchController.publishPaper(teacher.getResearchProfile(), paper);
        successMsg("Paper published.");
    }

    private static void addCitationMenu(ResearchDecorator profile) throws IOException {
        List<ResearchPaper> papers = profile.getPapers();
        if (papers.isEmpty()) { errorMsg("No papers yet. Publish one first."); return; }
        System.out.println("Your papers:");
        for (int i = 0; i < papers.size(); i++) {
            System.out.println((i + 1) + " - " + papers.get(i).getTitle()
                + " | citations: " + papers.get(i).getCitations());
        }
        int pi = readIntRange("Pick paper: ", 1, papers.size());
        int count = readIntRange("Add citations (1-10): ", 1, 10);
        for (int i = 0; i < count; i++) {
            papers.get(pi - 1).addCitation();
        }
        DataStorage.save();
        successMsg("Citations: " + papers.get(pi - 1).getCitations()
            + " | H-index: " + profile.calculateHIndex());
    }

    private static void recommendationMenu(Teacher teacher) throws IOException {
        List<Student> students = new ArrayList<>();
        for (User u : DataStorage.getUsers()) {
            if (u instanceof Student) students.add((Student) u);
        }
        if (students.isEmpty()) { errorMsg("No students."); return; }
        System.out.println("Students:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println((i + 1) + " - "
                + students.get(i).getFirstName() + " "
                + students.get(i).getLastName()
                + " | GPA: " + String.format("%.2f", students.get(i).getGpa()));
        }
        int si = readIntRange("Pick student: ", 1, students.size());
        String purpose = readString("Purpose: ");
        String body    = readString("Letter body: ");
        RecommendationLetter letter =
            teacher.writeRecommendation(students.get(si - 1), purpose, body);
        letter.print();
    }

    private static void submitRequest(Teacher teacher) throws IOException {
        String desc = readString("Describe the issue: ");
        TechSupportController.createRequest(desc, teacher);
        successMsg("Request submitted.");
    }

    private static void markAttendanceMenu(Teacher teacher) throws IOException {
        List<Course> courses = teacher.getCourses();
        if (courses.isEmpty()) { errorMsg("No courses assigned."); return; }
        System.out.println("Courses:");
        for (int i = 0; i < courses.size(); i++) {
            System.out.println((i + 1) + " - " + courses.get(i).getName());
        }
        int ci = readIntRange("Pick course: ", 1, courses.size());
        Course course = courses.get(ci - 1);

        Course realCourse = course;
        for (Course c : DataStorage.getCourses()) {
            if (c.getCourseId().equals(course.getCourseId())) { realCourse = c; break; }
        }

        List<Student> students = realCourse.getStudents();
        if (students.isEmpty()) { errorMsg("No students in this course."); return; }

        model.academic.Lesson lesson = new model.academic.Lesson(
            "L" + System.currentTimeMillis(),
            model.enums.LessonType.LECTURE,
            readString("Lesson topic: "),
            readString("Room: "),
            90, teacher
        );

        System.out.println("Mark attendance (1=PRESENT, 0=ABSENT):");
        for (Student s : students) {
            System.out.println(s.getFirstName() + " " + s.getLastName());
            int p = readIntRange("> ", 0, 1);
            realCourse.markAttendance(s, lesson, p == 1);
        }
        DataStorage.save();
        successMsg("Attendance marked.");
    }
}