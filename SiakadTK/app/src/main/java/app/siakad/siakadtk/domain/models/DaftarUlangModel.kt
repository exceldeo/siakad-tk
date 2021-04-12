package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlangModel (
    @get:Exclude
    var dafulId: String = "",
    var userId: String = "",
    var tanggal: String = "",
    var status: String = "",
    var nominalbayar: Int = 1000000,
    var fotoBayar: String = ""
): Parcelable {
    val tanggalLahir: String = ""
}