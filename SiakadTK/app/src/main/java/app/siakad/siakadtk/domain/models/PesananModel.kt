package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PesananModel(
    @get:Exclude
    var pesananId: String = "",
    var detailPesananProdukId: ArrayList<DetailPesananProdukModel>? = arrayListOf(),
    var userId: String = "",
    var tanggalPesan: String = "",
    var statusPesan: String = ""
): Parcelable