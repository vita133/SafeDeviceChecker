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
import android.widget.Button
import com.example.safedevicechecker.data.FirebaseDevice
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SearchFragment : Fragment() {

    private lateinit var typeAutoCompleteTextView: AutoCompleteTextView
    private lateinit var brandAutoCompleteTextView: AutoCompleteTextView
    private lateinit var modelAutoCompleteTextView: AutoCompleteTextView
    private lateinit var buttonSubmit: Button
    private lateinit var textViewType: TextView
    private lateinit var textViewBrand: TextView
    private lateinit var textViewModel: TextView

    private lateinit var deviceDB: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        buttonSubmit = view.findViewById(R.id.button_submit)

        textViewType = view.findViewById<TextView>(R.id.textView2_hiddenType)
        textViewBrand = view.findViewById<TextView>(R.id.textView2_hiddenBrand)
        textViewModel = view.findViewById<TextView>(R.id.textView2_hiddenModel)

        typeAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                typeAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewType.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                typeAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewType.visibility = View.VISIBLE
            }
        })


        brandAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                brandAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewBrand.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                brandAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewBrand.visibility = View.VISIBLE
            }
        })

        modelAutoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                modelAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewModel.visibility = View.VISIBLE
            }

            override fun afterTextChanged(s: Editable?) {
                modelAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_w)
                textViewModel.visibility = View.VISIBLE
            }
        })

        fetchDeviceTypes()
        fetchDeviceBrands()
        fetchDeviceModels()
        setOnButtonClickListener(view)
        return view
    }

    private fun fetchDeviceTypes() {
        deviceDB.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uniqueTypes = snapshot.children.mapNotNull {
                    it.getValue(FirebaseDevice::class.java)?.deviceType
                }.toSet()
                val typeAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    uniqueTypes.toList()
                )
                typeAutoCompleteTextView.setAdapter(typeAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching device types: ${error.message}")
            }
        })
    }

    private fun fetchDeviceBrands() {
        deviceDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uniqueBrands = snapshot.children.mapNotNull {
                    it.getValue(FirebaseDevice::class.java)?.deviceBrand
                }.toSet()
                val brandAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    uniqueBrands.toList()
                )
                brandAutoCompleteTextView.setAdapter(brandAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching device brands: ${error.message}")
            }
        })
    }

    private fun fetchDeviceModels() {
        deviceDB.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uniqueModels = snapshot.children.mapNotNull {
                    it.getValue(FirebaseDevice::class.java)?.deviceModel
                }.toSet()
                val modelAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    uniqueModels.toList()
                )
                modelAutoCompleteTextView.setAdapter(modelAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching device models: ${error.message}")
            }
        })
    }

    private fun setOnButtonClickListener(view: View) {
        buttonSubmit.setOnClickListener {
                searchDevice()
        }
    }



    companion object {
        private const val TAG = "SearchFragment"
    }

    private fun searchDevice() {
        val selectedType = typeAutoCompleteTextView.text.toString().trim()
        val selectedBrand = brandAutoCompleteTextView.text.toString().trim()
        val selectedModel = modelAutoCompleteTextView.text.toString().trim()

        val query = deviceDB.orderByChild("deviceModel").equalTo(selectedModel)

        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var  key: String? = null
                val deviceData = snapshot.children.firstOrNull { key = it.key
                    it.child("deviceBrand").value == selectedBrand && it.child("deviceType").value == selectedType }?.getValue(FirebaseDevice::class.java)
                if (deviceData != null) {
                    if (deviceData.deviceIsSecure == true) {
                        val successFragment = SuccessFragment(key)
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.container, successFragment)
                            ?.commit()
                    } else  if (deviceData.deviceIsSecure == false){
                        val notSecureFragment = NotSecureFragment(key)
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.container, notSecureFragment)
                            ?.commit()
                    } else {
                        val errorFragment = ErrorFragment()
                        activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.container, errorFragment)
                            ?.commit()
                    }
                } else {
                    typeAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_r)
                    textViewType.setTextColor(resources.getColor(R.color.red))
                    brandAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_r)
                    textViewBrand.setTextColor(resources.getColor(R.color.red))
                    modelAutoCompleteTextView.setBackgroundResource(R.drawable.rounded_corners_edittext_r)
                    textViewModel.setTextColor(resources.getColor(R.color.red))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error searching device: ${error.message}")
            }
        })
    }
}
