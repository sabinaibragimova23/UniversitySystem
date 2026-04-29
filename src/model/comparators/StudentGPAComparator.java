package model.comparators;

import java.util.Comparator;
import model.users.Student;

public class StudentGPAComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2) {
        return Double.compare(
            s1.getTranscript().calculateGPA(),
            s2.getTranscript().calculateGPA()
        );
    }
}