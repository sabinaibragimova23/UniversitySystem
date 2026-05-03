package model.patterns;

import model.users.*;
import model.enums.UserType;

public class UserFactory {

    public static User createUser(UserType type,
                                  int id,
                                  String firstName,
                                  String lastName,
                                  String email,
                                  String password) {

        switch (type) {

            case STUDENT:
            	return new Student(id,
            	        firstName + " " + lastName,
            	        email,
            	        password);

            case EMPLOYEE:
                return new Employee(id,
                        firstName + " " + lastName,
                        email,
                        password,
                        "EMP-" + id,
                        "General");

            case ADMIN:
                return new Admin(id, firstName + " " + lastName, email, password);

            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}