package model.communication;

import model.users.User;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    private User sender;
    private User receiver;
    private String content;
    private Date date;

    public Message(User sender, User receiver, String content) {
        this.sender = sender;
        this.receiver = receiver;
        this.content = content;
        this.date = new Date();
    }

    public void send() {
        receiver.update( "Message", "[From " + sender + "]: " + content
        );

        System.out.println( "[Sent] " + sender + " " + receiver + ": " + content);
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Message[from=" + sender + ", to=" + receiver + ", content=" + content + "]";
    }
}