package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import com.google.firebase.database.Exclude

@Parcelize
data class PengumumanModel(
    @get:Exclude
    var pengumumanId: String = "",
    var tipe: String = "",
    var tujuanId: String = "",
    var judul: String = "",
    var keterangan: String = "",
    var tanggal: String = "",
    var confirmable: Boolean = false,
    var confirmableState: Boolean = false
) : Parcelable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "tipe" to tipe,
            "tujuanId" to tujuanId,
            "judul" to judul,
            "keterangan" to keterangan,
            "tanggal" to tanggal,
            "confirmable" to confirmable,
            "confirmableState" to confirmableState
        )
    }
}

