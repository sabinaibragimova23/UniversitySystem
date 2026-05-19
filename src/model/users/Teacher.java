package model.users;

import model.academic.AcademicReport;
import model.academic.Course;
import model.academic.Mark;
import model.communication.Complaint;
import model.communication.RecommendationLetter;
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

/**
 * Represents a teacher in the university system.
 * A teacher can manage courses, put marks,
 * generate reports, write recommendation letters,
 * send complaints, and participate in research activities.
 *
 * Inherits employee functionality from {@link Employee}.
 *
 * @author Sabina
 * @version 1.0
 */
public class Teacher extends Employee implements Serializable {

    /**
     * Serialization version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Teacher academic position.
     */
    private TeacherType position;

    /**
     * Courses assigned to the teacher.
     */
    private List<Course> courses;

    /**
     * Average teacher rating.
     */
    private double rating;

    /**
     * Number of submitted ratings.
     */
    private int ratingCount;

    /**
     * Journals subscribed by the teacher.
     */
    private List<Journal> subscribedJournals;

    /**
     * Research profile of the teacher.
     * Null if teacher is not a researcher.
     */
    private ResearchDecorator researchProfile;

    /**
     * Constructs a Teacher object.
     *
     * @param id system user id
     * @param login teacher login
     * @param password teacher password
     * @param firstName teacher first name
     * @param lastName teacher last name
     * @param position teacher academic position
     */
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

    /**
     * Converts the teacher into a researcher
     * by creating a research profile.
     */
    public void becomeResearcher() {

        if (researchProfile == null) {

            researchProfile = new ResearchDecorator(this);

            System.out.println(this + " is now a Researcher.");
        }
    }

    /**
     * Checks whether the teacher is a researcher.
     *
     * @return true if teacher has a research profile
     */
    public boolean isResearcher() {

        return researchProfile != null;
    }

    /**
     * Returns teacher research profile.
     *
     * @return research profile
     */
    public ResearchDecorator getResearchProfile() {

        return researchProfile;
    }

    /**
     * Assigns a mark to a student for a course.
     *
     * @param student selected student
     * @param course selected course
     * @param mark assigned mark
     */
    public void putMark(Student student,
                        Course course,
                        Mark mark) {

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

            System.out.println(
                    "[Mark ERROR] "
                            + e.getMessage()
            );
        }
    }

    /**
     * Sends a complaint about a student.
     *
     * @param student target student
     * @param urgency urgency level
     * @param reason complaint reason
     */
    public void sendComplaint(Student student,
                              UrgencyLevel urgency,
                              String reason) {

        Complaint c =
                new Complaint(student,
                        this,
                        urgency,
                        reason);

        c.sign();

        System.out.println(
                "[Complaint sent] "
                        + c
        );
    }

    /**
     * Generates a report for all teacher courses.
     */
    public void generateReport() {

        System.out.println("Report by " + this);

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

    /**
     * Displays all teacher courses.
     */
    public void viewCourses() {

        for (Course c : courses) {

            System.out.println("  " + c);
        }
    }

    /**
     * Displays course management information.
     */
    public void manageCourses() {

        System.out.println(
                "[Teacher] Managing "
                        + courses.size()
                        + " course(s)."
        );
    }

    /**
     * Writes a recommendation letter for a student.
     *
     * @param student target student
     * @param purpose recommendation purpose
     * @param body recommendation text
     * @return signed recommendation letter
     */
    public RecommendationLetter writeRecommendation(
            Student student,
            String purpose,
            String body) {

        RecommendationLetter letter =
                new RecommendationLetter(
                        this,
                        student,
                        purpose,
                        body
                );

        letter.sign();

        return letter;
    }

    /**
     * Generates a detailed report for a course.
     *
     * @param course selected course
     */
    public void generateDetailedReport(Course course) {

        new AcademicReport(this)
                .generateCourseReport(course);
    }

    /**
     * Generates a summary report for the teacher.
     */
    public void generateTeacherSummary() {

        new AcademicReport(this)
                .generateTeacherSummary();
    }

    /**
     * Returns formatted teacher information.
     *
     * @return teacher information string
     */
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

    /**
     * Adds a new rating to the teacher.
     *
     * @param score rating score
     */
    public void addRating(double score) {

        rating =
                (rating * ratingCount + score)
                        / (++ratingCount);
    }

    /**
     * Assigns a course to the teacher.
     *
     * @param course course to assign
     */
    public void assignCourse(Course course) {

        if (!courses.contains(course)) {

            courses.add(course);
        }

        course.addTeacher(this);
    }

    /**
     * Subscribes the teacher to a journal.
     *
     * @param journal journal to subscribe
     */
    public void subscribeToJournal(Journal journal) {

        journal.subscribe(this);

        subscribedJournals.add(journal);
    }

    /**
     * Unsubscribes the teacher from a journal.
     *
     * @param journal journal to unsubscribe
     */
    public void unsubscribeFromJournal(Journal journal) {

        journal.unsubscribe(this);

        subscribedJournals.remove(journal);
    }

    /**
     * Returns string representation of teacher.
     *
     * @return teacher information
     */
    @Override
    public String toString() {

        return getInfo();
    }

    /**
     * Sets teacher position.
     *
     * @param position new teacher position
     */
    public void setPosition(TeacherType position) {

        this.position = position;
    }

    /**
     * Returns teacher position.
     *
     * @return teacher type
     */
    public TeacherType getPosition() {

        return position;
    }

    /**
     * Returns average teacher rating.
     *
     * @return rating value
     */
    public double getRating() {

        return rating;
    }

    /**
     * Returns assigned courses.
     *
     * @return unmodifiable list of courses
     */
    public List<Course> getCourses() {

        return Collections.unmodifiableList(courses);
    }

    /**
     * Returns subscribed journals.
     *
     * @return unmodifiable list of journals
     */
    public List<Journal> getSubscribedJournals() {

        return Collections.unmodifiableList(subscribedJournals);
    }
}