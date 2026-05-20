package model.users;

import java.util.*;

public class StudentOrganization implements java.io.Serializable{

    private String name;
    private Student head;
    private List<Student> members;

    public StudentOrganization(String name) {
        this.name = name;
        this.members = new ArrayList<>();
    }

 
    public void addMember(Student student) {
        if (!members.contains(student)) {
            members.add(student);
            student.joinOrganization(this);
        }
    }


    public void setHead(Student student) {
        this.head = student;
        addMember(student);
    }

  

    @Override
    public String toString() {
        return "StudentOrganization{'" + name + "', members=" + members.size() + "}";
    }

    public String getName() { 
    	return name; 
    	}
    public Student getHead() {
    	return head; 
    	}
    public List<Student> getMembers() {
    	return Collections.unmodifiableList(members); 
    	}
}
