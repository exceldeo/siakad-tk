package app.siakad.siakadtkadmin.infrastructure.data

import android.os.Parcelable
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
    var nominalbayar: Int = 1000000,
    var fotoBayar: String = ""
): Parcelable