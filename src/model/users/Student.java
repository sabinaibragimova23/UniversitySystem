package model.users;

import model.academic.*;
import model.enums.Language;
import model.exceptions.CreditLimitException;
import model.exceptions.FailLimitException;
import model.research.Journal;
import model.research.ResearchDecorator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User implements Comparable<Student>, Serializable {
    private static final long serialVersionUID = 1L;
    protected String studentId;
    protected String major;
    protected int year;
    private double gpa;
    private int credits;
    private int failCount;

    private Transcript transcript;
    private StudentOrganization organization;

    private Map<Course, Mark> marks;
    private List<Course> courses;
    private List<Journal> subscribedJournals;

    private ResearchDecorator researchProfile;

    public Student(int id,
                   String login,
                   String password,
                   String firstName,
                   String lastName,
                   String studentId,
                   String major,
                   int year) {

    	super(id, login, password, firstName, lastName);

        this.studentId = studentId;
        this.major = major;
        this.year = year;

        this.credits = 0;
        this.failCount = 0;
        this.gpa = 0.0;

        this.marks = new HashMap<>();
        this.courses = new ArrayList<>();
        this.subscribedJournals = new ArrayList<>();

        this.transcript = new Transcript();

        this.language = Language.EN;
        this.researchProfile = null;
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

    public void registerCourse(Course c) throws CreditLimitException {
        if (credits + c.getCredits() > 21)
            throw new CreditLimitException(
                "Cannot register " + c.getName() + ": exceeds 21 credits (current=" + credits + ")");
        courses.add(c);
        credits += c.getCredits();
        c.enrollStudent(this); 
        System.out.println("[Register] " + this.firstName + " registered for " + c.getName());
    }




    public void addMark(Course course, Mark mark) throws FailLimitException {

        if (!mark.isPassed()) {

            if (failCount >= 3) {
                throw new FailLimitException(this + " already failed 3 courses.");
            }

            failCount++;
        }

        marks.put(course, mark);

        transcript.addMark(course, mark);

        this.gpa = transcript.calculateGPA();
    }

    public Map<Course, Mark> viewMarks() {

        for (Map.Entry<Course, Mark> e : marks.entrySet()) {
            System.out.println("  " + e.getKey().getName() + ": " + e.getValue());
        }

        return Collections.unmodifiableMap(marks);
    }

    public void viewCourses() {

        for (Course c : courses) {
            System.out.println("  " + c);
        }
    }

    public void viewTeacherInfo(Course course) {

        System.out.println("Instructors for " + course.getName() + ":");

        course.getInstructors().forEach(t ->
                System.out.println(
                        "  " + t
                                + " | "
                                + t.getPosition()
                                + " | rating="
                                + String.format("%.1f", t.getRating())
                )
        );
    }

    public Transcript getTranscript() {
        return transcript;
    }

    public void rateTeacher(Teacher teacher, double rating) {

        teacher.addRating(rating);

        System.out.println(
                "[Rating] "
                        + firstName
                        + " rated "
                        + teacher.getFirstName()
                        + ": "
                        + rating
        );
    }

    public void viewOrg() {

        if (organization == null) {
            System.out.println("Not in any organization.");
        } else {
            System.out.println(organization);
        }
    }

    public void joinOrganization(StudentOrganization org) {
        this.organization = org;
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
    public int compareTo(Student other) {

        return Double.compare(other.gpa, this.gpa);
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getMajor() {
        return major;
    }

    public int getYear() {
        return year;
    }

    public double getGpa() {
        return gpa;
    }

    public int getCredits() {
        return credits;
    }

    public int getFailCount() {
        return failCount;
    }

    public List<Course> getCourses() {
        return Collections.unmodifiableList(courses);
    }

    public Map<Course, Mark> getMarks() {
        return Collections.unmodifiableMap(marks);
    }

    public StudentOrganization getOrganization() {
        return organization;
    }

    @Override
    public String toString() {

        return getClass().getSimpleName()
                + "[name="
                + firstName
                + " "
                + lastName
                + ", major="
                + major
                + ", gpa="
                + String.format("%.2f", gpa)
                + ", credits="
                + credits
                + ", researcher="
                + isResearcher()
                + "]";
    }
}