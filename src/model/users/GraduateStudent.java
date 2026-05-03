package model.users;

import model.enums.DegreeType;

public class GraduateStudent extends Student {
    private DegreeType degreeType;
    private String supervisorName; 

    public GraduateStudent(int id, String name, String email, String password, DegreeType degreeType) {
        super(id, name, email, password);
        this.degreeType = degreeType;
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setSupervisor(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisor() {
        return supervisorName;
    }

    @Override
    public String toString() {
        return "GraduateStudent: " + name +
               ", Degree: " + degreeType +
               ", Supervisor: " + supervisorName +
               ", GPA: " + calculateGPA();
    }
}