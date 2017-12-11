package pk.nimgade.calculator

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var inputDisplayTextView: TextView


    private lateinit var _0_Button: Button
    private lateinit var _1_Button: Button
    private lateinit var _2_Button: Button
    private lateinit var _3_Button: Button
    private lateinit var _4_Button: Button
    private lateinit var _5_Button: Button
    private lateinit var _6_Button: Button
    private lateinit var _7_Button: Button
    private lateinit var _8_Button: Button
    private lateinit var _9_Button: Button

    private lateinit var clearButton: Button
    private lateinit var plusButton: Button
    private lateinit var minusButton: Button
    private lateinit var divideButton: Button
    private lateinit var multiplyButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
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
}
