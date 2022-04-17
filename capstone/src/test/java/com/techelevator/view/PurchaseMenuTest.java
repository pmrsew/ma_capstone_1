package com.techelevator.view;

import com.techelevator.VendingMachineItems;
import org.junit.Before;
import org.junit.Test;

public class PurchaseMenuTest {

    // will need to set up a before case that creates
    // a new menu and list of vending items based on
    // our test resource file. we'll create a purchase
    // menu object with those variables. we can then test
    // the methods to that object.

    //feedMoney method tests

    @Test
    public void call_feed_money_with_valid_input_collected_start_0() {
        //feedMoney([valid string input], 0)
    }

    @Test
    public void call_feed_money_with_invalid_input_collected_start_0() {
        //feedMoney([invalid string input], 0)
    }

    @Test
    public void call_feed_money_with_valid_input_collected_start_greater_than_0() {
        //feedMoney([valid string input], 10)
    }

    @Test
    public void call_feed_money_twice_with_valid_invalid_input() {
        //feedMoney([valid string value], 0)
        //feedMoney([invalid string input], returned value from prev method use)
    }

    //purchaseItem method tests

    //i don't believe we will need to test for invalid inputs/zero collected start
    //since those checks are done via the runPurchaseMenu method that can't be tested.
    //if we wanted to test that, we would have to rework our purchaseItem and
    //runPurchaseMenu methods.

    @Test
    public  void call_purchase_item_with_valid_input_collected_start_greater_than_0(){

    }

    //completeTransaction method tests

    //i need to do further research into how we can test this method, but i believe there is a way

}
