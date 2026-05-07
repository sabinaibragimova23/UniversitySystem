package model.comunication;

import model.enums.UrgencyLevel;
import model.users.Student;
import model.users.Teacher;
import java.util.Objects;

public class Complaint {

    private Student student;
    private Teacher head;
    private UrgencyLevel urgency;
    private String reason;
    private boolean isSigned;

    public Complaint(Student student, Teacher head,
                     UrgencyLevel urgency, String reason) {
        this.student = student;
        this.head = head;
        this.urgency = urgency;
        this.reason = reason;
        this.isSigned = false;
    }

    public void sign() {
        this.isSigned = true;
        System.out.println("[Complaint] Signed by " + head
                + " about " + student + " [" + urgency + "]");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[student=" + student
                + ", urgency=" + urgency
                + ", signed=" + isSigned
                + ", reason=" + reason + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complaint)) return false;
        Complaint c = (Complaint) o;
        return Objects.equals(student, c.student)
                && Objects.equals(head, c.head);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, head);
    }

    public Student getStudent() { 
    	return student; 
    	}
    public Teacher getHead() { 
    	return head; 
    	}
    public UrgencyLevel getUrgency() { 
    	return urgency;
    	}
    public String getReason() {
    	return reason; 
    	}
    public boolean isSigned() { 
    	return isSigned; 
    	}
}
