package controllers;

import core.DataStorage;
import model.comunication.Request;
import model.users.Employee;
import model.users.TechSupportSpecialist;

public class TechSupportController {

    public static Request createRequest(String description,
                                        Employee author) {
        Request req = new Request(description, author);
        DataStorage.addRequest(req);
        System.out.println("[TechSupport] Request created: " + description);
        return req;
    }

    public static void viewNewRequests(TechSupportSpecialist tech) {
        System.out.println("=== New Requests ===");
        for (Request r : DataStorage.getRequests()) {
            if (r.getStatus().name().equals("NEW")) {
                System.out.println("  " + r);
            }
        }
    }

    public static void acceptRequest(TechSupportSpecialist tech,
                                      Request request) {
        tech.addRequest(request);
        tech.acceptRequest(request);
        DataStorage.save();
    }

    public static void rejectRequest(TechSupportSpecialist tech,
                                      Request request) {
        tech.addRequest(request);
        tech.rejectRequest(request);
        DataStorage.save();
    }

    public static void markDone(TechSupportSpecialist tech,
                                Request request) {
        tech.markDone(request);
        DataStorage.save();
    }
}