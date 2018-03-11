package pk.nimgade.calculator.model

/**
 * Created by Pankaj Nimgade on 12/20/2017.
 */
enum class MemberType {

    NUMBER, DECIMAL, ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION;


    fun isNumber(): Boolean {
        return return this == NUMBER
    }

    fun isOperator(): Boolean {
        return when (this) {
            ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION -> true
            NUMBER -> false
            DECIMAL -> false
        }
    }

    fun isDecimal(): Boolean {
        return return this == DECIMAL
    }

}