package com.vau.biometric.vau_biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt

/** for inject dependencies purpose */
class Injector {
    companion object {

        /** provide biometric manager */
        fun provideBiometricManager(context: Context): BiometricManager {
            return BiometricManager.from(context)
        }
    }
}