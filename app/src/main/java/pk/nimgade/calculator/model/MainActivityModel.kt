package pk.nimgade.calculator.model

import android.support.annotation.NonNull

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */
class MainActivityModel : IMainActivityModel {

    private val memberItemList: MutableList<MemberItem> = mutableListOf()

    companion object {
        private var lastMemberItem: MemberItem? = null
    }

    override fun addCharacter(character: String) {
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

    fun getNumberOfMemberType(): Int {
        return memberItemList.size
    }

    override fun getEquationFromInputText(): String? {
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


    override fun compute() {

    }

    override fun toString(): String {
        return memberItemList.toString()
    }

}



