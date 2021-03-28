package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KeranjangModel(
    @get:Exclude
    var keranjangId: String = "",
    var basketDetailId: ArrayList<String>? = arrayListOf(),
    var userId: String = "",
    var status: String = ""
): Parcelable