package DAO;

public interface InventoryDAO {

    void readInventoryFromFile(String filename);

    void writeInventoryToFile(String filename);

}
