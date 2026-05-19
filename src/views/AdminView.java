package views;

import controllers.*;
import core.DataStorage;
import model.enums.*;
import model.users.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminView extends BaseView {

    public static void run(Admin admin) throws IOException {
        System.out.println("\n=== Admin Panel: " + admin.getFirstName() + " " + admin.getLastName() + " ===");
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
                case 1: listUsers();             break;
                case 2: addUser(admin);          break;
                case 3: removeUser(admin);       break;
                case 4: updateUser(admin);       break;
                case 5: admin.viewLogs();        break;
                case 6: searchUsers();           break;
                case 7: switchLanguage(admin);   break;
                case 0:
                    System.out.println("Logged out.");
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
        System.out.println("1 - List all users");
        System.out.println("2 - Add user");
        System.out.println("3 - Remove user");
        System.out.println("4 - Update user");
        System.out.println("5 - View logs");
        System.out.println("6 - Search users");
        System.out.println("7 - Switch language");
        System.out.println("0 - Logout");
        System.out.print("> ");
    }

    private static void listUsers() {
        List<User> users = DataStorage.getUsers();
        System.out.println("All users (" + users.size() + "):");
        for (User u : users) {
            System.out.println("  [" + u.getClass().getSimpleName() + "] "
                + u.getFirstName() + " " + u.getLastName()
                + " | login: " + u.getLogin());
        }
    }

    private static void addUser(Admin admin) throws IOException {
        System.out.println("Role:  1-Student  2-Teacher  3-Manager  4-TechSupport");
        System.out.print("> ");
        int role = Integer.parseInt(reader.readLine().trim());

        int id = DataStorage.getUsers().size() + 1;
        System.out.print("Login: ");       String login = reader.readLine().trim();
        System.out.print("Password: ");    String pass  = reader.readLine().trim();
        System.out.print("First name: ");  String fn    = reader.readLine().trim();
        System.out.print("Last name: ");   String ln    = reader.readLine().trim();

        User user = null;
        switch (role) {
            case 1:
                System.out.print("Student ID: ");  String sid   = reader.readLine().trim();
                System.out.print("Major: ");        String major = reader.readLine().trim();
                System.out.print("Year: ");         int year = Integer.parseInt(reader.readLine().trim());
                user = new Student(id, login, pass, fn, ln, sid, major, year);
                break;
            case 2:
                System.out.println("Position:  1-TUTOR  2-LECTOR  3-SENIOR_LECTOR  4-PROFESSOR");
                System.out.print("> ");
                int pos = Integer.parseInt(reader.readLine().trim());
                TeacherType ttype;
                switch (pos) {
                    case 1:  ttype = TeacherType.TUTOR;         break;
                    case 3:  ttype = TeacherType.SENIOR_LECTOR; break;
                    case 4:  ttype = TeacherType.PROFESSOR;     break;
                    default: ttype = TeacherType.LECTOR;
                }
                user = new Teacher(id, login, pass, fn, ln, ttype);
                break;
            case 3:
                System.out.println("Type:  1-OR  2-DEAN_OFFICE  3-DEPARTMENT");
                System.out.print("> ");
                int mt = Integer.parseInt(reader.readLine().trim());
                ManagerType mtype;
                switch (mt) {
                    case 2:  mtype = ManagerType.DEAN_OFFICE;  break;
                    case 3:  mtype = ManagerType.DEPARTMENT;   break;
                    default: mtype = ManagerType.OR;
                }
                user = new Manager(id, login, pass, fn, ln, mtype);
                break;
            case 4:
                user = new TechSupportSpecialist(id, login, pass, fn, ln);
                break;
            default:
                errorMsg("Unknown role.");
                return;
        }
        UserController.addUser(admin, user);
        DataStorage.save();
        successMsg("User added: " + fn + " " + ln);
    }

    private static void removeUser(Admin admin) throws IOException {
        System.out.print("Login to remove: ");
        String login = reader.readLine().trim();
        User target = null;
        for (User u : DataStorage.getUsers()) {
            if (u.getLogin() != null && login.equals(u.getLogin())) {
                target = u;
                break;
            }
        }
        if (target == null) { errorMsg("User not found: " + login); return; }
        UserController.removeUser(admin, target);
        DataStorage.save();
        successMsg("Removed.");
    }

    private static void updateUser(Admin admin) throws IOException {
        System.out.print("Login of user to update: ");
        String login = reader.readLine().trim();
        User target = null;
        for (User u : DataStorage.getUsers()) {
            if (u.getLogin() != null && login.equals(u.getLogin())) {
                target = u;
                break;
            }
        }
        if (target == null) { errorMsg("Not found: " + login); return; }
        System.out.println("Current name: " + target.getFirstName() + " " + target.getLastName());
        System.out.print("New first name (Enter = keep): ");
        String newFirst = reader.readLine().trim();
        System.out.print("New last name (Enter = keep): ");
        String newLast = reader.readLine().trim();
        if (!newFirst.isEmpty()) target.setFirstName(newFirst);
        if (!newLast.isEmpty())  target.setLastName(newLast);
        admin.updateUser(target, target.getFirstName(), target.getLastName());
        DataStorage.save();
        successMsg("Updated.");
    }

    private static void searchUsers() throws IOException {
        System.out.print("Search (name or login): ");
        String query = reader.readLine().trim().toLowerCase();
        List<User> found = new ArrayList<>();
        for (User u : DataStorage.getUsers()) {
            String fn = u.getFirstName() != null ? u.getFirstName().toLowerCase() : "";
            String ln = u.getLastName()  != null ? u.getLastName().toLowerCase()  : "";
            String lg = u.getLogin()     != null ? u.getLogin().toLowerCase()     : "";
            if (fn.contains(query) || ln.contains(query) || lg.contains(query)) {
                found.add(u);
            }
        }
        System.out.println("Found " + found.size() + " user(s):");
        for (User u : found) {
            System.out.println("  [" + u.getClass().getSimpleName() + "] "
                + u.getFirstName() + " " + u.getLastName()
                + " (login: " + u.getLogin() + ")");
        }
    }

    private static void switchLanguage(Admin admin) throws IOException {
        System.out.println("1-EN  2-KZ  3-RU");
        System.out.print("> ");
        int l = Integer.parseInt(reader.readLine().trim());
        Language lang = l == 2 ? Language.KZ : l == 3 ? Language.RU : Language.EN;
        UserController.switchLanguage(admin, lang);
        successMsg("Language set to " + lang);
    }
}