package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Buku (
    var produkId: String = "",
    var namaProduk: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0
): Parcelable