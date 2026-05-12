package model.users;

import model.enums.DegreeType;
import model.exceptions.LowHIndexException;
import model.research.ResearchDecorator;
import model.research.ResearchPaper;
import model.research.ResearchProject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GraduateStudent extends Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private DegreeType degree;
    private ResearchDecorator supervisor;
    private List<ResearchPaper> diplomaPapers;
    private ResearchDecorator researchProfile;

    public GraduateStudent(int id,
                           String login,
                           String password,
                           String firstName,
                           String lastName,
                           String studentId,
                           String major,
                           int year,
                           DegreeType degree) {

        super(id, login, password, firstName, lastName, studentId, major, year);
        this.degree = degree;
        this.diplomaPapers = new ArrayList<>();
        this.researchProfile = new ResearchDecorator(this);
    }

    public void setSupervisor(ResearchDecorator supervisor) throws LowHIndexException {

        int h = supervisor.calculateHIndex();

        if (h < 3) {
            throw new LowHIndexException(supervisor.getUser().toString(), h);
        }

        this.supervisor = supervisor;
        System.out.println(
                "[Supervisor] "
                + supervisor.getUser()
                + " assigned to "
                + this
        );
    }

    public void addDiplomaPaper(ResearchPaper paper) {
        diplomaPapers.add(paper); 
        researchProfile.publishPaper(paper);
    }

    public void submitDiploma() {

        System.out.println(
                this
                + " submitted diploma with "
                + diplomaPapers.size()
                + " paper(s)."
        );
    }

    public int calculateHIndex() {
        return researchProfile.calculateHIndex();
    }

    public void joinProject(ResearchProject project) {
        researchProfile.joinProject(project);
    }

    public void publishPaper(ResearchPaper paper) {
        researchProfile.publishPaper(paper);
    }

    public ResearchDecorator getResearcher() {
        return researchProfile;
    }

    @Override
    public boolean isResearcher() {
        return true;
    }

    @Override
    public ResearchDecorator getResearchProfile() {
        return researchProfile;
    }

    public DegreeType getDegree() {
        return degree;
    }

    public ResearchDecorator getSupervisor() {
        return supervisor;
    }

    public List<ResearchPaper> getDiplomaPapers() {
        return Collections.unmodifiableList(diplomaPapers);
    }
    
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDegreeType(DegreeType degree) {
        this.degree = degree;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[name="
                + getFirstName()
                + " "
                + getLastName()
                + ", degree="
                + degree
                + ", hIndex="
                + calculateHIndex()
                + "]";
    }
}