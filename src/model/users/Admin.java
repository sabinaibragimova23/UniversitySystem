package model.users;
import java.util.Vector;

public class Admin extends User {
    private Vector<User> users = new Vector<User>();

    public Admin(int id, String name, String email, String password) {
        super(id, name, email, password);
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void removeUser(User user) {
        users.remove(user);
    }

    public void viewUsers() {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }
}