package model.comunication;

import model.users.Student;
import model.users.Teacher;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class RecommendationLetter implements Serializable {

    private static final long serialVersionUID = 1L;

    private Teacher author;
    private Student student;

    private String purpose;
    private String text;

    private Date date;

    private boolean signed;

    public RecommendationLetter(Teacher author,
                                Student student,
                                String purpose,
                                String text) {

        this.author = author;
        this.student = student;

        this.purpose = purpose;
        this.text = text;

        this.date = new Date();

        signed = false;
    }

    public void sign() {
        signed = true;

        System.out.println("\n==============================");
        System.out.println(" Letter signed successfully ");
        System.out.println("==============================");

        System.out.println("Teacher : " + author);
        System.out.println("Student : " + student);
        System.out.println("Purpose : " + purpose);
    }

    public void print() {

        System.out.println("\n========================================");
        System.out.println("         RECOMMENDATION LETTER");
        System.out.println("========================================");

        System.out.println("Date      : " + date);

        System.out.println("Teacher   : "
                + author.getFirstName()
                + " "
                + author.getLastName());

        System.out.println("Position  : "
                + author.getPosition());

        System.out.println("Student   : "
                + student.getFirstName()
                + " "
                + student.getLastName());

        System.out.printf("GPA       : %.2f%n",
                student.getGpa());

        System.out.println("Purpose   : " + purpose);

        System.out.println("----------------------------------------");

        System.out.println(text);

        System.out.println("----------------------------------------");

        if (signed) {
            System.out.println("Status    : SIGNED");
        }
        else {
            System.out.println("Status    : NOT SIGNED");
        }

        System.out.println("========================================");
    }

    @Override
    public String toString() {

        return "RecommendationLetter{"
                + "teacher=" + author
                + ", student=" + student
                + ", purpose='" + purpose + '\''
                + ", signed=" + signed
                + '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof RecommendationLetter)) {
            return false;
        }

        RecommendationLetter that = (RecommendationLetter) o;

        return Objects.equals(author, that.author)
                && Objects.equals(student, that.student)
                && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, student, date);
    }

    public Teacher getAuthor() {
        return author;
    }

    public Student getStudent() {
        return student;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getText() {
        return text;
    }

    public Date getDate() {
        return date;
    }

    public boolean isSigned() {
        return signed;
    }
}