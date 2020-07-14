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

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
}

-keepclasseswithmembers class * {
    public static void main(java.lang.String[]);
}

-keepclasseswithmembernames class org.apache.commons.** {
    <fields>;
}

-keepclasseswithmembers class com.xhstormr.app.*Filter {
    <methods>;
}

-dontwarn javax.**
-dontwarn org.apache.catalina.**

-printusage ../build/usage.txt
-printmapping ../build/mapping.txt
