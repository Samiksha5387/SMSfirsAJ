# SMS Received

Sample of how to intercept a SMS with BroadcastReceiver

Simples steps:

1 - Declare in your manifest: [AndroidManifest.xml](/app/src/main/AndroidManifest.xml)

```xml

<uses-permission android:name="android.permission.RECEIVE_SMS" />

<application[...]<receiver android:name="br.com.helpdev.kycform.receiver.SMSReceiver"
android:exported="true" android:permission="android.permission.BROADCAST_SMS">
<intent-filter android:priority="1000">
    <action android:name="android.provider.Telephony.SMS_RECEIVED" />
</intent-filter>
</receiver></application>
```
2 - Give the permission in your app [MainActivity.kt](/app/src/main/java/br/com/helpdev/smsreceiver/MainActivity.kt)

```kotlin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestSmsPermission()
    }

    private fun requestSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), REQUEST_CODE_SMS_PERMISSION)
        }
    }
```

3 - Implement your receiver: [SMSReceiver.kt](/app/src/main/java/br/com/helpdev/smsreceiver/receiver/SMSReceiver.kt)

```kotlin
class SMSReceiver : BroadcastReceiver() {
    companion object {
        private val TAG by lazy { SMSReceiver::class.java.simpleName }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage -> Log.v(TAG, smsMessage.displayMessageBody) }
        //TODO
    }
}
```
