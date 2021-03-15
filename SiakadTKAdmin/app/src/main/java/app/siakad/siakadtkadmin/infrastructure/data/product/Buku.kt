package app.siakad.siakadtkadmin.infrastructure.data.product

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Buku (
    var produkId: String = "",
    var namaProduk: String = "",
    var fotoProduk: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0
): Parcelable