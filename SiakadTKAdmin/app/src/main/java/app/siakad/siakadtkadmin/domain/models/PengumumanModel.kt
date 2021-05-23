package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PengumumanModel(
  @get:Exclude
  var pengumumanId: String = "",
  var adminId: String = "",
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
      "adminId" to adminId,
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