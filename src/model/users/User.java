package model.users;

import model.academic.Course;
import model.academic.Mark;
import model.enums.Language;
import model.observer.Observer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class User implements Observer, Serializable {
    private static final long serialVersionUID = 1L;

    protected int id;
    protected String login;
    protected String password;
    protected Language language;
    protected String firstName;
    protected String lastName;

    private List<String> notifications;

    public User(int id, String login, String password, String firstName, String lastName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;

        this.language = Language.EN;
        this.notifications = new ArrayList<>();
    }

    public boolean login(String login, String password) {
        return this.login != null
                && this.password != null
                && this.login.equals(login)
                && this.password.equals(password);
    }
    public void logout() {
        System.out.println(this + " logged out.");
    }

    public void switchLanguage(Language lang) {
        this.language = lang;
        System.out.println("Language switched to: " + lang);
    }

    @Override
    public void update(String event, Object data) {

        String msg = "[" + event + "] " + data;
        notifications.add(msg);

        System.out.println(
                "[Notification -> "
                        + firstName
                        + " "
                        + lastName
                        + "]: "
                        + msg
        );
    }

    public void viewNotifications() {

        if (notifications.isEmpty()) {
            System.out.println("No notifications.");
            return;
        }

        for (String n : notifications) {
            System.out.println("  - " + n);
        }
    }

    public void sendMessage(User receiver, String content) {
    
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof User)) return false;

        User u = (User) o;
        return id == u.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }



    public List<String> getNotifications() {
        return notifications;
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fn) {
        this.firstName = fn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String ln) {
        this.lastName = ln;
    }

    public Language getLanguage() {
        return language;
    }
}