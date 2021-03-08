package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPesanan (
    var detialPesananId: String? = null,
    var namaProduk: String? = null,
    var harga: Int = 0
): Parcelable