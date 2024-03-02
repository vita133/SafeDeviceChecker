package com.example.safedevicechecker.data

import com.google.firebase.database.PropertyName

data class FirebaseDevice(
    @get:PropertyName("Device_type") val deviceType: String? = null,
    @get:PropertyName("Device_brand") val deviceBrand: String? = null,
    @get:PropertyName("Device_model") val deviceModel: String? = null,
    val video: Boolean = false,
    val wiFi: Boolean = false,
    @get:PropertyName("2,4GHz") val twoFourGHz: Boolean = false,
    val fiveGHz: Boolean = false,
    @get:PropertyName("Security_Protocol") val securityProtocol: String? = null,
    @get:PropertyName("Privacy_Shutter") val privacyShutter: Boolean = false,
    val encryption: String? = null,
    @get:PropertyName("Device_is_secure") val deviceIsSecure: Boolean? = null,
    @get:PropertyName("Device_info_link") val deviceInfoLink: String? = null,
    val comments: String? = null
) {
    @JvmOverloads
    constructor() : this(
        null,
        null,
        null,
        false,
        false,
        false,
        false,
        null,
        false,
        null,
        null,
        null
    )
}
