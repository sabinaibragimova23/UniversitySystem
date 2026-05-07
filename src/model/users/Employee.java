package model.users;

public  abstract class Employee extends User {

    protected String employeeId;
    protected String department;

    public Employee(int id, String name, String email, String password,
                    String employeeId, String department) {

        super(id, name, email, password);

        this.employeeId = employeeId;
        this.department = department;
    }

    public void sendMessage(Employee other, String text) {
        System.out.println(this.name + " sends message to " + other.getName() + ": " + text);
    }

    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }

    public void setDepartment(String department) {
        this.department = department;
    }
}