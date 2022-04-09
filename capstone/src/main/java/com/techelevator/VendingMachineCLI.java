package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE ,MAIN_MENU_OPTION_EXIT};

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

		List<VendingMachineItems> vendingmachineitems = new ArrayList<VendingMachineItems>();

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items
				vendingmachineitems = displayVendingMachineItems();
				System.out.println(" Slot Location " + " Item " + " Price " );
				for(VendingMachineItems items : vendingmachineitems ){
					System.out.println(items.getSlotLocation() + "  " + items.getItemName() + " " + items.getItemPrice());
				}

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
				String[] subMenuItems ={"Feed Money", "Select Product","Finish Transaction"};

				String ch = (String) menu.getChoiceFromOptions(subMenuItems);
				double collectedMoney=0.0;
				if(ch.equals( "Feed Money" )){
					System.out.println("feed money");
					Scanner input = new Scanner( System.in );
					String dollorInput;
					String c = "n";
					do{
						System.out.println("Enter the money($1,$2,$5,$10) :");
						dollorInput= input.nextLine();


						if(dollorInput.equals("1") || dollorInput.equals("2") || dollorInput.equals("5")||dollorInput.equals("10")) {
							int money = Integer.parseInt( dollorInput);
							collectedMoney += money;
						}else
							System.out.println("Please enter $1,$2,$5,$10");

						System.out.println("Do you want to enter more money(y/n)");
						c = input.nextLine().toLowerCase(  );

					}while(c.equals( "y" ));
					System.out.println("Collected Money :" + collectedMoney);

				} else if(ch.equals( "Select Product" )){
					System.out.println("select product");
				} else if (ch.equals( "Finish Transaction" )){
					System.out.println("finish transtion");
				}

			} else if (choice.equals( MAIN_MENU_OPTION_EXIT ))
				System.exit( 0 );
		}
	}

	public List<VendingMachineItems> displayVendingMachineItems (){

       List<VendingMachineItems> itemsList = new ArrayList<VendingMachineItems>();

		String currentDir = System.getProperty( "user.dir" );

		String path = "\\capstone\\vendingmachine.csv";
		String filePath = currentDir + path;

		File fileName = new File( filePath );

		try(Scanner sc = new Scanner( fileName )){
			while(sc.hasNextLine())
			{
				String lineofText = sc.nextLine();
				String[] item = lineofText.split( "\\|" );
				VendingMachineItems vendingMachineItems = new VendingMachineItems(item[0],item[1],Double.parseDouble(item[2]),item[3]);
				itemsList.add( vendingMachineItems );

			}

		}
		catch(FileNotFoundException e){
			e.getMessage();
			System.out.println(" In display items methods" + e.getMessage());
		}


		return itemsList ;


	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

}
