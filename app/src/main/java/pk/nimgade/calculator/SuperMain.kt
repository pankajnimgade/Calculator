package pk.nimgade.calculator

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.TextView

/**
 * Created by Pankaj Nimgade on 12/10/2017.
 */
open class SuperMain : AppCompatActivity() {
    protected lateinit var historyRecyclerView: RecyclerView
    protected lateinit var inputDisplayTextView: TextView

    protected lateinit var _0_Button: Button
    protected lateinit var _1_Button: Button
    protected lateinit var _2_Button: Button
    protected lateinit var _3_Button: Button
    protected lateinit var _4_Button: Button
    protected lateinit var _5_Button: Button
    protected lateinit var _6_Button: Button
    protected lateinit var _7_Button: Button
    protected lateinit var _8_Button: Button
    protected lateinit var _9_Button: Button
    protected lateinit var _dotPeriod_Button: Button

    protected lateinit var clearButton: Button
    protected lateinit var plusButton: Button
    protected lateinit var minusButton: Button
    protected lateinit var divideButton: Button
    protected lateinit var multiplyButton: Button
}