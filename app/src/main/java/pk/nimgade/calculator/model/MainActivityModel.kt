package pk.nimgade.calculator.model

import android.support.annotation.NonNull
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
                if (memberItemList.isEmpty()) {
                    lastMemberItem = null
                } else {
                    lastMemberItem = memberItemList.last()
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
                        (lastMemberItem as MemberItem).memberString += character
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType == MemberType.NUMBER &&
                            currentCharacterMemberType != MemberType.NUMBER -> {
                        // last  is number
                        // current  is ( '/', '*', '-', '+' )
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                        memberItemList.add((lastMemberItem as MemberItem))
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType != MemberType.NUMBER &&
                            (lastMemberItem as MemberItem).memberType == MemberType.SUBTRACTION &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // last is subtraction
                        // current is number

                        (lastMemberItem as MemberItem).memberType = MemberType.NUMBER
                        (lastMemberItem as MemberItem).memberString += character
                        memberItemList.add(memberItemList.size - 1, MemberItem(MemberType.ADDITION, ""))

                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType != MemberType.NUMBER &&
                            ((lastMemberItem as MemberItem).memberType == MemberType.MULTIPLICATION ||
                                    (lastMemberItem as MemberItem).memberType == MemberType.DIVISION) &&
                            currentCharacterMemberType == MemberType.SUBTRACTION -> {
                        // last is ('*' or '/')
                        // current is ( '-' )
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                        memberItemList.add((lastMemberItem as MemberItem))
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType != MemberType.NUMBER &&
                            (lastMemberItem as MemberItem).memberType != MemberType.SUBTRACTION &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // last ( '*' , '/' )
                        // current number
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                        memberItemList.add((lastMemberItem as MemberItem))
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType == MemberType.NUMBER &&
                            currentCharacterMemberType == MemberType.DECIMAL -> {
                        // last number
                        // current . (decimal)
                        (lastMemberItem as MemberItem).memberType = MemberType.DECIMAL
                        (lastMemberItem as MemberItem).memberString += character
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType == MemberType.DECIMAL &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // last . (decimal)
                        // current number
                        (lastMemberItem as MemberItem).memberType = MemberType.NUMBER
                        (lastMemberItem as MemberItem).memberString += character
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
        }
        return memberType
    }

    override fun calculateOrCompute(): String? {
        var result: String? = null
        checkEquationText()
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
                .any { it >= 0 && it < memberItemList.size && memberItemList[it].bigNumber == BigDecimal.ZERO }
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
        var isMultiplicationPresent = false
        while (iterator.hasNext()) {
            val currentMemberItem = iterator.next()
            if (currentMemberItem.memberType == operationMemberType) {
                isMultiplicationPresent = true
                break
            }
            index++
        }
        if (isMultiplicationPresent) {
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


    private fun checkEquationText() {
        if (memberItemList.isNotEmpty()) {
            if (memberItemList.last().memberType != MemberType.NUMBER) {
                memberItemList.removeAt(memberItemList.size - 1)
            }
        }
        if (memberItemList.isNotEmpty()) {
            if (memberItemList.first().memberType != MemberType.NUMBER) {
                memberItemList.removeAt(0)
            }
        }
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