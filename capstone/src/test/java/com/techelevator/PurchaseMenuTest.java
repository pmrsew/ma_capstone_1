package com.techelevator;

import com.techelevator.view.Menu;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;


public class PurchaseMenuTest {

    //feedMoney method tests

    private ByteArrayOutputStream output;
    Menu menu;
    private List<VendingMachineItems> itemsList;
    PurchaseMenu purchaseMenu;
    VendingMachineApplication vendingMachineApplication = new VendingMachineApplication( menu );
    File itemsFile = new File( "vendingmachine.csv" );


    @Before
    public void setUp() {
        output = new ByteArrayOutputStream();
        menu = getMenuForTesting();
        itemsList = vendingMachineApplication.getVendingMachineItems( itemsFile );
        purchaseMenu = new PurchaseMenu( menu, itemsList );

    }

    @Test
    public void call_feed_money_with_valid_input_collected_start_0() {
        //feedMoney([valid string input], 10)
        BigDecimal collectedMoney = new BigDecimal( 5.0 );
        collectedMoney = purchaseMenu.feedMoney( "5", new BigDecimal( 0.0 ) );
        Assert.assertEquals( new BigDecimal( 5.0 ), collectedMoney );

    }

    @Test
    public void call_feed_money_with_invalid_input_collected_start_0() {

        BigDecimal collectedMoney = new BigDecimal( 3.0 );
        collectedMoney = purchaseMenu.feedMoney( "3", new BigDecimal( 0.0 ) );
        Assert.assertNotEquals( new BigDecimal( 5.0 ), collectedMoney );
    }

    @Test
    public void call_feed_money_with_valid_input_collected_start_greater_than_0() {

        BigDecimal collectedMoney = new BigDecimal( 5.0 );
        collectedMoney = purchaseMenu.feedMoney( "5", new BigDecimal( 5.0 ) );
        Assert.assertEquals( new BigDecimal( 10.0 ), collectedMoney );
    }

    @Test
    public void test_quantity_remaining_after_purchase() {
        BigDecimal collectedMoney = purchaseMenu.purchaseItem( "A1", new BigDecimal( 10.0 ) );
        int quantity = 0;
        for (VendingMachineItems list : itemsList) {
            if (list.getSlotLocation().equals( "A1" ))
                quantity = list.getItemStock();
        }
        Assert.assertEquals( 4, quantity );
    }

    @Test
    public void remaining_change_test() {
        BigDecimal balance = new BigDecimal( 0.0 );
        balance = purchaseMenu.purchaseItem( "A1", new BigDecimal( "10.0" ) );
        Assert.assertEquals( new BigDecimal( "6.95" ), balance );

    }

    private Menu getMenuForTestingWithUserInput(String userInput) {
        ByteArrayInputStream input = new ByteArrayInputStream( String.valueOf( userInput ).getBytes() );
        return new Menu( input, output );
    }

    private Menu getMenuForTesting() {
        return getMenuForTestingWithUserInput( "1" + System.lineSeparator() );
    }
}
