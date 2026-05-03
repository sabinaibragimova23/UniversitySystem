package model.users;

import java.util.Vector;
import model.academic.Course;
import model.academic.Mark;
import model.academic.Transcript;
import model.exceptions.CreditLimitException;
import model.research.Journal;
import java.util.*;

public class Student extends User {
    private Vector<Course> courses = new Vector<Course>();
    private Vector<Mark> marks = new Vector<Mark>();
    private int totalCredits = 0;
    private StudentOrganization organization;
    private List<Journal> subscribedJournals;
    private Transcript transcript;

    public Student(int id, String name, String email, String password) {
        super(id, name, email, password);
        
        this.subscribedJournals = new ArrayList<>();
        this.transcript = new Transcript();
    }

    public void registerCourse(Course course) throws CreditLimitException {
        if (totalCredits + course.getCredits() > 21) {
            throw new CreditLimitException("Credit limit exceeded!");
        }

        courses.add(course);
        totalCredits += course.getCredits();
    }

    public void addMark(Mark mark) {
        marks.add(mark);
    }

    public double calculateGPA() {
        if (marks.size() == 0) return 0;

        double sum = 0;
        for (int i = 0; i < marks.size(); i++) {
            sum += marks.get(i).getFinalExam();
        }

        return sum / marks.size();
    }
    
    public Transcript getTranscript() {
        return transcript;
    }
    
    public void joinOrganization(StudentOrganization org) { this.organization = org; }

    public void viewMarks() {
        for (int i = 0; i < marks.size(); i++) {
            System.out.println(marks.get(i));
        }
    }
    
    public void subscribeToJournal(Journal journal) {
        journal.subscribe(this);
        subscribedJournals.add(journal);
    }

    public void unsubscribeFromJournal(Journal journal) {
        journal.unsubscribe(this);
        subscribedJournals.remove(journal);
    }

    public String toString() {
        return "Student: " + name + ", GPA: " + calculateGPA();
    }
}