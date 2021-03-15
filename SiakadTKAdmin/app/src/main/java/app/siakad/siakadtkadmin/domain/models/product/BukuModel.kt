package app.siakad.siakadtkadmin.domain.models.product

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BukuModel (
    @get:Exclude
    var produkId: String = "",
    var adminId: String = "",
    var namaProduk: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0
): Parcelable