package model.research;

import core.DataStorage;
import model.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResearchDecorator implements Researcher, Serializable {
    private static final long serialVersionUID = 1L;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private User user;
    private Journal journal;

    public ResearchDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
        DataStorage.getInstance().registerResearcher(this);
    }

    @Override
    public int calculateHIndex() {
        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper p : papers) {
            citations.add(p.getCitations());
        }
        citations.sort(Collections.reverseOrder());
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        if (comparator != null) {
            sorted.sort(comparator);
        } else {
            Collections.sort(sorted);
        }

        System.out.println("Papers of " + user + " (h-index=" + calculateHIndex() + "):");

        for (ResearchPaper p : sorted) {
            System.out.println("  " + p);
        }
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        papers.add(paper);
        System.out.println( "[Research] " + user + " published: " + paper.getTitle());

        News announcement = new News(
                "New paper by " + user,
                user + " published: " + paper.getTitle()
                        + " in " + paper.getJournal(),
                "Research",
                new Date()
        );

        DataStorage.getInstance().addNews(announcement);

        if (journal != null) {
            journal.publishPaper(paper);
        }
    }

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    public void joinProject(ResearchProject project) {
        project.addParticipant(this);
        projects.add(project);
    }

    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    public User getUser() {
        return user;
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public List<ResearchProject> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    @Override
    public String toString() {
        return "ResearchDecorator[user=" + user + ", papers=" + papers.size() + ", hIndex=" + calculateHIndex() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof ResearchDecorator))
            return false;
        ResearchDecorator r = (ResearchDecorator) o;
        return Objects.equals(user, r.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }
}