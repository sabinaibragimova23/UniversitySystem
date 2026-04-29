package model.patterns;

import model.users.*;
import model.enums.UserType;

public class UserFactory {

    public static User createUser(UserType type) {

        switch (type) {
            case STUDENT:
                return new Student();

            case ADMIN:
                return new Admin();

            default:
                throw new IllegalArgumentException("Unknown user type");
        }
    }
}