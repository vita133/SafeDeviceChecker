package com.example.safedevicechecker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val acTextViewType = view.findViewById<AutoCompleteTextView>(R.id.editTextText_type)
        val textViewType = view.findViewById<TextView>(R.id.textView2_hiddenType)
        val acTextViewBrand = view.findViewById<AutoCompleteTextView>(R.id.editTextText_brand)
        val textViewBrand = view.findViewById<TextView>(R.id.textView2_hiddenBrand)
        val acTextViewModel = view.findViewById<AutoCompleteTextView>(R.id.editTextText_model)
        val textViewModel = view.findViewById<TextView>(R.id.textView2_hiddenModel)

        acTextViewType.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                acTextViewType.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewType.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                acTextViewType.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewType.visibility = View.VISIBLE
            }
        })


        acTextViewBrand.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                acTextViewBrand.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewBrand.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                acTextViewBrand.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewBrand.visibility = View.VISIBLE
            }
        })

        acTextViewModel.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                acTextViewModel.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewModel.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                acTextViewModel.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewModel.visibility = View.VISIBLE
            }
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}