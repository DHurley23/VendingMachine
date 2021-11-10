package DTO;

import java.math.BigDecimal;

public class Item {

    private String id;
    private static int count;
    private String itemName;
    private BigDecimal price;


    public Item(String itemName, BigDecimal price) {
        int div = count / 4;
        int modulus = count % 4;
        count++;
        char[] LETTERS = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        this.id = LETTERS[div] + Integer.toString(modulus + 1);             //creating an ID to resemble a Vending machine input i.e. A1, A2, B1 etc.
        this.itemName = itemName;
        this.price = price;
    }

    @Override
    public String toString() {
        return "" + id + " " + itemName + " $" + price;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {                             //hashcode to allow for items to be updated in files, rather than having different items of the same name
        String str = this.itemName;
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum = sum + str.charAt(i);
        }
        return sum;
    }

    @Override
    public boolean equals(Object obj) {         //for comparison of items
        try {
            Item item = (Item) obj;
            return this.itemName.equalsIgnoreCase(item.getItemName()) && (this.price.equals(item.getPrice()));
        }catch (NullPointerException e){
            e.printStackTrace();
        }
        return false;
    }

}
