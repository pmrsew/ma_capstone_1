package com.techelevator;

import com.techelevator.*;
import com.techelevator.exceptions.InsufficientFundsException;
import com.techelevator.exceptions.InvalidBillException;
import com.techelevator.exceptions.ProductNotFoundException;
import com.techelevator.exceptions.ProductOutOfStockException;
import com.techelevator.view.Menu;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PurchaseMenu {

    //Instance Variables
    private Menu menu;
    private List<VendingMachineItems> vendingMachineItemsList = new ArrayList<VendingMachineItems>();

    // Date variable to log the data to log file
    private static final SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss a" );
    private Timestamp timestamp = new Timestamp( System.currentTimeMillis() );

    public PurchaseMenu() {
    }

    public PurchaseMenu(Menu menu, List<VendingMachineItems> vendingMachineItemsList) {
        this.menu = menu;
        this.vendingMachineItemsList = vendingMachineItemsList;
    }

    // This method performs actions required by Feed Money Menu option, i.e to ask user to enter money, log it and calculated total
    // collected money
    public BigDecimal feedMoney(String userInput, BigDecimal collectedMoney) {

        try {
            if (userInput.equals( "1" ) || userInput.equals( "2" ) || userInput.equals( "5" ) || userInput.equals( "10" )) {
                BigDecimal money = new BigDecimal( userInput );
                collectedMoney = collectedMoney.add( money );
                LogWriter.log( ">" + sdf.format( timestamp )
                        + " FEED MONEY: "
                        + "$" + money.setScale( 2, RoundingMode.UP )
                        + " $" + collectedMoney.setScale( 2, RoundingMode.UP ) );
            } else {
                throw new InvalidBillException( "Invalid bill entry" );
            }
        } catch (InvalidBillException e) {
            System.err.println( e.toString() );
        }

        return collectedMoney;
    }

    // This method performs actions required for Select Product Menu option i.e
    // allowing user to select a slot items and display msgs accordingly
    public BigDecimal purchaseItem(String selectedProduct, BigDecimal collectedMoney) {
        int result; // to store the Big decimal comparision value

        for (VendingMachineItems currentItem : vendingMachineItemsList) {

            if (currentItem.getSlotLocation().equalsIgnoreCase( selectedProduct )) {
                try {
                    if (currentItem.getItemStock() > 0) {
                        result = collectedMoney.compareTo( currentItem.getItemPrice() );
                        try {
                            if (result == 1) { // if collected money  >= currentItem.getItemprice
                                currentItem.setItemStock( currentItem.getItemStock() - 1 );
                                LogWriter.log( ">" + sdf.format( timestamp )
                                        + " " + currentItem.getItemName()
                                        + "  " + currentItem.getSlotLocation()
                                        + " $" + collectedMoney.setScale( 2, RoundingMode.UP )
                                        + " $" + collectedMoney.subtract( currentItem.getItemPrice() ) );
                                collectedMoney = collectedMoney.subtract( currentItem.getItemPrice() );

                                System.out.println( "Item: " + currentItem.getItemName() + " Price: " + currentItem.getItemPrice() + " Remaining Balance: " + collectedMoney );
                                System.out.println( currentItem.dispenseMessage() );

                            } else if (result == -1) {  // collected money < current Item price
                                throw new InsufficientFundsException( "You don't have enough balance to buy this product." );
                            }
                        } catch (InsufficientFundsException e) {
                            System.err.println( e.toString() );
                        }
                    } else {
                        throw new ProductOutOfStockException( "Product is SOLD OUT (out of stock)" );
                    }
                } catch (ProductOutOfStockException e) {
                    System.err.println( e.toString() );
                }
            }
        }
        return collectedMoney;
    }

    // This method performs actions required by Final Transaction Menu option i.e
    // after all the purchase letting the user know balance money displayed in quarters,dimes and nickles
    public void completeTransaction(BigDecimal collectedMoney) {

        int quarterCount = 0;
        int dimeCount = 0;
        int nickleCount = 0;

        int remainder = 0;

        LogWriter.log( ">" + sdf.format( timestamp )
                + " GIVE CHANGE: $" + collectedMoney.setScale( 2, RoundingMode.UP ) );

        //Turn collected money balance into cents
        collectedMoney = collectedMoney.multiply( new BigDecimal( 100 ) );

        quarterCount = collectedMoney.intValue() / 25;
        remainder = collectedMoney.intValue() % 25;
        if (remainder > 0) {
            dimeCount = remainder / 10;
            remainder = remainder % 10;
            System.out.println( remainder );
            if (remainder > 0) {
                nickleCount = remainder / 5;
            }
        }

        System.out.println( System.lineSeparator() + "Transaction completed. Change returned:"
                + quarterCount + " Quarter(s), "
                + dimeCount + " Dime(s), "
                + nickleCount + " Nickle(s)." );

    }


    public void runPurchaseMenu() {
        int userSelection = -1; //added

        Timestamp timestamp = new Timestamp( System.currentTimeMillis() );
        BigDecimal collectedMoney = new BigDecimal( 0.0 );
        do {

            String[] subMenuItems = {"Feed Money", "Select Product", "Finish Transaction"};
            String ch = (String) menu.getChoiceFromOptions( subMenuItems );


            if (ch.equals( "Feed Money" )) {
                userSelection = 1;
                Scanner input = new Scanner( System.in );
                String dollorInput;

                System.out.println( System.lineSeparator() + "Enter Bill(s) ($1,$2,$5,$10): " );
                dollorInput = input.nextLine();

                collectedMoney = feedMoney( dollorInput, collectedMoney );
                System.out.println( System.lineSeparator() + "Total Money Provided: $" + collectedMoney );


            } else if (ch.equals( "Select Product" )) {
                userSelection = 2;

                //variable is used to keep track if the slot entered exists
                boolean isFound = false;

                try {
                    // if the money > 0 proceed further else let user know to enter the money
                    if (collectedMoney.compareTo( BigDecimal.ZERO ) > 0) {

                        // Printing the items for user to select
                        VendingMachineApplication.printVendingMachineItems( vendingMachineItemsList );

                        Scanner sc = new Scanner( System.in );
                        System.out.println( System.lineSeparator() + "Enter Slot Number of the Product Willing to Purchase: " );
                        String selectedProduct = sc.nextLine();

                        for (VendingMachineItems currentItem : vendingMachineItemsList) {
                            if (currentItem.getSlotLocation().equalsIgnoreCase( selectedProduct )) {
                                isFound = true;
                            }
                        }
                        // if user enters an invalid slot number throw an exception
                        try {
                            if (isFound) {
                                collectedMoney = purchaseItem( selectedProduct, collectedMoney );
                            } else
                                throw new ProductNotFoundException( "Invalid Product Slot Selected" );
                        } catch (ProductNotFoundException e) {
                            System.err.println( e.toString() );
                        }

                    } else {
                        throw new InsufficientFundsException( "Please Enter the Bill First to continue the transaction : " );
                    }
                } catch (InsufficientFundsException e) {
                    System.err.println( e.toString() );
                    System.err.println( System.lineSeparator() );
                    System.err.flush();
                }


            } else if (ch.equals( "Finish Transaction" )) {
                userSelection = 3;

                completeTransaction( collectedMoney );
                break;
            }

        } while (userSelection == 1 || userSelection == 2);

    }

}
