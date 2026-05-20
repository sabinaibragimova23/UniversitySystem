package model.research;

import model.observer.Observable;
import model.observer.Observer;
import model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Journal implements Observable, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private List<ResearchPaper> papers;
    private List<User> subscribers;

    public Journal(String name) {
        this.name = name;
        this.papers = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(User user) {
        if (!subscribers.contains(user)) {
            subscribers.add(user);
            System.out.println( "[Journal: " + name + "] " + user + " subscribed." );
        }
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    public void publishPaper(ResearchPaper paper) {
        papers.add(paper);
        notifyObservers(
                "NewPaper in " + name, paper.getTitle()
        );
    }

    @Override
    public void addObserver(Observer o) {
        if (o instanceof User) {
            User u = (User) o;
            if (!subscribers.contains(u)) {
                subscribers.add(u);
            }
        }
    }

    @Override
    public void removeObserver(Observer o) {
        subscribers.remove(o);
    }

    @Override
    public void notifyObservers(String event, Object data) {
        for (User u : subscribers) {
            u.update(event, data);
        }
    }

    public String getName() {
        return name;
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public List<User> getSubscribers() {
        return Collections.unmodifiableList(subscribers);
    }

    @Override
    public String toString() {
        return "Journal[name=" + name + ", papers=" + papers.size() + ", subscribers=" + subscribers.size() + "]";
    }
}