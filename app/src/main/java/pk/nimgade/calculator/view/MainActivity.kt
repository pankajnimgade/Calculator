package pk.nimgade.calculator.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.app.DialogFragment
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.LinearInterpolator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import pk.nimgade.calculator.R
import pk.nimgade.calculator.SuperMain
import pk.nimgade.calculator.application.StartUp
import pk.nimgade.calculator.presenter.IMainActivityPresenter
import pk.nimgade.calculator.view.about.AboutFragment
import javax.inject.Inject


class MainActivity : SuperMain(), IMainActivityView {

    private val TAG = "MainActivity"

    @Inject
    lateinit var presenter: IMainActivityPresenter

    private var lastCharacter: Char? = null

    private lateinit var snackBar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        snackBar = Snackbar.make(findViewById(R.id.MainActivity_root_layout_CoordinatorLayout), "",
                Snackbar.LENGTH_SHORT)
        initializeUI()
    }

    private fun initializeUI() {
        historyRecyclerView = findViewById(R.id.MainActivity_history_recyclerView)
        inputEquationDisplayHorizontalScrollView = findViewById(R.id.MainActivity_InputEquation_HorizontalScrollView)
        inputEquationDisplayTextView = findViewById(R.id.MainActivity_InputEquation_textView)
        inputEquationDisplayTextView.isSelected = true
        inputOutputDisplayHorizontalScrollView = findViewById(R.id.MainActivity_inputOutputDisplay_HorizontalScrollView)
        inputOutputDisplayTextView = findViewById(R.id.MainActivity_inputOutputDisplay_textView)
        inputOutputDisplayTextView.isSelected = true

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

        clearButton.setOnLongClickListener({
            presenter.clearAll()
            inputEquationDisplayTextView.text = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                circularReveal()
            }
            true
        })

        if (presenter == null) Log.d(TAG, ":presenter is null ") else Log.d(TAG, ":presenter ${presenter.javaClass}")
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun circularReveal() {
        val view = findViewById<View>(R.id.MainActivity_reveal_background)
        val centerX = view.right
        val centerY = view.bottom
        val startRadius = 0
        val endRadius = Math.hypot(view.width.toDouble(), view.height.toDouble()).toInt()

        val anim = ViewAnimationUtils.createCircularReveal(view, centerX, centerY,
                startRadius.toFloat(), endRadius.toFloat())
        anim.duration = 500
        anim.interpolator = LinearInterpolator()
        view.visibility = View.VISIBLE
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.visibility = View.INVISIBLE
            }
        })
        anim.start()
    }

    override fun onResume() {
        super.onResume()
        presenter.setView(this)
    }

    fun inputCollector(view: View) {
        var collectChar = true
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
                collectChar = false
                Log.d(TAG, "Do nothing to clear here: ")
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
        }
        if (collectChar) {
            presenter.inputCharacter(lastCharacter.toString())
        }
    }

    fun clearEquation(view: View) {
        Log.d(TAG, ": Clear equation")
        presenter.deleteLastCharacter()
    }

    fun computeEquals(view: View) {
        presenter.compute()
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
            R.id.MainActivity_menu_about -> {
                val aboutFragment: DialogFragment = AboutFragment.newInstance()
                aboutFragment.show(fragmentManager.beginTransaction(), "About-Dialog")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getCharacter(): Char {
        return lastCharacter!!.toChar()
    }

    override fun deleteLastCharacter() {
        Log.d(TAG, ": deleteLastCharacter()")
        this.presenter.deleteLastCharacter()
    }

    override fun setInputEquationTextData(inputData: String?) {
        inputEquationDisplayTextView.text = inputData
        inputEquationDisplayHorizontalScrollView.post {
            inputEquationDisplayHorizontalScrollView
                    .fullScroll(View.FOCUS_RIGHT)
        }
    }

    /**
     * Use this for the equation
     * @param inputData for the equation
     * */
    override fun setInputData(inputData: String?) {
        inputOutputDisplayTextView.text = inputData
        inputOutputDisplayHorizontalScrollView.post {
            inputOutputDisplayHorizontalScrollView
                    .fullScroll(View.FOCUS_RIGHT)
        }
    }

    /**
     * Use this for output of the equation or for equation itself until computation starts
     * @param outputResult output of the equation or for equation itself until computation starts
     * */
    override fun setOutputResult(outputResult: String?) {
        Log.d(TAG, "setOutputResult(): $outputResult")
        inputOutputDisplayTextView.text = outputResult
        inputOutputDisplayHorizontalScrollView.post {
            inputOutputDisplayHorizontalScrollView.fullScroll(View.FOCUS_RIGHT)
        }

    }

    override fun showErrorMessage(errorMessage: String?) {
        Log.d(TAG, "$errorMessage: $errorMessage")
        inputOutputDisplayTextView.text = "$errorMessage"
        snackBar.setText("$errorMessage").show()
        inputOutputDisplayHorizontalScrollView.post {
            inputOutputDisplayHorizontalScrollView.fullScroll(View.FOCUS_RIGHT)
        }
    }

    override fun lastComputationEquation(lastComputationEquation: String?) {

    }
}
