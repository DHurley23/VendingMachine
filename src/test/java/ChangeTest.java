import Service.Change;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ChangeTest {

    @Test
    void calculateCoinCounts() {
        Change change = new Change();
        BigDecimal money = new BigDecimal("0.96");
        change.calculateCoinCounts(money);
        assertEquals(BigDecimal.valueOf(3), change.getNumberOfQuarters());
        assertEquals(BigDecimal.valueOf(2), change.getNumberOfDimes());
        assertEquals(BigDecimal.valueOf(0), change.getNumberOfNickels());
        assertEquals(BigDecimal.valueOf(1), change.getNumberOfPennies());
    }
}