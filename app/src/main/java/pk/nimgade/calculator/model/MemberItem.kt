package pk.nimgade.calculator.model

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */
data class MemberItem(var memberType: MemberType, var memberString: String) {

    var bigNumber: BigDecimal? = null
        get() {
            return BigDecimal(memberString)
        }

    fun operation(operationMemberType: MemberType, secondMemberItem: MemberItem) {
        when (operationMemberType) {
            MemberType.MULTIPLICATION -> {
                multiply(secondMemberItem)
            }
            MemberType.DIVISION -> {
                divide(secondMemberItem)
            }
            MemberType.ADDITION -> {
                add(secondMemberItem)
            }
            MemberType.SUBTRACTION -> {
                subtract(secondMemberItem)
            }
        }
    }

    private fun multiply(memberItem: MemberItem) {
        val multiply = bigNumber!!.multiply(memberItem.bigNumber).stripTrailingZeros()
        memberString = if (multiply.compareTo(BigDecimal.ZERO) == 0) {
            "0"
        } else {
            multiply.toString()
        }
    }

    private fun divide(memberItem: MemberItem) {
        val divide = bigNumber!!.divide(memberItem.bigNumber, 4, RoundingMode.HALF_UP).stripTrailingZeros()
        memberString = if (divide.compareTo(BigDecimal.ZERO) == 0) {
            "0"
        } else {
            divide.toString()
        }
    }

    private fun add(memberItem: MemberItem) {
        val add = bigNumber!!.add(memberItem.bigNumber).stripTrailingZeros()
        memberString = if (add.compareTo(BigDecimal.ZERO) == 0) {
            "0"
        } else {
            add.toString()
        }

    }

    private fun subtract(memberItem: MemberItem) {
        val subtract = bigNumber!!.subtract(memberItem.bigNumber).stripTrailingZeros()
        memberString = if (subtract.compareTo(BigDecimal.ZERO) == 0) {
            "0"
        } else {
            subtract.toString()
        }
    }


}