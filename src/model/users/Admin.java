package model.users;

import core.DataStorage;
import model.enums.Language;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Admin extends Employee {

	private static final long serialVersionUID = 1L;
	private List<String> logFile;

    public Admin(int id,
                 String login,
                 String password,
                 String firstName,
                 String lastName) {

    	super(id,
  	          login,
  	          password,
  	          firstName,
  	          lastName);

        this.language = Language.EN;
        this.logFile = new ArrayList<>();
    }

    public void addUser(User user) {
        DataStorage.getInstance();
		DataStorage.addUser(user);
        log("Added: " + user);
        System.out.println("[Admin] User added: " + user);
    }

    public void removeUser(User user) {
        DataStorage.getInstance();
		DataStorage.removeUser(user);
        log("Removed: " + user);
    }

    public void updateUser(User user,
                           String newFirst,
                           String newLast) {

        user.setFirstName(newFirst);
        user.setLastName(newLast);

        log("Updated: " + user);
    }

    public List<String> viewLogs() {
        System.out.println("=== Admin Logs ===");
        for (String l : logFile) {
            System.out.println("  [LOG] " + l);
        }
        return Collections.unmodifiableList(logFile);
    }

    public String getInfo() {
        return "Admin{'"
                + firstName
                + " "
                + lastName
                + "'}";
    }

    private void log(String action) {
        logFile.add(action);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " [Admin]";
    }
}