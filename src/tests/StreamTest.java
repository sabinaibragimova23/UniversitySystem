package tests;

import model.academic.Course;
import model.academic.Mark;
import model.enums.CourseType;
import model.enums.TeacherType;
import model.research.ResearchPaper;
import model.users.Student;
import model.users.Teacher;

import java.util.*;
import java.util.stream.Collectors;

public class StreamTest {

    public static void main(String[] args) throws Exception {

        Course oop = new Course("CS101", "OOP & Design", CourseType.MAJOR, 5);
        Course math = new Course("MATH1", "Discrete Math", CourseType.MAJOR, 5);
        Course ai = new Course("AI401", "AI Fundamentals", CourseType.MAJOR, 5);

        Teacher prof = new Teacher(1, "prof", "pass", "Ali", "Seitkali", TeacherType.PROFESSOR);

        Student s1 = new Student(2, "stud1", "pass", "Maxim", "Petrov", "s001", "SITE", 2);
        Student s2 = new Student(3, "stud2", "pass", "Asel", "Nurova", "s002", "SITE", 2);
        Student s3 = new Student(4, "stud3", "pass", "Daniyar", "Bekurov", "s003", "CS", 3);
        Student s4 = new Student(5, "stud4", "pass", "Zarina", "Ospanova", "s004", "SITE", 2);

        s1.registerCourse(oop);
        s1.registerCourse(math);

        s2.registerCourse(oop);
        s2.registerCourse(ai);

        s3.registerCourse(oop);
        s3.registerCourse(math);

        s4.registerCourse(ai);

        prof.putMark(s1, oop, new Mark(28, 27, 38, oop));
        prof.putMark(s1, math, new Mark(20, 20, 30, math));

        prof.putMark(s2, oop, new Mark(25, 25, 35, oop));
        prof.putMark(s2, ai, new Mark(30, 28, 40, ai));

        prof.putMark(s3, oop, new Mark(15, 15, 20, oop));
        prof.putMark(s3, math, new Mark(22, 23, 33, math));

        prof.putMark(s4, ai, new Mark(10, 10, 10, ai));

        List<Student> students = Arrays.asList(s1, s2, s3, s4);

        System.out.println("======================================");
        System.out.println("STREAM API DEMO — University Data");
        System.out.println("======================================");

        System.out.println("\n-- 1. Students with GPA > 3.0 --");
        students.stream()
                .filter(s -> s.getGpa() > 3.0)
                .sorted()
                .forEach(s -> System.out.println("  " + s.getFirstName() + " GPA=" + s.getGpa()));

        System.out.println("\n-- 2. Student names (uppercase) --");
        students.stream()
                .map(s -> s.getFirstName().toUpperCase() + " " + s.getLastName().toUpperCase())
                .forEach(n -> System.out.println("  " + n));

        System.out.println("\n-- 3. Average GPA --");
        double avgGpa = students.stream()
                .mapToDouble(Student::getGpa)
                .average()
                .orElse(0);

        System.out.printf("  Average GPA: %.2f%n", avgGpa);

        System.out.println("\n-- 4. Students grouped by major --");
        Map<String, Long> byMajor = students.stream()
                .collect(Collectors.groupingBy(Student::getMajor, Collectors.counting()));

        byMajor.forEach((major, count) ->
                System.out.println("  " + major + ": " + count + " student(s)"));

        System.out.println("\n-- 5. Top student (max GPA) --");
        students.stream()
                .max(Comparator.comparingDouble(Student::getGpa))
                .ifPresent(s ->
                        System.out.println("  " + s.getFirstName() + " " + s.getLastName()
                                + " | GPA=" + s.getGpa()));

        System.out.println("\n-- 6. Students with at least 1 fail --");
        long failedCount = students.stream()
                .filter(s -> s.getFailCount() > 0)
                .count();

        System.out.println("  Failed students: " + failedCount);

        System.out.println("\n-- 7. Distinct majors --");
        students.stream()
                .map(Student::getMajor)
                .distinct()
                .sorted()
                .forEach(m -> System.out.println("  " + m));

        System.out.println("\n-- 8. OOP course mark totals --");
        students.stream()
                .filter(s -> s.getMarks().containsKey(oop))
                .sorted((a, b) -> Double.compare(
                        b.getMarks().get(oop).getTotal(),
                        a.getMarks().get(oop).getTotal()))
                .forEach(s -> {
                    Mark m = s.getMarks().get(oop);
                    System.out.printf("  %-15s total=%.0f grade=%s%n",
                            s.getFirstName(), m.getTotal(), m.getGrade());
                });

        System.out.println("\n-- 9. Research papers (sorted by citations) --");

        ResearchPaper p1 = new ResearchPaper("Deep Learning in NLP",
                Arrays.asList("Seitkali A."), "AI Journal", 45, new Date(), "doi1");

        ResearchPaper p2 = new ResearchPaper("RL Survey",
                Arrays.asList("Seitkali A."), "ML Journal", 30, new Date(), "doi2");

        ResearchPaper p3 = new ResearchPaper("Federated Learning",
                Arrays.asList("Seitkali A."), "IEEE", 60, new Date(), "doi3");

        p1.setCitations(5);
        p2.setCitations(2);
        p3.setCitations(8);

        List<ResearchPaper> papers = Arrays.asList(p1, p2, p3);

        papers.stream()
                .sorted(Comparator.comparingInt(ResearchPaper::getCitations).reversed())
                .forEach(p -> System.out.printf("  %-30s citations=%d pages=%d%n",
                        p.getTitle(), p.getCitations(), p.getPages()));

        System.out.println("\n-- 10. Students who passed OOP --");

        String passedNames = students.stream()
                .filter(s -> s.getMarks().containsKey(oop)
                        && s.getMarks().get(oop).isPassed())
                .map(s -> s.getFirstName() + " " + s.getLastName())
                .collect(Collectors.joining(", "));

        System.out.println("  " + passedNames);

        System.out.println("\n======================================");
        System.out.println("Stream API Demo Complete!");
        System.out.println("======================================");
    }
}