package model.comunication;

import model.users.Employee;
import model.enums.RequestStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @Override
    public String toString() {
        return "Request[description="
                + description
                + ", status="
                + status
                + ", author="
                + author
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, author);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Request))
            return false;
        Request r = (Request) o;
        return Objects.equals(description, r.description)
                && Objects.equals(author, r.author);
    }
}