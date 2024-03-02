package com.example.safedevicechecker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.ArrayAdapter
import com.example.safedevicechecker.data.FirebaseDevice
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var typeAutoCompleteTextView: AutoCompleteTextView
    private lateinit var brandAutoCompleteTextView: AutoCompleteTextView
    private lateinit var modelAutoCompleteTextView: AutoCompleteTextView

    private lateinit var deviceDB: DatabaseReference
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

        deviceDB = FirebaseDatabase.getInstance().getReference("devices")

        typeAutoCompleteTextView = view.findViewById(R.id.editTextText_type)
        brandAutoCompleteTextView = view.findViewById(R.id.editTextText_brand)
        modelAutoCompleteTextView = view.findViewById(R.id.editTextText_model)

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

        fetchDeviceTypes()
        fetchDeviceBrands()
        fetchDeviceModels()
        return view
    }

    private fun fetchDeviceTypes() {
        deviceDB.child("devices").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val types = mutableListOf<String>()
                if (snapshot.exists()) {
                    for (deviceSnapshot in snapshot.children) {
                        val deviceData = deviceSnapshot.getValue(FirebaseDevice::class.java)
                        deviceData?.deviceType?.let { types.add(it) }
                    }
                }
                Log.d(TAG, types.toString())
                val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, types)
                typeAutoCompleteTextView.setAdapter(typeAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching device types: ${error.message}")
            }
        })
    }

    private fun fetchDeviceBrands() {
        deviceDB.child("devices").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val brands = mutableListOf<String>()
                if (snapshot.exists()) {
                    for (deviceSnapshot in snapshot.children) {
                        val deviceData = deviceSnapshot.getValue(FirebaseDevice::class.java)
                        deviceData?.deviceBrand?.let { brands.add(it) }
                    }
                }

                val brandAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, brands)
                brandAutoCompleteTextView.setAdapter(brandAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching device brands: ${error.message}")
            }
        })
    }

    private fun fetchDeviceModels() {
        deviceDB.child("devices").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val models = mutableListOf<String>()
                if (snapshot.exists()) {
                    for (deviceSnapshot in snapshot.children) {
                        val deviceData = deviceSnapshot.getValue(FirebaseDevice::class.java)
                        deviceData?.deviceModel?.let { models.add(it) }
                    }
                }

                val modelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, models)
                modelAutoCompleteTextView.setAdapter(modelAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching device models: ${error.message}")
            }
        })
    }

    companion object {
        private const val TAG = "SearchFragment"
    }
}