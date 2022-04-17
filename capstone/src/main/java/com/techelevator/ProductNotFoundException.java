package com.techelevator;

public class ProductNotFoundException extends Exception {
    private String exception;

    public ProductNotFoundException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "\nProductNotFoundException{" +
                "exception='" + exception + '\'' +
                '}';
    }
}


