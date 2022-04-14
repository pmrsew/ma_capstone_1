package com.techelevator;

import com.techelevator.view.Menu;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;
/*
I worked on the priting of the items..formatting and all
then created the Generate log class and all the code related to logging to the log file, creating date fields.
I was still working on log file..step 8:....I need to run and check if its working fine
after step 8..I planned to move to step 9, then unit testing and exception handling..

 */
public class VendingMachineCLI {

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE ,MAIN_MENU_OPTION_EXIT};
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");



	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run() {

		List<VendingMachineItems> vendingmachineitems = new ArrayList<VendingMachineItems>();
		vendingmachineitems = getVendingMachineItems();
		Timestamp timestamp = new Timestamp( System.currentTimeMillis() );


		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);


			GenerateLog.log( ">"+sdf.format( timestamp ) );

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				// display vending machine items

//				System.out.println(" Slot Location " + " Item " + " Price " );
//				for(VendingMachineItems items : vendingmachineitems ){
//					System.out.println(items.getSlotLocation() + "  " + items.getItemName() + " " + items.getItemPrice());
//				}
				printVendingMachineItems( vendingmachineitems );


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
				VendingMachineItems vendingMachineItems = new VendingMachineItems(item[0],item[1],new BigDecimal( item[2]),item[3]);
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
		System.out.println("-----------------------------------------------------------------");
		System.out.printf(  "%5s %15s %15s %15s\n","Slot","Item","Price","Qty Remaning" );
		System.out.println("--------------------------------------------------------------------");
		for(VendingMachineItems items : vendingMachineItems ){
			System.out.printf ("%5s %20s %10s %10s" ,
					             items.getSlotLocation()
					          ,  items.getItemName()
					          ,  items.getItemPrice()
					          ,items.getItemStock()==0? "SOLD OUT\n" : items.getItemStock() + "\n");
		}
	}

	public static void runPurchaseMenu(Menu menu, List<VendingMachineItems> vendingMachineItemsList){
		int userSelection = -1; //added
		//double collectedMoney = 0.00; //added
		Timestamp timestamp = new Timestamp( System.currentTimeMillis() );
		BigDecimal collectedMoney = new BigDecimal( 0.0 );

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
						BigDecimal money = new BigDecimal( dollorInput);
						collectedMoney=collectedMoney.add(money);
						GenerateLog.log( ">"+sdf.format( timestamp )
								+ " FEED MONEY: "
								+ "$"+money.setScale( 2, RoundingMode.UP )
								+ " $"+collectedMoney.setScale( 2, RoundingMode.UP ));
					}else{
						System.out.println(System.lineSeparator() + "Invalid bill entry");}

					//System.out.println("Do you want to enter more money(y/n)");
					//c = input.nextLine().toLowerCase(  );

				//}while(c.equals( "y" ));
				//System.out.println("Collected Money :" + collectedMoney);


			} else if(ch.equals( "Select Product" )){
				//all below is added
				userSelection = 2;

				if(collectedMoney.compareTo( BigDecimal.ZERO ) > 0){

					Scanner sc = new Scanner(System.in);

					//List<VendingMachineItems> listOfItems = getVendingMachineItems();

					printVendingMachineItems(vendingMachineItemsList);

					System.out.println("Enter Slot of Product Wanted: ");
					String selectedProduct = sc.nextLine();

					for(VendingMachineItems currentItem : vendingMachineItemsList){

						if(currentItem.getSlotLocation().equalsIgnoreCase(selectedProduct)){

							if(currentItem.getItemStock() > 0){

								if(collectedMoney.compareTo( BigDecimal.ZERO )>= currentItem.getItemPrice().compareTo( BigDecimal.ZERO )){


									currentItem.setItemStock(currentItem.getItemStock()-1);

									GenerateLog.log( ">"+sdf.format( timestamp )
											+ " "+currentItem.getItemName()
											+"  "+currentItem.getSlotLocation()
											+ " $"+collectedMoney.setScale( 2, RoundingMode.UP )
											+ " $"+collectedMoney.subtract( currentItem.getItemPrice()));
									collectedMoney = collectedMoney.subtract( currentItem.getItemPrice());

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

				GenerateLog.log( ">"+sdf.format( timestamp )
						+ " GIVE CHANGE: $"+collectedMoney.setScale( 2, RoundingMode.UP ) );
				//Turn collected money balance into cents
				collectedMoney = collectedMoney.multiply( new BigDecimal( 100 ));


					quarterCount = collectedMoney.intValue()/25;
					remainder = collectedMoney.intValue()%25;
					if(remainder > 0){
						dimeCount = remainder/10;
						remainder = remainder%10;
						System.out.println(remainder);
						if(remainder >0){
							nickleCount = remainder/5;
						}
					}


				System.out.println("Transaction completed. Change returned:");
				System.out.println(quarterCount + " Quarters, " + dimeCount + " Dimes, " + nickleCount+ " Nickles.");


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
