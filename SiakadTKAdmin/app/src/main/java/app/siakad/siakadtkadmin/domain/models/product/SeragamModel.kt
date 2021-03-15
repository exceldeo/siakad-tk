package app.siakad.siakadtkadmin.domain.models.product

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SeragamModel (
    @get:Exclude
    var produkId: String = "",
    var adminId: String = "",
    var namaProduk: String = "",
    var jenisKelamin: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var detailSeragam: ArrayList<DetailSeragamModel> = arrayListOf()
): Parcelable
