package pk.nimgade.calculator.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pk.nimgade.calculator.R
import pk.nimgade.calculator.StartUp
import pk.nimgade.calculator.SuperMain
import pk.nimgade.calculator.presenter.IMainActivityPresenter
import javax.inject.Inject

class MainActivity : SuperMain(), IMainActivityView {

    val TAG = "MainActivity"


    @Inject
    lateinit var presenter: IMainActivityPresenter

    private var lastCharacter: Char? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        initializeUI()
    }

    private fun initializeUI() {
        historyRecyclerView = findViewById(R.id.MainActivity_history_recyclerView)
        inputDisplayTextView = findViewById(R.id.MainActivity_input_textView)

        _0_Button = findViewById(R.id.MainActivity_0_button)
        _1_Button = findViewById(R.id.MainActivity_1_button)
        _2_Button = findViewById(R.id.MainActivity_2_button)
        _3_Button = findViewById(R.id.MainActivity_3_button)
        _4_Button = findViewById(R.id.MainActivity_4_button)
        _5_Button = findViewById(R.id.MainActivity_5_button)
        _6_Button = findViewById(R.id.MainActivity_6_button)
        _7_Button = findViewById(R.id.MainActivity_7_button)
        _8_Button = findViewById(R.id.MainActivity_8_button)
        _9_Button = findViewById(R.id.MainActivity_9_button)
        _dotPeriod_Button = findViewById(R.id.MainActivity_dot_period_button)

        clearButton = findViewById(R.id.MainActivity_delete_clear_button)
        plusButton = findViewById(R.id.MainActivity_plus_button)
        minusButton = findViewById(R.id.MainActivity_minus_button)
        divideButton = findViewById(R.id.MainActivity_divide_button)
        multiplyButton = findViewById(R.id.MainActivity_multiply_button)

        (application as StartUp).component.inject(this)

        if (presenter == null) Log.d(TAG, ":presenter is null ") else Log.d(TAG, ":presenter ${presenter.javaClass}")
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    fun inputCollector(view: View) {
        when (view.id) {
            R.id.MainActivity_0_button -> {
                lastCharacter = '0'
            }
            R.id.MainActivity_1_button -> {
                lastCharacter = '1'
            }
            R.id.MainActivity_2_button -> {
                lastCharacter = '2'
            }
            R.id.MainActivity_3_button -> {
                lastCharacter = '3'
            }
            R.id.MainActivity_4_button -> {
                lastCharacter = '4'
            }
            R.id.MainActivity_5_button -> {
                lastCharacter = '5'
            }
            R.id.MainActivity_6_button -> {
                lastCharacter = '6'
            }
            R.id.MainActivity_7_button -> {
                lastCharacter = '7'
            }
            R.id.MainActivity_8_button -> {
                lastCharacter = '8'
            }
            R.id.MainActivity_9_button -> {
                lastCharacter = '9'
            }
            R.id.MainActivity_dot_period_button -> {
                lastCharacter = '.'
            }
            R.id.MainActivity_delete_clear_button -> {
                lastCharacter = '\u0127'
            }
            R.id.MainActivity_plus_button -> {
                lastCharacter = '+'
            }
            R.id.MainActivity_minus_button -> {
                lastCharacter = '-'
            }
            R.id.MainActivity_divide_button -> {
                lastCharacter = '/'
            }
            R.id.MainActivity_multiply_button -> {
                lastCharacter = '*'
            }
            R.id.MainActivity_equals_button -> {
                lastCharacter = '='
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getCharacter(): Char {
        return lastCharacter!!.toChar()
    }

    override fun setInputData(inputData: String?) {

    }

    override fun setOutputResult(outputResult: String?) {

    }

    override fun showErrorMessage(errorMessage: String?) {

    }

    override fun lastComputationEquation(lastComputationEquation: String?) {

    }
}
