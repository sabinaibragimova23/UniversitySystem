package model.research;

import model.exceptions.NotResearcherException;
import model.users.Teacher;
import model.users.GraduateStudent;

import java.util.*;

public class ResearchProject {

    private String topic;
    private List<ResearchPaper> papers;
    private List<Researcher> participants;
    private String status;

    public ResearchProject(String topic) {
        this.topic = topic;
        this.papers = new ArrayList<>();
        this.participants = new ArrayList<>();
        this.status = "NEW";
    }

    public void addParticipant(ResearchDecorator rd) {
        if (rd == null) return;

        participants.add(rd);

        System.out.println("[ResearchProject] "
                + rd.getUser() + " joined '" + topic + "'");
    }


    public void addParticipant(Teacher teacher) throws NotResearcherException {

        if (!teacher.isResearcher()) {
            throw new NotResearcherException(teacher.toString());
        }

        teacher.getResearchProfile()
                .ifPresent(this::addParticipant);
    }
    
    public void addParticipant(model.users.Student student) {
        ResearchDecorator rd = new ResearchDecorator(student);

        addParticipant(rd);
    }


    public void addParticipant(GraduateStudent student) {

        student.getResearchProfile()
                .ifPresent(this::addParticipant);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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