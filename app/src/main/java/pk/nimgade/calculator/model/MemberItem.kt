package pk.nimgade.calculator.model

import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */
data class MemberItem(var memberType: MemberType, var memberString: String = "1") {

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
        val multiply = bigNumber!!.multiply(memberItem.bigNumber)
        memberString = multiply.toString()
    }

    private fun divide(memberItem: MemberItem) {
        val divide = bigNumber!!.divide(memberItem.bigNumber,4, RoundingMode.HALF_UP)
        memberString = divide.toString()
    }

    private fun add(memberItem: MemberItem) {
        val divide = bigNumber!!.add(memberItem.bigNumber)
        memberString = divide.toString()
    }

    private fun subtract(memberItem: MemberItem) {
        val divide = bigNumber!!.subtract(memberItem.bigNumber)
        memberString = divide.toString()
    }


}