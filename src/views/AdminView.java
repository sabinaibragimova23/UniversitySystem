package views;

import controllers.*;
import model.users.*;
import model.enums.*;
import core.DataStorage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AdminView extends BaseView {

    public static void run(Admin admin) throws IOException {

        System.out.println("\n=== Admin Panel: " + admin + " ===");
        boolean active = true;
        while (active) {
            showMenu();
            int option;
            try {
                option = Integer.parseInt(reader.readLine().trim());
            }
            catch (Exception e) {
                errorMsg("Invalid input.");
                continue;
            }
            switch (option) {

                case 1:
                    listUsers();
                    break;

                case 2:
                    addUserMenu(admin);
                    break;

                case 3:
                    removeUserMenu(admin);
                    break;

                case 4:
                    admin.viewLogs();
                    break;

                case 5:
                    searchUsersMenu();
                    break;

                case 6:
                    switchLanguageMenu(admin);
                    break;

                case 0:
                    System.out.println("[Admin] Logged out.");
                    active = false;
                    break;

                default:
                    errorMsg("Unknown option.");
            }
        }
    }

    private static void showMenu() {
        separator();
        System.out.println("ADMIN MENU:");
        System.out.println("1 - List users");
        System.out.println("2 - Add user");
        System.out.println("3 - Remove user");
        System.out.println("4 - View logs");
        System.out.println("5 - Search users");
        System.out.println("6 - Switch language");
        System.out.println("0 - Logout");

        System.out.print("> ");
    }

    private static void listUsers() {
        System.out.println("=== All Users (" + DataStorage.getUsers().size() + ") ===");

        DataStorage.getUsers().forEach(u -> System.out.printf(
                "  [%-20s] %s %s (login: %s)%n",
                u.getClass().getSimpleName(),
                u.getFirstName() != null ? u.getFirstName() : "",
                u.getLastName() != null ? u.getLastName() : "",
                u.getLogin() != null ? u.getLogin() : "?"
        ));
    }

    private static void addUserMenu(Admin admin) throws IOException {
        System.out.println("Role:");
        System.out.println("1 - Student");
        System.out.println("2 - Teacher");
        System.out.println("3 - Manager");
        System.out.println("4 - TechSupport");

        System.out.print("> ");

        int role = Integer.parseInt(reader.readLine().trim());

        System.out.print("ID: ");
        int id = Integer.parseInt(reader.readLine().trim());

        System.out.print("Login: ");
        String login = reader.readLine().trim();

        System.out.print("Password: ");
        String pass = reader.readLine().trim();

        System.out.print("First name: ");
        String fn = reader.readLine().trim();

        System.out.print("Last name: ");
        String ln = reader.readLine().trim();
        User user = null;

        switch (role) {
            case 1:

                System.out.print("Student ID: ");
                String sid = reader.readLine().trim();

                System.out.print("Major: ");
                String major = reader.readLine().trim();

                System.out.print("Year: ");
                int year = Integer.parseInt(reader.readLine().trim());

                user = new Student(
                        id,
                        login,
                        pass,
                        fn,
                        ln,
                        sid,
                        major,
                        year
                );
                break;

            case 2:
                System.out.println("Teacher position:");
                System.out.println("1 - TUTOR");
                System.out.println("2 - LECTOR");
                System.out.println("3 - SENIOR_LECTOR");
                System.out.println("4 - PROFESSOR");

                System.out.print("> ");

                int pos = Integer.parseInt(reader.readLine().trim());
                TeacherType type;
                switch (pos) {

                    case 1:
                        type = TeacherType.TUTOR;
                        break;

                    case 3:
                        type = TeacherType.SENIOR_LECTOR;
                        break;

                    case 4:
                        type = TeacherType.PROFESSOR;
                        break;

                    default:
                        type = TeacherType.LECTOR;
                }

                user = new Teacher(
                        id,
                        login,
                        pass,
                        fn,
                        ln,
                        type
                );

                break;

            case 3:

                System.out.println("Manager type:");
                System.out.println("1 - OR");
                System.out.println("2 - DEAN_OFFICE");
                System.out.println("3 - DEPARTMENT");

                System.out.print("> ");

                int mt = Integer.parseInt(reader.readLine().trim());

                ManagerType mtype;

                switch (mt) {

                    case 2:
                        mtype = ManagerType.DEAN_OFFICE;
                        break;

                    case 3:
                        mtype = ManagerType.DEPARTMENT;
                        break;

                    default:
                        mtype = ManagerType.OR;
                }

                user = new Manager(
                        id,
                        login,
                        pass,
                        fn,
                        ln,
                        mtype
                );

                break;

            case 4:

                user = new TechSupportSpecialist(
                        id,
                        login,
                        pass,
                        fn,
                        ln
                );

                break;

            default:
                errorMsg("Unknown role.");
                return;
        }

        UserController.addUser(admin, user);

        DataStorage.save();

        successMsg("User added.");
    }
    
    

    private static void removeUserMenu(Admin admin) throws IOException {

        System.out.print("Login to remove: ");

        String login = reader.readLine().trim();

        DataStorage.getUsers()
                .stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .ifPresentOrElse(

                        u -> {

                            UserController.removeUser(admin, u);

                            DataStorage.save();

                            successMsg("Removed.");
                        },

                        () -> errorMsg("User not found.")
                );
    }

    private static void searchUsersMenu() throws IOException {

        System.out.print("Search: ");
        String regex = reader.readLine().trim();

        List<User> result = DataStorage.getUsers()
                .stream()
                .filter(u -> {
                    String fn = u.getFirstName();
                    String ln = u.getLastName();
                    String lg = u.getLogin();

                    return (fn != null && fn.matches("(?i).*" + regex + ".*"))
                            || (ln != null && ln.matches("(?i).*" + regex + ".*"))
                            || (lg != null && lg.matches("(?i).*" + regex + ".*"));
                })
                .collect(Collectors.toList());

        System.out.println("\nFound: " + result.size());

        for (User u : result) {
            System.out.println("  [" + u.getClass().getSimpleName() + "] " + u);
        }
    }

    private static void switchLanguageMenu(Admin admin) throws IOException {

        System.out.println("Language:");
        System.out.println("1 - EN");
        System.out.println("2 - KZ");
        System.out.println("3 - RU");

        System.out.print("> ");

        int l = Integer.parseInt(reader.readLine().trim());

        Language lang;

        switch (l) {

            case 2:
                lang = Language.KZ;
                break;

            case 3:
                lang = Language.RU;
                break;

            default:
                lang = Language.EN;
        }

        UserController.switchLanguage(admin, lang);

        successMsg("Language changed.");
    }
}