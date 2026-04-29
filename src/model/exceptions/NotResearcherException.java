package model.exceptions;

public class NotResearcherException extends Exception {

    public NotResearcherException(String userName) {
        super("'" + userName + "' is not a Researcher and cannot join a ResearchProject");
    }
}