package com.techelevator;

public class InsufficientFundsException extends Exception {
    private String exception;

    public InsufficientFundsException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "\nInsufficientFundsException{" +
                "exception='" + exception + '\'' +
                '}';
    }
}
