
import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

const int BIOMETRIC_STRONG = 15;
const int BIOMETRIC_WEAK = 255;
const int DEVICE_CREDENTIAL = 32768;

/// device biometric check
/// 
/// [success] check successfully, would start processing biometric process
/// [error] unavailable
/// [noErolled] auth service is not enrolled, for example, fingerprint is not registered
/// if android version >= R. then would jump to enrollment page
/// [noHardware] biometric process is not supported by device
enum BioStatus {
  success,
  error,
  noErolled,
  noHardware,
}

/// Biometric process
class VauBiometric {

  static BioCallBack success;
  static BioCallBack error;
  static BioCallBack fail;

  /// channel id
  static const MethodChannel _channel =
      const MethodChannel('vau_biometric');

  /// [type] only 3 type available, 
  /// [BIOMETRIC_STRONG], [BIOMETRIC_WEAK], [DEVICE_CREDENTIAL]
  /// for each specific documentation, 
  /// please go check https://developer.android.com/training/sign-in/biometric-auth#kotlin
  /// [successCallBack] callback after auth process if success
  /// [errorCallBack] callback after auth process if error
  /// [failCallBack] callback after auth process if fail
  static Future<BioStatus> biometriLogin({int type,
    @required String title, String subtitle, String negativeButtonText,
    BioCallBack successCallBack, BioCallBack errorCallBack, BioCallBack failCallBack}) async {
      success = successCallBack;
      error = errorCallBack;
      fail = failCallBack;
      return await _channel.invokeMethod('beginBiometricLogin', {
        'type' : type,
        'title' : title ?? '',
        'subtitle' : subtitle ?? '',
        'negativeButtonText' : negativeButtonText ?? '~'
      });
  }

  static void setBiometricHandler() {
    _channel.setMethodCallHandler((call) {
      if (call.method == 'biometricAuthResult') {
        Map result = call.arguments;
        switch(result['result']) {
          case 'success' : {
            success(0, 'success');
            break;
          }
          case 'error' : {
            error(result['errorCode'], result['errorMessage']);
            break;
          }
          case 'fail' : {
            fail(1, 'fail');
            break;
          }
        }
      }
      return null;
    });
  }

}

typedef void BioCallBack(int code, String message);
