package model.users;

import model.comunication.Request;
import model.enums.Language;
import model.enums.RequestStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TechSupportSpecialist extends Employee {
    private List<Request> requests;
    public TechSupportSpecialist(int id,
                                  String login,
                                  String password,
                                  String firstName,
                                  String lastName) {

    	super(id,
    	          login,
    	          password,
    	          firstName,
    	          lastName);

        this.language = Language.EN;
        this.requests = new ArrayList<>();
    }

    public void addRequest(Request request) {
        requests.add(request);
        updateStatus(request, RequestStatus.VIEWED);
    }

    public void acceptRequest(Request request) {
        updateStatus(request, RequestStatus.ACCEPTED);
    }

    public void rejectRequest(Request request) {
        updateStatus(request, RequestStatus.REJECTED);
    }

    public void markDone(Request request) {
        updateStatus(request, RequestStatus.DONE);
    }

    public void updateStatus(Request request,
                             RequestStatus status) {
        request.updateStatus(status);
        System.out.println(
                "[TechSupport] "
                        + status
                        + ": "
                        + request.getDescription()
        );
    }

    public void viewRequests() {
        System.out.println("Tech Support Requests");
        for (Request r : requests) {
            System.out.println("  " + r);
        }
    }

    public String getInfo() {
        return "TechSupport{'"
                + firstName
                + " "
                + lastName
                + "', requests="
                + requests.size()
                + "}";
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }
}