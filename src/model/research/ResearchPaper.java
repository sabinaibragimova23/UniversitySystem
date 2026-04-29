package model.research;

import model.enums.CitationFormat;
import java.util.*;

public class ResearchPaper implements Comparable<ResearchPaper> {

    private String title;
    private List<String> authors;
    private String journal;
    private int citations;
    private int pages;
    private Date date;
    private String doi;

    public ResearchPaper(String title, List<String> authors, String journal,
                         int pages, Date date, String doi) {
        this.title = title;
        this.authors = new ArrayList<>(authors);
        this.journal = journal;
        this.pages = pages;
        this.date = date;
        this.doi = doi;
        this.citations = 0;
    }

    public String getCitation(CitationFormat format) {
        int year = getYear();

        if (format == CitationFormat.PLAINTEXT) {
            return String.join(", ", authors) + " (" + year + "). "
                    + title + ". " + journal + ", " + pages + " pp. DOI: " + doi;
        }

        String key = (authors.isEmpty() ? "Unknown"
                : authors.get(0).split(" ")[0]) + year;

        return "@article{" + key + ",\n" +
                "  author  = {" + String.join(" and ", authors) + "},\n" +
                "  title   = {" + title + "},\n" +
                "  journal = {" + journal + "},\n" +
                "  year    = {" + year + "},\n" +
                "  pages   = {" + pages + "},\n" +
                "  doi     = {" + doi + "}\n}";
    }

    public void addCitation() {
        citations++;
    }

    public int getYear() {
        if (date == null) return 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                "[title=" + title +
                ", authors=" + authors +
                ", journal=" + journal +
                ", citations=" + citations +
                ", pages=" + pages +
                ", year=" + getYear() +
                ", doi=" + doi + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;

        ResearchPaper that = (ResearchPaper) o;
        return Objects.equals(doi, that.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return Collections.unmodifiableList(authors);
    }

    public void addAuthor(String author) {
        authors.add(author);
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public int getCitations() {
        return citations;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDoi() {
        return doi;
    }
}