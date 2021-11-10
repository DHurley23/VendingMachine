import Controller.VendingMachineController;
import Service.VendingMachineServiceImpl;

public class VendingMachineApp {


    public static void main(String[] args) {
        VendingMachineController vending = new VendingMachineController(new VendingMachineServiceImpl());
        vending.vend();
    }

}
