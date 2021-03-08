package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPesanan (
    var detialPesananId: String = "",
    var namaProduk: String = "",
    var harga: Int = 0
): Parcelable