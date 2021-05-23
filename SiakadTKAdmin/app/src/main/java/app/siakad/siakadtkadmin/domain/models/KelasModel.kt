package app.siakad.siakadtkadmin.domain.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class KelasModel(
    @get:Exclude
    var kelasId: String = "",
    var namaKelas: String = "",
    var tahunMulai: Int = 2020,
    var tahunSelesai: Int = 2021,
    var daftarSiswa: ArrayList<String> = arrayListOf()
) : Parcelable {
  override fun toString(): String {
    return namaKelas
  }

  fun pairNameId(): Map<String, String> {
    return mapOf(namaKelas to kelasId)
  }

  fun toMap(): Map<String, Any?> {
    return mapOf(
      "namaKelas" to namaKelas,
      "tahunMulai" to tahunMulai,
      "tahunSelesai" to tahunSelesai,
      "daftarSiswa" to daftarSiswa
    )
  }
}
