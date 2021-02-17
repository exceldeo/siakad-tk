package app.siakad.siakadtkadmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderDetail (
    var foto: String? = null,
    var namaProduk: String? = null,
    var harga: String? = null,
    var status: Int = 2
): Parcelable