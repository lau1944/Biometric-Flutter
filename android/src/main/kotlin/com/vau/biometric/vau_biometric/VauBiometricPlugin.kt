package com.vau.biometric.vau_biometric

import android.content.Context
import android.hardware.biometrics.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.annotation.NonNull;
import com.vau.biometric.vau_biometric.core.BiometricUtil
import com.vau.biometric.vau_biometric.model.AuthStatus

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

/** VauBiometricPlugin */
class VauBiometricPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  private lateinit var channel : MethodChannel

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.flutterEngine.dartExecutor, "vau_biometric")
    channel.setMethodCallHandler(this)
    setChannel(channel)
  }

  companion object {
    @Volatile private var channel: MethodChannel ?= null
    private var context: Context ?= null

    @JvmStatic
    fun setChannel(methodChannel: MethodChannel) {
      channel = methodChannel
    }

    @JvmStatic
    fun setContext(activity: Context) {
      context = activity
    }
  }

  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "beginBiometricLogin") {
      var type = call.argument<String>("type")
      when(BiometricUtil.getInstance().checkAuth(context!!)) {
        is AuthStatus.AuthSuccess -> {
          return result.success(hashMapOf(
              "result" to "success"
          ))
        }
        is AuthStatus.AuthError -> {
          return result.error(null,"error", null)
        }
        is AuthStatus.AuthNoHardware -> {
          return result.error(null, "noHardware", null)
        }
        is AuthStatus.AuthNoEnrolled -> {
          return result.error(null, "noEnrolled", null)
        }
      }
    }
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
  }

  override fun onDetachedFromActivity() {

  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {

  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    setContext(binding.activity)
  }

  override fun onDetachedFromActivityForConfigChanges() {

  }
}
