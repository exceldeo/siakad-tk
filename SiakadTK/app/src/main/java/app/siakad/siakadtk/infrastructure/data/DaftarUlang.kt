package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlang (
    var dafulId: String = "",
    var userId: String = "",
    var namaSiswa: String = "",
    var kelas: String = "",
    var jenisKelamin: String = "",
    var namaWali: String = "",
    var alamat: String = "",
    var noHP: String = "",
    var thnAjaran : String = "",
    var nominalbayar: Int = 1000000,
    var fotoBayar: String = ""
): Parcelable {
    val tanggalLahir: String = ""
}