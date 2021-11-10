package Controller;

import DTO.Item;
import Exceptions.InsufficientFundsException;
import Exceptions.NoItemInventoryException;
import Service.VendingMachineServiceImpl;

import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineController {

    private static final String EXIT = "Quit";
    private static final String FILENAME = "inventory.txt";
    private static VendingMachineServiceImpl vendingMachine;

    public VendingMachineController(VendingMachineServiceImpl vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    //perform the main act of the vending machine by taking in user input
    public static void run() {
        vendingMachine.getInventory().readInventoryFromFile(FILENAME);
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
        vendingMachine.getInventory().writeInventoryToFile(FILENAME);
    }
}
