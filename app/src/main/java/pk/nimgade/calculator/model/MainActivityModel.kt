package pk.nimgade.calculator.model

import android.support.annotation.NonNull
import android.util.Log
import javax.inject.Inject

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */
class MainActivityModel : IMainActivityModel {


    val TAG = "MainActivityModel"

    //    private val memberItemList: MutableList<MemberItem> = mutableListOf()
    val memberItemList: MutableList<MemberItem> = mutableListOf()

    var lastInputEquationText: String? = null

    @Inject
    constructor()


    companion object {
        //        private var lastMemberItem: MemberItem? = null
        var lastMemberItem: MemberItem? = null
    }

    override fun addCharacter(character: String): String? {
        if (!character.isNullOrEmpty() && character.length == 1) {
            if (character.matches(Regex(pattern = "[0-9]|[+]|[-]|[*]|[/]"))) {
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
                        memberItemList.add(memberItemList.size - 1, MemberItem(MemberType.ADDITION, "+"))

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
                }
            }
        }

        return getEquationFromInputText()
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
            checkMultiplication(memberItemList)
            checkDivision(memberItemList)
            checkAddition(memberItemList)
            checkSubtraction(memberItemList)
            Log.d(TAG, ": ${memberItemList.first().memberString}")
        }

        if (memberItemList.isNotEmpty()) {
            result = memberItemList.first().memberString
        }
        memberItemList.clear()
        return result
    }


    override fun getInputTextEquationBeforeComputation(): String? {
        val result = ""
        if (!lastInputEquationText.isNullOrEmpty()) {
            return "$lastInputEquationText ="
        }
        return result
    }


    private fun checkMultiplication(list: MutableList<MemberItem>) {
        val iterator = list.iterator()
        var index = 0
        var isMultiplicationPresent = false
        while (iterator.hasNext()) {
            val currentMemberItem = iterator.next()
            if (currentMemberItem.memberType == MemberType.MULTIPLICATION) {
                isMultiplicationPresent = true
                break
            }
            index++
        }
        if (isMultiplicationPresent) {
            var previousMemberItem = list.get(index - 1)
            var currentMemberTypeMultiplication = list.removeAt(index)
            var nextMemberItem = list.removeAt(index)
            previousMemberItem.operation(currentMemberTypeMultiplication.memberType, nextMemberItem)
            checkMultiplication(list)
        }
    }

    private fun checkDivision(list: MutableList<MemberItem>) {
        val iterator = list.iterator()
        var index = 0
        var isMultiplicationPresent = false
        while (iterator.hasNext()) {
            val currentMemberItem = iterator.next()
            if (currentMemberItem.memberType == MemberType.DIVISION) {
                isMultiplicationPresent = true
                break
            }
            index++
        }
        if (isMultiplicationPresent) {
            var previousMemberItem = list.get(index - 1)
            var currentMemberTypeMultiplication = list.removeAt(index)
            var nextMemberItem = list.removeAt(index)
            previousMemberItem.operation(currentMemberTypeMultiplication.memberType, nextMemberItem)
            checkDivision(list)
        }
    }

    private fun checkAddition(list: MutableList<MemberItem>) {
        val iterator = list.iterator()
        var index = 0
        var isMultiplicationPresent = false
        while (iterator.hasNext()) {
            val currentMemberItem = iterator.next()
            if (currentMemberItem.memberType == MemberType.ADDITION) {
                isMultiplicationPresent = true
                break
            }
            index++
        }
        if (isMultiplicationPresent) {
            var previousMemberItem = list.get(index - 1)
            var currentMemberTypeMultiplication = list.removeAt(index)
            var nextMemberItem = list.removeAt(index)
            previousMemberItem.operation(currentMemberTypeMultiplication.memberType, nextMemberItem)
            checkAddition(list)
        }
    }

    private fun checkSubtraction(list: MutableList<MemberItem>) {
        val iterator = list.iterator()
        var index = 0
        var isMultiplicationPresent = false
        while (iterator.hasNext()) {
            val currentMemberItem = iterator.next()
            if (currentMemberItem.memberType == MemberType.SUBTRACTION) {
                isMultiplicationPresent = true
                break
            }
            index++
        }
        if (isMultiplicationPresent) {
            var previousMemberItem = list.get(index - 1)
            var currentMemberTypeMultiplication = list.removeAt(index)
            var nextMemberItem = list.removeAt(index)
            previousMemberItem.operation(currentMemberTypeMultiplication.memberType, nextMemberItem)
            checkSubtraction(list)
        }
    }

    override fun getEquationFromInputText(): String? {
//        checkEquationText()
        val equationFromInputText = StringBuilder()
        if (memberItemList.isNotEmpty()) {
            for (memberItem in memberItemList) {
                equationFromInputText.append("${memberItem.memberString} ")
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
        var inputListValue:String = ""
        if (memberItemList.isNotEmpty()) {
            for (memberItem in memberItemList) {
              inputListValue += "{${memberItem.memberString}\n"
            }
        }
        return inputListValue
    }

}