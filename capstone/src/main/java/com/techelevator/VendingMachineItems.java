package com.techelevator;


import java.math.BigDecimal;

public class VendingMachineItems {

    //Instance Variables
    private String slotLocation;
    private String itemName;
    BigDecimal itemPrice = new BigDecimal( "0.0" );
    private String itemType;
    private int itemStock;

    //Constructors

    public VendingMachineItems() {
    }

    // Parameterized constructor with base stock set to 5
    public VendingMachineItems(String slotLocation, String itemName, BigDecimal itemPrice, String itemType) {
        this.slotLocation = slotLocation;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        this.itemStock = 5;
    }

    //Methods to handle printing the user required responses.
    @Override
    public String toString() {
        return "VendingMachineItems{" +
                "slotLocation='" + slotLocation + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                '}';
    }

    public String dispenseMessage() {
        String result = null;

        String itemType = getItemType();
        switch (itemType) {
            case "Chip":
                result = "Crunch Crunch, Yum!";
                break;
            case "Candy":
                result = "Munch Munch, Yum!";
                break;
            case "Drink":
                result = "Glug Glug, Yum!";
                break;
            case "Gum":
                result = "Chew Chew, Yum!";
                break;
            default:
                break;
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

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
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
