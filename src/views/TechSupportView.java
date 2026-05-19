package views;

import controllers.*;
import model.users.*;
import model.communication.Request;
import model.enums.RequestStatus;
import core.DataStorage;

import java.io.IOException;
import java.util.List;

public class TechSupportView extends BaseView {

    public static void run(TechSupportSpecialist tech) throws IOException {
        System.out.println("\n=== TECH SUPPORT PANEL: " + tech + " ===");
        boolean running = true;
        while (running) {
            menu();
            int option = readIntRange("> ", 0, 6);
            switch (option) {
                case 1 -> viewNewRequests();
                case 2 -> tech.viewRequests();
                case 3 -> acceptMenu(tech);
                case 4 -> rejectMenu(tech);
                case 5 -> doneMenu(tech);
                case 6 -> sendMessageMenu(tech);
                case 0 -> {
                    System.out.println("Session closed.");
                    running = false;
                }

                default -> errorMsg("Unknown option.");
            }
        }
    }

    private static void menu() {
        separator();
        System.out.println("TECH SUPPORT MENU");
        System.out.println("1 - New requests");
        System.out.println("2 - My requests");
        System.out.println("3 - Accept request");
        System.out.println("4 - Reject request");
        System.out.println("5 - Mark DONE");
        System.out.println("6 - Send message");
        System.out.println("0 - Logout");
    }
    
    public static void showNewRequests() {
        separator();
        System.out.println("NEW REQUESTS:");
        boolean found = false;
        for (Request r : DataStorage.getRequests()) {
            if (r.getStatus() == RequestStatus.NEW
                    || r.getStatus() == RequestStatus.VIEWED) {
                System.out.println("  [" + r.getStatus() + "] "
                    + r.getDescription()
                    + " | from: "
                    + r.getAuthor().getFirstName()
                    + " " + r.getAuthor().getLastName());
                found = true;
            }
        }
        if (!found) System.out.println("  No new requests.");
    }

    private static void viewNewRequests() {
        System.out.println("\nNEW REQUESTS");
        DataStorage.getRequests().stream()
                .filter(r -> r.getStatus() == RequestStatus.NEW)
                .forEach(r -> System.out.println("  " + r));
    }

    private static void acceptMenu(TechSupportSpecialist tech) throws IOException {

        Request r = pickRequest();

        if (r == null) return;

        TechSupportController.acceptRequest(tech, r);

        successMsg("Request accepted.");
    }

    private static void rejectMenu(TechSupportSpecialist tech) throws IOException {

        Request r = pickRequest();

        if (r == null) return;

        TechSupportController.rejectRequest(tech, r);

        successMsg("Request rejected.");
    }

    private static void doneMenu(TechSupportSpecialist tech) throws IOException {

        Request r = pickRequest();

        if (r == null) return;

        TechSupportController.markDone(tech, r);

        successMsg("Request marked as DONE.");
    }

    private static Request pickRequest() throws IOException {

        List<Request> requests = DataStorage.getRequests();

        if (requests.isEmpty()) {

            System.out.println("No requests available.");
            return null;
        }

        System.out.println("\nREQUEST LIST");

        for (int i = 0; i < requests.size(); i++) {

            System.out.println("  " + (i + 1) + " - " + requests.get(i));
        }

        int idx = readIntRange("> ", 1, requests.size());

        return requests.get(idx - 1);
    }

    private static void sendMessageMenu(TechSupportSpecialist tech) throws IOException {

        String login = readString("Recipient login: ");
        String msg = readString("Message: ");

        User receiver = DataStorage.getUsers().stream()
                .filter(u -> u.getLogin().equals(login))
                .findFirst()
                .orElse(null);

        if (receiver == null) {

            errorMsg("User not found: " + login);
            return;
        }
        tech.sendMessage(receiver, msg);
        successMsg("Message sent.");
    }
}