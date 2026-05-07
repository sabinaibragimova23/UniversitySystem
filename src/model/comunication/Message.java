package model.comunication;

import model.users.User;
import java.util.*;

public class Message {

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
        receiver.update("Message", "[From " + sender + "]: " + content);
        System.out.println("[Sent] " + sender + " → " + receiver + ": " + content);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[from=" + sender
                + ", to=" + receiver
                + ", content=" + content
                + ", date=" + date + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message m = (Message) o;
        return Objects.equals(sender, m.sender)
                && Objects.equals(receiver, m.receiver)
                && Objects.equals(date, m.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender, receiver, date);
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
}
