package model.research;

import model.users.User;
import java.util.*;


public class Journal {

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
        }
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }

    public void publishPaper(ResearchPaper paper) {
        papers.add(paper);
        notifySubscribers(paper);
    }

    private void notifySubscribers(ResearchPaper paper) {
        for (User user : subscribers) {
            System.out.println("[Journal: " + name + "] Notification for " + user +
                    ": new paper published — '" + paper.getTitle() + "'");
        }
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

    @Override
    public String toString() {
        return "Journal{" +
                "name='" + name + '\'' +
                ", papers=" + papers.size() +
                ", subscribers=" + subscribers.size() +
                '}';
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
}
