package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PesananModel (
    @get:Exclude
    var pesananId: String = "",
    var detailPesanan: ArrayList<DetailKeranjangModel>? = arrayListOf(),
    var userId: String = "",
    var tanggalPesan: String = "",
    var statusPesan: String = ""
): Parcelable