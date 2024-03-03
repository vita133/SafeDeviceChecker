package com.example.safedevicechecker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


class ErrorFragment : Fragment() {
    private lateinit var buttonGoBack: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}