package Service;

import DAO.InventoryDAOImpl;
import DTO.Change;
import DTO.Item;
import Exceptions.NoItemInventoryException;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;


public class VendingMachineServiceImpl implements VendingMachineService<Item>{

    private InventoryDAOImpl inventory;
    private static final String EXIT = "Quit";

    public VendingMachineServiceImpl() {
        this.inventory = new InventoryDAOImpl();
    }

    @Override
    public void addItem(Item item, int numberOfItem) {
        if (this.inventory.getItemsAndQuantities().containsKey(item)) {
            int quantity = this.inventory.getItemsAndQuantities().get(item);
            this.inventory.getItemsAndQuantities().put(item, numberOfItem + quantity);
        } else {
            this.inventory.getItemsAndQuantities().put(item, numberOfItem);
        }
    }

    @Override
    public void removeItem(Item item) throws NoItemInventoryException {
        if (inventory.getItemsAndQuantities().containsKey(item)) {
            int quantity = inventory.getItemsAndQuantities().get(item);
            if (quantity > 0) {
                inventory.getItemsAndQuantities().put(item, quantity - 1);
            } else {
                throw new NoItemInventoryException();
            }

        }
    }

    //returns an item by passing in the name of the item
    public Item getItemByName(String itemName) {
        Optional<Item> optionalItem = inventory.getItemsAndQuantities().entrySet().stream().map(entry -> entry.getKey()).filter(entry -> entry.getItemName().equals(itemName)).findFirst();
        Item item = null;
        if(optionalItem.isPresent()) {
            item = optionalItem.get();
        }
        return item;
    }

    //returns an item by passing in the id/key of the item
    public Item getItemById(String id) {
        Optional<Item> optionalItem = inventory.getItemsAndQuantities().entrySet().stream().map(entry -> entry.getKey()).filter(entry -> entry.getId().equals(id)).findFirst();
        Item item = null;
        if(optionalItem.isPresent()) {
            item = optionalItem.get();
        }
        return item;
    }

    //prints the quantity of a specific item
    public void printQuantity(Item item) {
        Optional<Integer> optionalQuantity = inventory.getItemsAndQuantities().entrySet().stream().filter(entry -> entry.getKey().equals(item)).map(entry -> entry.getValue()).findFirst();
        int quantity = 0;
        if(optionalQuantity.isPresent()) {
            quantity = optionalQuantity.get();
        }
        System.out.println(quantity);
    }

    @Override
    public void printAllItemsAndPrices(){
        Stream<String> itemsAndPrices = this.inventory.getItemsAndQuantities().entrySet().stream().filter(entry -> entry.getValue() > 0).map(entry -> entry.getKey().toString());
        itemsAndPrices.forEach(System.out::println);
    }

    public InventoryDAOImpl getInventory() {
        return inventory;
    }

    public void displayChange(BigDecimal input){
        Change change = new Change();
        change.calculateCoinCounts(input);
        System.out.println("Quarters:" + change.getNumberOfQuarters());
        System.out.println("Dimes:" + change.getNumberOfDimes());
        System.out.println("Nickels:" + change.getNumberOfNickels());
        System.out.println("Pennies:" + change.getNumberOfPennies());
    }

}
