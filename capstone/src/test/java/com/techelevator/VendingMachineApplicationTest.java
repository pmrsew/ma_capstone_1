package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class VendingMachineApplicationTest {

    @Test
    public void to_check_the_total_records_generated_equals_actual_file() throws FileNotFoundException {

        VendingMachineApplication vendingMachine = new VendingMachineApplication();
        List<VendingMachineItems> list = new ArrayList<>();
        int count = 0;

        File srcFile = new File( "vendingmachine.csv" );
        Scanner sc = new Scanner( srcFile );
        while (sc.hasNextLine()) {
            sc.nextLine();
            count++;
        }
        sc.close();
        Assert.assertEquals( count, vendingMachine.getVendingMachineItems( srcFile ).size() );
    }
}
