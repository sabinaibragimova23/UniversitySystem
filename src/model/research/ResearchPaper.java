package model.research;

import model.enums.CitationFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResearchPaper implements Comparable<ResearchPaper>, Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private List<String> authors;
    private String journal;
    private int citations;
    private int pages;
    private Date date;
    private String doi;

    public ResearchPaper(String title, List<String> authors, String journal, int pages, Date date, String doi) {

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
            return String.join(", ", authors)
                    + " ("
                    + year
                    + "). "
                    + title
                    + ". "
                    + journal
                    + ", "
                    + pages
                    + " pp. DOI: "
                    + doi;
        }

        String key;
        if (authors.isEmpty()) {
            key = "Unknown" + year;
        } else {
            key = authors.get(0).split(" ")[0] + year;
        }

        return "@article{"
                + key
                + ",\n"
                + "  author  = {"
                + String.join(" and ", authors)
                + "},\n"
                + "  title   = {"
                + title
                + "},\n"
                + "  journal = {"
                + journal
                + "},\n"
                + "  year    = {"
                + year
                + "},\n"
                + "  pages   = {"
                + pages
                + "},\n"
                + "  doi     = {"
                + doi
                + "}\n}";
    }

    public void addCitation() {
        citations++;
    }

    public void setCitations(int citations) {
        if (citations >= 0) {
            this.citations = citations;
        }
    }

    public int getYear() {
        if (date == null) {
            return 0;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    public String getTitle() {
        return title;
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

    public int getCitations() {
        return citations;
    }

    public int getPages() {
        return pages;
    }

    public Date getDate() {
        return date;
    }

    public String getDoi() {
        return doi;
    }

    @Override
    public String toString() {

        return "ResearchPaper[title=" + title + ", authors=" + authors + ", journal=" + journal + ", citations=" + citations + ", pages=" + pages + ", year=" + getYear() + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null || !(o instanceof ResearchPaper))
            return false;
        ResearchPaper r = (ResearchPaper) o;
        return Objects.equals(doi, r.doi);
    }
}