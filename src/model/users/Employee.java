package model.users;

import model.comunication.Message;

import java.io.Serializable;

public abstract class Employee extends User implements Serializable {
    public Employee(int id2, String login2, String password2, String firstName2, String lastName2) {
		super(id2, login2, password2, firstName2, lastName2);
	}

	private static final long serialVersionUID = 1L;

    protected String employeeId;
    protected String department;
    @Override
    public void sendMessage(User receiver, String content) {
        Message msg = new Message(this, receiver, content);

        msg.send();
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String dept) {
        this.department = dept;
    }
}