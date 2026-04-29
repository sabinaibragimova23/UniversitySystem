package model.research;

import model.exceptions.NotResearcherException;
import model.users.User;
import java.util.*;

public class ResearchDecorator implements Researcher {

    private final User user;
    private final List<ResearchPaper> papers;
    private final List<ResearchProject> projects;

    public ResearchDecorator(User user) {
        this.user = user;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
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

        System.out.println("Papers of " + user + ":");

        for (ResearchPaper p : sorted) {
            System.out.println("  " + p);
        }
    }

    @Override
    public void publishPaper(ResearchPaper paper) {
        papers.add(paper);
        System.out.println("[News] " + user + " published: " + paper.getTitle());
    }

    @Override
    public void joinProject(ResearchProject project) throws NotResearcherException {
        project.addParticipant(user);
        projects.add(project);
    }

    @Override
    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    @Override
    public List<ResearchProject> getProjects() {
        return Collections.unmodifiableList(projects);
    }

    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "ResearchDecorator{" +
                "user=" + user +
                ", papers=" + papers.size() +
                ", hIndex=" + calculateHIndex() +
                '}';
    }
}