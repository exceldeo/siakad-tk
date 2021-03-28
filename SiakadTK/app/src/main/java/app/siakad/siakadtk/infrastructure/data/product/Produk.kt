package app.siakad.siakadtk.infrastructure.data.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Produk (
    var produkId: String = "",
    var namaProduk: String = "",
    var jenisKelamin: String = "",
    var fotoProduk: Int = 0,
    var harga: Int = 0,
    var jumlah: Int = 0
): Parcelable
