package controllers;

import core.DataStorage;
import model.research.*;
import model.users.GraduateStudent;
import model.users.Student;
import model.users.Teacher;
import model.users.User;
import model.exceptions.LowHIndexException;
import model.exceptions.NotResearcherException;

import java.util.Comparator;
public class ResearchController {
    public static void publishPaper(ResearchDecorator researcher,
                                    ResearchPaper paper) {
        researcher.publishPaper(paper);
        DataStorage.save();
    }

    public static void printPapers(ResearchDecorator researcher,
                                   Comparator<ResearchPaper> comparator) {
        researcher.printPapers(comparator);
    }

    public static void printAllPapers(Comparator<ResearchPaper> comparator) {
        DataStorage.printAllResearcherPapers(comparator);
    }

    public static void showTopCited() {

        ResearchDecorator top = DataStorage.getTopCitedResearcher();

        if (top == null) {
            System.out.println("No researchers found.");
            return;
        }

        System.out.println( "Top cited researcher: " + top.getUser() + " | h-index=" + top.calculateHIndex() + " | papers=" + top.getPapers().size()
        );

        model.research.News announcement = new model.research.News( "Top Cited: " + top.getUser(), top.getUser() + " is the most cited researcher with h-index="  + top.calculateHIndex(),
                "Research",
                new java.util.Date()
        );

        DataStorage.addNews(announcement);
    }

    public static void showTopCitedByYear(int year) {
        ResearchDecorator top = DataStorage.getTopCitedResearcherByYear(year);
        if (top == null) {
            System.out.println("No researchers found for year " + year + ".");
            return;
        }

        System.out.println(
                "Top cited in "
                + year
                + ": "
                + top.getUser()
                + " | h-index="
                + top.calculateHIndex()
        );
    }

    public static boolean setSupervisor(GraduateStudent student,
                                        ResearchDecorator supervisor) {
        try {
            student.setSupervisor(supervisor);
            return true;

        } catch (LowHIndexException e) {
            System.out.println(
                    "[Research] Supervisor error: "
                            + e.getMessage()
            );
            return false;
        }
    }

    public static boolean joinProject(Teacher teacher,
                                      ResearchProject project) {
        try {
            project.addParticipant(teacher);
            return true;
        } catch (NotResearcherException e) {
            System.out.println(
                    "[Research] Join error: "
                            + e.getMessage()
            );
            return false;
        }
    }

    public static void subscribeJournal(User user,
                                        Journal journal) {
        if (user instanceof Student) {
            ((Student) user).subscribeToJournal(journal);
        } else if (user instanceof Teacher) {
            ((Teacher) user).subscribeToJournal(journal);
        } else {
            journal.subscribe(user);
        }
    }
    public static void showHIndex(ResearchDecorator researcher) {
        System.out.println(
                "[h-index] " + researcher.getUser() + " → " + researcher.calculateHIndex()
        );
    }
}