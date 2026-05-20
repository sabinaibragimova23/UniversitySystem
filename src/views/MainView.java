package views;

import controllers.UserController;
import core.AppContext;
import core.DataStorage;
import core.UserFactory;
import model.enums.*;
import model.users.*;

import java.io.IOException;

public class MainView extends BaseView {

    public static void run() throws IOException {
        welcomeMsg();

        boolean running = true;

        while (running) {
            mainMenuMsg();

            int option = readIntRange(AppContext.tr("choose"), 0, 4);

            switch (option) {
                case 1 -> handleLogin();
                case 2 -> handleRegister();
                case 3 -> showAllUsers();
                case 4 -> handleChangeLanguage();
                case 0 -> {
                    System.out.println(AppContext.tr("goodbye"));
                    running = false;
                }
            }
        }
    }

    private static void handleLogin() throws IOException {

        System.out.println("\n--- AUTH ---");

        String login = readString("Login: ");
        String password = readString("Password: ");

        User user = UserController.login(login, password);

        if (user == null) {
            errorMsg("Invalid credentials.");
            return;
        }

        if (user instanceof Admin) AdminView.run((Admin) user);
        else if (user instanceof Manager) ManagerView.run((Manager) user);
        else if (user instanceof Teacher) TeacherView.run((Teacher) user);
        else if (user instanceof GraduateStudent) GraduateStudentView.run((GraduateStudent) user);
        else if (user instanceof Student) StudentView.run((Student) user);
        else if (user instanceof TechSupportSpecialist) TechSupportView.run((TechSupportSpecialist) user);
        else errorMsg("Role not recognized.");
    }

    private static void handleRegister() throws IOException {

        System.out.println("\n--- REGISTRATION ---");

        System.out.println("Role:");
        System.out.println("1 - Student");
        System.out.println("2 - Graduate Student");
        System.out.println("3 - Teacher");
        System.out.println("4 - Manager");
        System.out.println("5 - Tech Support");
        System.out.println("(Note: Admin accounts can only be created by an existing Admin)");

        int role = readIntRange("> ", 1, 5);

        int id = DataStorage.getUsers().size() + 1;
        String login = readString("Login: ");
        String password = readString("Password: ");
        String firstName = readString("First name: ");
        String lastName = readString("Last name: ");

        UserType type = switch (role) {
            case 1 -> UserType.STUDENT;
            case 2 -> UserType.GRADUATE_STUDENT;
            case 3 -> UserType.TEACHER;
            case 4 -> UserType.MANAGER;
            default -> UserType.TECH_SUPPORT;
        };

        User newUser = UserFactory.createUser(
                type,
                id,
                firstName,
                lastName,
                login,
                password
        );

        if (newUser instanceof GraduateStudent gs) {

            String sid = readString("Student ID: ");
            String major = readString("Major: ");
            int year = readIntRange("Year (1-4): ", 1, 4);

            System.out.println("Degree: 1-MASTER  2-PHD");
            int d = readIntRange("> ", 1, 2);

            DegreeType degree = (d == 1) ? DegreeType.MASTER : DegreeType.PHD;

            gs.setStudentId(sid);
            gs.setMajor(major);
            gs.setYear(year);
            gs.setDegreeType(degree);

        } else if (newUser instanceof Student s) {

            String sid = readString("Student ID: ");
            String major = readString("Major: ");
            int year = readIntRange("Year (1-4): ", 1, 4);

            s.setStudentId(sid);
            s.setMajor(major);
            s.setYear(year);
        }

        if (newUser instanceof Teacher t) {

            System.out.println("Position:");
            System.out.println("1 - TUTOR");
            System.out.println("2 - LECTOR");
            System.out.println("3 - SENIOR LECTOR");
            System.out.println("4 - PROFESSOR");

            int p = readIntRange("> ", 1, 4);

            TeacherType typePos = TeacherType.values()[p - 1];
            t.setPosition(typePos);
        }

        if (newUser instanceof Manager m) {

            System.out.println("Manager type:");
            System.out.println("1 - OR");
            System.out.println("2 - DEAN OFFICE");
            System.out.println("3 - DEPARTMENT");

            int mt = readIntRange("> ", 1, 3);

            ManagerType mtType = ManagerType.values()[mt - 1];
            m.setManagerType(mtType);
        }

        DataStorage.addUser(newUser);
        DataStorage.save();

        successMsg("Account created successfully.");
    }

    private static void showAllUsers() {
        System.out.println("\n===== " + AppContext.tr("allUsers").toUpperCase() + " =====");
        for (model.users.User u : DataStorage.getUsers()) {
            System.out.println("  [" + u.getClass().getSimpleName() + "] "
                    + u.getFirstName() + " " + u.getLastName()
                    + " | login: " + u.getLogin());
        }
    }

    private static void handleChangeLanguage() throws IOException {
        System.out.println("\n1 - English");
        System.out.println("2 - Қазақша");
        System.out.println("3 - Русский");
        int l = readIntRange("> ", 1, 3);
        Language lang = l == 2 ? Language.KZ : l == 3 ? Language.RU : Language.EN;
        AppContext.setLanguage(lang);
        System.out.println(AppContext.tr("langChanged") + lang);
    }

    public static void welcomeMsg() {
        System.out.println("==================================");
        System.out.println("   UNIVERSITY SYSTEM");
        System.out.println("==================================");
        System.out.println(AppContext.tr("welcome"));
    }

    public static void mainMenuMsg() {
        System.out.println("\n" + AppContext.tr("mainMenu"));
        System.out.println("1 - " + AppContext.tr("login"));
        System.out.println("2 - " + AppContext.tr("register"));
        System.out.println("3 - " + AppContext.tr("showUsers"));
        System.out.println("4 - " + AppContext.tr("changeLanguage"));
        System.out.println("0 - " + AppContext.tr("exit"));
    }

    public static void byeMsg() {
        System.out.println("\n" + AppContext.tr("goodbye"));
    }
}