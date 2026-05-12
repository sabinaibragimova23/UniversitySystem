package model.users;

import model.academic.AcademicReport;
import model.academic.Course;
import model.academic.Mark;
import model.comunication.RecommendationLetter;
import model.comunication.Complaint;
import model.enums.Language;
import model.enums.TeacherType;
import model.enums.UrgencyLevel;
import model.exceptions.FailLimitException;
import model.research.Journal;
import model.research.ResearchDecorator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Teacher extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private TeacherType position;
    private List<Course> courses;
    private double rating;
    private int ratingCount;
    private List<Journal> subscribedJournals;
    private ResearchDecorator researchProfile;

    public Teacher(int id,
                   String login,
                   String password,
                   String firstName,
                   String lastName,
                   TeacherType position) {

    	super(id,
    	          login,
    	          password,
    	          firstName,
    	          lastName);
        this.position = position;
        this.language = Language.EN;

        this.courses = new ArrayList<>();
        this.subscribedJournals = new ArrayList<>();

        this.rating = 0.0;
        this.ratingCount = 0;

        if (position.isAlwaysResearcher()) {
            this.researchProfile = new ResearchDecorator(this);
        }
    }

    public void becomeResearcher() {
        if (researchProfile == null) {
            researchProfile = new ResearchDecorator(this);
            System.out.println(this + " is now a Researcher.");
        }
    }

    public boolean isResearcher() {
        return researchProfile != null;
    }

    public ResearchDecorator getResearchProfile() {
        return researchProfile;
    }

    public void putMark(Student student, Course course, Mark mark) {

        try {
            student.addMark(course, mark);
            System.out.println(
                    "[Mark] "
                            + student.getFirstName()
                            + " -> "
                            + course.getName()
                            + ": "
                            + mark.getGrade()
                            + " (total="
                            + mark.getTotal()
                            + ")"
            );

        } catch (FailLimitException e) {
            System.out.println("[Mark ERROR] " + e.getMessage());
        }
    }

    public void sendComplaint(Student student,
                              UrgencyLevel urgency,
                              String reason) {
        Complaint c = new Complaint(student, this, urgency, reason);

        c.sign();

        System.out.println("[Complaint sent] " + c);
    }

    public void generateReport() {
        System.out.println("=== Report by " + this + " ===");
        for (Course c : courses) {
            System.out.println(
                    "Course: "
                            + c.getName()
                            + " | students: "
                            + c.getStudents().size()
            );
            for (Student s : c.getStudents()) {
                System.out.println("  " + s);
            }
        }
    }

    public void viewCourses() {
        for (Course c : courses) {
            System.out.println("  " + c);
        }
    }

    public void manageCourses() {
        System.out.println(
                "[Teacher] Managing "
                        + courses.size()
                        + " course(s)."
        );
    }

    public RecommendationLetter writeRecommendation(Student student,
                                                    String purpose,
                                                    String body) {

        RecommendationLetter letter =
                new RecommendationLetter(this, student, purpose, body);

        letter.sign();

        return letter;
    }

    public void generateDetailedReport(Course course) {

        new AcademicReport(this).generateCourseReport(course);
    }

    public void generateTeacherSummary() {
        new AcademicReport(this).generateTeacherSummary();
    }

    public String getInfo() {

        return getClass().getSimpleName()
                + "[name="
                + firstName
                + " "
                + lastName
                + ", position="
                + position
                + ", rating="
                + String.format("%.1f", rating)
                + "]";
    }

    public void addRating(double score) {
        rating = (rating * ratingCount + score) / (++ratingCount);
    }

    public void assignCourse(Course course) {
        courses.add(course);
    }

    public void subscribeToJournal(Journal journal) {
        journal.subscribe(this);
        subscribedJournals.add(journal);
    }

    public void unsubscribeFromJournal(Journal journal) {
        journal.unsubscribe(this);
        subscribedJournals.remove(journal);
    }

    @Override
    public String toString() {
        return getInfo();
    }
    
    public void setPosition(TeacherType position) {
        this.position = position;
    }

    public TeacherType getPosition() {
        return position;
    }

    public double getRating() {
        return rating;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public List<Journal> getSubscribedJournals() {
        return Collections.unmodifiableList(subscribedJournals);
    }
}