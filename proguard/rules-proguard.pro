# common
-assumenosideeffects class android.util.Log {
    public static *** v(...);
    public static *** d(...);
    public static *** i(...);
    public static *** w(...);
    public static *** e(...);
}
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-keep public class * extends android.support.v7.app.AppCompatActivity

# Android Support
-keep class android.support.** { *; }

# Firebase SDK
-keepattributes Signature
-keepclassmembers class com.todo.data.model.** { *; }
-keep class com.google.firebase.provider.FirebaseInitProvider { *; }
-keep class com.google.firebase.iid.FirebaseInstanceId { *; }

# Butterknife Library
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

# Crashlytics SDK
-keep class com.crashlytics.** { *; }
-keep class com.crashlytics.android.**
-keepattributes SourceFile, LineNumberTable, *Annotation*

# Google Services
-keep public class com.google.android.gms.* { public *; }
-dontwarn com.google.android.gms.**
-keep class * extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable { public static final *** NULL; }
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * { @com.google.android.gms.common.annotation.KeepName *;}
-keepnames class * implements android.os.Parcelable { public static final ** CREATOR; }
