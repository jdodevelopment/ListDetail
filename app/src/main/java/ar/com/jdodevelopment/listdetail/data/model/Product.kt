package ar.com.jdodevelopment.listdetail.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val title: String,
    val description: String,
    val image: String,
): Parcelable