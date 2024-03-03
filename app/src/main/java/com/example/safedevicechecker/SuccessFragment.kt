package com.example.safedevicechecker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.safedevicechecker.data.FirebaseDevice

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SuccessFragment : Fragment() {
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

        val arguments = arguments
        //get all arguments
        if (arguments != null) {
            val device = FirebaseDevice(
                arguments.getBoolean("twoFourGHz"),
                arguments.getBoolean("fiveGHz"),
                arguments.getString("comments"),
                arguments.getString("deviceBrand"),
                arguments.getString("deviceInfoLink"),
                arguments.getBoolean("deviceIsSecure"),
                arguments.getString("deviceModel"),
                arguments.getString("deviceType"),
                arguments.getString("encryption"),
                arguments.getBoolean("privacyShutter"),
                arguments.getString("securityProtocol"),
                arguments.getBoolean("video"),
                arguments.getBoolean("wiFi")
            )
            displayInfo(device)
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success, container, false)
    }

    private fun displayInfo(device: FirebaseDevice) {
        //display the device information
        val deviceType = view?.findViewById<TextView>(R.id.textView_typeInfo)
        val deviceBrand = view?.findViewById<TextView>(R.id.textView_brandInfo)
        val deviceModel = view?.findViewById<TextView>(R.id.textView_modelInfo)
        val deviceWiFi = view?.findViewById<TextView>(R.id.textView_wifiInfo)
        val deviceSupport = view?.findViewById<TextView>(R.id.textView_support)
        val deviceEncryption = view?.findViewById<TextView>(R.id.textView_encryption)
        val deviceSecurityProtocol = view?.findViewById<TextView>(R.id.textView_protocol)
        val devicePrivacyShutter = view?.findViewById<TextView>(R.id.textView_privacy)
        val deviceVideo = view?.findViewById<TextView>(R.id.textView_video)

        deviceType?.text = device.deviceType
        deviceBrand?.text = device.deviceBrand
        deviceModel?.text = device.deviceModel
        deviceWiFi?.text = device.wiFi.toString()
        deviceSupport?.text = device.twoFourGHz.toString() + " / " + device.fiveGHz.toString()
        deviceEncryption?.text = device.encryption
        deviceSecurityProtocol?.text = device.securityProtocol
        devicePrivacyShutter?.text = device.privacyShutter.toString()
        deviceVideo?.text = device.video.toString()
    }


}