package views;

import controllers.*;
import model.academic.*;
import model.enums.*;
import model.research.*;
import model.users.*;
import model.users.StudentOrganization;
import core.DataStorage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class StudentView extends BaseView {

    public static void run(Student student) throws IOException {

        System.out.println("\n=== Student Panel: "
                + student.getFirstName()
                + " "
                + student.getLastName()
                + " ===");

        System.out.println("Major: "
                + student.getMajor()
                + " | Year: "
                + student.getYear()
                + " | Credits: "
                + student.getCredits()
                + " | GPA: "
                + String.format("%.2f", student.getGpa()));

        boolean active = true;

        while (active) {

            showMenu(student);

            int option = readIntRange("> ", 0, 14);

            switch (option) {

                case 1 -> listAvailableCourses();

                case 2 -> registerCourseMenu(student);

                case 3 -> student.viewCourses();

                case 4 -> student.viewMarks();

                case 5 -> student.getTranscript().print();

                case 6 -> viewTeacherInfoMenu(student);

                case 7 -> rateTeacherMenu(student);

                case 8 -> listAndJoinOrgMenu(student);

                case 9 -> student.viewOrg();

                case 10 -> subscribeJournalMenu(student);

                case 11 -> student.viewNotifications();

                case 12 -> sendMessageMenu(student);

                case 13 -> switchLanguageMenu(student);

                case 14 -> becomeResearcherMenu(student);

                case 0 -> {

                    System.out.println("[Student] Logged out.");

                    active = false;
                }
            }
        }
    }

    private static void showMenu(Student student) {

        separator();

        System.out.println("STUDENT MENU  [Credits: "
                + student.getCredits()
                + "/21 | GPA: "
                + String.format("%.2f", student.getGpa())
                + "]");

        System.out.println("1  - Available courses");
        System.out.println("2  - Register for course");
        System.out.println("3  - My courses");
        System.out.println("4  - My marks");
        System.out.println("5  - Transcript");
        System.out.println("6  - View teacher info");
        System.out.println("7  - Rate teacher");
        System.out.println("8  - Student organizations");
        System.out.println("9  - My organization");
        System.out.println("10 - Subscribe to journal");
        System.out.println("11 - Notifications");
        System.out.println("12 - Send message");
        System.out.println("13 - Switch language");
        System.out.println("14 - Become Researcher");
        System.out.println("0  - Logout");
    }

    // 1
    private static void listAvailableCourses() {

        List<Course> all = DataStorage.getCourses();

        if (all.isEmpty()) {

            System.out.println("No courses available.");

            return;
        }

        System.out.println("=== Available Courses ===");

        for (int i = 0; i < all.size(); i++) {

            Course c = all.get(i);

            String teachers;

            if (c.getInstructors().isEmpty()) {

                teachers = "TBA";

            } else {

                teachers = c.getInstructors().stream()
                        .map(t -> t.getFirstName() + " " + t.getLastName())
                        .collect(Collectors.joining(", "));
            }

            System.out.printf(
                    "%d - [%s] %s | %d credits | Teachers: %s%n",
                    i + 1,
                    c.getCourseId(),
                    c.getName(),
                    c.getCredits(),
                    teachers
            );
        }
    }

    private static void registerCourseMenu(Student student) throws IOException {

        List<Course> all = DataStorage.getCourses();

        if (all.isEmpty()) {

            System.out.println("No courses available.");

            return;
        }

        listAvailableCourses();

        int pick = readIntRange(
                "Pick course number: ",
                1,
                all.size()
        );

        Course course = all.get(pick - 1);

        boolean ok = CourseController.registerStudent(student, course);

        if (ok) {

            successMsg(
                    "Registered for "
                            + course.getName()
                            + " | Credits: "
                            + student.getCredits()
                            + "/21"
            );
        }
    }


    private static void viewTeacherInfoMenu(Student student) throws IOException {

        List<Course> mine = student.getCourses();

        if (mine.isEmpty()) {

            System.out.println("You have no registered courses.");

            return;
        }

        System.out.println("=== My Courses ===");

        for (int i = 0; i < mine.size(); i++) {

            System.out.println(
                    (i + 1)
                            + " - "
                            + mine.get(i).getName()
            );
        }

        int pick = readIntRange(
                "Pick course: ",
                1,
                mine.size()
        );

        student.viewTeacherInfo(mine.get(pick - 1));
    }

    private static void rateTeacherMenu(Student student) throws IOException {
        List<Teacher> teachers = student.getCourses().stream()

                .flatMap(c -> c.getInstructors().stream())

                .distinct()

                .collect(Collectors.toList());

        if (teachers.isEmpty()) {

            System.out.println(
                    "No teachers found. Register for courses first."
            );

            return;
        }

        System.out.println("=== Teachers ===");

        for (int i = 0; i < teachers.size(); i++) {

            Teacher t = teachers.get(i);

            System.out.printf(
                    "%d - %s %s | %s | Rating: %.1f%n",
                    i + 1,
                    t.getFirstName(),
                    t.getLastName(),
                    t.getPosition(),
                    t.getRating()
            );
        }

        int pick = readIntRange(
                "Pick teacher: ",
                1,
                teachers.size()
        );

        double rating = readIntRange(
                "Rating (1-5): ",
                1,
                5
        );

        student.rateTeacher(
                teachers.get(pick - 1),
                rating
        );

        successMsg("Rating submitted.");
    }

    // 8
    private static void listAndJoinOrgMenu(Student student)
            throws IOException {

        List<StudentOrganization> orgs =
                DataStorage.getOrganizations();

        if (orgs.isEmpty()) {

            System.out.println("No organizations found.");

            return;
        }

        System.out.println("=== Student Organizations ===");

        for (int i = 0; i < orgs.size(); i++) {

            StudentOrganization org = orgs.get(i);

            String head;

            if (org.getHead() != null) {

                head =
                        org.getHead().getFirstName()
                        + " "
                        + org.getHead().getLastName();

            } else {

                head = "No head";
            }

            System.out.printf(
                    "%d - %s | Head: %s | Members: %d%n",
                    i + 1,
                    org.getName(),
                    head,
                    org.getMembers().size()
            );
        }

        System.out.println("0 - Back");

        int pick = readIntRange(
                "Join organization: ",
                0,
                orgs.size()
        );

        if (pick == 0) return;

        StudentOrganization chosen =
                orgs.get(pick - 1);

        chosen.addMember(student);

        DataStorage.save();

        successMsg(
                "Joined: "
                + chosen.getName()
        );
    }
 
    private static void subscribeJournalMenu(Student student)
            throws IOException {

        String name = readString(
                "Journal name: "
        );

        Journal journal = new Journal(name);

        ResearchController.subscribeJournal(
                student,
                journal
        );

        successMsg("Subscribed to " + name);
    }


    private static void sendMessageMenu(Student student)
            throws IOException {

        List<User> users = DataStorage.getUsers().stream()

                .filter(u ->
                        u.getLogin() != null
                                &&
                                !u.getLogin().equals(student.getLogin())
                )

                .collect(Collectors.toList());

        if (users.isEmpty()) {

            System.out.println("No users found.");

            return;
        }

        System.out.println("=== Users ===");

        for (int i = 0; i < users.size(); i++) {

            User u = users.get(i);

            System.out.printf(
                    "%d - [%s] %s %s%n",
                    i + 1,
                    u.getClass().getSimpleName(),
                    u.getFirstName(),
                    u.getLastName()
            );
        }

        int pick = readIntRange(
                "Send to: ",
                1,
                users.size()
        );

        String msg = readString("Message: ");

        student.sendMessage(
                users.get(pick - 1),
                msg
        );

        successMsg("Message sent.");
    }

    // 13
    private static void switchLanguageMenu(Student student)
            throws IOException {

        System.out.println("Language:");
        System.out.println("1 - EN");
        System.out.println("2 - KZ");
        System.out.println("3 - RU");

        int l = readIntRange("> ", 1, 3);

        Language lang =
                (l == 2)
                        ? Language.KZ
                        : (l == 3)
                        ? Language.RU
                        : Language.EN;

        student.switchLanguage(lang);

        System.out.println(
                "Greeting: "
                        + lang.getGreeting()
        );
    }

    // 14
    private static void becomeResearcherMenu(Student student)
            throws IOException {

        if (student.isResearcher()) {

            System.out.println(
                    "You are already a Researcher."
            );

            System.out.println(
                    "h-index: "
                            + student.getResearchProfile()
                            .calculateHIndex()
            );

            return;
        }

        System.out.println(
                "Become Researcher?"
        );

        System.out.println("1 - Yes");
        System.out.println("0 - No");

        int choose = readIntRange("> ", 0, 1);

        if (choose == 1) {

            student.becomeResearcher();

            DataStorage.save();

            successMsg(
                    "You are now a Researcher!"
            );
        }
    }
}