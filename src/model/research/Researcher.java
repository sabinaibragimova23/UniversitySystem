package model.research;

import java.util.Comparator;

public interface Researcher {

    int calculateHIndex();

    void printPapers(Comparator<ResearchPaper> c);

    void publishPaper(ResearchPaper p);
}
