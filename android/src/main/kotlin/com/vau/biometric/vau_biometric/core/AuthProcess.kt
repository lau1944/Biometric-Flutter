package com.vau.biometric.vau_biometric.core

import android.app.Activity
import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.vau.biometric.vau_biometric.Injector
import com.vau.biometric.vau_biometric.VauBiometricPlugin
import java.util.concurrent.Executor

class AuthProcess {

    companion object {
        private lateinit var executor: Executor

        fun startAuthProcess(context: Context, type: Int, title: String, subTitle: String?, negativeButtonText: String?) {
            getPrompt(context).authenticate(providePromptInfo(
                    type, title, subTitle, negativeButtonText
            ))
        }

        private fun getPrompt(context: Context) : BiometricPrompt{
            executor = ContextCompat.getMainExecutor(context)
            return BiometricPrompt(context as FragmentActivity, executor,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                            VauBiometricPlugin.getChannel().invokeMethod("biometricAuthResult", hashMapOf(
                                    "errorCode" to errorCode,
                                    "errorMessage" to errString,
                                    "result" to "error"
                            ))
                        }

                        override fun onAuthenticationFailed() {
                            VauBiometricPlugin.getChannel().invokeMethod("biometricAuthResult", hashMapOf(
                                    "result" to "fail"
                            ))
                            super.onAuthenticationFailed()
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            VauBiometricPlugin.getChannel().invokeMethod("biometricAuthResult", hashMapOf(
                                    "result" to "success"
                            ))
                            super.onAuthenticationSucceeded(result)
                        }
                    })
        }

        /** provide PromptInfo class */
        private fun providePromptInfo(type: Int, title: String, subTitle: String ?= "", negativeButtonText: String ?= "")
                : BiometricPrompt.PromptInfo {
            return BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setSubtitle(subTitle)
                    .setAllowedAuthenticators(type)
                    .build()
        }
    }

}