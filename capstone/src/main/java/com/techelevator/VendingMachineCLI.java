package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
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
		vendingmachineitems = getVendingMachineItems();

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items

//				System.out.println(" Slot Location " + " Item " + " Price " );
//				for(VendingMachineItems items : vendingmachineitems ){
//					System.out.println(items.getSlotLocation() + "  " + items.getItemName() + " " + items.getItemPrice());
//				}


			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				// do purchase
//				String[] subMenuItems ={"Feed Money", "Select Product","Finish Transaction"};
//
//				String ch = (String) menu.getChoiceFromOptions(subMenuItems);
//				double collectedMoney=0.0;
//				if(ch.equals( "Feed Money" )){
//					System.out.println("feed money");
//					Scanner input = new Scanner( System.in );
//					String dollorInput;
//					String c = "n";
//					do{
//						System.out.println("Enter the money($1,$2,$5,$10) :");
//						dollorInput= input.nextLine();
//
//
//						if(dollorInput.equals("1") || dollorInput.equals("2") || dollorInput.equals("5")||dollorInput.equals("10")) {
//							int money = Integer.parseInt( dollorInput);
//							collectedMoney += money;
//						}else
//							System.out.println("Please enter $1,$2,$5,$10");
//
//						System.out.println("Do you want to enter more money(y/n)");
//						c = input.nextLine().toLowerCase(  );
//
//					}while(c.equals( "y" ));
//					System.out.println("Collected Money :" + collectedMoney);
//
//				} else if(ch.equals( "Select Product" )){
//					System.out.println("select product");
//				} else if (ch.equals( "Finish Transaction" )){
//					System.out.println("finish transtion");
//				}
				runPurchaseMenu(menu, vendingmachineitems);

			} else if (choice.equals( MAIN_MENU_OPTION_EXIT ))
				System.exit( 0 );
		}
	}

	public static List<VendingMachineItems> getVendingMachineItems(){

        List<VendingMachineItems> itemsList = new ArrayList<VendingMachineItems>();

		String currentDir = System.getProperty( "user.dir" );

		String path = "\\capstone\\vendingmachine.csv";
		String filePath = currentDir + path;

		File fileName = new File( filePath );

		try(Scanner sc = new Scanner( fileName )){
			while(sc.hasNextLine()) {
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

	public static void printVendingMachineItems(List<VendingMachineItems> vendingMachineItems){
		System.out.println(" Slot Location " + " Item " + " Price " );
		for(VendingMachineItems items : vendingMachineItems ){
			System.out.println(items.getSlotLocation() + "  " + items.getItemName() + " " + items.getItemPrice());
		}
	}

	public static void runPurchaseMenu(Menu menu, List<VendingMachineItems> vendingMachineItemsList){
		int userSelection = -1; //added
		double collectedMoney = 0.00; //added

		do {

			System.out.println("*********************");//added
			System.out.println("Current Money Provided: " + collectedMoney);//added
			String[] subMenuItems ={"Feed Money", "Select Product","Finish Transaction"};
			String ch = (String) menu.getChoiceFromOptions(subMenuItems);


			//double collectedMoney=0.0;
			if(ch.equals( "Feed Money" )){
				userSelection = 1;
				//System.out.println("feed money");
				Scanner input = new Scanner( System.in );
				String dollorInput;
				//String c = "n";
				//do{
					System.out.println(System.lineSeparator() + "Enter the money($1,$2,$5,$10) :");
					dollorInput= input.nextLine();


					if(dollorInput.equals("1") || dollorInput.equals("2") || dollorInput.equals("5")||dollorInput.equals("10")) {
						int money = Integer.parseInt( dollorInput);
						collectedMoney += money;
					}else
						System.out.println(System.lineSeparator() + "Invalid bill entry");

					//System.out.println("Do you want to enter more money(y/n)");
					//c = input.nextLine().toLowerCase(  );

				//}while(c.equals( "y" ));
				//System.out.println("Collected Money :" + collectedMoney);

			} else if(ch.equals( "Select Product" )){
				//all below is added
				userSelection = 2;

				if(collectedMoney > 0){

					Scanner sc = new Scanner(System.in);

					//List<VendingMachineItems> listOfItems = getVendingMachineItems();

					printVendingMachineItems(vendingMachineItemsList);

					System.out.println("Enter Slot of Product Wanted: ");
					String selectedProduct = sc.nextLine();

					for(VendingMachineItems currentItem : vendingMachineItemsList){

						if(currentItem.getSlotLocation().equalsIgnoreCase(selectedProduct)){

							if(currentItem.getItemStock() > 0){

								if(collectedMoney >= currentItem.getItemPrice()){


									currentItem.setItemStock(currentItem.getItemStock()-1);
									collectedMoney -= currentItem.getItemPrice();

									System.out.println("Item: " + currentItem.getItemName() + " Price: " + currentItem.getItemPrice() + " Remaining Balance: " + collectedMoney);
									System.out.println(currentItem.dispenseMessage());



								}else{
									System.out.println("Insufficient money provided. Can't purchase item.");
								}

							}else{
								System.out.println("Product is out of stock");
							}
						}

						continue;

					}

				}else{
					System.out.println("Please enter money first");
				}





			} else if (ch.equals( "Finish Transaction" )){
				userSelection = 3;





				int quarterCount = 0;
				int dimeCount = 0;
				int nickleCount = 0;

				int remainder = 0;



				//Turn collected money balance into cents
				collectedMoney = Math.ceil(collectedMoney*100.0);



					quarterCount = (int)collectedMoney/25;
					System.out.println("Quarter: " +quarterCount);
					remainder = (int)collectedMoney%25;
					System.out.println(remainder);
					if(remainder > 0){
						dimeCount = remainder/10;
						System.out.println("Dime: "+dimeCount);
						remainder = remainder%10;
						System.out.println(remainder);
						if(remainder >0){
							nickleCount = remainder/5;
							System.out.println("Nickle: " +nickleCount);
						}
					}


				System.out.println("Transaction completed. Change returned:");


				break;
			}

		}while(userSelection == 1 || userSelection == 2);

	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}

}
