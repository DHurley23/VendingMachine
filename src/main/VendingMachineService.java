package main;

import java.math.BigDecimal;

public interface VendingMachineService<T> {

    void printAllItemsAndPrices();

    void displayChange(BigDecimal input);

    void addItem(T t, int numberOfItem);

    void removeItem(T t) throws NoItemInventoryException;
}
