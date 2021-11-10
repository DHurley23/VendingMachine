package Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Change {

    private BigDecimal numberOfQuarters;
    private BigDecimal numberOfDimes;
    private BigDecimal numberOfNickels;
    private BigDecimal numberOfPennies;

    //prints out change due and calls function to update the number of coins required
    public void calculateCoinCounts(BigDecimal change) {
        System.out.println("Total change due: $" + change);
        BigDecimal percentage = new BigDecimal("100");
        BigDecimal costInPennies = change.multiply(percentage);
        calculateNumberOfCoins(costInPennies);
    }

    //calculates the number of coins required as change
    private void calculateNumberOfCoins(BigDecimal costInPennies) {
        numberOfQuarters = costInPennies.divide(CoinValues.QUARTER.price()).setScale(0, RoundingMode.FLOOR);
        costInPennies = costInPennies.remainder(CoinValues.QUARTER.price());

        numberOfDimes = costInPennies.divide(CoinValues.DIME.price()).setScale(0, RoundingMode.FLOOR);
        costInPennies = costInPennies.remainder(CoinValues.DIME.price());

        numberOfNickels = costInPennies.divide(CoinValues.NICKEL.price()).setScale(0, RoundingMode.FLOOR);
        costInPennies = costInPennies.remainder(CoinValues.NICKEL.price());

        numberOfPennies = costInPennies.setScale(0, RoundingMode.FLOOR);
    }

    public BigDecimal getNumberOfQuarters() {
        return numberOfQuarters;
    }

    public BigDecimal getNumberOfDimes() {
        return numberOfDimes;
    }

    public BigDecimal getNumberOfNickels() {
        return numberOfNickels;
    }

    public BigDecimal getNumberOfPennies() {
        return numberOfPennies;
    }

}
