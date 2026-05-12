package model.academic;

import java.io.Serializable;
import java.util.*;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<Course, Mark> records;

    private double gpa;

    public Transcript() {

        this.records = new LinkedHashMap<>();
    }

    public void addMark(
            Course course,
            Mark mark
    ) {

        records.put(course, mark);

        this.gpa = calculateGPA();
    }

    public double calculateGPA() {

        if (records.isEmpty()) {

            return 0.0;
        }

        double totalPoints = 0.0;

        int totalCredits = 0;

        for (Map.Entry<Course, Mark> entry
                : records.entrySet()) {

            Course course = entry.getKey();

            Mark mark = entry.getValue();

            totalPoints +=
                    mark.getGradePoints()
                    * course.getCredits();

            totalCredits +=
                    course.getCredits();
        }

        if (totalCredits == 0) {

            return 0.0;
        }

        return Math.round(
                (totalPoints / totalCredits)
                * 100.0
        ) / 100.0;
    }

    public void print() {

        System.out.println("Transcript:");

        for (Map.Entry<Course, Mark> entry
                : records.entrySet()) {

            Course course = entry.getKey();

            Mark mark = entry.getValue();

            System.out.println(
                    course.getName()
                    + " | "
                    + mark.getGrade()
                    + " | GPA points: "
                    + mark.getGradePoints()
            );
        }

        System.out.println(
                "GPA: "
                + calculateGPA()
        );
    }

    @Override
    public String toString() {

        return "Transcript[gpa="
                + calculateGPA()
                + "]";
    }

    public Map<Course, Mark> getRecords() {

        return Collections.unmodifiableMap(
                records
        );
    }

    public double getGpa() {

        return gpa;
    }
}