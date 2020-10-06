package com.vau.biometric.vau_biometric_example

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.FragmentActivity
import com.vau.biometric.vau_biometric.view.AuthActivity

class ExampleActivity : AuthActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.layout)
        super.onCreate(savedInstanceState)

    }
}