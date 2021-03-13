package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPesanan (
    var detialPesananId: String = "",
    var namaProduk: String = "",
    var harga: Int = 0
): Parcelable