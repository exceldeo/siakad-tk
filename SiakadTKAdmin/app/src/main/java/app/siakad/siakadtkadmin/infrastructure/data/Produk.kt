package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Produk (
    var produkId: String? = null,
    var namaProduk: String? = null,
    var jenisKelamin: String? = null,
    var fotoProduk: String? = null,
    var jumlah: Int = 0,
    var detailProduk: Map<String, DetailProduk>? = null
): Parcelable
