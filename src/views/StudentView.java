package views;

import controllers.*;
import core.DataStorage;
import model.academic.Course;
import model.enums.Language;
import model.research.Journal;
import model.research.News;
import model.research.ResearchDecorator;
import model.users.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentView extends BaseView {

    public static void run(Student student) throws IOException {
        System.out.println("\n=== Student: " + student.getFirstName()
            + " " + student.getLastName() + " ===");
        System.out.println("Major: " + student.getMajor()
            + "  Year: " + student.getYear()
            + "  Credits: " + student.getCredits() + "/21"
            + "  GPA: " + String.format("%.2f", student.getGpa()));

        boolean active = true;
        while (active) {
            printMenu(student);
            int option = readIntRange("> ", 0, 16);
            switch (option) {
                case 1  -> showAllCourses();
                case 2  -> registerForCourse(student);
                case 3  -> student.viewCourses();
                case 4  -> student.viewMarks();
                case 5  -> student.getTranscript().print();
                case 6  -> viewTeacherInfo(student);
                case 7  -> rateTeacher(student);
                case 8  -> showOrgsAndJoin(student);
                case 9  -> student.viewOrg();
                case 10 -> subscribeJournal(student);
                case 11 -> student.viewNotifications();
                case 12 -> sendMessage(student);
                case 13 -> switchLang(student);
                case 14 -> becomeResearcher(student);
                case 15 -> submitRequest(student);
                case 16 -> showNews();
                case 0  -> {
                    System.out.println("Logged out.");
                    active = false;
                }
            }
        }
    }

    private static void printMenu(Student student) {
        separator();
        System.out.println("STUDENT MENU"
            + "  [Credits: " + student.getCredits() + "/21"
            + "  GPA: " + String.format("%.2f", student.getGpa()) + "]");
        System.out.println("1  - All courses");
        System.out.println("2  - Register for course");
        System.out.println("3  - My courses");
        System.out.println("4  - My marks");
        System.out.println("5  - Transcript");
        System.out.println("6  - Teacher info");
        System.out.println("7  - Rate teacher");
        System.out.println("8  - Organizations");
        System.out.println("9  - My organization");
        System.out.println("10 - Subscribe to journal");
        System.out.println("11 - Notifications");
        System.out.println("12 - Send message");
        System.out.println("13 - Language");
        System.out.println("14 - Researcher menu");
        System.out.println("15 - Submit tech request");
        System.out.println("16 - News");
        System.out.println("0  - Logout");
    }

    private static void showAllCourses() {
        List<Course> all = DataStorage.getCourses();
        if (all.isEmpty()) {
            System.out.println("No courses available yet.");
            return;
        }
        System.out.println("\nAvailable courses:");
        for (int i = 0; i < all.size(); i++) {
            Course c = all.get(i);
            String teachers;
            if (!c.getInstructors().isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (var t : c.getInstructors()) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(t.getFirstName()).append(" ").append(t.getLastName());
                }
                teachers = " | " + sb;
            } else {
                teachers = " | teacher TBA";
            }
            System.out.println((i + 1) + ". [" + c.getCourseId() + "] "
                + c.getName() + " (" + c.getCredits() + " cr)"
                + " " + c.getType() + teachers);
        }
    }

    private static void registerForCourse(Student student) throws IOException {
        List<Course> all = DataStorage.getCourses();
        if (all.isEmpty()) { System.out.println("No courses available."); return; }
        showAllCourses();
        int pick = readIntRange("Pick number: ", 1, all.size());
        Course course = all.get(pick - 1);
        boolean ok = CourseController.registerStudent(student, course);
        if (ok) {
            successMsg("Registered for " + course.getName()
                + ". Credits: " + student.getCredits() + "/21");
        }
    }

    private static void viewTeacherInfo(Student student) throws IOException {
        List<Course> mine = student.getCourses();
        if (mine.isEmpty()) {
            System.out.println("Register for a course first.");
            return;
        }
        System.out.println("Your courses:");
        for (int i = 0; i < mine.size(); i++) {
            System.out.println((i + 1) + " - " + mine.get(i).getName());
        }
        int pick = readIntRange("Pick: ", 1, mine.size());
        student.viewTeacherInfo(mine.get(pick - 1));
    }

    private static void rateTeacher(Student student) throws IOException {
        List<Teacher> teachers = new ArrayList<>();
        for (Course c : student.getCourses()) {
            for (Teacher t : c.getInstructors()) {
                if (!teachers.contains(t)) teachers.add(t);
            }
        }
        if (teachers.isEmpty()) {
            System.out.println("No teachers to rate. Register for courses first.");
            return;
        }
        System.out.println("Your teachers:");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher t = teachers.get(i);
            System.out.println((i + 1) + " - " + t.getFirstName()
                + " " + t.getLastName()
                + " | current: " + String.format("%.1f", t.getRating()));
        }
        int ti = readIntRange("Pick teacher: ", 1, teachers.size());
        double rating = readIntRange("Rating (1-5): ", 1, 5);
        student.rateTeacher(teachers.get(ti - 1), rating);
        successMsg("Rating submitted.");
    }

    private static void showOrgsAndJoin(Student student) throws IOException {
        List<StudentOrganization> orgs = DataStorage.getOrganizations();
        if (orgs.isEmpty()) {
            System.out.println("No organizations yet. Ask manager to create one.");
            return;
        }
        System.out.println("\nStudent organizations:");
        for (int i = 0; i < orgs.size(); i++) {
            StudentOrganization org = orgs.get(i);
            String head = org.getHead() != null
                ? org.getHead().getFirstName() + " " + org.getHead().getLastName()
                : "no head";
            System.out.println((i + 1) + " - " + org.getName()
                + " | Head: " + head
                + " | Members: " + org.getMembers().size());
        }
        System.out.println("0 - Back");
        int pick = readIntRange("Join which? ", 0, orgs.size());
        if (pick == 0) return;
        orgs.get(pick - 1).addMember(student);
        DataStorage.save();
        successMsg("Joined " + orgs.get(pick - 1).getName() + ".");
    }

    private static void subscribeJournal(Student student) throws IOException {
        String name = readString("Journal name: ");
        Journal journal = new Journal(name);
        ResearchController.subscribeJournal(student, journal);
        successMsg("Subscribed to " + name + ".");
    }

    private static void sendMessage(Student student) throws IOException {
        List<User> all = new ArrayList<>();
        for (User u : DataStorage.getUsers()) {
            if (u.getLogin() != null && !u.getLogin().equals(student.getLogin())) {
                all.add(u);
            }
        }
        if (all.isEmpty()) { errorMsg("No other users."); return; }
        System.out.println("Users:");
        for (int i = 0; i < all.size(); i++) {
            System.out.println((i + 1) + " - ["
                + all.get(i).getClass().getSimpleName() + "] "
                + all.get(i).getFirstName() + " " + all.get(i).getLastName());
        }
        int ri = readIntRange("Send to: ", 1, all.size());
        String msg = readString("Message: ");
        student.sendMessage(all.get(ri - 1), msg);
        successMsg("Sent.");
    }

    private static void switchLang(Student student) throws IOException {
        System.out.println("1 - EN   2 - KZ   3 - RU");
        int l = readIntRange("> ", 1, 3);
        Language lang = l == 2 ? Language.KZ : l == 3 ? Language.RU : Language.EN;
        student.switchLanguage(lang);
        System.out.println(lang.getGreeting());
    }

    private static void becomeResearcher(Student student) throws IOException {
        if (!student.isResearcher()) {
            System.out.println("Become a Researcher?  1-Yes  0-No");
            if (readIntRange("> ", 0, 1) == 1) {
                student.becomeResearcher();
                DataStorage.save();
                successMsg("You are now a Researcher!");
            }
            return;
        }
        ResearchDecorator profile = student.getResearchProfile();
        separator();
        System.out.println("RESEARCH  [H-index: " + profile.calculateHIndex()
            + "  Papers: " + profile.getPapers().size() + "]");
        System.out.println("1 - Publish paper");
        System.out.println("2 - My papers (by citations)");
        System.out.println("3 - My papers (by date)");
        System.out.println("4 - H-index");
        System.out.println("5 - All university papers");
        System.out.println("0 - Back");
        int opt = readIntRange("> ", 0, 5);
        switch (opt) {
            case 1 -> publishPaper(student);
            case 2 -> ResearchController.printPapers(profile,
                          new model.comparators.PaperCitationComparator());
            case 3 -> ResearchController.printPapers(profile,
                          new model.comparators.PaperDateComparator());
            case 4 -> ResearchController.showHIndex(profile);
            case 5 -> ResearchController.printAllPapers(
                          new model.comparators.PaperCitationComparator());
            case 0 -> {}
        }
    }

    private static void publishPaper(Student student) throws IOException {
        String title   = readString("Title: ");
        String journal = readString("Journal: ");
        int pages      = readInt("Pages: ");
        String doi     = readString("DOI: ");
        String authors = readString("Authors (comma separated): ");
        model.research.ResearchPaper paper = new model.research.ResearchPaper(
            title,
            java.util.Arrays.asList(authors.split(",")),
            journal, pages, new java.util.Date(), doi
        );
        ResearchController.publishPaper(student.getResearchProfile(), paper);
        successMsg("Paper published.");
    }

    private static void submitRequest(Student student) throws IOException {
        String desc = readString("Describe the problem: ");
        TechSupportController.createRequest(desc, student);
        successMsg("Request submitted.");
    }

    private static void showNews() throws IOException {
        List<News> newsList = DataStorage.getNews();
        if (newsList.isEmpty()) {
            System.out.println("No news yet.");
            return;
        }
        System.out.println("\nNEWS:");
        for (int i = 0; i < newsList.size(); i++) {
            News n = newsList.get(i);
            String pin = n.isPinned() ? "[PINNED] " : "";
            System.out.println((i + 1) + ". " + pin + n.getTitle()
                + " [" + n.getTopic() + "]");
        }
        System.out.println("0 - Back");
        int pick = readIntRange("Open article: ", 0, newsList.size());
        if (pick == 0) return;
        News selected = newsList.get(pick - 1);
        System.out.println("\n--- " + selected.getTitle() + " ---");
        System.out.println(selected.getContent());
        List<String> comments = selected.getComments();
        if (!comments.isEmpty()) {
            System.out.println("\nComments:");
            for (String c : comments) {
                System.out.println("  > " + c);
            }
        }
        System.out.print("Add comment (Enter to skip): ");
        String comment = reader.readLine();
        if (comment != null && !comment.trim().isEmpty()) {
            selected.addComment(comment.trim());
            DataStorage.save();
            successMsg("Comment added.");
        }
    }
}