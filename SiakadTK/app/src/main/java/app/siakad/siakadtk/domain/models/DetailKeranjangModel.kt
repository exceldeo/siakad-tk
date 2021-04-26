package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailKeranjangModel (
    @get:Exclude
    var produkId: String = "",
    var nama: String = "",
    var gambar: String = "",
    var ukuran: String = "",
    var jumlah: Int = 0,
    var harga: Int = 0,
    var status: Boolean = true
): Parcelable