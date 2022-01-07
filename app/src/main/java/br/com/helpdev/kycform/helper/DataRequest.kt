package br.com.helpdev.kycform.helper

import com.google.gson.annotations.SerializedName

data class DataRequest(
    @SerializedName("sender_no") val sender_no: String?,
    @SerializedName("body") val body: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("dob") val dob: String?,
    @SerializedName("contact") val contact: String?
)

