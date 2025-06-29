package com.example.wadaihjparty.domain.model

import android.media.Image
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.text.NumberFormat
import java.util.Locale

@Entity(tableName = "cake_table")
@Parcelize
data class Cake(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val imageUrl: String = "",
    var description: String,
    var price: Double
) : Parcelable {

    @IgnoredOnParcel
    val formattedPrice: String
        get() {
            val localeID = Locale("in", "ID")
            val numberFormat = NumberFormat.getCurrencyInstance(localeID)
            numberFormat.maximumFractionDigits = 0
            return numberFormat.format(price)
        }
}