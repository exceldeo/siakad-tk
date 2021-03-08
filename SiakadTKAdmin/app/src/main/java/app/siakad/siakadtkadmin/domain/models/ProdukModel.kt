package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import app.siakad.siakadtkadmin.domain.models.DetailProdukModel
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProdukModel (
    @get:Exclude
    var produkId: String = "",
    var adminId: String = "",
    var namaProduk: String = "",
    var jenisKelamin: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var detailProduk: Map<String, DetailProdukModel>? = null
): Parcelable
