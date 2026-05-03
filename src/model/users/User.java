package model.users;
import java.io.Serializable;
import model.observer.Observer;
import java.util.*;

public abstract class User implements Serializable, Observer {
    protected int id;
    protected String name;
    protected String email;
    protected String password;
    protected String firstName;
    protected String lastName;
    
    private List<String> notifications = new ArrayList<>();

    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
    

    @Override
    public void update(String event, Object data) {
        String msg = "[" + event + "] " + data;
        notifications.add(msg);
        System.out.println("[Notification -> " + firstName + " " + lastName + "]: " + msg);
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "User: " + name + " (" + email + ")";
    }
}