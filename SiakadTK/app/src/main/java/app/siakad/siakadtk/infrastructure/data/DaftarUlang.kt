package app.siakad.siakadtk.infrastructure.data

import android.os.Parcelable
import com.google.firebase.database.Exclude
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaftarUlang (
    var dafulId: String = "",
    var namaSiswa: String = "",
    var tanggalLahir: String = "",
    var kelas: String = "",
    var namaWali: String = "",
    var jenisKelamin: String = "",
    var alamat: String = "",
    var noHP: String = "",
    var nominalbayar: Int = 1000000,
    var fotoBayar: String = ""
): Parcelable