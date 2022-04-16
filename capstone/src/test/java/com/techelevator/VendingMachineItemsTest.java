package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class VendingMachineItemsTest {

    @Test
    public void to_check_if_dispenseMessage_diplayes_the_chip_output() {
        VendingMachineItems vendingMachineItems = new VendingMachineItems(
                "A1"
                , "Potato Crisps"
                , new BigDecimal( "3.05" )
                , "Chip" );
        Assert.assertEquals( "Crunch Crunch, Yum!", vendingMachineItems.dispenseMessage() );
    }

    @Test
    public void to_check_if_dispenseMessage_diplayes_the_candy_output() {
        VendingMachineItems vendingMachineItems = new VendingMachineItems(
                "B1"
                , "Moonpie"
                , new BigDecimal( "1.80" )
                , "Candy" );
        Assert.assertEquals( "Munch Munch, Yum!", vendingMachineItems.dispenseMessage() );
    }

    @Test
    public void to_check_if_dispenseMessage_diplayes_the_drink_output() {
        VendingMachineItems vendingMachineItems = new VendingMachineItems(
                "C3"
                , "Mountain Melter"
                , new BigDecimal( "1.50" )
                , "Drink" );
        Assert.assertEquals( "Glug Glug, Yum!", vendingMachineItems.dispenseMessage() );
    }

    @Test
    public void to_check_if_dispenseMessage_diplayes_the_gum_output() {
        VendingMachineItems vendingMachineItems = new VendingMachineItems(
                "D3"
                , "Chiclets"
                , new BigDecimal( "0.75" )
                , "Gum" );
        Assert.assertEquals( "Chew Chew, Yum!", vendingMachineItems.dispenseMessage() );
    }
}
