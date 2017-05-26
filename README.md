# RapidPRO Android SDK

[![](https://jitpack.io/v/ilhasoft/rapidpro-android-sdk.svg?style=flat-square)](https://jitpack.io/#ilhasoft/rapidpro-android-sdk)

RapidPRO Android SDK is a client library for [RapidPRO](http://rapidpro.github.io/rapidpro) platform that can be used inside Android apps to enable users receive and send messages through Firebase Cloud Messaging channel.

## Download

Step 1: Add it in your root build.gradle at the end of repositories:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

Step 2: Add the dependency
```
  dependencies {
    compile 'com.github.ilhasoft.rapidpro-android-sdk:fcm-channel:LATEST-VERSION'
  }
```
Latest Version: [![Latest version](https://jitpack.io/v/ilhasoft/rapidpro-android-sdk.svg?style=flat-square)](https://jitpack.io/#ilhasoft/rapidpro-android-sdk)

## Features:

* Interactive messages activity that handles the sending and receiving of messages;
* Notifications of new messages by using Firebase Cloud Messaging;
* Works well with Right-to-left languages;
* Floating chat when users aren't with your app open;

## Sample

<img src="sample.gif" alt="...">
