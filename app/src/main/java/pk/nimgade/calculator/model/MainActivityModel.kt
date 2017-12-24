package pk.nimgade.calculator.model

import android.support.annotation.NonNull
import android.util.Log
import javax.inject.Inject

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */
class MainActivityModel : IMainActivityModel {

    val TAG = "MainActivityModel"

    private val memberItemList: MutableList<MemberItem> = mutableListOf()

    @Inject
    constructor()


    companion object {
        private var lastMemberItem: MemberItem? = null
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

    override fun calculateOrCompute() {
        var result: Int = 0
        checkEquationText()
        if (memberItemList.isNotEmpty()) {

            checkMultiplication(memberItemList)
            Log.d(TAG, ": ${memberItemList.first().memberString}")
        }
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
        if (isMultiplicationPresent){
            var previousMemberItem = list.get(index - 1)
            var currentMemberTypeMultiplication = list.removeAt(index)
            var nextMemberItem = list.removeAt(index )
            previousMemberItem.memberString = multiplication(previousMemberItem, nextMemberItem)
            checkMultiplication(list)
        }
    }

    private fun multiplication(a: MemberItem, b: MemberItem): String {
        var result = Integer.parseInt(a.memberString) * Integer.parseInt(b.memberString)
        return Integer.toString(result)
    }

    private fun division(a: Int, b: Int): Int {
        return a / b
    }

    private fun addition(a: Int, b: Int): Int {
        return a + b
    }

    private fun substraction(a: Int, b: Int): Int {
        return a * b
    }

    private fun getNumberOfMemberType(): Int {
        return memberItemList.size
    }

    fun getEquationFromInputText(): String? {
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

    override fun toString(): String {
        return memberItemList.toString()
    }

}



