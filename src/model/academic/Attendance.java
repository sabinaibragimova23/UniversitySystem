package model.academic;

import model.users.Student;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    private Lesson lesson;
    private Student student;
    private boolean isPresent;
    private Date date;

    public Attendance(Lesson lesson, Student student, boolean isPresent) {
        this.lesson = lesson;
        this.student = student;
        this.isPresent = isPresent;
        this.date = new Date();
    }

    public static void markAt(Lesson lesson,
                              Student student,
                              boolean presence) {

        new Attendance(lesson, student, presence);

        System.out.println(
                "[Attendance] "
                + student.getFirstName()
                + " - "
                + lesson.getType()
                + " '"
                + lesson.getTopic()
                + "' : "
                + (presence ? "PRESENT" : "ABSENT")
        );
    }

    public Lesson getLesson() {
        return lesson;
    }

    public Student getStudent() {
        return student;
    }

    public boolean isPresent() {
        return isPresent;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Attendance[student="
                + student
                + ", lesson="
                + lesson.getType()
                + ", present="
                + isPresent
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(lesson, student);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof Attendance))
            return false;
        Attendance a = (Attendance) o;
        return lesson.equals(a.lesson)
                && student.equals(a.student);
    }
}