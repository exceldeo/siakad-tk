package app.siakad.siakadtkadmin.domain.models.product

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailSeragamModel (
    var ukuran: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0
): Parcelable