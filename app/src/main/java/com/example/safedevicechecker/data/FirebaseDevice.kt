package com.example.safedevicechecker.data


data class FirebaseDevice(
    val twoFourGHz: Boolean? = null,
    val fiveGHz: Boolean? = null,
    val comments: String? = null,
    val deviceBrand: String? = null,
    val deviceInfoLink: String? = null,
    val deviceIsSecure: Boolean? = null,
    val deviceModel: String? = null,
    val deviceType: String? = null,
    val encryption: String? = null,
    val privacyShutter: Boolean? = null,
    val securityProtocol: String? = null,
    val video: Boolean? = null,
    val wiFi: Boolean? = null,

)
