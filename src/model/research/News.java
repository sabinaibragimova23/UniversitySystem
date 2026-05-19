package model.research;

import java.util.*;

public class News implements java.io.Serializable {
    private String title;
    private String content;
    private String topic;
    private boolean isPinned;
    private Date date;
    private List<String> comments;

    public News(String title, String content, String topic, Date date) {
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.date = date;
        this.comments = new ArrayList<>();
        this.isPinned = topic != null && topic.equalsIgnoreCase("Research");
    }

    public void addComment(String comment) {
        comments.add(comment);
    }

    public void pin() {
        this.isPinned = true;
    }

    public void unpin() {
        this.isPinned = false;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", pinned=" + isPinned +
                ", date=" + date +
                '}';
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getTopic() {
        return topic;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public Date getDate() {
        return date;
    }

    public List<String> getComments() {
        return Collections.unmodifiableList(comments);
    }
}
