package app.siakad.siakadtk.data.db.refs

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
@Parcelize
data class User (
    @get:Exclude
    var userId: String? = null,
    var nama: String? = null,
    var alamat: String? = null,
    var noHP: String? = null,
    var email: String? = null,
    var passwd: String? = null,
    var role: Boolean = false
): Parcelable