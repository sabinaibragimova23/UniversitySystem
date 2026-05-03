package model.patterns;

import model.academic.Course;
import model.comunication.Request;
import model.comunication.News;
import model.users.User;
import java.util.*;

public class DataStorage {
    private static DataStorage instance;
    private List<Course> courses;
    private List<News> news;
    private List<Request> requests;
    private Vector<User> users = new Vector<User>();

    private DataStorage() {}

    public static DataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    public void addUser(User user) {
        users.add(user);
    }
    
    public void removeUser(User user) { 
    	users.remove(user); 
    	}
    public void addCourse(Course c) { 
    	courses.add(c);
    	}
    public void addNews(News n) { 
    	news.add(n);
    	}
    public void addRequest(Request r){ 
    	requests.add(r); 
    	}

    public Vector<User> getUsers() {
        return users;
    }
}