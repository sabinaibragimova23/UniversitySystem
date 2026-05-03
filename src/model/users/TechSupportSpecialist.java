package model.users;

import model.comunication.Request;
import model.enums.RequestStatus;

import java.util.*;

public class TechSupportSpecialist extends Employee {

    private List<Request> requests = new ArrayList<>();

    public TechSupportSpecialist(int id,
                                 String firstName,
                                 String lastName,
                                 String email,
                                 String password,
                                 String employeeId,
                                 String department) {

        super(id, firstName + " " + lastName, email, password, employeeId, department);
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

    public void updateStatus(Request request, RequestStatus status) {
        request.updateStatus(status);
        System.out.println("[TechSupport] " + status + ": " + request);
    }

    public String getInfo() {
        return "TechSupport{name='" + name +
                "', requests=" + requests.size() + "}";
    }

    public void viewRequests() {
        requests.forEach(System.out::println);
    }

    @Override
    public String toString() {
        return getInfo();
    }

    public List<Request> getRequests() {
        return Collections.unmodifiableList(requests);
    }
}