package model.research;

import model.exceptions.NotResearcherException;
import model.users.User;
import java.util.*;

public class ResearchDecorator implements Researcher {

    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
    private User user;
    private Journal journal;

    public ResearchDecorator(User user) {
        this.user     = user;
        this.papers   = new ArrayList<>();
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
            if (citations.get(i) >= i + 1) h = i + 1;
            else break;
        }
        return h;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        if (c != null) sorted.sort(c);
        else Collections.sort(sorted);
        System.out.println("Papers of " + user + " (h-index=" + calculateHIndex() + "):");
        for (ResearchPaper p : sorted) {
            System.out.println("  " + p);
        }
    }

    @Override
    public void publishPaper(ResearchPaper p) {
        papers.add(p);
        System.out.println("[Research] " + user + " published: " + p.getTitle());
        if (journal != null) {
            journal.publishPaper(p);
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

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[user=" + user
                + ", papers=" + papers.size()
                + ", hIndex=" + calculateHIndex() + "]";
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
}
