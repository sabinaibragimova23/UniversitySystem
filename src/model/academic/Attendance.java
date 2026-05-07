package model.academic;

import model.users.Student;
import java.util.Date;
import java.util.Objects;

public class Attendance {

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

    public static void markAt(Lesson lesson, Student student, boolean presence) {
        Attendance a = new Attendance(lesson, student, presence);
        System.out.println("[Attendance] " + student
                + " — " + lesson.getType()
                + ": " + (presence ? "present" : "absent"));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[student=" + student
                + ", lesson=" + lesson.getType()
                + ", present=" + isPresent
                + ", date=" + date + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attendance)) return false;
        Attendance a = (Attendance) o;
        return Objects.equals(lesson, a.lesson)
                && Objects.equals(student, a.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lesson, student);
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
}
