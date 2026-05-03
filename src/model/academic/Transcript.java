package model.academic;

import java.util.*;

public class Transcript {

    private Map<Course, Mark> records;
    private double gpa;
    private Course course;

    public Transcript() {
        this.records = new LinkedHashMap<>();
    }

    public void addMark(Course course, Mark mark) {
        records.put(course, mark);
        this.gpa = calculateGPA();
    }

    public double calculateGPA() {
        if (records.isEmpty()) return 0.0;
        double total = 0;
        for (Mark m : records.values()) total += m.getTotal();
        return Math.round((total / records.size() / 10.0) * 100.0) / 100.0;
    }

    public void print() {
        System.out.println("Transcript:");
        records.forEach((c, m) ->
                System.out.printf("  %-30s %s%n", c.getName(), m));
        System.out.printf("  GPA: %.2f%n", calculateGPA());
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[records=" + records.size()
                + ", gpa=" + calculateGPA() + "]";
    }

    public Map<Course, Mark> getRecords() { return Collections.unmodifiableMap(records); }
    public double getGpa()                { return gpa; }
    public Course getCourse()             { return course; }
}
