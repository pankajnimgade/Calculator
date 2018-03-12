package pk.nimgade.calculator.view.about


import android.app.DialogFragment
import android.app.Fragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import pk.nimgade.calculator.R


/**
 * A simple [Fragment] subclass.
 */
class AboutFragment : DialogFragment() {

    private lateinit var closeButton: Button

    companion object {
        fun newInstance(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        dialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        closeButton = view.findViewById(R.id.AboutFragment_close_button)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        closeButton.setOnClickListener { dialog.dismiss() }
    }

}// Required empty public constructor
