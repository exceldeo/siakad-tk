package app.siakad.siakadtkadmin.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductList (
    var foto: String? = null,
    var namaProduk: String? = null,
    var jumlahStok: Int = 0
): Parcelable
