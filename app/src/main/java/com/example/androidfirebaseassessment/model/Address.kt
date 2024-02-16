package com.example.androidfirebaseassessment.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Address(
    var city: String? = null,
    var district: String? = null,
    var pincode: String?= null,
    var postOfficeName: String?=null,
    var state: String?=null,
    var isLike: Boolean = false
):Parcelable{
    override fun toString(): String {
       // return pincode.toString()

        return city.toString()
    }
}