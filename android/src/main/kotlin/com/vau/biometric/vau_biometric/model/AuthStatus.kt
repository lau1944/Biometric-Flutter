package com.vau.biometric.vau_biometric.model

sealed class AuthStatus {
    object AuthSuccess: AuthStatus()
    object AuthNoHardware: AuthStatus()
    object AuthError: AuthStatus()
    object AuthNoEnrolled: AuthStatus()

}