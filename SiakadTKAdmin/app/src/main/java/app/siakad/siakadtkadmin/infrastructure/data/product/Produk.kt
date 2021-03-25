package app.siakad.siakadtkadmin.infrastructure.data.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Produk (
    var produkId: String = "",
    var namaProduk: String = "",
    var jenisKelamin: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0
): Parcelable
