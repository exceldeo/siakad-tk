package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PenggunaModel (
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