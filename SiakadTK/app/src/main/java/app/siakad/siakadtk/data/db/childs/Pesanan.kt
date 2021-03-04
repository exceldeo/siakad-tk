package app.siakad.siakadtk.data.db.childs

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pesanan(
    @get:Exclude
    var pesananId: String? = null,
    var detailPesananId: Array<String>? = null,
    var userId: String? = null,
    var tanggalPesan: String? = null,
    var statusPesan: String? = null
): Parcelable