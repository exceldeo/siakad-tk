package app.siakad.siakadtk.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PesananModel(
    @get:Exclude
    var pesananId: String = "",
    var detailPesanan: ArrayList<DetailKeranjangModel>? = arrayListOf(),
    var userId: String = "",
    var tanggalDipesan: String = "",
    var tanggalDiproses: String = "",
    var tanggalSelesai: String = "",
    var statusPesan: String = "",
    var fotoBayar: String = "",
): Parcelable {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "detailPesanan" to detailPesanan,
            "userId" to userId,
            "tanggalDipesan" to tanggalDipesan,
            "tanggalDiproses" to tanggalDiproses,
            "tanggalSelesai" to tanggalSelesai,
            "statusPesan" to statusPesan,
            "fotoBayar" to fotoBayar
        )
    }
}