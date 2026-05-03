package model.academic;

import model.enums.RequestStatus;
import model.users.Student;
import java.util.Date;
import java.util.Objects;

public class Enrollment {

    private RequestStatus status;
    private Date date;
    private Student student;
    private Course course;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course  = course;
        this.status  = RequestStatus.NEW;
        this.date    = new Date();
    }

    public void approve() {
        this.status = RequestStatus.ACCEPTED;
        course.enrollStudent(student);
        System.out.println("[Enrollment] Approved: " + student + " → " + course);
    }

    public void reject() {
        this.status = RequestStatus.REJECTED;
        System.out.println("[Enrollment] Rejected: " + student + " → " + course);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[student=" + student
                + ", course=" + course
                + ", status=" + status + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment e = (Enrollment) o;
        return Objects.equals(student, e.student)
                && Objects.equals(course, e.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }

    public RequestStatus getStatus() { return status; }
    public Student getStudent()      { return student; }
    public Course getCourse()        { return course; }
    public Date getDate()            { return date; }
}
