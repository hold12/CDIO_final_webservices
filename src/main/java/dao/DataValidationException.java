package dao;

public class DataValidationException extends Exception {
    public DataValidationException(String msg) {
        super(msg);
    }

    public DataValidationException(Exception e) {
        super(e);
    }

}
