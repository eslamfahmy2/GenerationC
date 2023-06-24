package com.testapp.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val model: String = "",
    val plate_number: String = "",
    val brand: String = "",
    val unit_price: Double = 0.0,
    val currency: String = "",
    val color: String = "",
) : Parcelable


