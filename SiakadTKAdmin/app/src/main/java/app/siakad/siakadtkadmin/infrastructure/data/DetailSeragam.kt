package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailSeragam (
    var ukuran: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0
): Parcelable