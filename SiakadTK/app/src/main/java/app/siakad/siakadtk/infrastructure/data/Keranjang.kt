package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import app.siakad.siakadtk.domain.models.DetailKeranjangProdukModel
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Keranjang(
    @get:Exclude
    var keranjangId: String = "",
    var basketDetailId: ArrayList<DetailKeranjangProdukModel>? = arrayListOf()
): Parcelable