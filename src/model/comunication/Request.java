package model.comunication;

import model.enums.RequestStatus;
import model.users.Employee;
import java.util.Date;
import java.util.Objects;

public class Request {

    private String description;
    private RequestStatus status;
    private Employee author;
    private Date date;

    public Request(String description, Employee author) {
        this.description = description;
        this.author = author;
        this.status = RequestStatus.NEW;
        this.date = new Date();
    }

    public void updateStatus(RequestStatus newStatus) {
        this.status = newStatus;
        System.out.println("[Request] Status → " + newStatus + ": " + description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[description=" + description
                + ", status=" + status
                + ", author=" + author + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request r = (Request) o;
        return Objects.equals(description, r.description)
                && Objects.equals(author, r.author)
                && Objects.equals(date, r.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, author, date);
    }

    public String getDescription() { 
    	return description; 
    	}
    public RequestStatus getStatus() { 
    	return status; 
    	}
    public Employee getAuthor() { 
    	return author; 
    	}
    public Date getDate() { 
    	return date; 
    	}
}
