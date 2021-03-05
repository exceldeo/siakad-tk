package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SiswaModel (
    var userId: String? = null,
    var nama: String? = null,
    var alamat: String? = null,
    var noHP: String? = null,
    var email: String? = null,
    var passwd: String? = null,
    var fotoBayarAwal: String? = null
): Parcelable