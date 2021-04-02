package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import app.siakad.siakadtk.infrastructure.data.DetailKeranjang
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KeranjangModel(
    @get:Exclude
    var keranjangId: String = "",
    var basketDetailId: ArrayList<DetailKeranjang>? = arrayListOf(),
    var userId: String = ""
): Parcelable