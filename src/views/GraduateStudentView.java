package views;

import controllers.*;
import model.users.*;
import model.academic.*;
import model.comparators.*;
import model.enums.*;
import model.research.*;
import core.DataStorage;

import java.io.IOException;
import java.util.*;

public class GraduateStudentView extends BaseView {
    public static void run(GraduateStudent student) throws IOException {
        System.out.println(
                "\n=== Graduate Student Panel: "
                + student
                + " ==="
        );

        boolean active = true;
        while (active) {
            showMenu();
            int option;
            try {
                option = Integer.parseInt(reader.readLine().trim());

            } catch (Exception e) {
                errorMsg("Invalid input.");
                continue;
            }
            switch (option) {

                case 1:
                    student.viewCourses();
                    break;

                case 2:
                    registerMenu(student);
                    break;

                case 3:
                    student.viewMarks();
                    break;

                case 4:
                    student.getTranscript().print();
                    break;

                case 5:
                    researchMenu(student);
                    break;

                case 6:
                    diplomaMenu(student);
                    break;

                case 7:
                    setSupervisorMenu(student);
                    break;

                case 8:
                    student.viewNotifications();
                    break;

                case 9:
                    sendMessageMenu(student);
                    break;

                case 10:
                    switchLanguageMenu(student);
                    break;

                case 0:
                    System.out.println("[GraduateStudent] Logged out.");
                    active = false;
                    break;

                default:
                    errorMsg("Unknown option.");
            }
        }
    }

    private static void showMenu() {
        separator();
        System.out.println("GRADUATE STUDENT MENU:");
        System.out.println("1  - My courses");
        System.out.println("2  - Register course");
        System.out.println("3  - My marks");
        System.out.println("4  - Transcript");
        System.out.println("5  - Research");
        System.out.println("6  - Diploma");
        System.out.println("7  - Supervisor");
        System.out.println("8  - Notifications");
        System.out.println("9  - Send message");
        System.out.println("10 - Change language");
        System.out.println("0  - Logout");

        System.out.print("> ");
    }

    private static void registerMenu(GraduateStudent student)
            throws IOException {

        System.out.print("Course ID: ");

        String cid = reader.readLine().trim();

        Course course = DataStorage.getCourses()
                .stream()
                .filter(c -> c.getCourseId().equals(cid))
                .findFirst()
                .orElse(null);

        if (course == null) {
            errorMsg("Course not found.");
            return;
        }

        CourseController.registerStudent(student, course);
        successMsg("Registered successfully.");
    }

    private static void researchMenu(GraduateStudent student)
            throws IOException {

        ResearchDecorator profile =
                student.getResearchProfile();

        System.out.println("\nRESEARCH MENU:");
        System.out.println("1 - Publish paper");
        System.out.println("2 - Papers by citations");
        System.out.println("3 - Papers by date");
        System.out.println("4 - Papers by pages");
        System.out.println("5 - Show h-index");
        System.out.println("6 - Join project");
        System.out.println("7 - Subscribe journal");

        System.out.print("> ");

        int option = Integer.parseInt(reader.readLine().trim());

        switch (option) {

            case 1:
                publishPaperMenu(student);
                break;

            case 2:
                ResearchController.printPapers(
                        profile,
                        new PaperCitationComparator()
                );
                break;

            case 3:
                ResearchController.printPapers(
                        profile,
                        new PaperDateComparator()
                );
                break;

            case 4:
                ResearchController.printPapers(
                        profile,
                        new PaperPageComparator()
                );
                break;

            case 5:
                ResearchController.showHIndex(profile);
                break;

            case 6:
                joinProjectMenu(student);
                break;

            case 7:
                subscribeJournalMenu(student);
                break;

            default:
                errorMsg("Unknown option.");
        }
    }

    private static void publishPaperMenu(GraduateStudent student)
            throws IOException {

        System.out.print("Title: ");
        String title = reader.readLine().trim();

        System.out.print("Journal: ");
        String journal = reader.readLine().trim();

        System.out.print("Pages: ");
        int pages = Integer.parseInt(reader.readLine().trim());

        System.out.print("DOI: ");
        String doi = reader.readLine().trim();

        System.out.print("Authors: ");
        String authors = reader.readLine().trim();

        ResearchPaper paper =
                new ResearchPaper(
                        title,
                        Arrays.asList(authors.split(",")),
                        journal,
                        pages,
                        new Date(),
                        doi
                );

        student.publishPaper(paper);

        successMsg("Paper published.");
    }

    private static void diplomaMenu(GraduateStudent student)
            throws IOException {

        System.out.println(
                "\nDiploma papers: "
                + student.getDiplomaPapers().size()
        );

        for (ResearchPaper p : student.getDiplomaPapers()) {

            System.out.println(
                    "  - "
                    + p.getTitle()
            );
        }

        System.out.println("\n1 - Add paper");
        System.out.println("2 - Submit diploma");
        System.out.println("0 - Back");

        System.out.print("> ");

        int option = Integer.parseInt(reader.readLine().trim());

        if (option == 1) {

            System.out.print("Title: ");
            String title = reader.readLine().trim();

            System.out.print("Journal: ");
            String journal = reader.readLine().trim();

            System.out.print("Pages: ");
            int pages = Integer.parseInt(reader.readLine().trim());

            System.out.print("DOI: ");
            String doi = reader.readLine().trim();

            ResearchPaper paper =
                    new ResearchPaper(
                            title,
                            Collections.singletonList(
                                    student.getFirstName()
                                    + " "
                                    + student.getLastName()
                            ),
                            journal,
                            pages,
                            new Date(),
                            doi
                    );

            student.addDiplomaPaper(paper);

            successMsg("Diploma paper added.");
        }

        else if (option == 2) {

            student.submitDiploma();

            successMsg("Diploma submitted.");
        }
    }

    private static void setSupervisorMenu(GraduateStudent student)
            throws IOException {

        System.out.print("Teacher login: ");

        String login = reader.readLine().trim();

        Teacher teacher = (Teacher) DataStorage.getUsers()
                .stream()
                .filter(u ->
                        u instanceof Teacher
                        && u.getLogin().equals(login))
                .findFirst()
                .orElse(null);

        if (teacher == null) {

            errorMsg("Teacher not found.");
            return;
        }

        if (!teacher.isResearcher()) {

            errorMsg("Teacher is not researcher.");
            return;
        }

        boolean ok =
                ResearchController.setSupervisor(
                        student,
                        teacher.getResearchProfile()
                );

        if (ok) {

            successMsg("Supervisor assigned.");
        }
    }

    private static void joinProjectMenu(GraduateStudent student)
            throws IOException {

        System.out.print("Project topic: ");

        String topic = reader.readLine().trim();

        ResearchProject project =
                new ResearchProject(topic);

        student.joinProject(project);

        successMsg("Project joined.");
    }

    private static void subscribeJournalMenu(GraduateStudent student)
            throws IOException {

        System.out.print("Journal name: ");

        String name = reader.readLine().trim();

        Journal journal = new Journal(name);

        ResearchController.subscribeJournal(
                student,
                journal
        );

        successMsg("Subscribed successfully.");
    }

    private static void sendMessageMenu(GraduateStudent student)
            throws IOException {

        java.util.List<User> allUsers = DataStorage.getUsers().stream()
                .filter(u -> u.getLogin() != null
                        && !u.getLogin().equals(student.getLogin()))
                .collect(java.util.stream.Collectors.toList());

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

        String message = readString("Message: ");

        student.sendMessage(allUsers.get(ri - 1), message);

        successMsg("Message sent.");
    }

    private static void switchLanguageMenu(GraduateStudent student)
            throws IOException {

        System.out.println("Choose language:");
        System.out.println("1 - EN");
        System.out.println("2 - KZ");
        System.out.println("3 - RU");

        System.out.print("> ");

        int option = Integer.parseInt(reader.readLine().trim());

        Language language;

        switch (option) {

            case 2:
                language = Language.KZ;
                break;

            case 3:
                language = Language.RU;
                break;

            default:
                language = Language.EN;
        }

        UserController.switchLanguage(student, language);

        successMsg("Language changed.");
    }
}