# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\klemeilleur\Programs\Android\AndroidSDK/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# REQUIRED BY: Otto Event Bus by Square
-keepattributes *Annotation*
-keepclassmembers class ** {
    @com.squareup.otto.Subscribe public *;
    @com.squareup.otto.Produce public *;
}

# REQUIRED BY: Google Play Services
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}
-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}

#REQUIRED BY: Realm
-keepnames public class * extends io.realm.RealmObject
-keep class io.realm.** { *; }
-dontwarn javax.**
-dontwarn io.realm.**

#REQUIRED BY: RetroLambda/RxParse
-dontwarn java.lang.invoke.*

#REQUIRED BY: Icepick
-dontwarn icepick.**
-keep class **$$Icicle { *; }
-keepnames class * { @icepick.Icicle *;}

#REQUIRED BY: Vector-Compat
-keep class com.wnafee.vector.** { *; }
