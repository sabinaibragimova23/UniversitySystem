package model.academic;

import model.users.Student;
import model.users.Teacher;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class AcademicReport implements Serializable {

    private static final long serialVersionUID = 1L;

    private Teacher teacher;
    private Date generatedAt;

    public AcademicReport(Teacher teacher) {
        this.teacher = teacher;
        this.generatedAt = new Date();
    }

    public String generateCourseReport(Course course) {

        List<Student> students = new ArrayList<>(course.getStudents());

        if (students.isEmpty()) {
            System.out.println("[Report] No students in " + course.getName());
            return "";
        }

        List<Mark> marks = new ArrayList<>();

        for (Student s : students) {
            Mark mark = s.getMarks().get(course);

            if (mark != null) {
                marks.add(mark);
            }
        }

        StringBuilder sb = new StringBuilder();

       
        sb.append("                COURSE REPORT                      \n");
        sb.append("Course    : ").append(course.getName()).append("\n");
        sb.append("Teacher   : ").append(teacher).append("\n");
        sb.append("Generated : ").append(generatedAt).append("\n");

        if (marks.isEmpty()) {
            sb.append("\nNo marks available.\n");
            sb.append("==================================================\n");

            System.out.println(sb);
            return sb.toString();
        }

        double average = marks.stream()
                .mapToDouble(Mark::getTotal)
                .average()
                .orElse(0);

        long passed = marks.stream()
                .filter(Mark::isPassed)
                .count();

        double passRate = (double) passed / marks.size() * 100;

        double highest = marks.stream()
                .mapToDouble(Mark::getTotal)
                .max()
                .orElse(0);

        double lowest = marks.stream()
                .mapToDouble(Mark::getTotal)
                .min()
                .orElse(0);

        sb.append("\n STATISTICS\n");

        sb.append(String.format("Students enrolled : %d%n", students.size()));
        sb.append(String.format("Marks recorded    : %d%n", marks.size()));
        sb.append(String.format("Average score     : %.1f%n", average));
        sb.append(String.format("Pass rate         : %.1f%%%n", passRate));
        sb.append(String.format("Highest score     : %.1f%n", highest));
        sb.append(String.format("Lowest score      : %.1f%n", lowest));

        sb.append("\nGRADE DISTRIBUTION\n");

        Map<String, Long> distribution = marks.stream()
                .collect(Collectors.groupingBy(
                        m -> m.getGrade().toString(),
                        Collectors.counting()
                ));

        distribution.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    sb.append(
                            String.format(
                                    "%-5s : %s (%d)%n",
                                    entry.getKey(),
                                    buildBar(entry.getValue(), marks.size()),
                                    entry.getValue()
                            )
                    );
                });

        sb.append("\nTOP 3 STUDENTS\n");

        students.stream()
                .filter(s -> s.getMarks().containsKey(course))
                .sorted((a, b) -> Double.compare(
                        b.getMarks().get(course).getTotal(),
                        a.getMarks().get(course).getTotal()
                ))
                .limit(3)
                .forEach(s -> {

                    Mark mark = s.getMarks().get(course);

                    sb.append(
                            String.format(
                                    "%-20s | %-5.1f | %s%n",
                                    s.getFirstName() + " " + s.getLastName(),
                                    mark.getTotal(),
                                    mark.getGrade()
                            )
                    );
                });

        sb.append("==================================================\n");

        System.out.println(sb);

        return sb.toString();
    }

    public void generateTeacherSummary() {
        System.out.println("            TEACHER SUMMARY             ");
        System.out.println("Teacher : " + teacher);
        System.out.println("Courses : " + teacher.getCourses().size());

        for (Course course : teacher.getCourses()) {
            long graded = course.getStudents()
                    .stream()
                    .filter(s -> s.getMarks().containsKey(course))
                    .count();

            System.out.printf(
                    "%-25s | students=%d | graded=%d%n",
                    course.getName(),
                    course.getStudents().size(),
                    graded
            );
        }

        System.out.println("========================================");
    }

    private String buildBar(long count, int total) {

        int filled = (int) Math.round((double) count / total * 10);

        return "█".repeat(filled)
                + "░".repeat(10 - filled);
    }
}