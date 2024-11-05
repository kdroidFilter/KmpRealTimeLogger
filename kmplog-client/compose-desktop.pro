# Désactiver l'obfuscation et les optimisations
-dontobfuscate
-dontoptimize
-dontpreverify

-dontwarn kotlinx.**

-keep class org.jetbrains.skia.** { *; }
-keep class org.jetbrains.skiko.** { *; }

-keep class com.kdroid.kmplog.*TokenMarker { *; }
-dontnote com.kdroid.kmplog.*TokenMarker

-keep class org.fife.** { *; }
-dontnote org.fife.**

-keep class com.jetbrains.JBR* { *; }
-dontnote com.jetbrains.JBR*

-keep class com.sun.jna** { *; }
-dontnote com.sun.jna**

-keep class androidx.compose.ui.input.key.KeyEvent_desktopKt { *; }
-dontnote androidx.compose.ui.input.key.KeyEvent_desktopKt

-keep class androidx.compose.ui.input.key.KeyEvent_skikoKt { *; }
-dontnote androidx.compose.ui.input.key.KeyEvent_skikoKt
-dontwarn androidx.compose.ui.input.key.KeyEvent_skikoKt

-dontnote org.jetbrains.jewel.intui.markdown.standalone.styling.extensions.**

-keepclassmembers class * { public <init>(...); }