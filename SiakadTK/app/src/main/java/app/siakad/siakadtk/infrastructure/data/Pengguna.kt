package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pengguna (
    var userId: String = "",
    var nama: String = "",
    var email: String = "",
    var passwd: String = "",
    var status: Boolean = false
): Parcelable {
    override fun toString(): String = nama
}