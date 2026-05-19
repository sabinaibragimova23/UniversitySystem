package model.communication;

import model.users.Student;
import model.users.Teacher;
import model.enums.UrgencyLevel;

import java.io.Serializable;
import java.util.Objects;

public class Complaint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Student student;
    private Teacher head;
    private UrgencyLevel urgency;
    private String reason;
    private boolean isSigned;

    public Complaint(Student student,
                     Teacher head,
                     UrgencyLevel urgency,
                     String reason) {

        this.student = student;
        this.head = head;
        this.urgency = urgency;
        this.reason = reason;
        this.isSigned = false;
    }

    public void sign() {

        isSigned = true;

        System.out.println(
                "[Complaint] Signed by "
                + head
                + " about "
                + student
                + " ["
                + urgency
                + "]"
        );
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

    @Override
    public String toString() {
        return "Complaint[student="
                + student
                + ", urgency="
                + urgency
                + ", signed="
                + isSigned
                + ", reason="
                + reason
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, head);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Complaint))
            return false;
        Complaint c = (Complaint) o;
        return student.equals(c.student)
                && head.equals(c.head);
    }
}