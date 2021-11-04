package main;

import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineApp {

    private static final String FILENAME = "inventory.txt";
    private static final String EXIT = "Quit";

    public static void main(String[] args) {
        VendingMachineServiceImpl vendingMachine = new VendingMachineServiceImpl();
        vendingMachine.getInventory().readInventoryFromFile(FILENAME);              //import before beginning the process of the vending machine
        vend(vendingMachine);
        vendingMachine.getInventory().writeInventoryToFile(FILENAME);               //export back out to the cvs file with updated values
    }

    //perform the main act of the vending machine by taking in user input
    private static void vend(VendingMachineServiceImpl vendingMachine) {
        boolean active = true;
        while (active) {
            vendingMachine.printAllItemsAndPrices();
            System.out.println("Please enter your amount of money now (e.g. 1.75 = 1 dollar, 75 cent) or quit");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            if(!input.equalsIgnoreCase(EXIT)) {
                BigDecimal inputMoney = new BigDecimal("0");
                try {
                    inputMoney = new BigDecimal(input);
                } catch (NumberFormatException e ){
                    System.out.println("Invalid input, restart to try again.");
                    break;
                }
                System.out.println("You put $" + input + " in");
                System.out.println("Please enter the key of the item you would like to purchase: ");
                String itemKey = scanner.nextLine();
                Item itemToBeVended = vendingMachine.getItemById(itemKey);
                BigDecimal itemCost = itemToBeVended.getPrice();
                BigDecimal change = inputMoney.subtract(itemCost);
                BigDecimal zero = new BigDecimal("0");
                try {
                    if (change.compareTo(zero) < 0) {
                        throw new InsufficientFundsException(inputMoney, itemCost);
                    } else {
                        try {
                            vendingMachine.removeItem(itemToBeVended);
                            vendingMachine.displayChange(change);
                        } catch (NoItemInventoryException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (InsufficientFundsException e) {
                    e.printStackTrace();
                }
            }
            else {
                active= false;
            }
        }
    }
}
