package Exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    private final BigDecimal inputMoney;
    private final BigDecimal itemCost;

    public InsufficientFundsException(BigDecimal inputMoney, BigDecimal itemCost) {
        this.inputMoney = inputMoney;
        this.itemCost = itemCost;
    }

    @Override
    public void printStackTrace() {
        System.out.println("Insufficient funds: You put in $" + inputMoney + " and the item costs $" + itemCost);
    }
}
