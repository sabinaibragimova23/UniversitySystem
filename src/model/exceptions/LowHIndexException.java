package model.exceptions;

public class LowHIndexException extends Exception {

    private final int hIndexValue;

    public LowHIndexException(String name, int hIndexValue) {
        super("Supervisor '" + name + "' cannot be assigned: h-index = "
                + hIndexValue + ", minimum required is 3");
        this.hIndexValue = hIndexValue;
    }

    public int getHIndexValue() {
        return hIndexValue;
    }
}