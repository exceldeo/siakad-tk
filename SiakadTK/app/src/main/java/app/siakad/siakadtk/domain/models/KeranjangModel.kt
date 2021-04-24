package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import app.siakad.siakadtk.domain.models.product.DetailSeragamModel
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KeranjangModel(
    @get:Exclude
    var keranjangId: String = "",
    var detailKeranjang: ArrayList<DetailKeranjangModel> = arrayListOf(),
    var userId: String = ""
): Parcelable