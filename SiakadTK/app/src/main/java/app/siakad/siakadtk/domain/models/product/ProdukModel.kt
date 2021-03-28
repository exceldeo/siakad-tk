package app.siakad.siakadtk.domain.models.product

import android.os.Parcelable
import app.siakad.siakadtk.domain.models.product.DetailSeragamModel
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
    var detailSeragam: Map<String, DetailSeragamModel>? = null
): Parcelable
