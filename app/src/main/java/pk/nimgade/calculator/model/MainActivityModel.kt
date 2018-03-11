package pk.nimgade.calculator.model

import android.support.annotation.NonNull
import android.util.Log
import pk.nimgade.calculator.application.utils.Constants
import pk.nimgade.calculator.presenter.IMainActivityPresenter
import java.math.BigDecimal
import javax.inject.Inject

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */
class MainActivityModel : IMainActivityModel {

    val TAG = "MAIN_ACTIVITY_MODEL"

    private val memberItemList: MutableList<MemberItem> = mutableListOf()

    private lateinit var presenter: IMainActivityPresenter

    private var lastInputEquationText: String? = null

    @Inject
    constructor()


    companion object {
        private var lastMemberItem: MemberItem? = null
    }

    override fun setPresenter(presenter: IMainActivityPresenter) {
        this.presenter = presenter
    }

    override fun addCharacter(character: String): String? {
        if (!character.isNullOrEmpty() && character.length == 1) {
            if (character.matches(Regex(pattern = "[0-9]|[+]|[-]|[*]|[/]|[.]"))) {
                val currentCharacterMemberType = getCharacterType(character[0])
                lastMemberItem = if (memberItemList.isEmpty()) {
                    null
                } else {
                    memberItemList.last()
                }
                when {
                    lastMemberItem == null -> {
                        // last is empty (null)
                        // create a MemberItem
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                        memberItemList.add(lastMemberItem as MemberItem)
                    }
                    ((lastMemberItem != null) &&
                            (lastMemberItem as MemberItem).memberType == MemberType.NUMBER &&
                            (currentCharacterMemberType == MemberType.NUMBER)) -> {
                        // last    is number
                        // current is number
                        if ((lastMemberItem as MemberItem).memberString.contains(".") &&
                                character.contains(".")) {
                        } else {
                            (lastMemberItem as MemberItem).memberString += character
                        }
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType == MemberType.NUMBER &&
                            currentCharacterMemberType.isOperator() -> {
                        // last  is number
                        // current  is ( '/', '*', '-', '+' )
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                        memberItemList.add((lastMemberItem as MemberItem))
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType.isOperator() &&
                            (lastMemberItem as MemberItem).memberType == MemberType.SUBTRACTION &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // last is subtraction
                        // current is number

                        (lastMemberItem as MemberItem).memberType = MemberType.NUMBER
                        (lastMemberItem as MemberItem).memberString += character
                        memberItemList.add(memberItemList.size - 1, MemberItem(MemberType.ADDITION, ""))
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType.isOperator() &&
                            ((lastMemberItem as MemberItem).memberType == MemberType.MULTIPLICATION ||
                                    (lastMemberItem as MemberItem).memberType == MemberType.DIVISION) &&
                            currentCharacterMemberType == MemberType.SUBTRACTION -> {
                        // last is ('*' or '/')
                        // current is ( '-' )
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                        memberItemList.add((lastMemberItem as MemberItem))
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType.isOperator() &&
                            (lastMemberItem as MemberItem).memberType != MemberType.SUBTRACTION &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // last ( '*' , '/' )
                        // current number
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                        memberItemList.add((lastMemberItem as MemberItem))
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType == MemberType.NUMBER &&
                            currentCharacterMemberType.isDecimal() -> {
                        // last number
                        // current . (decimal)
                        (lastMemberItem as MemberItem).memberType = MemberType.DECIMAL
                        (lastMemberItem as MemberItem).memberString += character
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType.isDecimal() &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // last . (decimal)
                        // current number
                        (lastMemberItem as MemberItem).memberType = MemberType.NUMBER
                        (lastMemberItem as MemberItem).memberString += character
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType == MemberType.DECIMAL &&
                            currentCharacterMemberType.isOperator() -> {
                        // last . (decimal)
                        // current operators ( +, -, *, and /)
                        (lastMemberItem as MemberItem).memberType = MemberType.NUMBER
                        (lastMemberItem as MemberItem).memberString += "0"
                        memberItemList.add(MemberItem(currentCharacterMemberType, character))
                    }
                }
            }
        }

        return equationFromInputText
    }

    private fun getCharacterType(@NonNull character: Char): MemberType {
        var memberType: MemberType = MemberType.NUMBER
        when {
            character.isDigit() -> {
                memberType = MemberType.NUMBER
            }
            character == '+' -> {
                memberType = MemberType.ADDITION
            }
            character == '-' -> {
                memberType = MemberType.SUBTRACTION
            }
            character == '*' -> {
                memberType = MemberType.MULTIPLICATION
            }
            character == '/' -> {
                memberType = MemberType.DIVISION
            }
            character == '.' -> {
                memberType = MemberType.DECIMAL
            }
        }
        return memberType
    }

    override fun calculateOrCompute(): String? {
        var result: String? = null
        removeUnwantedMemberTypesFromEquation()
        lastInputEquationText = getEquationFromInputText()
        println()
        if (memberItemList.isNotEmpty()) {

            if (!checkForDivideByZero(memberItemList)) {
                // according to BODMAS
                /**
                 * The BODMAS acronym is for:
                 *  Brackets (parts of a calculation inside brackets always come first).
                 *  Orders (numbers involving powers or square roots).
                 *  Division.
                 *  Multiplication.
                 *  Addition.
                 *  Subtraction.
                 *
                 *  Operation should follow in mentioned order
                 */
                operation(memberItemList, MemberType.MULTIPLICATION)
                operation(memberItemList, MemberType.DIVISION)
                operation(memberItemList, MemberType.ADDITION)
                operation(memberItemList, MemberType.SUBTRACTION)
                println("$TAG ${memberItemList.first().memberString}")
            } else {
                val errorMessage = Constants.CANT_DIVIDE_BY_ZERO
                this.presenter.divideByZeroOccurred(errorMessage)
                return errorMessage
            }
        } else {
            return ""
        }
        if (memberItemList.isNotEmpty()) {
            result = memberItemList.first().memberString
        }
        return result
    }

    private fun checkForDivideByZero(memberItemList: MutableList<MemberItem>): Boolean {
        return (0 until memberItemList.size)
                .filter { (memberItemList[it].memberType == MemberType.DIVISION) }
                .map { it + 1 }
                .any {
                    it >= 0 && it < memberItemList.size &&
                            memberItemList[it].bigNumber!!.compareTo(BigDecimal.ZERO) == 0
                }
    }


    override fun getInputTextEquationBeforeComputation(): String? {
        val result = ""
        if (!lastInputEquationText.isNullOrEmpty()) {
            return "$lastInputEquationText ="
        }
        return result
    }

    private fun operation(list: MutableList<MemberItem>, operationMemberType: MemberType) {
        val iterator = list.iterator()
        var index = 0
        var isCurrentOperationType = false // checks for  *, /, + and -
        while (iterator.hasNext()) {
            val currentMemberItem = iterator.next()
            if (currentMemberItem.memberType == operationMemberType) {
                isCurrentOperationType = true
                break
            }
            index++
        }
        if (isCurrentOperationType) {
            var previousMemberItem = list.get(index - 1)
            var currentMemberTypeMultiplication = list.removeAt(index)
            var nextMemberItem = list.removeAt(index)
            previousMemberItem.operation(operationMemberType, nextMemberItem)
            operation(list, operationMemberType)
        }
    }

    override fun getEquationFromInputText(): String? {
        val equationFromInputText = StringBuilder()
        val space: String = if (memberItemList.size == 1) {
            ""
        } else {
            " "
        }
        if (memberItemList.isNotEmpty()) {
            for (memberItem in memberItemList) {
                equationFromInputText.append(if (!memberItem.memberString.isNullOrEmpty())
                    "${memberItem.memberString}$space"
                else
                    ""
                )
            }
        }
        return equationFromInputText.toString()
    }

    override fun clear() {
        lastMemberItem = null
        memberItemList.clear()
    }


    private fun removeUnwantedMemberTypesFromEquation() {
        if (memberItemList.isNotEmpty()) {
            do {
                if (!memberItemList.first().memberType.isNumber()) {
                    memberItemList.removeAt(0)
                } else {
                    break
                }
            } while (memberItemList.isNotEmpty())

        }
        if (memberItemList.isNotEmpty()) {
            do {
                if (!memberItemList.last().memberType.isNumber()) {
                    memberItemList.removeAt(memberItemList.size - 1)
                } else {
                    break
                }
            } while (memberItemList.isNotEmpty())
        }
    }

    override fun deleteLastCharacter() {
        Log.d(TAG, "deleteLastCharacter(): ")
        if (memberItemList != null && memberItemList.size > 0) {
            val lastMemberItem = memberItemList.last()
            if (lastMemberItem != null) {
                if (lastMemberItem.memberType == MemberType.NUMBER) {
                    if (lastMemberItem.memberString.trim().length <= 1) {
                        memberItemList.removeAt(memberItemList.size - 1)
                    } else {
                        lastMemberItem.memberString = lastMemberItem.memberString.dropLast(1)
                    }
                } else {
                    memberItemList.removeAt(memberItemList.size - 1)
                }
            }
            val restOfEquation = equationFromInputText
            this.presenter.setUpdatedInput(restOfEquation)
        }
    }

    override fun clearCompleteEquation() {
        Log.d(TAG, ": clearCompleteEquation()")
        if (memberItemList != null && memberItemList.isNotEmpty()) {
            memberItemList.clear()
        }
        this.presenter.setUpdatedInput(equationFromInputText)
    }

    private fun println() {
        if (memberItemList.isNotEmpty()) {
            for (memberItem in memberItemList) {
                print("${memberItem.memberString} # ")
            }
        }
    }

    override fun toString(): String {
        var inputListValue: String = ""
        if (memberItemList.isNotEmpty()) {
            for (memberItem in memberItemList) {
                inputListValue += "${memberItem.memberString} "
            }
        }
        return inputListValue
    }

}