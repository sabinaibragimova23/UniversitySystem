package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BaseView {

    protected static final BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static String readString(String prompt) throws IOException {

        while (true) {
            System.out.print(prompt);
            String value = reader.readLine();
            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
            System.out.println("ERROR: Input cannot be empty.");
        }
    }

    public static int readInt(String prompt) throws IOException {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(reader.readLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid number.");
            }
        }
    }
    public static double readDouble(String prompt) throws IOException {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(reader.readLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("ERROR: Invalid decimal number.");
            }
        }
    }

    public static int readIntRange(String prompt,
                                   int min,
                                   int max) throws IOException {
        while (true) {
            int value = readInt(prompt);
            if (value >= min && value <= max) {
                return value;
            }
            System.out.println(
                    "ERROR: Enter number between "
                    + min
                    + " and "
                    + max
            );
        }
    }

    public static void successMsg(String message) {
        System.out.println(
                "SUCCESS: "
                + message
        );
    }

    public static void errorMsg(String message) {
        System.out.println(
                "ERROR: "
                + message
        );
    }

    public static void separator() {
        System.out.println(
                "------------------------------------------"
        );
    }
}