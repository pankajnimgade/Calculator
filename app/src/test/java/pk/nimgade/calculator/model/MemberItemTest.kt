package pk.nimgade.calculator.model

import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by Pankaj Nimgade on 12/25/2017.
 */
class MemberItemTest {
    @Test
    fun multiply12and12() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "12")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "12")
        _1.operation(MemberType.MULTIPLICATION, _2)
        Assert.assertEquals(BigDecimal(144), _1.bigNumber)
    }

    @Test
    fun multiply0and12() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "0")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "12")
        _1.operation(MemberType.MULTIPLICATION, _2)
        Assert.assertEquals(BigDecimal(0), _1.bigNumber)
    }

    @Test
    fun multiply2and5() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "2")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "5")
        _1.operation(MemberType.MULTIPLICATION, _2)
        Assert.assertEquals(BigDecimal(10), _1.bigNumber)
    }

    @Test
    fun multiply5and0() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "5")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "0")
        _1.operation(MemberType.MULTIPLICATION, _2)
        Assert.assertEquals(BigDecimal(0), _1.bigNumber)
    }

    @Test
    fun multiply1and7() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "1")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "7")
        _1.operation(MemberType.MULTIPLICATION, _2)
        Assert.assertEquals(BigDecimal(7), _1.bigNumber)
    }

    @Test
    fun multiply20and1() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "20")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "1")
        _1.operation(MemberType.MULTIPLICATION, _2)
        Assert.assertEquals(BigDecimal(20), _1.bigNumber)
    }

    @Test
    fun divide11and11() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "11")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "11")
        _1.operation(MemberType.DIVISION, _2)
        Assert.assertEquals(BigDecimal(1), _1.bigNumber)
    }

    @Test
    fun divide11and7() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "11")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "7")
        _1.operation(MemberType.DIVISION, _2)
        val actual = BigDecimal(1.5714).divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP)
        Assert.assertEquals(actual, _1.bigNumber)
    }

    @Test
    fun divide7and11() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "7")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "11")
        _1.operation(MemberType.DIVISION, _2)
        val actual = BigDecimal(0.6364).divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP)
        Assert.assertEquals(actual, _1.bigNumber)
    }

    @Test
    fun divide0and4() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "0")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "4")
        _1.operation(MemberType.DIVISION, _2)
        val expected = BigDecimal(0).divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP)
        Assert.assertEquals(expected.stripTrailingZeros(), _1.bigNumber)
    }

    @Test(expected =  ArithmeticException::class)
    fun divide5and0() {
        val _1: MemberItem = MemberItem(MemberType.NUMBER, "5")
        val _2: MemberItem = MemberItem(MemberType.NUMBER, "0")
        _1.operation(MemberType.DIVISION, _2)
        val actual = BigDecimal(0).divide(BigDecimal.ONE, 4, RoundingMode.HALF_UP)
        Assert.assertEquals(actual, _1.bigNumber)
    }

}