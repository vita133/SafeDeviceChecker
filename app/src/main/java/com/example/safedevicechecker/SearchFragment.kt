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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
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
    private lateinit var searchButton: Button

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
        searchButton = view.findViewById(R.id.button_submit)


        val textViewType = view.findViewById<TextView>(R.id.textView2_hiddenType)
        val textViewBrand = view.findViewById<TextView>(R.id.textView2_hiddenBrand)
        val textViewModel = view.findViewById<TextView>(R.id.textView2_hiddenModel)

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
                val typeAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, uniqueTypes.toList())
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
                val brandAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, uniqueBrands.toList())
                typeAutoCompleteTextView.setAdapter(brandAdapter)
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
                val modelAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, uniqueModels.toList())
                typeAutoCompleteTextView.setAdapter(modelAdapter)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Error fetching device models: ${error.message}")
            }
        })
    }

    private fun setOnButtonClickListener(view: View) {
        searchButton.setOnClickListener {
            val type = typeAutoCompleteTextView.text.toString()
            val brand = brandAutoCompleteTextView.text.toString()
            val model = modelAutoCompleteTextView.text.toString()
            //get device info from firebase

//            val action = SearchFragmentDirections.actionSearchFragmentToSuccessFragment(twoFourGHz = true, fiveGHz = true, comments = "comments", deviceBrand = "deviceBrand", deviceInfoLink = "deviceInfoLink", deviceIsSecure = true, deviceModel = "deviceModel", deviceType = "deviceType", encryption = "encryption", privacyShutter = true, securityProtocol = "securityProtocol", video = true, wiFi = true)
//            findNavController().navigate(action)
        }
    }



    companion object {
        private const val TAG = "SearchFragment"
    }
}