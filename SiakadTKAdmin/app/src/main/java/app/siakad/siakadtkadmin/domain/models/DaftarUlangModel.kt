package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlangModel(
  @get:Exclude
  var dafulId: String = "",
  var userId: String = "",
  var tanggal: String = "",
  var fotoBayar: String = "",
  var statusDaful: Boolean = false
) : Parcelable {
  fun toMap(): Map<String, Any?> {
    return mapOf(
      "userId" to userId,
      "tanggal" to tanggal,
      "fotoBayar" to fotoBayar,
      "statusDaful" to statusDaful
    )
  }
}