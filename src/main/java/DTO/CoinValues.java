package DTO;

import java.math.BigDecimal;

public enum CoinValues {                //ENUM for USD coins
    QUARTER(new BigDecimal("25")),
    DIME(new BigDecimal("10")),
    NICKEL(new BigDecimal("5")),
    PENNY(new BigDecimal("1"));

    private final BigDecimal price;

    CoinValues(BigDecimal price) {
        this.price = price;
    }

    BigDecimal price() {
        return price;
    }
}
