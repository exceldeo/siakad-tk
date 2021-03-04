package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ProdukModel (
    var produkId: String? = null,
    var namaProduk: String? = null,
    var jenisKelamin: String? = null,
    var fotoProduk: Int = 0,
    var jumlah: Int = 0,
    var status: String? = null,
    var tanggal: Date? = null,
    var detailProduk: Map<String, DetailProdukModel>? = null
): Parcelable
