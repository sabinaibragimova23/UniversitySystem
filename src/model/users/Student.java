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

/**
 * Represents a student in the university system.
 * A student can register for courses, receive grades,
 * join organizations, subscribe to journals,
 * and participate in research activities.
 *
 * Inherits basic user functionality from {@link User}.
 *
 * @author Sabina
 * @version 1.0
 */
public class Student extends User implements Comparable<Student>, Serializable {
    private static final long serialVersionUID = 1L;
    protected String studentId;
    protected String major;
    protected int year;
    private double gpa;
    int credits;
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

    /**
     * Converts the student into a researcher
     * by creating a research profile.
     */
    public void becomeResearcher() {

        if (researchProfile == null) {

            researchProfile = new ResearchDecorator(this);

            System.out.println(this + " is now a Researcher.");
        }
    }

    /**
     * Checks whether the student is a researcher.
     *
     * @return true if the student has a research profile
     */
    public boolean isResearcher() {
        return researchProfile != null;
    }

    /**
     * Returns the student's research profile.
     *
     * @return research profile
     */
    public ResearchDecorator getResearchProfile() {
        return researchProfile;
    }

    /**
     * Registers the student for a course.
     *
     * @param c course to register
     * @throws CreditLimitException if total credits exceed 21
     */
    public void registerCourse(Course c) throws CreditLimitException {

        if (credits + c.getCredits() > 21)

            throw new CreditLimitException(
                    "Cannot register " + c.getName()
                            + ": exceeds 21 credits (current="
                            + credits + ")");

        courses.add(c);
        credits += c.getCredits();

        c.enrollStudent(this);

        System.out.println(
                "[Register] "
                        + this.firstName
                        + " registered for "
                        + c.getName()
        );
    }

    /**
     * Adds a mark for a course and updates GPA.
     *
     * @param course selected course
     * @param mark mark received for the course
     * @throws FailLimitException if student already failed 3 courses
     */
    public void addMark(Course course, Mark mark)
            throws FailLimitException {

        if (!mark.isPassed()) {

            if (failCount >= 3) {

                throw new FailLimitException(
                        this + " already failed 3 courses."
                );
            }

            failCount++;
        }

        marks.put(course, mark);

        transcript.addMark(course, mark);

        this.gpa = transcript.calculateGPA();
    }

    /**
     * Displays and returns all student marks.
     *
     * @return unmodifiable map of marks
     */
    public Map<Course, Mark> viewMarks() {

        for (Map.Entry<Course, Mark> e : marks.entrySet()) {

            System.out.println(
                    "  "
                            + e.getKey().getName()
                            + ": "
                            + e.getValue()
            );
        }

        return Collections.unmodifiableMap(marks);
    }

    /**
     * Displays all registered courses.
     */
    public void viewCourses() {

        for (Course c : courses) {

            System.out.println("  " + c);
        }
    }

    /**
     * Displays information about instructors of a course.
     *
     * @param course selected course
     */
    public void viewTeacherInfo(Course course) {

        System.out.println(
                "Instructors for "
                        + course.getName()
                        + ":"
        );

        course.getInstructors().forEach(t ->

                System.out.println(
                        "  "
                                + t
                                + " | "
                                + t.getPosition()
                                + " | rating="
                                + String.format("%.1f", t.getRating())
                )
        );
    }

    /**
     * Returns student's transcript.
     *
     * @return transcript object
     */
    public Transcript getTranscript() {
        return transcript;
    }

    /**
     * Rates a teacher.
     *
     * @param teacher teacher to rate
     * @param rating rating value
     */
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

    /**
     * Displays organization information.
     */
    public void viewOrg() {

        if (organization == null) {

            System.out.println("Not in any organization.");

        } else {

            System.out.println(organization);
        }
    }

    /**
     * Adds the student to an organization.
     *
     * @param org organization to join
     */
    public void joinOrganization(StudentOrganization org) {

        this.organization = org;
    }

    /**
     * Subscribes the student to a journal.
     *
     * @param journal journal to subscribe
     */
    public void subscribeToJournal(Journal journal) {

        journal.subscribe(this);

        subscribedJournals.add(journal);
    }

    /**
     * Unsubscribes the student from a journal.
     *
     * @param journal journal to unsubscribe
     */
    public void unsubscribeFromJournal(Journal journal) {

        journal.unsubscribe(this);

        subscribedJournals.remove(journal);
    }

    /**
     * Compares students by GPA in descending order.
     *
     * @param other another student
     * @return comparison result
     */
    @Override
    public int compareTo(Student other) {

        return Double.compare(other.gpa, this.gpa);
    }

    /**
     * Sets student identifier.
     *
     * @param studentId new student id
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Sets student's major.
     *
     * @param major new major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Sets academic year.
     *
     * @param year new year of study
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Returns student id.
     *
     * @return student id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Returns student major.
     *
     * @return major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Returns academic year.
     *
     * @return year of study
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns GPA.
     *
     * @return GPA value
     */
    public double getGpa() {
        return gpa;
    }

    /**
     * Returns total credits.
     *
     * @return number of credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Returns number of failed courses.
     *
     * @return fail count
     */
    public int getFailCount() {
        return failCount;
    }

    /**
     * Returns registered courses.
     *
     * @return unmodifiable list of courses
     */
    public List<Course> getCourses() {

        return Collections.unmodifiableList(courses);
    }

    /**
     * Returns all marks.
     *
     * @return unmodifiable map of marks
     */
    public Map<Course, Mark> getMarks() {

        return Collections.unmodifiableMap(marks);
    }

    /**
     * Returns student's organization.
     *
     * @return organization object
     */
    public StudentOrganization getOrganization() {
        return organization;
    }

    /**
     * Returns string representation of student.
     *
     * @return formatted student information
     */
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