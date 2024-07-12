# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Keep all classes and methods in AndroidX Core KTX library
-keep class androidx.core.** { *; }

# Keep all classes and methods in AndroidX AppCompat library
-keep class androidx.appcompat.** { *; }

# Keep all classes and methods in Material Components library
-keep class com.google.android.material.** { *; }

# Keep all classes and methods in JUnit library for testing
-keep class org.junit.** { *; }

# Keep all classes and methods in AndroidX JUnit library for instrumentation tests
-keep class androidx.test.** { *; }

# Keep all classes and methods in AndroidX Espresso library for UI testing
-keep class androidx.test.espresso.** { *; }
