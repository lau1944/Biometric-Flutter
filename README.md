# Biometric Authentication 🔒 🔑 🔓
This is a biometric authentication flutter plugin for android 

[Link](https://pub.dev/packages/vau_biometric) on pub.dev 

An implementation of [the newest biometric android library](https://developer.android.com/training/sign-in/biometric-auth#blog-posts).

### Sample test on 1+ 6t

<p align="center">
  <img width=255, height=515, src="https://github.com/lau1944/Biometric-Flutter/blob/main/demo.gif" />
</p>

## Usage 🖊️
* `type` type of authenticators
only 3 types :
BIOMETRIC_STRONG
BIOMETRIC_WEAK
DEVICE_CREDENTIAL
* `title` title on authentication process
* `subtitle` subtitle on authentication process, default to ''
* `negativeButtonText` similar to use other authentiation method, default to ''


After called this method, platform would check authentication requirement and return `BioStatus` to indicator if success or not.


if `success`, platform would start authentication immediately.


`BioCallBack` is a callback function the result of authentication itself, if the checking status is not `success`, callback would not be called.
```
VauBiometric.biometricLogin(type: BIOMETRIC_STRONG,
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
```

## One more important thing  🤦‍♂️
There is one big drawback using this newest biometric library -> It needs a reference of `FragmentActivity` 😫

We would start a `AuthActivity` which extends FragmentActivity after check success 

If you want a custom view on this activity 

create a new layout xml file named `biometric_layout` -> It has to be this name 😁😁😁

### That is It !!! 🙃 ###

It is still unstable for now, because it is so hard to test biometric authentication on each android device 🤕

so if you have any problem using this, please put on issues 
