package com.techelevator;

public class InvalidBillException extends Exception {
    private String exception;

    public InvalidBillException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "\nInvalidBillException{" +
                "exception='" + exception + '\'' +
                '}';
    }
}
