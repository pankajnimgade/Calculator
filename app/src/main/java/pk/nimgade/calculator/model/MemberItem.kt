package pk.nimgade.calculator.model

import java.math.BigDecimal

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
                multiply(secondMemberItem)
            }
            MemberType.ADDITION -> {
                multiply(secondMemberItem)
            }
            MemberType.SUBTRACTION -> {
                multiply(secondMemberItem)
            }
        }
    }

    private fun multiply(memberItem: MemberItem) {
        val multiply = bigNumber!!.multiply(memberItem.bigNumber)
        memberString = multiply.toString()
    }


}