package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailPesananModel (
    var detialPesananId: String? = null,
    var namaProduk: String? = null,
    var harga: Int = 0
): Parcelable