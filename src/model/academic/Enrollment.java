package model.academic;

import model.users.Student;
import model.enums.RequestStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Enrollment implements Serializable {

    private static final long serialVersionUID = 1L;

    private RequestStatus status;
    private Date date;
    private Student student;
    private Course course;

    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.status = RequestStatus.NEW;
        this.date = new Date();
    }

    public void approve() {

        status = RequestStatus.ACCEPTED;
        course.enrollStudent(student);
        System.out.println(
                "[Enrollment] Approved: "
                + student
                + " -> "
                + course
        );
    }

    public void reject() {
        status = RequestStatus.REJECTED;
        System.out.println(
                "[Enrollment] Rejected: "
                + student
                + " -> "
                + course
        );
    }

    public RequestStatus getStatus() {
        return status;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Enrollment[student="
                + student
                + ", course="
                + course
                + ", status="
                + status
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Enrollment))
            return false;
        Enrollment e = (Enrollment) o;
        return student.equals(e.student)
                && course.equals(e.course);
    }
}