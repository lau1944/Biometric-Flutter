package com.vau.biometric.vau_biometric

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt

/** for inject dependencies purpose */
class Injector {
    companion object {
        /** provide PromptInfo class */
        fun providePromptInfo(title: String, subTitle: String ?= "", negativeButtonText: String ?= "")
                : BiometricPrompt.PromptInfo {
            return BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setSubtitle(subTitle)
                    .setNegativeButtonText(negativeButtonText.toString())
                    .build()
        }

        /** provide biometric manager */
        fun provideBiometricManager(context: Context): BiometricManager {
            return BiometricManager.from(context)
        }
    }
}