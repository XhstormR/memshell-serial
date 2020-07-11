-optimizationpasses 5

-adaptclassstrings
-adaptresourcefilenames
-adaptresourcefilecontents
-allowaccessmodification

-obfuscationdictionary proguard-dict.txt
-classobfuscationdictionary proguard-dict.txt
-packageobfuscationdictionary proguard-dict.txt

-repackageclasses com.xhstormr.app

-keepclassmembers enum * {
    public static **[] values();
}

-keepclasseswithmembers class * {
    public static void main(java.lang.String[]);
}

-dontwarn javax.**
-dontwarn org.apache.catalina.**
