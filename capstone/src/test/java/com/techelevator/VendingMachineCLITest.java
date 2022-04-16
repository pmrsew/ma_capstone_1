package com.techelevator;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class VendingMachineCLITest  {

    @Test
    public void to_check_the_total_records_printed_equals_actual_file() throws FileNotFoundException {

        VendingMachineCLI vendingMachineCLI = new VendingMachineCLI();
        List<VendingMachineItems> list = new ArrayList<>();
        int count=0;

        File srcFile = new File( "C:\\Users\\gjyot\\OneDrive\\Desktop\\meritamerica\\pair-programming\\module-1-capstone\\capstone\\vendingmachine.csv" );

            Scanner sc = new Scanner(srcFile);

            while(sc.hasNextLine()) {
                sc.nextLine();
                count++;
            }
            sc.close();


        Assert.assertEquals( count, vendingMachineCLI.getVendingMachineItems().size() );
    }


}
