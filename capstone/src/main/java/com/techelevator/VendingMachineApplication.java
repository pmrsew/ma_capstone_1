package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
To do -
1. Every method must have a unit test case
2. What does step 9 in README mean
3. handle number format exceptions possible
4. validation if any
5. 
 */
public class VendingMachineApplication {

    // Variable declarations
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    // Variable to handle the current date and time.
    private static final SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss a" );

    private static final String VENDING_MACHINE_CSV = "capstone/vendingmachine.csv";

    File fileName = new File( VENDING_MACHINE_CSV );

    private Menu menu;

    public VendingMachineApplication() {
    }

    public VendingMachineApplication(Menu menu) {
        this.menu = menu;
    }

    // Run method mainly focuses on the menu options , based on the input we guide the program further.
    public void run() {

        List<VendingMachineItems> vendingmachineitems = new ArrayList<VendingMachineItems>();
        vendingmachineitems = getVendingMachineItems( fileName );
        Timestamp timestamp = new Timestamp( System.currentTimeMillis() );

        while (true) {
            // Giving the choice of Main menu
            String choice = (String) menu.getChoiceFromOptions( MAIN_MENU_OPTIONS );

            // To log the timestamp to log file.
            GenerateLog.log( ">" + sdf.format( timestamp ) + " Program Begins..." );

            if (choice.equals( MAIN_MENU_OPTION_DISPLAY_ITEMS )) {
                // calling a method to display vending machine items
                printVendingMachineItems( vendingmachineitems );

            } else if (choice.equals( MAIN_MENU_OPTION_PURCHASE )) {
                // calling a method to purchase the items

                PurchaseMenu purchaseMenu = new PurchaseMenu( menu, vendingmachineitems );
                purchaseMenu.runPurchaseMenu();

            } else if (choice.equals( MAIN_MENU_OPTION_EXIT ))
                // exit the main menu
                System.exit( 0 );
        }
    }

    // Converting the csv file to a list of items by reading the csv and adding the items to list
    public List<VendingMachineItems> getVendingMachineItems(File fileName) {

        List<VendingMachineItems> itemsList = new ArrayList<VendingMachineItems>();

        try (Scanner sc = new Scanner( fileName )) {
            while (sc.hasNextLine()) {
                String lineofText = sc.nextLine();
                String[] item = lineofText.split( "\\|" );
                VendingMachineItems vendingMachineItems = new VendingMachineItems( item[0], item[1], new BigDecimal( item[2] ), item[3] );
                itemsList.add( vendingMachineItems );
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return itemsList;
    }

    // To print the list of items from the list
    public static void printVendingMachineItems(List<VendingMachineItems> vendingMachineItems) {
        System.out.println( "-----------------------------------------------------------------" );
        System.out.printf( "%5s %15s %15s %15s\n", "Slot", "Item", "Price", "Qty Remaning" );
        System.out.println( "--------------------------------------------------------------------" );
        for (VendingMachineItems items : vendingMachineItems) {
            System.out.printf( "%5s %20s %10s %10s",
                    items.getSlotLocation()
                    , items.getItemName()
                    , items.getItemPrice()
                    , items.getItemStock() == 0 ? "SOLD OUT\n" : items.getItemStock() + "\n" );
        }
    }

    // The purchase option further open a submenu to choose from
//    public void runPurchaseMenu(Menu menu, List<VendingMachineItems> vendingMachineItemsList) {
//        int userSelection = -1; //added
//
//        Timestamp timestamp = new Timestamp( System.currentTimeMillis() );
//        BigDecimal collectedMoney = new BigDecimal( 0.0 );
//
//        do {
//			System.out.println( "*********************" );//added
//            System.out.println( "Current Money Provided: " + collectedMoney );//added
//            String[] subMenuItems = {"Feed Money", "Select Product", "Finish Transaction"};
//            String ch = (String) menu.getChoiceFromOptions( subMenuItems );
//
//            if (ch.equals( "Feed Money" )) {
//                userSelection = 1;
//                Scanner input = new Scanner( System.in );
//                String dollorInput;
//
//                System.out.println( System.lineSeparator() + "Enter the money($1,$2,$5,$10) :" );
//                dollorInput = input.nextLine();
//
//                try {
//                    if (dollorInput.equals( "1" ) || dollorInput.equals( "2" ) || dollorInput.equals( "5" ) || dollorInput.equals( "10" )) {
//                        BigDecimal money = new BigDecimal( dollorInput );
//                        collectedMoney = collectedMoney.add( money );
//                        GenerateLog.log( ">" + sdf.format( timestamp )
//                                + " FEED MONEY: "
//                                + "$" + money.setScale( 2, RoundingMode.UP )
//                                + " $" + collectedMoney.setScale( 2, RoundingMode.UP ) );
//                    } else {
//                        throw new InvalidBillException( "Invalid bill entry" );
//                    }
//                } catch (InvalidBillException e) {
//                    System.err.println( e.toString() );
//                }
//            } else if (ch.equals( "Select Product" )) {
//                //all below is added
//                userSelection = 2;
//                boolean isProductFound = false;
//
//                if (collectedMoney.compareTo( BigDecimal.ZERO ) > 0) {
//
//                    Scanner sc = new Scanner( System.in );
//
//                    //List<VendingMachineItems> listOfItems = getVendingMachineItems();
//
//                    printVendingMachineItems( vendingMachineItemsList );
//
//                    System.out.println( "Enter Slot of Product Wanted: " );
//                    String selectedProduct = sc.nextLine();
//
//                    for (VendingMachineItems currentItem : vendingMachineItemsList) {
//
//                        if (currentItem.getSlotLocation().equalsIgnoreCase( selectedProduct )) {
//                            isProductFound = true;
//                            try {
//                                if (currentItem.getItemStock() > 0) {
//
//                                    if (collectedMoney.compareTo( BigDecimal.ZERO ) >= currentItem.getItemPrice().compareTo( BigDecimal.ZERO )) {
//
//
//                                        currentItem.setItemStock( currentItem.getItemStock() - 1 );
//
//                                        GenerateLog.log( ">" + sdf.format( timestamp )
//                                                + " " + currentItem.getItemName()
//                                                + "  " + currentItem.getSlotLocation()
//                                                + " $" + collectedMoney.setScale( 2, RoundingMode.UP )
//                                                + " $" + collectedMoney.subtract( currentItem.getItemPrice() ) );
//                                        collectedMoney = collectedMoney.subtract( currentItem.getItemPrice() );
//
//                                        System.out.println( "Item: " + currentItem.getItemName() + " Price: " + currentItem.getItemPrice() + " Remaining Balance: " + collectedMoney );
//                                        System.out.println( currentItem.dispenseMessage() );
//
//                                    } else {
//                                        System.out.println( "Insufficient money provided. Can't purchase item." );
//                                    }
//
//                                } else {
//                                    throw new ProductOutOfStockException( "Product is out of stock" );
//                                }
//                            } catch (ProductOutOfStockException e) {
//                                System.err.println( e.toString() );
//                            }
//                        }
//                        continue;
//                    }
//                    try {
//                        if (!isProductFound)
//                            throw new ProductNotFoundException( "Product not found" );
//                    } catch (ProductNotFoundException e) {
//                        System.err.println( e.toString() );
//                    }
//                } else {
//                    System.out.println( "Please enter money first" );
//                }
//            } else if (ch.equals( "Finish Transaction" )) {
//                userSelection = 3;
//
//                int quarterCount = 0;
//                int dimeCount = 0;
//                int nickleCount = 0;
//
//                int remainder = 0;
//
//                GenerateLog.log( ">" + sdf.format( timestamp )
//                        + " GIVE CHANGE: $" + collectedMoney.setScale( 2, RoundingMode.UP ) );
//                //Turn collected money balance into cents
//                collectedMoney = collectedMoney.multiply( new BigDecimal( 100 ) );
//
//
//                quarterCount = collectedMoney.intValue() / 25;
//                remainder = collectedMoney.intValue() % 25;
//                if (remainder > 0) {
//                    dimeCount = remainder / 10;
//                    remainder = remainder % 10;
//                    System.out.println( remainder );
//                    if (remainder > 0) {
//                        nickleCount = remainder / 5;
//                    }
//                }
//
//
//                System.out.println( "Transaction completed. Change returned:" );
//                System.out.println( quarterCount + " Quarters, " + dimeCount + " Dimes, " + nickleCount + " Nickles." );
//
//
//                break;
//            }
//
//        } while (userSelection == 1 || userSelection == 2);
//
//    }

    public static void main(String[] args) {
        Menu menu = new Menu( System.in, System.out );
        VendingMachineApplication cli = new VendingMachineApplication( menu );
        cli.run();
    }

}