package com.example.safedevicechecker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ErrorFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var buttonGoBack: Button
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
        val view = inflater.inflate(R.layout.fragment_error, container, false)
        buttonGoBack = view.findViewById(R.id.button_go_back)
        setOnButtonClickListener(view)
        return view
    }

    private fun setOnButtonClickListener(view: View?) {
        buttonGoBack.setOnClickListener {
            val searchFragment = SearchFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, searchFragment)
                ?.commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ErrorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}