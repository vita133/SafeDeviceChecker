package com.example.safedevicechecker

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.safedevicechecker.data.FirebaseDevice
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SuccessFragment(private val deviceKey: String? ): Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var imageViewBack: ImageView
    private lateinit var textViewSeeMore: TextView

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
        if (deviceKey != null) {
            val database = FirebaseDatabase.getInstance().getReference("devices/$deviceKey")

            database.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d("dvdvdv", "$snapshot")
                    if (snapshot.exists()) {
                        val deviceData = snapshot.getValue(FirebaseDevice::class.java)

                        if (deviceData != null) {
                            displayInfo(deviceData)
                            setOnClickListener(deviceData)
                        } else {
                            Log.w("SuccessFragment", "Device data not found for key: $deviceKey")
                        }
                    } else {
                        Log.w("SuccessFragment", "No device data found in database for key: $deviceKey")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("SuccessFragment", "Failed to read device data: $error")
                }
            })
        } else {
            Log.w("SuccessFragment", "Missing device key in arguments")
        }


        imageViewBack = view?.findViewById(R.id.imageView_back) ?: ImageView(context)

        imageViewBack?.setOnClickListener {
            val searchFragment = SearchFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, searchFragment)
                ?.commit()
        }
        return inflater.inflate(R.layout.fragment_success, container, false)
    }
    private fun setOnClickListener(device: FirebaseDevice) {
        val seeMore = view?.findViewById<TextView>(R.id.textView_seeMore)

        seeMore?.setOnClickListener {
            val url = device.deviceInfoLink
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }

    private fun displayInfo(device: FirebaseDevice) {
        val deviceType = view?.findViewById<TextView>(R.id.textView_typeInfo)
        val deviceBrand = view?.findViewById<TextView>(R.id.textView_brandInfo)
        val deviceModel = view?.findViewById<TextView>(R.id.textView_modelInfo)
        val deviceWiFi = view?.findViewById<TextView>(R.id.textView_wifiInfo)
        val deviceSupport = view?.findViewById<TextView>(R.id.textView_supportInfo)
        val deviceEncryption = view?.findViewById<TextView>(R.id.textView_encryptionInfo)
        val deviceSecurityProtocol = view?.findViewById<TextView>(R.id.textView_protocolInfo)
        val devicePrivacyShutter = view?.findViewById<TextView>(R.id.textView_privacyInfo)
        val deviceVideo = view?.findViewById<TextView>(R.id.textView_videoInfo)

        deviceType?.text = device.deviceType
        deviceBrand?.text = device.deviceBrand
        deviceModel?.text = device.deviceModel
        deviceWiFi?.text = device.wiFi.toString()
        deviceSupport?.text = device.twoFourGHz.toString() + " / " + device.fiveGHz.toString()
        deviceEncryption?.text = device.encryption ?: "N/A"
        deviceSecurityProtocol?.text = device.securityProtocol ?: "N/A"
        devicePrivacyShutter?.text = device.privacyShutter.toString() ?: "N/A"
        deviceVideo?.text = device.video.toString()  ?: "N/A"
    }
}