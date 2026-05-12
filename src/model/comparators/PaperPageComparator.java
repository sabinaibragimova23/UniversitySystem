package model.comparators;
import model.research.ResearchPaper;
import java.util.Comparator;

public class PaperPageComparator implements Comparator<ResearchPaper> {
    @Override
    public int compare(ResearchPaper a, ResearchPaper b) {
        return Integer.compare(b.getPages(), a.getPages());
    }
}
