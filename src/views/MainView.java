package views;

import controllers.UserController;
import core.UserFactory;
import core.DataStorage;
import model.users.*;
import model.enums.*;

import java.io.IOException;

public class MainView extends BaseView {

    public static void run() throws IOException {
        welcomeMsg();

        boolean running = true;

        while (running) {
            mainMenuMsg();

            int option = readIntRange("> ", 0, 3);

            switch (option) {

                case 1 -> handleLogin();
                case 2 -> handleRegister();
                case 3 -> System.out.println("University System v1.0 | OOP Final Project");
                case 0 -> {
                    byeMsg();
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

        int id = readInt("ID: ");
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

        if (newUser instanceof Student s) {

            String sid = readString("Student ID: ");
            String major = readString("Major: ");
            int year = readIntRange("Year (1-4): ", 1, 4);

            s.setStudentId(sid);
            s.setMajor(major);
            s.setYear(year);
        }

        if (newUser instanceof GraduateStudent gs) {

            String sid = readString("Student ID: ");
            String major = readString("Major: ");
            int year = readIntRange("Year (1-4): ", 1, 4);

            System.out.println("Degree: 1-MASTER  2-PHD");
            int d = readIntRange("> ", 1, 2);

            DegreeType degree = (d == 1)
                    ? DegreeType.MASTER
                    : DegreeType.PHD;

            gs.setStudentId(sid);
            gs.setMajor(major);
            gs.setYear(year);
            gs.setDegreeType(degree);
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

    public static void welcomeMsg() {

        System.out.println("==================================");
        System.out.println("   UNIVERSITY SYSTEM");
        System.out.println("==================================");
    }

    public static void mainMenuMsg() {

        System.out.println("\nMAIN MENU");
        System.out.println("1 - Login");
        System.out.println("2 - Register");
        System.out.println("3 - About");
        System.out.println("0 - Exit");
    }

    public static void byeMsg() {

        System.out.println("\nSession ended.");
    }
}