package model.academic;

import java.util.Objects;
import model.enums.GradeLetter;

public class Mark {

    private double firstAttestation;
    private double secondAttestation;
    private double finalExam;
    private Course course;

    public Mark(double firstAttestation, double secondAttestation,
                double finalExam, Course course) {
        this.firstAttestation = firstAttestation;
        this.secondAttestation = secondAttestation;
        this.finalExam = finalExam;
        this.course = course;
    }

    public double getTotal() {
        return firstAttestation + secondAttestation + finalExam;
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
        if (t >= 50) return GradeLetter.D;
        return GradeLetter.F;
    }

    public boolean isPassed() {
        return getTotal() >= 50 && finalExam >= 20;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[att1=" + firstAttestation
                + ", att2=" + secondAttestation
                + ", final=" + finalExam
                + ", total=" + getTotal()
                + ", grade=" + getGrade() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mark)) return false;
        Mark m = (Mark) o;
        return Objects.equals(course, m.course)
                && Double.compare(getTotal(), m.getTotal()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, getTotal());
    }

    public double getFirstAttestation()  { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam()         { return finalExam; }
    public Course getCourse()            { return course; }
}
