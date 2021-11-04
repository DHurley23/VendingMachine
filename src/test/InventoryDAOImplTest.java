package test;

import main.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryDAOImplTest {

    @Test
    void importInventory() {
        InventoryDAOImpl inventory = new InventoryDAOImpl();
        int numberOfItemsInTestFile = 2;
        inventory.readInventoryFromFile("testImportFile.txt");
        int sizeAfterImport = inventory.getItemsAndQuantities().size();
        assertEquals(numberOfItemsInTestFile, sizeAfterImport);
    }



}