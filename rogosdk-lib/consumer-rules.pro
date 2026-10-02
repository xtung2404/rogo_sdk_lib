# Rules nay tu dong di kem AAR sang moi app consume rogo_sdk_lib.

# --- Giu code Raio SDK ---
# SDK dung Gson (reflection) va Room (entity/dao generated) nen khong cho R8 cua app doi ten.
-keep class com.example.ro_sdk.** { *; }
-keep interface com.example.ro_sdk.** { *; }
-keepattributes Signature, InnerClasses, EnclosingMethod, *Annotation*, Exceptions
