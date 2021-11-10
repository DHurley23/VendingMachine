import DTO.Item;
import Exceptions.NoItemInventoryException;
import Service.VendingMachineServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class VendingMachineServiceImplTest {

    VendingMachineServiceImpl vendingMachineService = new VendingMachineServiceImpl();

    @Test
    void addItem() {
        //adding a new item to the inventory
        Item iceCream = new Item("Ice Cream", BigDecimal.valueOf(1.50));
        int iceCreamsToBeAdded = 2;
        vendingMachineService.addItem(iceCream, iceCreamsToBeAdded);
        Optional<Integer> optionalIceCreamQuantity = vendingMachineService.getInventory().getItemsAndQuantities().entrySet().stream().filter(entry -> entry.getKey().equals(iceCream)).map(Map.Entry::getValue).findFirst();
        int iceCreamQuantity = optionalIceCreamQuantity.map(e -> e.intValue()).get();
        assertEquals(iceCreamsToBeAdded, iceCreamQuantity);

        //adding to an existing item in the inventory
        int currentMarsQuantity = 8;
        Item mars = new Item("Mars", BigDecimal.valueOf(2.30));
        vendingMachineService.addItem(mars, currentMarsQuantity);

        int marsToBeAdded = 4;
        vendingMachineService.addItem(mars, marsToBeAdded);

        Item tmp = vendingMachineService.getItemByName("Mars");
        int marsQuantity = vendingMachineService.getInventory().getItemsAndQuantities().get(tmp);
        assertEquals(currentMarsQuantity+marsToBeAdded, marsQuantity);

    }

    @Test
    void removeItem() throws NoItemInventoryException {
        int currentMarsQuantity = 8;
        Item mars = new Item("Mars", BigDecimal.valueOf(2.30));
        vendingMachineService.addItem(mars, currentMarsQuantity);
        //removing an existing item in the inventory
        vendingMachineService.removeItem(mars);
        Item tmp = vendingMachineService.getItemByName("Mars");
        int marsQuantity = vendingMachineService.getInventory().getItemsAndQuantities().get(tmp);
        assertEquals(currentMarsQuantity-1, marsQuantity);


        int currentTwixQuantity = 0;
        Item twix = new Item("Twix", BigDecimal.valueOf(2.20));
        vendingMachineService.addItem(twix, currentTwixQuantity);
        //removing an item that has 0 stock - should throw Exceptions.NoItemInventoryException and not decrement the quantity value.
        Assertions.assertThrows(NoItemInventoryException.class, () -> vendingMachineService.removeItem(twix));
        Item tmp2 = vendingMachineService.getItemByName("Twix");
        int twixQuantity = vendingMachineService.getInventory().getItemsAndQuantities().get(tmp2);
        assertEquals(currentTwixQuantity, twixQuantity);
    }

    @Test
    void getItemByName() {
        Item twix = new Item("Twix", BigDecimal.valueOf(2.2));
        vendingMachineService.addItem(twix, 2);
        Item temp = vendingMachineService.getItemByName("Twix");
        assertEquals(twix, temp);
    }

                            //works when this test class is run by itself, running all test classes at once requires the
    @Test                  // ID to be B1 as two items are added in the inventoryDAOImplTest, which offsets the result
    void getItemById() {
        Item crisps = new Item("Crisps", BigDecimal.valueOf(4.2));
        vendingMachineService.addItem(crisps, 3);
        Item temp = vendingMachineService.getItemById("B1");
        assertEquals(crisps, temp);
    }

}