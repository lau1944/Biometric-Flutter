import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:vau_biometric/vau_biometric.dart';

void main() {
  const MethodChannel channel = MethodChannel('vau_biometric');

  TestWidgetsFlutterBinding.ensureInitialized();

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    expect(await VauBiometric.platformVersion, '42');
  });
}
