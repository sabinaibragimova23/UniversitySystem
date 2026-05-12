package model.research;

import core.DataStorage;
import model.users.GraduateStudent;
import model.users.Teacher;
import model.exceptions.NotResearcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResearchProject implements Serializable {
    private static final long serialVersionUID = 1L;
    private String topic;
    private List<ResearchDecorator> participants;
    private List<ResearchPaper> papers;
    private String status;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.participants = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.status = "NEW";
    }

    public void addParticipant(ResearchDecorator researcher) {
        if (!participants.contains(researcher)) {
            participants.add(researcher);
            DataStorage.getInstance().registerResearcher(researcher);
            System.out.println(
                    "[ResearchProject] "
                    + researcher.getUser()
                    + " joined "
                    + topic
            );
        }
    }

    public void addParticipant(Teacher teacher) throws NotResearcherException {
        if (!teacher.isResearcher()) {
            throw new NotResearcherException(teacher.toString());
        }
        addParticipant(teacher.getResearchProfile());
    }

    public void addParticipant(GraduateStudent student) {
        addParticipant(student.getResearchProfile());
    }

    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
    }

    public String getTopic() {
        return topic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResearchPaper> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    public List<ResearchDecorator> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    @Override
    public String toString() {

        return "ResearchProject[topic="
                + topic
                + ", participants="
                + participants.size()
                + ", papers="
                + papers.size()
                + ", status="
                + status
                + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof ResearchProject))
            return false;
        ResearchProject r = (ResearchProject) o;
        return Objects.equals(topic, r.topic);
    }
}