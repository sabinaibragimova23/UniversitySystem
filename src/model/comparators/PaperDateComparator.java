package model.comparators;

import model.research.ResearchPaper;
import java.util.Comparator;

public class PaperDateComparator implements Comparator<ResearchPaper> {

    @Override
    public int compare(ResearchPaper p1, ResearchPaper p2) {

        if (p1.getDate()== null && p2.getDate()== null) {
            return 0;
        }

        if (p1.getDate() == null) {
            return 1;
        }

        if (p2.getDate() ==null) {
            return -1;
        }

        return p2.getDate().compareTo(p1.getDate());
    }
}