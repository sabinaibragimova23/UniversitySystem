package model.academic;

import model.enums.CourseType;
import model.users.Teacher;
import model.users.Student;

import java.util.*;

public class Course {
    private String courseId;
    private String name;
    private String idls;
    private CourseType type;
    private int credits;
    private List<Teacher> teachers;
    private List<Student> students;
    private List<Lesson> lessons;
    private String major;
    private int year;

    public Course(
            String courseId,
            String name,
            CourseType type,
            int credits
    ) {

        this.courseId = courseId;
        this.name = name;
        this.type = type;
        this.credits = credits;
        this.teachers = new ArrayList<>();
        this.students = new ArrayList<>();
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void addTeacher(Teacher teacher) {
        if (!teachers.contains(teacher)) {
            teachers.add(teacher);
        }
    }

    public void enrollStudent(Student student) {
        students.add(student);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return Objects.equals(
                courseId,
                ((Course) o).courseId
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId);
    }

    @Override
    public String toString() {

        return "Course{'"
                + name
                + "', "
                + type
                + ", "
                + credits
                + "cr}";
    }

    public String getCourseId() {

        return courseId;
    }

    public String getName() {

        return name;
    }

    public String getIdls() {

        return idls;
    }

    public CourseType getType() {

        return type;
    }

    public int getCredits() {

        return credits;
    }

    public String getMajor() {

        return major;
    }

    public void setMajor(String major) {

        this.major = major;
    }

    public int getYear() {

        return year;
    }

    public void setYear(int year) {

        this.year = year;
    }

    public List<Teacher> getInstructors() {

        return Collections.unmodifiableList(
                teachers
        );
    }

    public List<Student> getStudents() {

        return Collections.unmodifiableList(
                students
        );
    }

    public List<Lesson> getLessons() {

        return Collections.unmodifiableList(
                lessons
        );
    }
}