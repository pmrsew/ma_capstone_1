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

public class VendingMachineApplication {

    // Variable declarations
    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit";
    private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private Menu menu;

    // Variable to handle the current date and time.
    private static final SimpleDateFormat sdf = new SimpleDateFormat( "MM/dd/yyyy HH:mm:ss a" );

    private static final String VENDING_MACHINE_CSV = "capstone/vendingmachine.csv";
    private File fileName = new File( VENDING_MACHINE_CSV );

    public VendingMachineApplication() {
    }

    public VendingMachineApplication(Menu menu) {
        this.menu = menu;
    }

    // Run method mainly focuses on the menu options , based on the input we guide the program further.
    public void run() {

        List<VendingMachineItems> vendingMachineItems = new ArrayList<VendingMachineItems>();
        vendingMachineItems = getVendingMachineItems( fileName );
        Timestamp timestamp = new Timestamp( System.currentTimeMillis() );

        // To log the timestamp to log file.
        LogWriter.log( ">" + sdf.format( timestamp ) + " Program Begins..." );


        while (true) {
            // Giving the choice of Main menu
            String choice = (String) menu.getChoiceFromOptions( MAIN_MENU_OPTIONS );

            if (choice.equals( MAIN_MENU_OPTION_DISPLAY_ITEMS )) {
                // calling a method to display vending machine items
                printVendingMachineItems( vendingMachineItems );

            } else if (choice.equals( MAIN_MENU_OPTION_PURCHASE )) {
                // calling a method to purchase the items
                PurchaseMenu purchaseMenu = new PurchaseMenu( menu, vendingMachineItems );
                purchaseMenu.runPurchaseMenu();

            } else if (choice.equals( MAIN_MENU_OPTION_EXIT ))
                // exit the main menu and application
                System.exit( 0 );
        }
    }

    // Converting the given csv file to a list of items by reading the csv and adding the items to list
    public List<VendingMachineItems> getVendingMachineItems(File fileName) {

        List<VendingMachineItems> itemsList = new ArrayList<VendingMachineItems>();

        try (Scanner sc = new Scanner( fileName )) {
            while (sc.hasNextLine()) {
                String lineofText = sc.nextLine();
                String[] item = lineofText.split( "\\|" );
                VendingMachineItems vendingMachineItems = new VendingMachineItems( item[0], item[1], new BigDecimal( item[2] ), item[3], 5 );
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
        System.out.println( "-----------------------------------------------------------------" );
        for (VendingMachineItems items : vendingMachineItems) {
            System.out.printf( "%5s %20s %10s %10s",
                    items.getSlotLocation()
                    , items.getItemName()
                    , items.getItemPrice()
                    , items.getItemStock() == 0 ? "SOLD OUT\n" : items.getItemStock() + "\n" );
        }
    }


    public static void main(String[] args) {
        Menu menu = new Menu( System.in, System.out );
        VendingMachineApplication cli = new VendingMachineApplication( menu );
        cli.run();
    }

}
