package model.academic;

import java.io.Serializable;
import java.util.Objects;
import model.enums.GradeLetter;

public class Mark implements Serializable {
    private static final long serialVersionUID = 1L;

    private double firstAttestation;

    private double secondAttestation;

    private double finalExam;

    private Course course;

    public Mark(
            double firstAttestation,
            double secondAttestation,
            double finalExam,
            Course course
    ) {

        this.firstAttestation = firstAttestation;

        this.secondAttestation = secondAttestation;

        this.finalExam = finalExam;

        this.course = course;
    }

    public double getTotal() {

        return firstAttestation
                + secondAttestation
                + finalExam;
    }

    public GradeLetter getGrade() {
        double t = getTotal();
        if (t >= 95) return GradeLetter.A;
        if (t >= 90) return GradeLetter.A_MINUS;
        if (t >= 85) return GradeLetter.B_PLUS;
        if (t >= 80) return GradeLetter.B;
        if (t >= 75) return GradeLetter.B_MINUS;
        if (t >= 70) return GradeLetter.C_PLUS;
        if (t >= 65) return GradeLetter.C;
        if (t >= 60) return GradeLetter.C_MINUS;
        if (t >= 55) return GradeLetter.D_PLUS;
        if (t >= 50) return GradeLetter.D;

        return GradeLetter.F;
    }

    public double getGradePoints() {

        GradeLetter grade = getGrade();

        switch (grade) {

            case A:
                return 4.0;

            case A_MINUS:
                return 3.67;

            case B_PLUS:
                return 3.33;

            case B:
                return 3.0;

            case B_MINUS:
                return 2.67;

            case C_PLUS:
                return 2.33;

            case C:
                return 2.0;

            case C_MINUS:
                return 1.67;

            case D_PLUS:
                return 1.33;

            case D:
                return 1.0;

            default:
                return 0.0;
        }
    }

    public boolean isPassed() {

        return getTotal() >= 50
                && finalExam >= 20;
    }

    @Override
    public String toString() {

        return getClass().getSimpleName()
                + "[att1=" + firstAttestation
                + ", att2=" + secondAttestation
                + ", final=" + finalExam
                + ", total=" + getTotal()
                + ", grade=" + getGrade()
                + ", GPA points=" + getGradePoints()
                + "]";
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (!(o instanceof Mark)) return false;

        Mark m = (Mark) o;

        return Objects.equals(course, m.course)
                && Double.compare(
                        getTotal(),
                        m.getTotal()
                ) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                course,
                getTotal()
        );
    }

    public double getFirstAttestation() {

        return firstAttestation;
    }

    public double getSecondAttestation() {

        return secondAttestation;
    }

    public double getFinalExam() {

        return finalExam;
    }

    public Course getCourse() {

        return course;
    }
}