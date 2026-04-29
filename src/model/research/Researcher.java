package model.research;

import java.util.Comparator;
import java.util.List;

public interface Researcher {

    int calculateHIndex();

    void printPapers(Comparator<ResearchPaper> comparator);

    List<ResearchPaper> getPapers();

    List<ResearchProject> getProjects();

    void publishPaper(ResearchPaper paper);

    void joinProject(ResearchProject project) throws Exception;
}