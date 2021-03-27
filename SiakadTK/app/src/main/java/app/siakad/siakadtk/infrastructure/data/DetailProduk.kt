package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailProduk (
    var jumlah: Int = 0,
    var harga: Int = 0
): Parcelable