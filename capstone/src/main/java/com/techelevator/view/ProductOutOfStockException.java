package com.techelevator.view;

public class ProductOutOfStockException extends Exception{
    private String exception;

    public ProductOutOfStockException(String exception){
        this.exception=exception;
    }

    @Override
    public String toString() {
        return "ProductOutOfStockException{" +
                "exception='" + exception + '\'' +
                '}';
    }
}

