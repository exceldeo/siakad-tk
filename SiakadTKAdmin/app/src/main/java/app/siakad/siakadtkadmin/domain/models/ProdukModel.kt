package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import app.siakad.siakadtkadmin.domain.models.DetailProdukModel
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProdukModel (
    @get:Exclude
    var produkId: String? = null,
    var adminId: String? = null,
    var namaProduk: String? = null,
    var jenisKelamin: String? = null,
    var fotoProduk: String? = null,
    var jumlah: Int = 0,
    var detailProduk: Map<String, DetailProdukModel>? = null
): Parcelable
