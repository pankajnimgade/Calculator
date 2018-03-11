package pk.nimgade.calculator.mockito.big.decimal

import org.junit.Test
import java.math.BigDecimal

/**
 * Created by Pankaj Nimgade on 3/10/2018.
 */
class TestBigDecimal {

    @Test
    fun testZerosInBigDecimal() {
        println("testZerosInBigDecimal")

        println("BigDecimal.ZERO.compareTo(BigDecimal(0.0)): ${BigDecimal.ZERO.compareTo(BigDecimal("0.0"))}")
        println("BigDecimal.ZERO == BigDecimal(\"0.0\"): ${BigDecimal.ZERO == BigDecimal("0.0")}")
        println("BigDecimal.ZERO === BigDecimal(\"0.0\"): ${BigDecimal.ZERO === BigDecimal("0" +
                ".0")}")
    }
}
