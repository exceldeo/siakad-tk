package app.siakad.siakadtkadmin.infrastructure.data.product

import android.os.Parcelable
import app.siakad.siakadtkadmin.domain.models.product.DetailSeragamModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Seragam (
    var produkId: String = "",
    var namaProduk: String = "",
    var jenisKelamin: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var detailSeragam: ArrayList<DetailSeragamModel> = arrayListOf()
): Parcelable