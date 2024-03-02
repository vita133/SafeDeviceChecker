package com.example.safedevicechecker.data

data class Device(
    val deviceType: String? = null,
    val deviceBrand: String? = null,
    val deviceModel: String? = null,
    val video: Boolean = false,
    val wiFi: Boolean = false,
    val twoFourGHz: Boolean = false,
    val fiveGHz: Boolean = false,
    val securityProtocol: String? = null,
    val privacyShutter: Boolean = false,
    val encryption: String? = null,
    val deviceIsSecure: Boolean? = null,
    val deviceInfoLink: String? = null,
    val comments: String? = null
)
