package model.users;

import model.academic.Course;
import model.academic.Mark;
import model.enums.TeacherType;
import model.enums.UrgencyLevel;
import model.research.Journal;
import model.research.ResearchDecorator;

import java.util.*;

public class Teacher extends Employee {

    private TeacherType position;
    private List<Course> courses = new ArrayList<>();
    private double rating = 0.0;
    private int ratingCount = 0;
    private List<Journal> subscribedJournals = new ArrayList<>();
    private ResearchDecorator researchProfile;

    public Teacher(int id,
                   String firstName,
                   String lastName,
                   String email,
                   String password,
                   TeacherType position,
                   String employeeId,
                   String department) {

        super(id, firstName + " " + lastName, email, password, employeeId, department);

        this.position = position;

        if (position == TeacherType.PROFESSOR) {
            this.researchProfile = new ResearchDecorator(this);
        }
    }


    public void putMark(Student student, Course course, Mark mark) {
        student.addMark(mark);
        System.out.println("[Mark] " + student + " → " + course.getName() + ": " + mark);
    }

    public void sendComplaint(Student student, UrgencyLevel urgency, String reason) {
        System.out.println("[Complaint | " + urgency + "] "
                + this + " → Dean about " + student + ": " + reason);
    }

    public void generateReport() {
        System.out.println("=== Report by " + this + " ===");
        for (Course c : courses) {
            System.out.println("  " + c.getName() + ": " + c.getStudents().size() + " students");
        }
    }
    



    public void assignCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }



    public void addRating(double score) {
        if (score < 0 || score > 5) return;

        rating = (rating * ratingCount + score) / (++ratingCount);
    }

    public double getRating() {
        return rating;
    }

   

    public void becomeResearcher() {
        if (researchProfile == null) {
            researchProfile = new ResearchDecorator(this);
        }
    }

    public boolean isResearcher() {
        return researchProfile != null;
    }

    public Optional<ResearchDecorator> getResearchProfile() {
        return Optional.ofNullable(researchProfile);
    }

    public void subscribeToJournal(Journal journal) {
        journal.subscribe(this);
        subscribedJournals.add(journal);
    }

    public void unsubscribeFromJournal(Journal journal) {
        journal.unsubscribe(this);
        subscribedJournals.remove(journal);
    }

    public TeacherType getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Teacher{name='" + name +
                "', position=" + position +
                ", rating=" + String.format("%.1f", rating) + "}";
    }
}