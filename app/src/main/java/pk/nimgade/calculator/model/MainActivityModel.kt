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

    fun addCharacter(character: String) {
        if (!character.isNullOrEmpty() && character.length == 1) {
            if (character.matches(Regex(pattern = "[0-9]|[+]|[-]|[*]|[/]"))) {

                val currentCharacterMemberType = getCharacterType(character[0])
                when {
                    lastMemberItem == null -> {
                        // when @code{lastMemberItem} is empty for the first time.
                        // create one, so it will be added to @code{memberItemList}
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                    }
                    ((lastMemberItem != null) &&
                            (lastMemberItem as MemberItem).memberType == MemberType.NUMBER &&
                            (currentCharacterMemberType == MemberType.NUMBER)) -> {
                        // when last @code{lastMemberItem} is number we can append next number to it
                        (lastMemberItem as MemberItem).memberString += character
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType == MemberType.NUMBER &&
                            currentCharacterMemberType != MemberType.NUMBER -> {
                        // when next (current) MemberType is not a number we need to add the last MemberItem to list
                        // and create a new MemberItem and assign it as last MemberItem
                        memberItemList.add((lastMemberItem as MemberItem))
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                    }

                //
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType != MemberType.NUMBER &&
                            (lastMemberItem as MemberItem).memberType == MemberType.SUBTRACTION &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // when last MemberType is not a number or subtraction we need to add the last MemberItem to list
                        // and create a new MemberItem and assign it as last MemberItem
                        (lastMemberItem as MemberItem).memberType = MemberType.NUMBER
                        (lastMemberItem as MemberItem).memberString += character
                    }
                // same as below
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType != MemberType.NUMBER &&
                            ((lastMemberItem as MemberItem).memberType == MemberType.MULTIPLICATION ||
                                    (lastMemberItem as MemberItem).memberType == MemberType.DIVISION) &&
                            currentCharacterMemberType == MemberType.SUBTRACTION -> {
                        // when last MemberType is not a number or subtraction we need to add the last MemberItem to list
                        // and create a new MemberItem and assign it as last MemberItem

                        memberItemList.add((lastMemberItem as MemberItem))
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                    }
                // same as below

                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType != MemberType.NUMBER &&
                            (lastMemberItem as MemberItem).memberType != MemberType.SUBTRACTION &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // when last MemberType is not a number or subtraction we need to add the last MemberItem to list
                        // and create a new MemberItem and assign it as last MemberItem
                        memberItemList.add((lastMemberItem as MemberItem))
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                    }
                    lastMemberItem != null &&
                            (lastMemberItem as MemberItem).memberType != MemberType.NUMBER &&
                            (lastMemberItem as MemberItem).memberType == MemberType.SUBTRACTION &&
                            currentCharacterMemberType == MemberType.NUMBER -> {
                        // when last MemberType is not a number or subtraction we need to add the last MemberItem to list
                        // and create a new MemberItem and assign it as last MemberItem
                        memberItemList.add((lastMemberItem as MemberItem))
                        lastMemberItem = MemberItem(currentCharacterMemberType, character)
                    }
                }
            }
        }
    }

    fun getCharacterType(@NonNull character: Char): MemberType {
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

    fun printElements() {
        if (memberItemList.isNotEmpty()) {
            for (memberItem in memberItemList) {
                print("${memberItem.memberString} $ ")
            }
        }
        println()
    }

    fun clear() {
        lastMemberItem = null
        memberItemList.clear()
    }


    fun compute() {
        lastMemberItem?.let {
            if (lastMemberItem!!.memberType == MemberType.NUMBER) {
                memberItemList.add(it)
            }
        }
    }

    override fun toString(): String {
        return memberItemList.toString()
    }

}



