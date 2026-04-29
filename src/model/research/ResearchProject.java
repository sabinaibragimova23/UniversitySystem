package model.research;

import model.exceptions.NotResearcherException;
import model.users.User;
import java.util.*;

public class ResearchProject {

    private String topic;
    private List<ResearchPaper> papers;
    private List<Researcher> participants;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.papers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }


    public void addParticipant(User user) throws NotResearcherException {
        if (!(user instanceof Researcher)) {
            throw new NotResearcherException(user.toString());
        }

        participants.add((Researcher) user);
    }

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "topic='" + topic + '\'' +
                ", participants=" + participants.size() +
                ", papers=" + papers.size() +
                '}';
    }


    public String getTopic() {
        return topic;
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public List<Researcher> getParticipants() {
        return Collections.unmodifiableList(participants);
    }
}