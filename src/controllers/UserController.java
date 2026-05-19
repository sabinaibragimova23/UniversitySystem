package controllers;

import core.DataStorage;
import model.enums.Language;
import model.users.Admin;
import model.users.User;

public class UserController {
    public static User login(String login,
                             String password) {
    	for (User u : DataStorage.getUsers()) {
            if (u.login(login, password)) {
                System.out.println("Welcome, " + u.getFirstName() + "!");
                return u;
            }
        }

        System.out.println("Wrong login or password. Try again.");
        return null;
    }

    public static boolean addUser(Admin admin,
                                  User user) {
        admin.addUser(user);
        return true;
    }

    public static boolean removeUser(Admin admin,
                                     User user) {

        admin.removeUser(user);
        return true;
    }

    public static boolean updateUser(Admin admin,
                                     User user,
                                     String newFirst,
                                     String newLast) {

        admin.updateUser(user, newFirst, newLast);
        return true;
    }
    
    public static boolean registerUser(User user) {
        DataStorage.addUser(user);
        boolean result = DataStorage.save();

        if (result) {
            System.out.println("[Auth] User registered: " + user);
        } else {
            System.out.println("[Auth] Registration failed.");
        }

        return result;
    }

    public static void listUsers() {
        for (User u : DataStorage.getUsers()) {
            System.out.println(
                    "  ["
                            + u.getClass().getSimpleName()
                            + "] "
                            + u
            );
        }
    }

    public static void switchLanguage(User user,
                                      Language lang) {
        user.switchLanguage(lang);
        System.out.println(
                "[Language] "
                        + user
                        + " -> "
                        + lang.getGreeting()
        );
    }
}