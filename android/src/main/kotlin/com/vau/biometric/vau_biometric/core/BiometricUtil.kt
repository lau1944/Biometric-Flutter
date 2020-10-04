package com.vau.biometric.vau_biometric.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import android.hardware.biometrics.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import android.os.Build
import android.provider.Settings
import androidx.biometric.BiometricManager

import com.vau.biometric.vau_biometric.Injector
import com.vau.biometric.vau_biometric.model.AuthStatus
import io.flutter.BuildConfig

/** Biometric Helper Class */
class BiometricUtil {

    /** Check if auth is register */
    fun checkAuth(context: Context) : AuthStatus {
        val manager: BiometricManager = Injector.provideBiometricManager(context)
        when(manager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                // do something if success
                return AuthStatus.AuthSuccess
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                return AuthStatus.AuthNoHardware
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                return AuthStatus.AuthError
            }
            // only return if device version lower than R
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    // Prompts the user to create credentials that your app accepts.
                    val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                        putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                    }
                    (context as Activity).startActivityForResult(enrollIntent, 1234)
                    AuthStatus.AuthNoEnrolled
                } else {
                    AuthStatus.AuthNoEnrolled
                }
            }
            else -> {
                return AuthStatus.AuthError
            }
        }
    }

    companion object {
        @Volatile private var instance : BiometricUtil ?= null
        fun getInstance() : BiometricUtil
            = instance ?: synchronized(this) {
            return instance ?: BiometricUtil().also { instance = it }
        }
    }
}