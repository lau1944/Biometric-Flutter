import 'package:flutter/material.dart';
import 'dart:async';

import 'package:vau_biometric/vau_biometric.dart';


void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  

  @override
  void initState() {
    super.initState();
    VauBiometric.setBiometricHandler();
  }

  Future<void> startAuth() async {
    await VauBiometric.biometriLogin(type: BIOMETRIC_STRONG,
      title: 'Login', subtitle: 'click to login in', negativeButtonText: 'no',
      successCallBack: (code, message) {
        print(message);
      },
      errorCallBack: (code, message) {
        print(message);
      },
      failCallBack: (code, message) {
        print(message);
      });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: FlatButton(
            onPressed: () {
              startAuth();
            },
            child: Text('Start'),
            ),
        ),
      ),
    );
  }
}
