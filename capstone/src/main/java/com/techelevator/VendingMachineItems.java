package com.techelevator;



public class VendingMachineItems {

    //Instance Variables
    private String slotLocation;
    private String itemName;
    private double itemPrice;
    private String itemType;
    private int itemStock;

    //Constructors

    public VendingMachineItems() {
    }

    public VendingMachineItems(String slotLocation, String itemName, double itemPrice, String itemType) {
        this.slotLocation = slotLocation;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        this.itemStock = 5;
    }

    //Methods
    @Override
    public String toString() {
        return "VendingMachineItems{" +
                "slotLocation='" + slotLocation + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }

    public String dispenseMessage() {
        String result = "";

        String itemType = getItemType();
        switch(itemType){
            case "Chip": result = "Crunch Crunch, Yum!";
            case "Candy": result = "Munch Munch, Yum!";
            case "Drink": result = "Glug Glug, Yum!";
            case "Gum": result = "Chew Chew, Yum!";
        }

        return result;

    }




    //Getters and Setters
    public String getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(String slotLocation) {
        this.slotLocation = slotLocation;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getItemStock() {
        return itemStock;
    }

    public void setItemStock(int itemStock) {
        this.itemStock = itemStock;
    }


}
