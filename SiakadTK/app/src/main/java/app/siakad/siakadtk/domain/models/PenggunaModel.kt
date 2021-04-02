package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Parcelize
data class PenggunaModel(
    @get:Exclude
    var userId: String = "",
    var nama: String = "",
    var alamat: String = "",
    var noHP: String = "",
    var email: String = "",
    var passwd: String = "",
    var role: String = "",
    var status: Boolean = false
): Parcelable