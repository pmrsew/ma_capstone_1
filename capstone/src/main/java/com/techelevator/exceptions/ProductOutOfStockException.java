package com.techelevator.exceptions;

public class ProductOutOfStockException extends Exception {
    private String exception;

    public ProductOutOfStockException(String exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "\nProductOutOfStockException{" +
                "exception='" + exception + '\'' +
                '}';
    }
}

