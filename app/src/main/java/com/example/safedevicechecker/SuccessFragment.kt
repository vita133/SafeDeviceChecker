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
import com.example.safedevicechecker.data.FirebaseDevice
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SuccessFragment(private val deviceKey: String? ): Fragment() {
    private lateinit var imageViewBack: ImageView
    private lateinit var textViewSeeMore: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_success, container, false)
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


        imageViewBack = view.findViewById(R.id.imageView_back) ?: ImageView(context)

        imageViewBack.setOnClickListener {
            val searchFragment = SearchFragment()
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.container, searchFragment)
                ?.commit()
        }
        return view
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
        deviceWiFi?.text = if (device.wiFi == true) "Supported" else if (device.wiFi == false) "Not Supported" else "N/A"

        val wifiSupportText = StringBuilder()
        if (device.twoFourGHz == true) {
            wifiSupportText.append("2.4GHz")
        }
        if (device.fiveGHz == true) {
            if (wifiSupportText.isNotEmpty()) {
                wifiSupportText.append(", ")
            }
            wifiSupportText.append("5GHz")
        }
        deviceSupport?.text = if (wifiSupportText.isEmpty()) "Not Supported" else wifiSupportText.toString()
        deviceEncryption?.text = device.encryption ?: "N/A"
        deviceSecurityProtocol?.text = device.securityProtocol ?: "N/A"
        devicePrivacyShutter?.text = if (device.privacyShutter == true) "Supported" else
            if (device.privacyShutter == false) "Not Supported" else "N/A"
        deviceVideo?.text = if (device.video == true) "Supported" else
            if (device.video == false) "Not Supported" else "N/A"
    }
}