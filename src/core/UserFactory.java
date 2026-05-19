package core;

import model.users.Admin;
import model.users.GraduateStudent;
import model.users.Manager;
import model.users.Student;
import model.users.Teacher;
import model.users.TechSupportSpecialist;
import model.users.User;
import model.enums.*;

public class UserFactory {

    public static User createUser(UserType type,
                                  int id,
                                  String firstName,
                                  String lastName,
                                  String login,
                                  String password) {

        switch (type) {

            case STUDENT:
                return new Student(
                        id,
                        login,
                        password,
                        firstName,
                        lastName,
                        "",   
                        "General",
                        1
                );

            case GRADUATE_STUDENT:
                return new GraduateStudent(
                        id,
                        login,
                        password,
                        firstName,
                        lastName,
                        "",
                        "General",
                        1,
                        DegreeType.MASTER
                );

            case TEACHER:
                return new Teacher(
                        id,
                        login,
                        password,
                        firstName,
                        lastName,
                        TeacherType.LECTOR
                );

            case ADMIN:
                return new Admin(
                        id,
                        login,
                        password,
                        firstName,
                        lastName
                );

            case MANAGER:
                return new Manager(
                        id,
                        login,
                        password,
                        firstName,
                        lastName,
                        ManagerType.ACADEMIC
                );

            case TECH_SUPPORT:
                return new TechSupportSpecialist(
                        id,
                        login,
                        password,
                        firstName,
                        lastName
                );

            default:
                throw new IllegalArgumentException("Unknown user type: " + type);
        }
    }
}