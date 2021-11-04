package main;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class InventoryDAOImpl implements InventoryDAO, AuditDAO {

    private static final String DELIMITER = ",";            //comma delimiter for csv file
    private Map<Item, Integer> itemsAndQuantities;

    public InventoryDAOImpl() {
        this.itemsAndQuantities = new HashMap<>();
    }

    @Override                                                   //reads in items and their quantities from a csv file
    public void readInventoryFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            List<String> rows = br.lines()
                    .map(s -> new AbstractMap.SimpleEntry<>(s, s.split(DELIMITER)))
                    .map(AbstractMap.SimpleEntry::getKey)
                    .collect(Collectors.toList());
            for (String row : rows) {
                String[] values = row.split(DELIMITER);
                BigDecimal price = new BigDecimal(values[1]);
                int quantity = Integer.parseInt(values[2]);
                Item newItem = new Item(values[0], price);
                itemsAndQuantities.put(newItem, quantity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        logImport();
    }

    @Override                                               //writes out items and their quantities to a csv file
    public void writeInventoryToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            //wait for streams to get keys and values
            Set<Item> items = new HashSet<>(itemsAndQuantities.keySet());
            List<Integer> quantities = new ArrayList<>(itemsAndQuantities.values());
            int i = 0;
            for (Item item : items) {
                writer.write(toCsv(item, quantities.get(i)));
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        logExport();
    }

    //function formats the item and quantity information to keep the csv files consistent
    private static String toCsv(Item item, int quantity) {
        String removeKey = item.toString().substring(3);
        String replaceSpace = removeKey.replace(" ", ",");
        replaceSpace = replaceSpace.replaceAll("\\s+", "");
        replaceSpace = replaceSpace.replaceAll("\\$", "");
        return replaceSpace + "," + quantity + "\n";
    }

    //getter for the Map
    public Map<Item, Integer> getItemsAndQuantities() {
        return itemsAndQuantities;
    }

    @Override                                                               //writes to log file when an import from csv file occurs
    public void logImport() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            SyncLogger logger = new SyncLogger();
            logger.log("Inventory imported at: ", dtf, now);

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void logExport() {                                               //writes to log file when an export to csv file occurs
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        try {
            SyncLogger logger = new SyncLogger();
            logger.log("Inventory exported at: ", dtf, now);
        }catch (IOException e ){
            e.printStackTrace();
        }
    }
}
